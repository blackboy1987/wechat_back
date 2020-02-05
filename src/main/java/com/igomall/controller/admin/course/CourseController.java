package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.Sn;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.SnService;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.CourseTagService;
import com.igomall.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController("adminCourseController")
@RequestMapping("/admin/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private CourseTagService courseTagService;
    @Autowired
    private SnService snService;
    @Autowired
    private MemberService memberService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Message save(Course course, Long courseCategoryId, Long[] courseTagIds,String[] courseTagNames,String content) {
        course.setSn(snService.generate(Sn.Type.course));
        course.setStatus(0);
        course.setDescription(content);
        course.setDuration(0L);
        course.setVideos(0);
        course.setCourseCategory(courseCategoryService.find(courseCategoryId));
        course.setCourseTags(new HashSet<>(courseTagService.findList(courseTagIds)));
        if(courseTagNames!=null){
            course.getCourseTags().addAll(createTags(courseTagNames));
        }
        course.setIsTop(true);
        course.setIsPublication(true);
        if (!isValid(course)) {
            return Message.error("参数错误");
        }
        course.setHits(0L);
        course.setScore(0f);
        course.setScoreCount(0L);
        course.setTotalScore(0L);
        course.setMember(memberService.find(1L));
        course.setAuthor(course.getMember().getUsername());
        courseService.save(course);
        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @JsonView(Course.EditView.class)
    public Course edit(Long id) {
        return courseService.find(id);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Message update(Course course, Long courseCategoryId, Long[] courseTagIds) {
        course.setCourseCategory(courseCategoryService.find(courseCategoryId));
        course.setCourseTags(new HashSet<>(courseTagService.findList(courseTagIds)));
        course.setIsTop(true);
        course.setIsPublication(true);
        if (!isValid(course)) {
            return Message.error("参数错误");
        }
        courseService.update(course, "hits","sn");
        return Message.success("操作成功");
    }

    /**
     * 课程列表
     * @param pageable
     *      分页属性
     * @return
     *      课程列表
     */
    @PostMapping("/list")
    @JsonView(Course.ListView.class)
    public Page<Course> list(Pageable pageable, Long courseCategoryId, Boolean isVip,Boolean isTop,Boolean isPublish,Integer status){
        return courseService.findPage(pageable);
    }

    @PostMapping("/course_topic")
    public Message articleTopic() {
        return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_course_topic order by orders "));
    }

    @PostMapping("/course_category")
    public Message articleCategory() {
        return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_course_category order by orders "));
    }

    @PostMapping("/course_tag")
    public Message articleTag() {
        return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_course_tag order by orders "));
    }

    private List<CourseTag> createTags(String[] courseTagNames){
        if(courseTagNames==null){
            return null;
        }
        List<CourseTag> courseTags = new ArrayList<>();
        for (String name:courseTagNames) {
            CourseTag courseTag = courseTagService.findByName(name);
            if(courseTag==null){
                courseTag = new CourseTag();
                courseTag.setName(name);
                courseTag.setCourses(null);
                courseTag = courseTagService.save(courseTag);
            }
            courseTags.add(courseTag);
        }

        return courseTags;
    }


    @PostMapping("/delete")
    public Message delete(Long[] ids){
        for (Long id :ids) {
          Course course = courseService.find(id);
          if(course!=null){
              course.setIsTop(false);
              course.setIsPublication(false);
              course.setStatus(5);
              courseService.update(course);
          }
        }
        return Message.success("操作成功");
    }
}
