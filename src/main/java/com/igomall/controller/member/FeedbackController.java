
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;
import com.igomall.security.CurrentUser;
import com.igomall.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 会员登录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("memberFeedbackController")
@RequestMapping("/api/member/feedback")
public class FeedbackController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Autowired
	private FeedbackService feedbackService;

	@PostMapping("/save")
	public Message save(@CurrentUser Member member, Feedback feedback){
		if(member==null){
			return Message.error("请先登陆");
		}
		if(!isValid(feedback)){
			return Message.error("参数错误");
		}
		feedback.setStatus(0);
		feedback.setMember(member);
		feedbackService.save(feedback);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Page<Feedback> list(Integer pageNumber, @CurrentUser Member currentUser) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return feedbackService.findPage(currentUser, pageable);
	}
}