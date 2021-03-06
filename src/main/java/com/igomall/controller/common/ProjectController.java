package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ProjectItem;
import com.igomall.entity.other.ProjectCategory;
import com.igomall.entity.other.ProjectItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.ProjectCategoryService;
import com.igomall.service.other.ProjectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("commonProjectController")
@RequestMapping("/api/project")
public class ProjectController extends BaseController{

    @Autowired
    private ProjectCategoryService projectCategoryService;
    @Autowired
    private ProjectItemService projectItemService;

    @PostMapping
    public List<Map<String,Object>> project(Long parentId,@CurrentUser Member member){
        if(parentId==null){
            return jdbcTemplate.queryForList("select id,name from edu_project_category where parent_id is null order by orders asc");
        }
        List<Map<String, Object>> projectCategories = jdbcTemplate.queryForList("select id, name from edu_project_category where parent_id=? order by orders asc",parentId);
        for (Map<String,Object> projectCategory:projectCategories) {
            projectCategory.put("projectItems",jdbcTemplate.queryForList(ProjectItem.QUERY_LIST,projectCategory.get("id")));
        }
        return projectCategories;
    }

    @PostMapping("/item")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<ProjectItem> item(@CurrentUser Member member, Long projectCategoryId){
        return projectItemService.findList(projectCategoryService.find(projectCategoryId),true,null,null,null);
    }
}
