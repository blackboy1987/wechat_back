package com.igomall.controller.common.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiCourseController")
@RequestMapping("/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseCategoryService courseCategoryService;

    /**
     * 课程列表
     * @param pageable
     *      分页属性
     * @return
     *      课程列表
     */
    @PostMapping("/list")
    @JsonView(Course.CommonListView.class)
    public Message list(Pageable pageable,Long courseCategoryId,Boolean isVip){
        CourseCategory courseCategory = courseCategoryService.find(courseCategoryId);


       return Message.success1("操作成功",courseService.findPage(courseCategory,isVip,pageable));
    }
}
