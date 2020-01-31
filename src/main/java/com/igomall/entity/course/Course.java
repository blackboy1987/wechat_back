package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
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

    /**
     * 排序类型
     */
    public enum OrderType {

        /**
         * 置顶降序
         */
        topDesc,

        /**
         * 价格升序
         */
        priceAsc,

        /**
         * 价格降序
         */
        priceDesc,

        /**
         * 销量降序
         */
        salesDesc,

        /**
         * 评分降序
         */
        scoreDesc,

        /**
         * 日期降序
         */
        dateDesc
    }

    /**
     * 排名类型
     */
    public enum RankingType {

        /**
         * 评分
         */
        score,

        /**
         * 评分数
         */
        scoreCount,

        /**
         * 周点击数
         */
        weekHits,

        /**
         * 月点击数
         */
        monthHits,

        /**
         * 点击数
         */
        hits,

        /**
         * 周销量
         */
        weekSales,

        /**
         * 月销量
         */
        monthSales,

        /**
         * 销量
         */
        sales
    }

    @Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
    @Length(max = 100)
    @Pattern(regexp = "^[0-9a-zA-Z_-]+$")
    @Column(nullable = false, updatable = false, unique = true)
    @JsonView({ListView.class})
    private String sn;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();

    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false,length = 100)
    @JsonView({ListView.class})
    private String title;

    private String memo;

    @Lob
    private String description;

    @JsonView({ListView.class})
    private Long duration;

    @NotEmpty
    @Length(max = 400)
    @Column(nullable = false,length = 400)
    @JsonView({ListView.class})
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
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    private Set<CourseTopic> courseTopics = new HashSet<>();

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

    @JsonView({ListView.class})
    private Integer videos;

    /**
     * 点击数
     */
    @Column(nullable = false)
    @JsonView({Article.ViewView.class,BaseView.class,ListView.class})
    private Long hits;


    /**
     * 总评分
     */
    @Column(nullable = false)
    private Long totalScore;

    /**
     * 评分数
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false)
    private Long scoreCount;

    /**
     * 评分
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NumericField
    @Column(nullable = false, precision = 12, scale = 6)
    private Float score;

    /**
     * 是否发布
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NotNull
    @Column(nullable = false)
    private Boolean isPublication;

    /**
     * 是否置顶
     */
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @NotNull
    @Column(nullable = false)
    private Boolean isTop;

    /**
     * 作者
     */
    @JsonView({ListView.class})
    @Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
    @Length(max = 200)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    @JsonIgnore
    private Member member;

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

    public Set<CourseTopic> getCourseTopics() {
        return courseTopics;
    }

    public void setCourseTopics(Set<CourseTopic> courseTopics) {
        this.courseTopics = courseTopics;
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

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    /**
     * 获取评分数
     *
     * @return 评分数
     */
    public Long getScoreCount() {
        return scoreCount;
    }

    /**
     * 设置评分数
     *
     * @param scoreCount
     *            评分数
     */
    public void setScoreCount(Long scoreCount) {
        this.scoreCount = scoreCount;
    }

    /**
     * 获取评分
     *
     * @return 评分
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置评分
     *
     * @param score
     *            评分
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取总评分
     *
     * @return 总评分
     */
    public Long getTotalScore() {
        return totalScore;
    }

    /**
     * 设置总评分
     *
     * @param totalScore
     *            总评分
     */
    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取是否发布
     *
     * @return 是否发布
     */
    public Boolean getIsPublication() {
        return isPublication;
    }

    /**
     * 设置是否发布
     *
     * @param isPublication
     *            是否发布
     */
    public void setIsPublication(Boolean isPublication) {
        this.isPublication = isPublication;
    }

    /**
     * 获取是否置顶
     *
     * @return 是否置顶
     */
    public Boolean getIsTop() {
        return isTop;
    }

    /**
     * 设置是否置顶
     *
     * @param isTop
     *            是否置顶
     */
    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    /**
     * 获取点击数
     *
     * @return 点击数
     */
    public Long getHits() {
        return hits;
    }

    /**
     * 设置点击数
     *
     * @param hits
     *            点击数
     */
    public void setHits(Long hits) {
        this.hits = hits;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    /**
     * 获取作者
     *
     * @return 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author
     *            作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonView({ListView.class})
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
    }

    public interface CommonListView extends CommonView{}
}
