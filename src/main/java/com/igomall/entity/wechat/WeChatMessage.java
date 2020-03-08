package com.igomall.entity.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.BaseEntity;
import com.vdurmont.emoji.EmojiParser;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "edu_wechat_message")
public class WeChatMessage extends BaseEntity<Long> {

    @JsonProperty("Content")
    private String content;

    @JsonProperty("CreateTime")
    private Long createTime;

    @JsonProperty("ToUserName")
    private String toUserName;

    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("MsgType")
    private String msgType;

    @JsonProperty("MsgId")
    private Long msgId;

    @JsonProperty("MediaId")
    private String mediaId;

    @JsonProperty("PicUrl")
    private String picUrl;

    @JsonProperty("ThumbMediaId")
    private String thumbMediaId;

    @JsonProperty("Location_X")
    private Double locationX;

    @JsonProperty("Location_Y")
    private Double locationY;

    @JsonProperty("Label")
    private String label;

    @JsonProperty("Scale")
    private Integer scale;

    @Column(length = 4000)
    private String receiveContent;

    public String getContent() {
        return EmojiParser.parseToUnicode(content);
    }

    public void setContent(String content) {
        this.content = EmojiParser.parseToAliases(content);
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getReceiveContent() {
        return receiveContent;
    }

    public void setReceiveContent(String receiveContent) {
        this.receiveContent = receiveContent;
    }
}
