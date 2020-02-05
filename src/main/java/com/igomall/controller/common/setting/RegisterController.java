
package com.igomall.controller.common.setting;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.igomall.common.Message;
import com.igomall.controller.member.BaseController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.igomall.common.Results;
import com.igomall.common.Setting;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberAttribute;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.member.MemberAttributeService;
import com.igomall.service.member.MemberRankService;
import com.igomall.service.member.MemberService;
import com.igomall.service.UserService;
import com.igomall.util.SystemUtils;

/**
 * Controller - 会员注册
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonRegisterController")
@RequestMapping("/api/register")
public class RegisterController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRankService memberRankService;
	@Autowired
	private MemberAttributeService memberAttributeService;

	/**
	 * 检查用户名是否存在
	 */
	@GetMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !memberService.usernameExists(username);
	}

	/**
	 * 检查E-mail是否存在
	 */
	@GetMapping("/check_email")
	public @ResponseBody boolean checkEmail(String email) {
		return StringUtils.isNotEmpty(email) && !memberService.emailExists(email);
	}

	/**
	 * 检查手机是否存在
	 */
	@GetMapping("/check_mobile")
	public @ResponseBody boolean checkMobile(String mobile) {
		return StringUtils.isNotEmpty(mobile) && !memberService.mobileExists(mobile);
	}

	/**
	 * 注册提交
	 */
	@PostMapping("/submit")
	public Message submit(String username, String password, String email, String mobile, HttpServletRequest request) {
		Setting setting = SystemUtils.getSetting();
		if (!ArrayUtils.contains(setting.getAllowedRegisterTypes(), Setting.RegisterType.member)) {
			return Message.error("网站暂未开放注册");
		}

		if (!isValid(Member.class, "username", username, BaseEntity.Save.class) || !isValid(Member.class, "password", password, BaseEntity.Save.class) || !isValid(Member.class, "email", email, BaseEntity.Save.class) || !isValid(Member.class, "mobile", mobile, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		if (memberService.usernameExists(username)) {
			return Message.error("用户名已存在");
		}
		if (memberService.emailExists(email)) {
			return Message.error("邮箱已存在");
		}
		if (StringUtils.isNotEmpty(mobile) && memberService.mobileExists(mobile)) {
			return Message.error("手机号已存在");
		}

		Member member = new Member();
		member.init();
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
			if (!memberAttributeService.isValid(memberAttribute, values)) {
				return Message.error("参数错误");
			}
			Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
			member.setAttributeValue(memberAttribute, memberAttributeValue);
		}
		member.setUsername(username);
		member.setPassword(password);
		member.setEmail(email);
		member.setMobile(mobile);
		member.setMemberRank(memberRankService.findDefault());
		userService.register(member);
		userService.login(new UserAuthenticationToken(Member.class, username, password, false, request.getRemoteAddr()));
		return Message.success("注册成功");
	}

}