package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ToolCategory;
import com.igomall.entity.other.ToolItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.ToolCategoryService;
import com.igomall.service.other.ToolItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("commonToolController")
@RequestMapping("/api/tool")
public class ToolController extends BaseController{

    @Autowired
    private ToolCategoryService toolCategoryService;
    @Autowired
    private ToolItemService toolItemService;

    @PostMapping
    @JsonView(BaseEntity.JsonApiView.class)
    public List<ToolCategory> tool(@CurrentUser Member member,Long toolCategoryId){
        if(toolCategoryId!=null){
            return toolCategoryService.findChildren(toolCategoryService.find(toolCategoryId),false,null);
        }
        return toolCategoryService.findRoots();

    }

    @PostMapping("/item")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<ToolItem> item(@CurrentUser Member member, Long toolCategoryId){
        return toolItemService.findList(toolCategoryService.find(toolCategoryId),true,null,null,null);
    }
}
