package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 章节的视频
 */
@Entity
@Table(name = "edu_lesson")
public class Lesson extends OrderedEntity<Long> {

    @JsonView(BaseView.class)
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String sn;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Course course;

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    private String title;

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    private String bilibiliUrl;

    private Long duration;

    private Long bilibiliCid;

    /**
     * 评论
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CourseComment> courseComments = new HashSet<>();

    /**
     * 咨询
     */
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CourseConsultation> courseConsultations = new HashSet<>();

    /**
     * 获取编号
     *
     * @return 编号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置编号
     *
     * @param sn
     *            编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<CourseComment> getCourseComments() {
        return courseComments;
    }

    public void setCourseComments(Set<CourseComment> courseComments) {
        this.courseComments = courseComments;
    }

    public Set<CourseConsultation> getCourseConsultations() {
        return courseConsultations;
    }

    public void setCourseConsultations(Set<CourseConsultation> courseConsultations) {
        this.courseConsultations = courseConsultations;
    }

    public String getBilibiliUrl() {
        return bilibiliUrl;
    }

    public void setBilibiliUrl(String bilibiliUrl) {
        this.bilibiliUrl = bilibiliUrl;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getBilibiliCid() {
        return bilibiliCid;
    }

    public void setBilibiliCid(Long bilibiliCid) {
        this.bilibiliCid = bilibiliCid;
    }
}
