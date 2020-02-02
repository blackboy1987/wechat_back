package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "yy_activity")
public class Activity extends BaseEntity<Long> {

    @NotEmpty(message = "请填写活动名称")
    @Column(nullable = false)
    @JsonView({ListView.class})
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
    @JsonView({ListView.class})
    private Date startTime;

    @NotNull(message = "请填写活动开始时间")
    @NotNull
    @Column(nullable = false)
    @JsonView({ListView.class})
    private Date finishTime;


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
}
