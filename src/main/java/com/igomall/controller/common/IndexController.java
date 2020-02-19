package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Order;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.FolderService;
import com.igomall.service.course.LessonService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("commonIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController{

    @Autowired
    private CourseService courseService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private LessonService lessonService;

    @PostMapping("/course")
    @JsonView(BaseEntity.ListView.class)
    public List<Course> course(){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("title"));
        return courseService.findList(null,null,orders);
    }

    @PostMapping("/folder")
    @JsonView(BaseEntity.ListView.class)
    public List<Folder> folder(Long courseId){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("name"));
        List<Folder> folders = folderService.findList(courseService.find(courseId),null,null,orders);
        return folders;
    }

    @PostMapping("/lesson")
    @JsonView(BaseEntity.ListView.class)
    public List<Lesson> lesson(Long courseId,Long folderId){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("title"));
        List<Lesson> lessons = lessonService.findList(courseService.find(courseId),folderService.find(folderId),null,null,orders);
        return lessons;
    }

}
