
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.Demo1;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.BookCategory;
import com.igomall.entity.other.BookItem;
import com.igomall.service.other.BookCategoryService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;
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
	public String tools(String tag,String name,Integer order) throws Exception{
		BookCategory toolCategory = Demo1.parse1(tag);
		toolCategory.setName(name);
		toolCategory.setOrder(order);
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
	@PostMapping("/tree")
	public List<Map<String,Object>> tree() {
		return jdbcTemplate.queryForList("select id,name from edu_book_category where parent_id is null order by orders desc");
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(BookCategory bookCategory, Long parentId) {
		bookCategory.setParent(bookCategoryService.find(parentId));
		if (!isValid(bookCategory)) {
			return Message.error("参数错误");
		}
		bookCategory.setTreePath(null);
		bookCategory.setGrade(null);
		bookCategory.setChildren(null);
		bookCategory.setBookItems(null);
		bookCategoryService.save(bookCategory);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public BookCategory edit(Long id) {
		return bookCategoryService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(BookCategory bookCategory, Long parentId) {
		bookCategory.setParent(bookCategoryService.find(parentId));
		if (!isValid(bookCategory)) {
			return Message.error("参数错误");
		}
		if (bookCategory.getParent() != null) {
			BookCategory parent = bookCategory.getParent();
			if (parent.equals(bookCategory)) {
				return Message.error("参数错误");
			}
			List<BookCategory> children = bookCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Message.error("参数错误");
			}
		}
		bookCategoryService.update(bookCategory, "treePath", "grade", "children", "bookItems");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public List<BookCategory> list() {
		return bookCategoryService.findTree();
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long id) {
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