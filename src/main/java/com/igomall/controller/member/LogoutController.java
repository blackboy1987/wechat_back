
package com.igomall.controller.member;

import com.igomall.entity.member.Member;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.UserService;
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
@RestController("memberLogoutController")
@RequestMapping("/member/api/logout")
public class LogoutController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 登录页面
	 */
	@PostMapping
	public Map<String,Object> index(String type,String username, String password, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> data = new HashMap<>();

		data.put("type",type);
		data.put("status","ok");
		data.put("msg","退出成功");
		userService.logout();
		return data;
	}

}