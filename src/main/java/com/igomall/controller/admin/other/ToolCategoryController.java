
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.Demo1;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.ToolCategory;
import com.igomall.entity.other.ToolItem;
import com.igomall.service.other.ToolCategoryService;
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
@RestController("adminToolCategoryController")
@RequestMapping("/api/admin/tool_category")
public class ToolCategoryController extends BaseController {

	@Autowired
	private ToolCategoryService toolCategoryService;


	@GetMapping("/tools")
	public String tools(String tag,String name) throws Exception{
		ToolCategory toolCategory = Demo1.parse(tag);
		toolCategory.setName(name);
		toolCategoryService.save(toolCategory);
		for (ToolCategory child:toolCategory.getChildren()) {
			child.setParent(toolCategory);
			toolCategoryService.save(child);
		}


		return "ok";
	}

	/**
	 * 添加
	 */
	@PostMapping("/tree")
	public List<Map<String,Object>> tree() {
		return jdbcTemplate.queryForList("select id,name from edu_tool_category where parent_id is null order by orders desc");
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(ToolCategory toolCategory, Long parentId) {
		toolCategory.setParent(toolCategoryService.find(parentId));
		if (!isValid(toolCategory)) {
			return Message.error("参数错误");
		}
		toolCategory.setTreePath(null);
		toolCategory.setGrade(null);
		toolCategory.setChildren(null);
		toolCategory.setToolItems(null);
		toolCategoryService.save(toolCategory);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public ToolCategory edit(Long id) {
		return toolCategoryService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ToolCategory toolCategory, Long parentId) {
		toolCategory.setParent(toolCategoryService.find(parentId));
		if (!isValid(toolCategory)) {
			return Message.error("参数错误");
		}
		if (toolCategory.getParent() != null) {
			ToolCategory parent = toolCategory.getParent();
			if (parent.equals(toolCategory)) {
				return Message.error("参数错误");
			}
			List<ToolCategory> children = toolCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Message.error("参数错误");
			}
		}
		toolCategoryService.update(toolCategory, "treePath", "grade", "children", "toolItems");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public List<ToolCategory> list() {
		return toolCategoryService.findTree();
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long id) {
		ToolCategory toolCategory = toolCategoryService.find(id);
		if (toolCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ToolCategory> children = toolCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.toolCategory.deleteExistChildrenNotAllowed");
		}
		Set<ToolItem> toolItems = toolCategory.getToolItems();
		if (toolItems != null && !toolItems.isEmpty()) {
			return Message.error("admin.toolCategory.deleteExistToolNotAllowed");
		}
		toolCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}