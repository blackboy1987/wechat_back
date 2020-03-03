package com.igomall.entity.wechat.receive;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.igomall.entity.wechat.MsgType;

/**
 * 微信用户向公众账号发消息的基类
 */
public class BaseMessage {

    @JacksonXmlCData
    @JacksonXmlProperty(localName="ToUserName")
    private String toUserName;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName="CreateTime")
    private Long createTime;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="MsgType")
    private MsgType msgType;

    @JacksonXmlProperty(localName="MsgId")
    private Long msgId;


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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
}
