
package com.igomall.controller.admin.member;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.member.MemberRank;
import com.igomall.service.member.MemberRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Controller - 会员等级
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminMemberRankController")
@RequestMapping("/admin/api/member_rank")
public class MemberRankController extends BaseController {

	@Autowired
	private MemberRankService memberRankService;

	/**
	 * 检查消费金额是否唯一
	 */
	@GetMapping("/check_amount")
	public boolean checkAmount(Long id, BigDecimal amount) {
		return amount != null && memberRankService.amountUnique(id, amount);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(MemberRank memberRank) {
		if (!isValid(memberRank)) {
			return Message.error("参数错误");
		}
		if (memberRank.getIsSpecial()) {
			memberRank.setAmount(null);
		} else if (memberRank.getAmount() == null || memberRankService.amountExists(memberRank.getAmount())) {
			return Message.error("参数错误");
		}
		memberRank.setMembers(null);
		memberRankService.save(memberRank);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public MemberRank edit(Long id) {
		return memberRankService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(MemberRank memberRank, Long id) {
		if (!isValid(memberRank)) {
			return Message.error("参数错误");
		}
		MemberRank pMemberRank = memberRankService.find(id);
		if (pMemberRank == null) {
			return Message.error("参数错误");
		}
		if (pMemberRank.getIsDefault()) {
			memberRank.setIsDefault(true);
		}
		if (memberRank.getIsSpecial()) {
			memberRank.setAmount(null);
		} else if (memberRank.getAmount() == null || !memberRankService.amountUnique(id, memberRank.getAmount())) {
			return Message.error("参数错误");
		}
		memberRankService.update(memberRank, "members");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public Page<MemberRank> list(Pageable pageable) {
		return memberRankService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				MemberRank memberRank = memberRankService.find(id);
				if (memberRank != null && memberRank.getMembers() != null && !memberRank.getMembers().isEmpty()) {
					return Message.error("admin.memberRank.deleteExistNotAllowed", memberRank.getName());
				}
			}
			long totalCount = memberRankService.count();
			if (ids.length >= totalCount) {
				return Message.error("admin.common.deleteAllNotAllowed");
			}
			memberRankService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}