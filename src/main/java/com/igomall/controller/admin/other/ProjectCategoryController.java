
package com.igomall.controller.admin.other;

import com.igomall.Demo1;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.other.BookCategory;
import com.igomall.entity.other.ProjectCategory;
import com.igomall.entity.other.ProjectItem;
import com.igomall.service.other.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controller - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminProjectCategoryController")
@RequestMapping("/api/admin/project_category")
public class ProjectCategoryController extends BaseController {

	@Autowired
	private ProjectCategoryService projectCategoryService;

	@GetMapping("/tools")
	public String tools(String tag,String name) throws Exception{
		ProjectCategory toolCategory = Demo1.parse2(tag);
		toolCategory.setName(name);
		projectCategoryService.save(toolCategory);
		for (ProjectCategory child:toolCategory.getChildren()) {
			child.setParent(toolCategory);
			projectCategoryService.save(child);
		}


		return "ok";
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("projectCategoryTree", projectCategoryService.findTree());
		return "admin/project_category/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ProjectCategory projectCategory, Long parentId) {
		projectCategory.setParent(projectCategoryService.find(parentId));
		if (!isValid(projectCategory)) {
			return ERROR_VIEW;
		}
		projectCategory.setTreePath(null);
		projectCategory.setGrade(null);
		projectCategory.setChildren(null);
		projectCategory.setProjectItems(null);
		projectCategoryService.save(projectCategory);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		ProjectCategory projectCategory = projectCategoryService.find(id);
		model.addAttribute("projectCategoryTree", projectCategoryService.findTree());
		model.addAttribute("projectCategory", projectCategory);
		model.addAttribute("children", projectCategoryService.findChildren(projectCategory, true, null));
		return "admin/project_category/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(ProjectCategory projectCategory, Long parentId) {
		projectCategory.setParent(projectCategoryService.find(parentId));
		if (!isValid(projectCategory)) {
			return ERROR_VIEW;
		}
		if (projectCategory.getParent() != null) {
			ProjectCategory parent = projectCategory.getParent();
			if (parent.equals(projectCategory)) {
				return ERROR_VIEW;
			}
			List<ProjectCategory> children = projectCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		projectCategoryService.update(projectCategory, "treePath", "grade", "children", "projectItems");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("projectCategoryTree", projectCategoryService.findTree());
		return "admin/project_category/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody
	Message delete(Long id) {
		ProjectCategory projectCategory = projectCategoryService.find(id);
		if (projectCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ProjectCategory> children = projectCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.projectCategory.deleteExistChildrenNotAllowed");
		}
		Set<ProjectItem> projectItems = projectCategory.getProjectItems();
		if (projectItems != null && !projectItems.isEmpty()) {
			return Message.error("admin.projectCategory.deleteExistProjectNotAllowed");
		}
		projectCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}