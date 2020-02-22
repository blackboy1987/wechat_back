package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "edu_course")
public class Course extends OrderedEntity<Long> {

    @JsonView({JsonApiView.class,EditView.class})
    private String title;

    private String path;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("order asc")
    private Set<Lesson> lessons = new HashSet<>();

    @OrderBy("order asc")
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Folder> folders = new HashSet<>();

    /**
     * 0: 待提交
     * 1： 待审核
     * 2： 审核通过
     * 3： 审核失败
     * 4：后台下架
     * 5：用户自己下架
     * 6：已删除
     */
    private Integer status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public interface IndexView extends ListView{

    }
}
