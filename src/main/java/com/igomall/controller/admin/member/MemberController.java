
package com.igomall.controller.admin.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberAttribute;
import com.igomall.service.UserService;
import com.igomall.service.member.MemberAttributeService;
import com.igomall.service.member.MemberRankService;
import com.igomall.service.member.MemberService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Controller - 会员
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminMemberController")
@RequestMapping("/admin/api/member")
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private UserService userService;
	@Autowired
	private MemberRankService memberRankService;
	@Autowired
	private MemberAttributeService memberAttributeService;

	/**
	 * 检查用户名是否存在
	 */
	@PostMapping("/check_username")
	public boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !memberService.usernameExists(username);
	}

	/**
	 * 检查E-mail是否唯一
	 */
	@PostMapping("/check_email")
	public boolean checkEmail(Long id, String email) {
		return StringUtils.isNotEmpty(email) && memberService.emailUnique(id, email);
	}

	/**
	 * 检查手机是否唯一
	 */
	@PostMapping("/check_mobile")
	public boolean checkMobile(Long id, String mobile) {
		return StringUtils.isNotEmpty(mobile) && memberService.mobileUnique(id, mobile);
	}

	/**
	 * 查看
	 */
	@PostMapping("/view")
	public Member view(Long id) {
		return memberService.find(id);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Member member, Long memberRankId, HttpServletRequest request) {
		member.init();
		member.setEmail(member.getUsername()+"@i-gomall.com");
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		if (memberService.usernameExists(member.getUsername())) {
			return Message.error("用户名已存在");
		}
		if (memberService.emailExists(member.getEmail())) {
			return Message.error("邮箱已存在");
		}
		if (StringUtils.isNotEmpty(member.getMobile()) && memberService.mobileExists(member.getMobile())) {
			return Message.error("手机号已存在");
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
			if (!memberAttributeService.isValid(memberAttribute, values)) {
				return Message.error("参数错误");
			}
			Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
			member.setAttributeValue(memberAttribute, memberAttributeValue);
		}
		memberService.save(member);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(Member.EditView.class)
	public Member edit(Long id) {
		return memberService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(Member member, Long id, Long memberRankId, Boolean unlock, HttpServletRequest request) {
		member.setMemberRank(memberRankService.find(memberRankId));
		member.setEmail(member.getUsername()+"@i-gomall.com");
		if (!isValid(member)) {
			return Message.error("参数错误");
		}
		if (!memberService.emailUnique(id, member.getEmail())) {
			return Message.error("邮箱已存在");
		}
		if (StringUtils.isNotEmpty(member.getMobile()) && !memberService.mobileUnique(id, member.getMobile())) {
			return Message.error("手机号已存在");
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
			if (!memberAttributeService.isValid(memberAttribute, values)) {
				return Message.error("参数错误");
			}
			Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
			member.setAttributeValue(memberAttribute, memberAttributeValue);
		}
		Member pMember = memberService.find(id);
		if (pMember == null) {
			return Message.error("参数错误");
		}
		if (BooleanUtils.isTrue(pMember.getIsLocked()) && BooleanUtils.isTrue(unlock)) {
			userService.unlock(member);
			memberService.update(member, "username", "encodedPassword", "point", "balance", "amount", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs", "couponCodes", "receivers", "reviews", "consultations",
					"productFavorites", "productNotifies", "inMessages", "outMessages", "pointLogs");
		} else {
			memberService.update(member, "username", "encodedPassword", "point", "balance", "amount", "isLocked", "lockDate", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs", "couponCodes", "receivers",
					"reviews", "consultations", "productFavorites", "productNotifies", "inMessages", "outMessages", "pointLogs");
		}
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Member.ListView.class)
	public Page<Member> list(Pageable pageable, String username, String name, String mobile, Integer status, Date beginDate, Date endDate) {
		return memberService.findPage(pageable,username,name,mobile,status,beginDate,endDate);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Member member = memberService.find(id);
				if (member != null && member.getBalance().compareTo(BigDecimal.ZERO) > 0) {
					return Message.error("admin.member.deleteExistDepositNotAllowed", member.getUsername());
				}
			}
			memberService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}