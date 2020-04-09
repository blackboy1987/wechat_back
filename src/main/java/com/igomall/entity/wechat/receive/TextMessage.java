package com.igomall.entity.wechat.receive;

/**
 * 接受的文本消息
 */
public class TextMessage extends BaseMessage {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
