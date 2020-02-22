
package com.igomall.controller.member;

import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberRank;
import com.igomall.entity.member.PointLog;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.UserService;
import com.igomall.service.member.MemberRankService;
import com.igomall.service.member.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 会员登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberLoginController")
@RequestMapping("/api/member/login")
public class LoginController extends BaseController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private UserService userService;
	@Autowired
	private MemberRankService memberRankService;

	/**
	 * 登录页面
	 */
	@PostMapping
	public Map<String,Object> index(String type,String username, String password, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> data = new HashMap<>();
		data.put("type",type);
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			data.put("status","error");
			data.put("content","请输入用户名或密码");
			return data;
		}
		Member member = memberService.findByUsername(username);
		if(member==null){
			/*data.put("status","error");
			data.put("msg","用户名或密码输入错误");
			return data;*/
			Member member1 = new Member();
			member1.init();
			member1.setUsername(username);
			member1.setPassword(password);
			member1.setMemberRank(memberRankService.findDefault());
			member = memberService.save(member1);
		}
		if(!member.isValidCredentials(password)){
			data.put("status","error");
			data.put("msg","用户名或密码输入错误");
			return data;
		}
		data.put("status","ok");
		data.put("msg","登陆成功");
		Map<String,Object> user = new HashMap<>();
		user.put("id",member.getId());
		user.put("username",member.getUsername());
		data.put("user",user);
		data.put("avatar",member.getAvatar());
		userService.login(new UserAuthenticationToken(Member.class, username, password, false, request.getRemoteAddr()));

		/**
		 * 登陆积分。每天只送一次
		 */
		memberService.addPoint(member,50, PointLog.Type.login,"登陆");

		return data;
	}

}