
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.ProjectCategory;
import com.igomall.entity.other.ProjectItem;
import com.igomall.service.other.ProjectCategoryService;
import com.igomall.service.other.ProjectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	public Message save(ProjectItem projectItem, Long projectCategoryId) {
		projectItem.setProjectCategory(projectCategoryService.find(projectCategoryId));
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
	@JsonView(BaseEntity.EditView.class)
	public ProjectItem edit(Long id) {
		return projectItemService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ProjectItem projectItem, Long projectCategoryId) {
		projectItem.setProjectCategory(projectCategoryService.find(projectCategoryId));
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
	@JsonView(BaseEntity.ListView.class)
	public Page<ProjectItem> list(Long categoryId,Pageable pageable,String name) {
		return projectItemService.findPage(projectCategoryService.find(categoryId),name,null,pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		projectItemService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@PostMapping("/category")
	@JsonView(BaseEntity.ListView.class)
	public List<ProjectCategory> category() {
		return projectCategoryService.findTree();
	}

}