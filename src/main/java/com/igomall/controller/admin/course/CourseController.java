package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.FolderService;
import com.igomall.service.course.LessonService;
import com.igomall.util.JsonUtils;
import com.igomall.util.MediaUtils;
import com.igomall.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private LessonService lessonService;


    @GetMapping("/course")
    private String course(){
        String path = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【01】2019 web前端课程大全（6套）\\01：2019黑吗WEB前端视频最新课程基础班+就业班（推荐）";
        File parent = new File(path);
        File[] files = parent.listFiles();
        for (File file:files) {
            Course course = new Course();
            course.setPath(file.getAbsolutePath());
            course.setTitle(file.getName());
            courseService.save(course);
        }
        return "course";
    }

    @GetMapping("/folder")
    private String folder(){
        List<Folder> folders = folderService.findAll();
        for (Folder parent:folders) {
            File[] files = new File(parent.getPath()).listFiles();
            for (File file:files) {
                if(file.isDirectory()){
                    Folder folder = new Folder();
                    folder.setName(file.getName());
                    folder.setPath(file.getAbsolutePath());
                    folder.setParent(parent);
                    folderService.save(folder);
                }else{
                    Lesson lesson = new Lesson();
                    lesson.setTitle(file.getName());
                    lesson.setPath(file.getAbsolutePath());
                    lesson.setFolder(parent);
                    lesson.setPlayUrls(new ArrayList<>());
                    lesson.setCourse(parent.getCourse());
                    lessonService.save(lesson);
                }
            }
        }
        return "course";
    }


    @GetMapping("/lesson")
    private String lesson(){
        List<Lesson> lessons = lessonService.findAll();
        for (Lesson lesson:lessons) {
           /* Map<String,String> props = MediaUtils.getInfo(new File(lesson.getPath()));
            lesson.setProps(props);*/
           if(lesson.getPlayUrls()==null){
               lesson.setPlayUrls(new ArrayList<>());
           }
            lessonService.update(lesson);
        }
        return "lesson";
    }

    @GetMapping("/bilibili")
    private BilibiliRespose bilibili(String avId,Long folderId){
        String url = "https://api.bilibili.com/x/player/pagelist?aid="+avId;
        String result = WebUtils.get(url,null);

        BilibiliRespose bilibiliRespose = JsonUtils.toObject(result,BilibiliRespose.class);
        List<Lesson> lessons = lessonService.findList(null,folderService.find(folderId),null,null,null);
        for (BilibiliRespose.LessonList lessonList:bilibiliRespose.getData()) {
            for (Lesson lesson:lessons) {
                // {"duration":"195956","size":"13598720","width":"1364","height":"768"}
                if(StringUtils.equalsAnyIgnoreCase(lesson.getTitle(),lessonList.getPart()+".mp4")){
                    lesson.setTitle(lessonList.getPart());
                    lesson.getProps().put("duration",lessonList.getDuration()+"");
                    lesson.getProps().put("width",lessonList.getDimension().get("width")+"");
                    lesson.getProps().put("height",lessonList.getDimension().get("height")+"");
                    lesson.addPlayUrl("哔哩哔哩","https://www.bilibili.com/video/av"+avId+"?p="+lessonList.getPage());
                    lessonService.update(lesson);
                    continue;
                }


            }
        }

        return bilibiliRespose;
    }




    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BilibiliRespose implements Serializable{
        private Integer code;

        private String message;

        private Integer ttl;

        private List<LessonList> data = new ArrayList<>();

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getTtl() {
            return ttl;
        }

        public void setTtl(Integer ttl) {
            this.ttl = ttl;
        }

        public List<LessonList> getData() {
            return data;
        }

        public void setData(List<LessonList> data) {
            this.data = data;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LessonList implements Serializable{
            private String cid;

            private Integer page;

            private String part;

            private Long duration;

            private String vid;

            private String weblink;

            private Map<String,Integer> dimension = new HashMap<>();


            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public Integer getPage() {
                return page;
            }

            public void setPage(Integer page) {
                this.page = page;
            }

            public String getPart() {
                return part;
            }

            public void setPart(String part) {
                this.part = part;
            }

            public Long getDuration() {
                return duration;
            }

            public void setDuration(Long duration) {
                this.duration = duration;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getWeblink() {
                return weblink;
            }

            public void setWeblink(String weblink) {
                this.weblink = weblink;
            }

            public Map<String, Integer> getDimension() {
                return dimension;
            }

            public void setDimension(Map<String, Integer> dimension) {
                this.dimension = dimension;
            }
        }
    }
}
