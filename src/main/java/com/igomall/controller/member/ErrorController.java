
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.BookItemError;
import com.igomall.entity.other.ProjectItem;
import com.igomall.entity.other.ProjectItemError;
import com.igomall.entity.other.ToolItemError;
import com.igomall.security.CurrentUser;
import com.igomall.service.FeedbackService;
import com.igomall.service.other.BookItemErrorService;
import com.igomall.service.other.ProjectItemErrorService;
import com.igomall.service.other.ToolItemErrorService;
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
@RestController("memberErrorController")
@RequestMapping("/api/member/error")
public class ErrorController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Autowired
	private BookItemErrorService bookItemErrorService;
	@Autowired
	private ProjectItemErrorService projectItemErrorService;
	@Autowired
	private ToolItemErrorService toolItemErrorService;

	/**
	 * 列表
	 */
	@PostMapping("/book")
	@JsonView(BaseEntity.ListView.class)
	public Page<BookItemError> book(Integer pageNumber, @CurrentUser Member currentUser) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return bookItemErrorService.findPage(currentUser, pageable);
	}
	/**
	 * 列表
	 */
	@PostMapping("/project")
	@JsonView(BaseEntity.ListView.class)
	public Page<ProjectItemError> project(Integer pageNumber, @CurrentUser Member currentUser) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return projectItemErrorService.findPage(currentUser, pageable);
	}
	/**
	 * 列表
	 */
	@PostMapping("/tool")
	@JsonView(BaseEntity.ListView.class)
	public Page<ToolItemError> tool(Integer pageNumber, @CurrentUser Member currentUser) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return toolItemErrorService.findPage(currentUser, pageable);
	}
}