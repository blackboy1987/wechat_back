package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.BookCategory;
import com.igomall.entity.other.BookItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.BookCategoryService;
import com.igomall.service.other.BookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("commonBookController")
@RequestMapping("/api/book")
public class BookController extends BaseController{

    @Autowired
    private BookCategoryService bookCategoryService;
    @Autowired
    private BookItemService bookItemService;

    @PostMapping
    @JsonView(BaseEntity.JsonApiView.class)
    public List<BookCategory> book(@CurrentUser Member member,Long bookCategoryId){
        if(bookCategoryId!=null){
            return bookCategoryService.findChildren(bookCategoryService.find(bookCategoryId),false,null);
        }
        return bookCategoryService.findRoots();

    }

    @PostMapping("/item")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<BookItem> item(@CurrentUser Member member, Long bookCategoryId){
        return bookItemService.findList(bookCategoryService.find(bookCategoryId),true,null,null,null);
    }
}
