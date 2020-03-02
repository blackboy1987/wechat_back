package com.igomall.entity.wechat.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BaseEvent {

    @JacksonXmlProperty(localName="CreateTime")
    @JsonProperty("CreateTime")
    private Long createTime;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="EventKey")
    @JsonProperty("EventKey")
    private String eventKey;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="Event")
    @JsonProperty("Event")
    private String event;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="ToUserName")
    @JsonProperty("ToUserName")
    private String toUserName;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="FromUserName")
    @JsonProperty("FromUserName")
    private String fromUserName;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="MsgType")
    @JsonProperty("MsgType")
    private String msgType = "event";

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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
}
