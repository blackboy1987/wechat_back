package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.LessonService;
import org.apache.poi.xwpf.usermodel.Borders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/lesson")
public class LessonController extends BaseController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Page<Lesson> list(Pageable pageable){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.desc("course"));
        orders.add(Order.desc("folder"));
        orders.add(Order.asc("order"));
        pageable.setOrders(orders);
        return lessonService.findPage(pageable);
    }

    @PostMapping("/edit")
    @JsonView(BaseEntity.EditView.class)
    public Lesson edit(Long id){
        return lessonService.find(id);
    }

    @PostMapping("/save")
    public Message save(Lesson lesson){
        if(!isValid(lesson)){
            return Message.error("参数错误");
        }
        lessonService.save(lesson);
        return Message.success("操作成功");
    }

    @PostMapping("/update")
    public Message update(Lesson lesson){
        Lesson pLesson = lessonService.find(lesson.getId());
        if(pLesson==null){
            return Message.error("课程不存在");
        }
        pLesson.setTitle(lesson.getTitle());
        pLesson.setOrder(lesson.getOrder());
        pLesson.setPath(lesson.getPath());
        lessonService.update(pLesson);
        return Message.success("操作成功");
    }


    @PostMapping("/course")
    public List<Map<String,Object>> course(){
        List<Map<String,Object>> courses = jdbcTemplate.queryForList("select id,title from edu_course order by orders asc");
        for (Map<String,Object> course:courses) {
            course.put("folders",jdbcTemplate.queryForList("select id,name from edu_folder where course_id="+course.get("id")+" order by orders asc"));
        }
        return courses;
    }
}
