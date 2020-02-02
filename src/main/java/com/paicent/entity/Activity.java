package com.paicent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "yy_activity")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity extends BaseEntity<Long> {

    @NotEmpty(message = "请填写活动名称")
    @Column(nullable = false)
    @JsonView({ListView.class,EditView.class})
    private String name;

    @Column(name = "descrption")
    @JsonView({ListView.class})
    private String description;

    @NotNull
    @Column(nullable = false)
    @JsonView({ListView.class})
    private Integer status;

    @NotNull(message = "请填写活动开始时间")
    @Column(nullable = false)
    @JsonView({ListView.class,EditView.class})
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @NotNull(message = "请填写活动开始时间")
    @NotNull
    @Column(nullable = false)
    @JsonView({ListView.class,EditView.class})
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    @Transient
    @JsonView({ViewView.class,ViewView1.class})
    private List<ConfigInfo> configInfos = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public List<ConfigInfo> getConfigInfos() {
        return configInfos;
    }

    public void setConfigInfos(List<ConfigInfo> configInfos) {
        this.configInfos = configInfos;
    }

    public interface ViewView extends EditView{}
    public interface ViewView1 extends EditView{}
}
