
package com.igomall.controller.admin.member;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.Admin;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberDepositLog;
import com.igomall.security.CurrentUser;
import com.igomall.service.member.MemberDepositLogService;
import com.igomall.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 会员预存款
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminMemberDepositController")
@RequestMapping("/admin/api/member_deposit")
public class MemberDepositController extends BaseController {

	@Autowired
	private MemberDepositLogService memberDepositLogService;
	@Autowired
	private MemberService memberService;

	/**
	 * 检查会员
	 */
	@GetMapping("/check_member")
	public Map<String, Object> checkMember(String username) {
		Map<String, Object> data = new HashMap<>();
		Member member = memberService.findByUsername(username);
		if (member == null) {
			data.put("message", Message.warn("admin.memberDeposit.memberNotExist"));
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);
		data.put("balance", member.getBalance());
		return data;
	}

	/**
	 * 调整
	 */
	@PostMapping("/adjust")
	public Message adjust(String username, BigDecimal amount, String memo, @CurrentUser Admin currentUser) {
		Member member = memberService.findByUsername(username);
		if (member == null) {
			return Message.error("会员不存在");
		}
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return Message.error("参数错误");
		}
		if (member.getBalance() == null || member.getBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
			return Message.error("参数错误");
		}
		memberService.addBalance(member, amount, MemberDepositLog.Type.adjustment, memo);
		return Message.success("操作成功");
	}

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public Page<MemberDepositLog> log(Long memberId, Pageable pageable) {
		Member member = memberService.find(memberId);
		if (member != null) {
			return memberDepositLogService.findPage(member, pageable);
		} else {
			return memberDepositLogService.findPage(pageable);
		}
	}

}