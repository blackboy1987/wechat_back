
package com.igomall.controller.admin.other;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.other.ToolItem;
import com.igomall.service.other.ToolCategoryService;
import com.igomall.service.other.ToolItemService;
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
	public Message save(ToolItem toolItem, Long toolItemCategoryId) {
		toolItem.setToolCategory(toolCategoryService.find(toolItemCategoryId));
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
	public ToolItem edit(Long id) {
		return toolItemService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ToolItem toolItem, Long toolItemCategoryId) {
		toolItem.setToolCategory(toolCategoryService.find(toolItemCategoryId));
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
	public Page<ToolItem> list(Pageable pageable) {
		return toolItemService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		toolItemService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}