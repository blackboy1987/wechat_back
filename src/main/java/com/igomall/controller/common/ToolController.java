package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ToolItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.ToolCategoryService;
import com.igomall.service.other.ToolItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("commonToolController")
@RequestMapping("/api/tool")
public class ToolController extends BaseController{

    @Autowired
    private ToolCategoryService toolCategoryService;
    @Autowired
    private ToolItemService toolItemService;

    @PostMapping
    public List<Map<String,Object>> tool(Long parentId,@CurrentUser Member member){
        if(parentId==null){
            return jdbcTemplate.queryForList("select id,name from edu_tool_category where parent_id is null order by orders asc");
        }
        List<Map<String, Object>> toolCategories = jdbcTemplate.queryForList("select id, name from edu_tool_category where parent_id=? order by orders asc",parentId);
        for (Map<String,Object> toolCategory:toolCategories) {
            toolCategory.put("toolItems",jdbcTemplate.queryForList(ToolItem.QUERY_LIST,toolCategory.get("id")));
        }
        return toolCategories;
    }

    @PostMapping("/item")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<ToolItem> item(@CurrentUser Member member, Long toolCategoryId){
        return toolItemService.findList(toolCategoryService.find(toolCategoryId),true,null,null,null);
    }
}
