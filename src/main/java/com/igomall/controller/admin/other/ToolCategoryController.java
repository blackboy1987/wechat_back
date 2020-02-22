
package com.igomall.controller.admin.other;

import com.igomall.Demo1;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.other.ToolCategory;
import com.igomall.entity.other.ToolItem;
import com.igomall.service.other.ToolCategoryService;
import com.igomall.service.other.ToolItemService;
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
@RestController("adminToolCategoryController")
@RequestMapping("/api/admin/tool_category")
public class ToolCategoryController extends BaseController {

	@Autowired
	private ToolCategoryService toolCategoryService;

	@GetMapping("/tools")
	public String tools(String tag) throws Exception{
		ToolCategory toolCategory = Demo1.parse(tag);
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
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("toolCategoryTree", toolCategoryService.findTree());
		return "admin/tool_category/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ToolCategory toolCategory, Long parentId) {
		toolCategory.setParent(toolCategoryService.find(parentId));
		if (!isValid(toolCategory)) {
			return ERROR_VIEW;
		}
		toolCategory.setTreePath(null);
		toolCategory.setGrade(null);
		toolCategory.setChildren(null);
		toolCategory.setToolItems(null);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		ToolCategory toolCategory = toolCategoryService.find(id);
		model.addAttribute("toolCategoryTree", toolCategoryService.findTree());
		model.addAttribute("toolCategory", toolCategory);
		model.addAttribute("children", toolCategoryService.findChildren(toolCategory, true, null));
		return "admin/tool_category/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(ToolCategory toolCategory, Long parentId) {
		toolCategory.setParent(toolCategoryService.find(parentId));
		if (!isValid(toolCategory)) {
			return ERROR_VIEW;
		}
		if (toolCategory.getParent() != null) {
			ToolCategory parent = toolCategory.getParent();
			if (parent.equals(toolCategory)) {
				return ERROR_VIEW;
			}
			List<ToolCategory> children = toolCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		toolCategoryService.update(toolCategory, "treePath", "grade", "children", "toolItems");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("toolCategoryTree", toolCategoryService.findTree());
		return "admin/tool_category/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody
	Message delete(Long id) {
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