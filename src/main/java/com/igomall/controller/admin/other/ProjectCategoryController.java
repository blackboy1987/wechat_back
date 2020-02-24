
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.Demo1;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.ProjectCategory;
import com.igomall.entity.other.ProjectItem;
import com.igomall.service.other.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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
	@PostMapping("/tree")
	public List<Map<String,Object>> tree() {
		return jdbcTemplate.queryForList("select id,name from edu_project_category where parent_id is null order by orders desc");
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(ProjectCategory projectCategory, Long parentId) {
		projectCategory.setParent(projectCategoryService.find(parentId));
		if (!isValid(projectCategory)) {
			return Message.error("参数错误");
		}
		projectCategory.setTreePath(null);
		projectCategory.setGrade(null);
		projectCategory.setChildren(null);
		projectCategory.setProjectItems(null);
		projectCategoryService.save(projectCategory);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public ProjectCategory edit(Long id) {
		return projectCategoryService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ProjectCategory projectCategory, Long parentId) {
		projectCategory.setParent(projectCategoryService.find(parentId));
		if (!isValid(projectCategory)) {
			return Message.error("参数错误");
		}
		if (projectCategory.getParent() != null) {
			ProjectCategory parent = projectCategory.getParent();
			if (parent.equals(projectCategory)) {
				return Message.error("参数错误");
			}
			List<ProjectCategory> children = projectCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Message.error("参数错误");
			}
		}
		projectCategoryService.update(projectCategory, "treePath", "grade", "children", "projectItems");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public List<ProjectCategory> list() {
		return projectCategoryService.findTree();
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long id) {
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