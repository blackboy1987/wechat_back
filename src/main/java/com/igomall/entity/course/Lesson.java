package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.BaseAttributeConverter;
import com.igomall.entity.OrderedEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "edu_lesson")
public class Lesson extends OrderedEntity<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    @JsonView({ListView.class,EditView.class})
    private String path;

    @JsonView({ListView.class,EditView.class,JsonApiView.class})
    @NotEmpty
    private String title;

    @Column(length = 1000)
    @Convert(converter = ProsConverter.class)
    private Map<String,String> props = new HashMap<>();

    @JsonView({EditView.class})
    @Column(length = 4000)
    @Convert(converter = PlayUrlConverter.class)
    private List<PlayUrl> playUrls = new ArrayList<>();

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public List<PlayUrl> getPlayUrls() {
        return playUrls;
    }

    public void setPlayUrls(List<PlayUrl> playUrls) {
        this.playUrls = playUrls;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlayUrl implements Serializable, Comparable<PlayUrl> {

        @JsonView({JsonApiView.class,EditView.class})
        private String name;

        @JsonView({JsonApiView.class,EditView.class})
        private String url;

        @JsonView({ListView.class,EditView.class})
        private String order;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        /**
         * 实现compareTo方法
         *
         * @param playUrl
         *            商品图片
         * @return 比较结果
         */
        public int compareTo(PlayUrl playUrl) {
            if (playUrl == null) {
                return 1;
            }
            return new CompareToBuilder().append(getOrder(), playUrl.getOrder()).toComparison();
        }

        /**
         * 重写equals方法
         *
         * @param obj
         *            对象
         * @return 是否相等
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        /**
         * 重写hashCode方法
         *
         * @return HashCode
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }


    public void addPlayUrl(String name,String url){
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(url)){
            return;
        }

        List<PlayUrl> playUrls = new ArrayList<>();
        playUrls.addAll(getPlayUrls());
        Boolean flag = false;
        for (PlayUrl playUrl:playUrls) {
            if(StringUtils.equalsAnyIgnoreCase(name,playUrl.getName())){
                playUrl.setUrl(url);
                flag = true;
            }
        }
        if(!flag){
            PlayUrl playUrl = new PlayUrl();
            playUrl.setUrl(url);
            playUrl.setName(name);
            playUrls.add(playUrl);
            setPlayUrls(playUrls);
        }
    }

    @Transient
    @JsonView({ListView.class,EditView.class})
    public Long getCourseId(){
        if(course!=null){
            return course.getId();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getCourseTitle(){
        if(course!=null){
            return course.getTitle();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class,EditView.class})
    public Long getFolderId(){
        if(folder!=null){
            return folder.getId();
        }
        return null;
    }

    @Transient
    @JsonView({ListView.class})
    public String getFolderName(){
        if(folder!=null){
            return folder.getName();
        }
        return null;
    }

    /**
     * 类型转换 - 可选项
     *
     * @author blackboy
     * @version 1.0
     */
    @Converter
    public static class ProsConverter extends BaseAttributeConverter<Map<String,String>> {
    }

    @Converter
    public static class PlayUrlConverter extends BaseAttributeConverter<List<PlayUrl>> {
    }
}
