package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Order;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.LessonReadRecord;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;
import com.igomall.security.CurrentUser;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.FolderService;
import com.igomall.service.course.LessonService;
import com.igomall.service.member.LessonReadRecordService;
import com.igomall.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("commonIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController{

    @Autowired
    private CourseService courseService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonReadRecordService lessonReadRecordService;
    @Autowired
    private MemberService memberService;

    @PostMapping("/course")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Course> course(@CurrentUser Member member){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        return courseService.findList(null,null,orders);
    }

    @PostMapping("/folder")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Folder> folder(Long courseId, @CurrentUser Member member){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        List<Folder> folders = folderService.findList(courseService.find(courseId),null,null,orders);
        if(folders.isEmpty()){
            Folder folder = new Folder();
            folder.setName(null);
            folder.setId(null);
            folder.setLessons(new HashSet<>(lessonService.findList(courseService.find(courseId),null,null,null,null)));
            folders.add(folder);
        }
        return folders;
    }

    @PostMapping("/lesson")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Lesson> lesson(Long courseId,Long folderId, @CurrentUser Member member){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        List<Lesson> lessons = lessonService.findList(courseService.find(courseId),folderService.find(folderId),null,null,orders);
        return lessons;
    }

    @PostMapping("/play_url")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Lesson.PlayUrl> playUrl(Long lessonId, @CurrentUser Member member){
        Lesson lesson = lessonService.find(lessonId);
        if(lesson!=null){
            return lesson.getPlayUrls();
        }
        return Collections.emptyList();
    }

    @PostMapping("/lesson_record")
    public Message lessonRecord(Long lessonId,String playUrlName,String playUrlUrl, @CurrentUser Member member){
        /*if(member==null){
            return Message.error("请先登录");
        }*/

        // 加积分操作
        // 当天，同一个课程，同一个链接只加一次
        if(member!=null){
            boolean exist = lessonReadRecordService.existToday(lessonId,member.getId(),playUrlName,playUrlUrl);
            if(!exist){
                LessonReadRecord lessonReadRecord = lessonReadRecordService.save(lessonId,member.getId(),playUrlName,playUrlUrl);
                if(lessonReadRecord!=null){
                    memberService.addPoint(member,10, PointLog.Type.reward,"看视频");
                }
            }
        }
        return Message.success("请求成功");
    }
}
