package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.Sn;
import com.igomall.entity.course.Lesson;
import com.igomall.service.SnService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController("adminLessonController")
@RequestMapping("/admin/api/lesson")
public class LessonController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private SnService snService;
    @Autowired
    private LessonService lessonService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Message save(Lesson lesson, Long courseId) {
        lesson.setSn(snService.generate(Sn.Type.lesson));
        lesson.setDuration(0L);
        lesson.setCourseComments(new HashSet<>());
        lesson.setCourse(courseService.find(courseId));
        if (!isValid(lesson)) {
            return Message.error("参数错误");
        }
        lessonService.save(lesson);
        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @JsonView(Lesson.EditView.class)
    public Lesson edit(Long id) {
        return lessonService.find(id);
    }


    /**
     * 课程列表
     * @param pageable
     *      分页属性
     * @return
     *      课程列表
     */
    @PostMapping("/list")
    @JsonView(Lesson.ListView.class)
    public Page<Lesson> list(Pageable pageable){
        return lessonService.findPage(pageable);
    }

    @PostMapping("/course")
    public Message article() {
        return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_course order by orders "));
    }

    @PostMapping("/delete")
    public Message delete(Long[] ids){
        for (Long id :ids) {
          Lesson lesson = lessonService.find(id);
          if(lesson!=null){
              lesson.setStatus(5);
              lessonService.update(lesson);
          }
        }
        return Message.success("操作成功");
    }
}
