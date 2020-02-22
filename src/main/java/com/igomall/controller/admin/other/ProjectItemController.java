
package com.igomall.controller.admin.other;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.other.ProjectItem;
import com.igomall.service.other.ProjectCategoryService;
import com.igomall.service.other.ProjectItemService;
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
@RestController("adminProjectItemController")
@RequestMapping("/api/admin/project_item")
public class ProjectItemController extends BaseController {

	@Autowired
	private ProjectItemService projectItemService;
	@Autowired
	private ProjectCategoryService projectCategoryService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(ProjectItem projectItem, Long projectItemCategoryId) {
		projectItem.setProjectCategory(projectCategoryService.find(projectItemCategoryId));
		if (!isValid(projectItem)) {
			return Message.error("参数错误");
		}
		projectItemService.save(projectItem);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public ProjectItem edit(Long id) {
		return projectItemService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ProjectItem projectItem, Long projectItemCategoryId) {
		projectItem.setProjectCategory(projectCategoryService.find(projectItemCategoryId));
		if (!isValid(projectItem)) {
			return Message.error("参数错误");
		}
		projectItemService.update(projectItem);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<ProjectItem> list(Pageable pageable) {
		return projectItemService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		projectItemService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}