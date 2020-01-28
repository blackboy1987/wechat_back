package com.igomall.entity;

import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "edu_material")
public class Material extends BaseEntity<Long> {

    /**
     * 类型
     */
    public enum Type{

        /**
         * 图片
         */
        image,

        /**
         * 音频
         */
        audio,

        /**
         * 视频
         */
        video,

        /**
         * 其他文件
         */
        file,
    }


    @NotEmpty
    @Column(nullable = false,unique = true)
    private String url;

    private String title;

    private String memo;

    private Long size;

    private Integer width;

    private Integer height;

    @NotNull
    @Column(nullable = false,updatable = false)
    private String contentType;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Type type;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
