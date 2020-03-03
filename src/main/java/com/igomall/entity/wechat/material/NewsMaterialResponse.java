package com.igomall.entity.wechat.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsMaterialResponse implements Serializable {

    private List<NewsMaterial> item = new ArrayList<>();

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("item_count")
    private Integer itemCount;

    public List<NewsMaterial> getItem() {
        return item;
    }

    public void setItem(List<NewsMaterial> item) {
        this.item = item;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}
