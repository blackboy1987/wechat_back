
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.ToolCategory;
import com.igomall.entity.other.ToolItem;
import com.igomall.service.other.ToolCategoryService;
import com.igomall.service.other.ToolItemService;
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
@RestController("adminToolItemController")
@RequestMapping("/api/admin/tool_item")
public class ToolItemController extends BaseController {

	@Autowired
	private ToolItemService toolItemService;
	@Autowired
	private ToolCategoryService toolCategoryService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(ToolItem toolItem, Long toolCategoryId) {
		toolItem.setToolCategory(toolCategoryService.find(toolCategoryId));
		if (!isValid(toolItem)) {
			return Message.error("参数错误");
		}
		toolItemService.save(toolItem);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public ToolItem edit(Long id) {
		return toolItemService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ToolItem toolItem, Long toolCategoryId) {
		toolItem.setToolCategory(toolCategoryService.find(toolCategoryId));
		if (!isValid(toolItem)) {
			return Message.error("参数错误");
		}
		toolItemService.update(toolItem);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Page<ToolItem> list(Long categoryId,Pageable pageable,String name) {
		return toolItemService.findPage(toolCategoryService.find(categoryId),name,null,pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		toolItemService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@PostMapping("/category")
	@JsonView(BaseEntity.ListView.class)
	public List<ToolCategory> category() {
		return toolCategoryService.findTree();
	}

}