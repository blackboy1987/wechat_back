
package com.igomall.controller.admin.other;

import com.igomall.Demo1;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.other.BookCategory;
import com.igomall.entity.other.BookItem;
import com.igomall.service.other.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

/**
 * Controller - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminBookCategoryController")
@RequestMapping("/api/admin/book_category")
public class BookCategoryController extends BaseController {

	@Autowired
	private BookCategoryService bookCategoryService;


	@GetMapping("/tools")
	public String tools(String tag,String name) throws Exception{
		BookCategory toolCategory = Demo1.parse1(tag);
		toolCategory.setName(name);
		bookCategoryService.save(toolCategory);
		for (BookCategory child:toolCategory.getChildren()) {
			child.setParent(toolCategory);
			bookCategoryService.save(child);
		}


		return "ok";
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("bookCategoryTree", bookCategoryService.findTree());
		return "admin/book_category/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(BookCategory bookCategory, Long parentId, RedirectAttributes redirectAttributes) {
		bookCategory.setParent(bookCategoryService.find(parentId));
		if (!isValid(bookCategory)) {
			return ERROR_VIEW;
		}
		bookCategory.setTreePath(null);
		bookCategory.setGrade(null);
		bookCategory.setChildren(null);
		bookCategory.setBookItems(null);
		bookCategoryService.save(bookCategory);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		BookCategory bookCategory = bookCategoryService.find(id);
		model.addAttribute("bookCategoryTree", bookCategoryService.findTree());
		model.addAttribute("bookCategory", bookCategory);
		model.addAttribute("children", bookCategoryService.findChildren(bookCategory, true, null));
		return "admin/book_category/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(BookCategory bookCategory, Long parentId) {
		bookCategory.setParent(bookCategoryService.find(parentId));
		if (!isValid(bookCategory)) {
			return ERROR_VIEW;
		}
		if (bookCategory.getParent() != null) {
			BookCategory parent = bookCategory.getParent();
			if (parent.equals(bookCategory)) {
				return ERROR_VIEW;
			}
			List<BookCategory> children = bookCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		bookCategoryService.update(bookCategory, "treePath", "grade", "children", "bookItems");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("bookCategoryTree", bookCategoryService.findTree());
		return "admin/book_category/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody
	Message delete(Long id) {
		BookCategory bookCategory = bookCategoryService.find(id);
		if (bookCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<BookCategory> children = bookCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.bookCategory.deleteExistChildrenNotAllowed");
		}
		Set<BookItem> bookItems = bookCategory.getBookItems();
		if (bookItems != null && !bookItems.isEmpty()) {
			return Message.error("admin.bookCategory.deleteExistBookNotAllowed");
		}
		bookCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}