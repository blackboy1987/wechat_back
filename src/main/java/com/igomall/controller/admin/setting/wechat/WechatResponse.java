package com.igomall.controller.admin.setting.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatResponse implements Serializable {

    @JsonProperty("appmsg_list")
    private List<WechatArticle> list = new ArrayList<>();

    @JsonProperty("has_more")
    private Boolean hasMore;

    public List<WechatArticle> getList() {
        return list;
    }

    public void setList(List<WechatArticle> list) {
        this.list = list;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
