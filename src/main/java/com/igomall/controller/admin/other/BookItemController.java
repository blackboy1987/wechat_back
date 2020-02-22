
package com.igomall.controller.admin.other;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.other.BookItem;
import com.igomall.service.other.BookCategoryService;
import com.igomall.service.other.BookItemService;
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
@RestController("adminBookItemController")
@RequestMapping("/api/admin/book_item")
public class BookItemController extends BaseController {

	@Autowired
	private BookItemService bookItemService;
	@Autowired
	private BookCategoryService bookCategoryService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(BookItem bookItem, Long bookItemCategoryId) {
		bookItem.setBookCategory(bookCategoryService.find(bookItemCategoryId));
		if (!isValid(bookItem)) {
			return Message.error("参数错误");
		}
		bookItemService.save(bookItem);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public BookItem edit(Long id) {
		return bookItemService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(BookItem bookItem, Long bookItemCategoryId) {
		bookItem.setBookCategory(bookCategoryService.find(bookItemCategoryId));
		if (!isValid(bookItem)) {
			return Message.error("参数错误");
		}
		bookItemService.update(bookItem);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<BookItem> list(Pageable pageable) {
		return bookItemService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		bookItemService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}