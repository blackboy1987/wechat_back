
package com.igomall.controller.member.other;

import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ProjectItem;
import com.igomall.entity.other.ProjectItemError;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.ProjectItemErrorService;
import com.igomall.service.other.ProjectItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("memberProjectItemController")
@RequestMapping("/api/member/project")
public class ProjectItemController extends BaseController {

	@Autowired
	private ProjectItemService projectItemService;
	@Autowired
	private ProjectItemErrorService projectItemErrorService;

	/**
	 * 删除
	 */
	@PostMapping("/error")
	public Message error(ProjectItem projectItem, @CurrentUser Member member) {
		if(member==null){
			return Message.error("请先登陆");
		}


		ProjectItem projectItem1 = projectItemService.find(projectItem.getId());
		if(projectItem1==null){
			return Message.error("书籍不存在");
		}
		ProjectItemError projectItemError = new ProjectItemError();
		BeanUtils.copyProperties(projectItem,projectItemError,"id","projectCategory","isPublication");
		projectItemError.setProjectItem(projectItem1);
		projectItemError.setMember(member);
		projectItemError.setIsPublication(false);
		projectItemErrorService.save(projectItemError);
		return Message.success("操作成功");

	}
}