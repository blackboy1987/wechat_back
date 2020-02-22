package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Folder;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/folder")
public class FolderController extends BaseController {

    @Autowired
    private FolderService folderService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Page<Folder> list(Pageable pageable){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.desc("course"));
        orders.add(Order.asc("order"));
        pageable.setOrders(orders);
        return folderService.findPage(pageable);
    }

    @PostMapping("/edit")
    @JsonView(BaseEntity.EditView.class)
    public Folder edit(Long id){
        return folderService.find(id);
    }

    @PostMapping("/save")
    public Message save(Folder folder,Long courseId){
        folder.setCourse(courseService.find(courseId));
        if(!isValid(folder)){
            return Message.error("参数错误");
        }
        folderService.save(folder);
        return Message.success("操作成功");
    }

    @PostMapping("/update")
    public Message update(Folder folder){
        Folder pFolder = folderService.find(folder.getId());
        if(pFolder==null){
            return Message.error("课程不存在");
        }
        pFolder.setName(folder.getName());
        pFolder.setOrder(folder.getOrder());
        pFolder.setPath(folder.getPath());
        folderService.update(pFolder);
        return Message.success("操作成功");
    }

    @PostMapping("/course")
    public List<Map<String,Object>> course(){
        return jdbcTemplate.queryForList("select id,title from edu_course order by orders asc");
    }
}
