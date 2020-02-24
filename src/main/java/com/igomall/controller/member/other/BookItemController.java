
package com.igomall.controller.member.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.BookCategory;
import com.igomall.entity.other.BookItem;
import com.igomall.entity.other.BookItemError;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.BookCategoryService;
import com.igomall.service.other.BookItemErrorService;
import com.igomall.service.other.BookItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
@RestController("memberBookItemController")
@RequestMapping("/api/member/book")
public class BookItemController extends BaseController {

	@Autowired
	private BookItemService bookItemService;
	@Autowired
	private BookItemErrorService bookItemErrorService;

	/**
	 * 删除
	 */
	@PostMapping("/error")
	public Message error(BookItem bookItem, @CurrentUser Member member) {
		if(member==null){
			return Message.error("请先登陆");
		}


		BookItem bookItem1 = bookItemService.find(bookItem.getId());
		if(bookItem1==null){
			return Message.error("书籍不存在");
		}
		BookItemError bookItemError = new BookItemError();
		BeanUtils.copyProperties(bookItem,bookItemError,"id","bookCategory","isPublication");
		bookItemError.setBookItem(bookItem1);
		bookItemError.setMember(member);
		bookItemError.setIsPublication(false);
		bookItemErrorService.save(bookItemError);
		return Message.success("操作成功");

	}
}