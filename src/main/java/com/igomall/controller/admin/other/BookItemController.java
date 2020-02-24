
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.BookCategory;
import com.igomall.entity.other.BookItem;
import com.igomall.service.other.BookCategoryService;
import com.igomall.service.other.BookItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

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
	public Message save(BookItem bookItem, Long bookCategoryId) {
		bookItem.setBookCategory(bookCategoryService.find(bookCategoryId));
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
	@JsonView(BaseEntity.EditView.class)
	public BookItem edit(Long id) {
		return bookItemService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(BookItem bookItem, Long bookCategoryId) {
		bookItem.setBookCategory(bookCategoryService.find(bookCategoryId));
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
	@JsonView(BaseEntity.ListView.class)
	public Page<BookItem> list(Long categoryId,Pageable pageable,String name) {
		return bookItemService.findPage(bookCategoryService.find(categoryId),name,null,pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		bookItemService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@PostMapping("/category")
	@JsonView(BaseEntity.ListView.class)
	public List<BookCategory> category() {
		return bookCategoryService.findTree();
	}


	@GetMapping("/aa")
	public String aa(){
		String path = "E:\\迅雷";
		List<BookItem> bookItems = bookItemService.findAll();
		File parent = new File(path);
		File[] files = parent.listFiles();
		for (File file:files) {
			for (BookItem bookItem:bookItems) {
				if(StringUtils.equalsAnyIgnoreCase(bookItem.getName()+".zip",file.getName())||StringUtils.equalsAnyIgnoreCase(bookItem.getName()+".docx",file.getName())||StringUtils.equalsAnyIgnoreCase(bookItem.getName()+".chm",file.getName())||StringUtils.equalsAnyIgnoreCase(bookItem.getName()+".doc",file.getName()) || StringUtils.equalsAnyIgnoreCase(bookItem.getName()+".pdf",file.getName())){
					bookItem.setDownloadUrl("https://ishangedu.oss-cn-hangzhou.aliyuncs.com/file/"+file.getName());
					bookItemService.update(bookItem);
					System.out.println(bookItem.getId());
					break;
				}
			}
		}

		return "aa";
	}
}