package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import org.apache.commons.lang3.ArrayUtils;
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
 * 课程
 */
@Entity
@Table(name = "edu_course")
public class Course extends OrderedEntity<Long> {

    /**
     * 点击数缓存名称
     */
    public static final String HITS_CACHE_NAME = "coursetHits";

    /**
     * 属性值属性个数
     */
    public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

    /**
     * 属性值属性名称前缀
     */
    public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

    @Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    @JsonView({CommonListView.class})
    private String sn;

    @Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    private String biliSn;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    @JsonView({CommonListView.class})
    private String title;

    private String memo;

    @Lob
    private String description;

    @JsonView({CommonListView.class})
    private Long duration;;

    @NotEmpty
    @Length(max = 400)
    @Column(nullable = false,length = 400)
    @JsonView({CommonListView.class})
    private String image;

    /**
     * 商品分类
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CourseCategory courseCategory;

    /**
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    private Set<CourseTag> courseTags = new HashSet<>();

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

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    private String bilibiliUrl;

    @JsonView({CommonListView.class})
    private Integer videos;

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public CourseCategory getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    public Set<CourseTag> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(Set<CourseTag> courseTags) {
        this.courseTags = courseTags;
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

    public String getBiliSn() {
        return biliSn;
    }

    public void setBiliSn(String biliSn) {
        this.biliSn = biliSn;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    @Transient
    public String getCourseCategoryName(){
        if(courseCategory!=null){
            return courseCategory.getName();
        }
        return null;
    }

    @Transient
    public Long[] getCourseCategoryIds(){
        if(courseCategory!=null){
            return ArrayUtils.add(courseCategory.getParentIds(),courseCategory.getId());
        }
        return null;
    }

    public void init(){
        setCourseCategory(null);
        setCourseComments(new HashSet<>());
        setCourseConsultations(new HashSet<>());
        setCourseTags(new HashSet<>());
        setDescription(null);
        setImage("https://i0.hdslb.com/bfs/sycp/creative_img/202001/0b1f67bd80d8ac9bda067ed904c18c82.jpg");
        setMemo(null);
        setOrder(0);
        setSn(null);
        setTitle(null);
        setBilibiliUrl(null);
        setBiliSn(null);
    }

    public String getBilibiliUrl() {
        return bilibiliUrl;
    }

    public void setBilibiliUrl(String bilibiliUrl) {
        this.bilibiliUrl = bilibiliUrl;
    }

    public interface CommonListView extends CommonView{}
}
