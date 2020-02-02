package com.paicent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "config_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigInfo extends BaseEntity<Long> {

    @NotEmpty(message = "请选择区域")
    @Column(nullable = false)
    @JsonView({Activity.ViewView.class,Activity.ViewView1.class})
    private String district;

    @NotNull(message = "请选择活动")
    @Column(name = "yy_activity_id",nullable = false,updatable = false)
    @JsonView({Activity.ViewView.class})
    private Long activityId;

    @NotNull(message = "必填")
    @Column(nullable = false)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date yyStartTime;

    @NotNull(message = "必填")
    @Column(nullable = false)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date yyFinishTime;

    @NotNull(message = "必填")
    @Column(nullable = false)
    @JsonView({Activity.ViewView.class,Activity.ViewView1.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date applyStartTime;

    @NotNull(message = "必填")
    @Column(nullable = false)
    @JsonView({Activity.ViewView.class,Activity.ViewView1.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date applyFinishTime;

    @NotNull(message = "必填")
    @Column(nullable = false)
    @Min(value = 1, message = "数量不能小于1")
    @JsonView({Activity.ViewView.class})
    private Integer kzCount;

    @NotNull(message = "必填")
    @Column(nullable = false)
    @Min(value = 0, message = "数量不能小于0")
    @JsonView({Activity.ViewView.class})
    private Integer kzYyCount;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Date createTime;

    @NotNull
    @Column(nullable = false)
    private Date modifyTime;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getYyStartTime() {
        return yyStartTime;
    }

    public void setYyStartTime(Date yyStartTime) {
        this.yyStartTime = yyStartTime;
    }

    public Date getYyFinishTime() {
        return yyFinishTime;
    }

    public void setYyFinishTime(Date yyFinishTime) {
        this.yyFinishTime = yyFinishTime;
    }

    public Date getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(Date applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public Date getApplyFinishTime() {
        return applyFinishTime;
    }

    public void setApplyFinishTime(Date applyFinishTime) {
        this.applyFinishTime = applyFinishTime;
    }

    public Integer getKzCount() {
        return kzCount;
    }

    public void setKzCount(Integer kzCount) {
        this.kzCount = kzCount;
    }

    public Integer getKzYyCount() {
        return kzYyCount;
    }

    public void setKzYyCount(Integer kzYyCount) {
        this.kzYyCount = kzYyCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @PrePersist
    public void preSave(){
        this.createTime = new Date();
        this.modifyTime = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        this.modifyTime = new Date();
    }
}
