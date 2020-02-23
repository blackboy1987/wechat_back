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
import java.util.Map;

@RestController("commonBookController")
@RequestMapping("/api/book")
public class BookController extends BaseController{

    @Autowired
    private BookCategoryService bookCategoryService;
    @Autowired
    private BookItemService bookItemService;

    @PostMapping
    public List<Map<String,Object>> book(){
        return bookCategoryService.findRoots1();

    }

    @PostMapping("/item")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<BookItem> item(@CurrentUser Member member, Long bookCategoryId){
        return bookItemService.findList(bookCategoryService.find(bookCategoryId),true,null,null,null);
    }
}
