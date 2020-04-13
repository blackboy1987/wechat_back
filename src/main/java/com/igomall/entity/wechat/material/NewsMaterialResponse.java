package com.igomall.entity.wechat.material;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.BaseEntity;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsMaterialResponse implements Serializable {

    private List<Material> item = new ArrayList<>();

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("item_count")
    private Integer itemCount;

    public List<Material> getItem() {
        return item;
    }

    public void setItem(List<Material> item) {
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


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Material implements Serializable{

        @JsonProperty("media_id")
        private String mediaId;

        @JsonProperty("update_time")
        private Date updateTime;

        private String name;

        private String url;

        private List<String> tags = new ArrayList<>();

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

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

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
