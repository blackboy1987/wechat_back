
package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.*;
import com.igomall.security.CurrentUser;
import com.igomall.service.FeedbackService;
import com.igomall.service.other.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
	@Autowired
	private BookItemService bookItemService;
	@Autowired
	private ProjectItemService projectItemService;

	@Autowired
	private ToolItemService toolItemService;

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


	@PostMapping("/save")
	public Message save(String type, BookItem bookItem, @CurrentUser Member currentUser) {
		if(currentUser==null){
			return Message.error("请先登陆");
		}

		if(StringUtils.equalsAnyIgnoreCase("book",type)){
			BookItemError bookItemError = new BookItemError();
			BookItem bookItem1 = bookItemService.find(bookItem.getId());
			if(bookItem1==null){
				return Message.error("书籍不存在");
			}
			BeanUtils.copyProperties(bookItem,bookItemError,"id","bookCategory","isPublication");
			bookItemError.setBookItem(bookItem1);
			bookItemError.setMember(currentUser);
			bookItemError.setIsPublication(false);
			bookItemErrorService.save(bookItemError);
			return Message.success("操作成功");
		}else if(StringUtils.equalsAnyIgnoreCase("project",type)){
			ProjectItem projectItem1 = projectItemService.find(bookItem.getId());
			if(projectItem1==null){
				return Message.error("书籍不存在");
			}
			ProjectItemError projectItemError = new ProjectItemError();
			BeanUtils.copyProperties(bookItem,projectItemError,"id","projectCategory","isPublication");
			projectItemError.setProjectItem(projectItem1);
			projectItemError.setMember(currentUser);
			projectItemError.setIsPublication(false);
			projectItemErrorService.save(projectItemError);
			return Message.success("操作成功");
		}else if(StringUtils.equalsAnyIgnoreCase("tool",type)){
			ToolItem toolItem1 = toolItemService.find(bookItem.getId());
			if(toolItem1==null){
				return Message.error("书籍不存在");
			}
			ToolItemError toolItemError = new ToolItemError();
			BeanUtils.copyProperties(bookItem,toolItemError,"id","toolCategory","isPublication");
			toolItemError.setToolItem(toolItem1);
			toolItemError.setMember(currentUser);
			toolItemError.setIsPublication(false);
			toolItemErrorService.save(toolItemError);
			return Message.success("操作成功");
		}
		return Message.error("参数错误");
	}
}