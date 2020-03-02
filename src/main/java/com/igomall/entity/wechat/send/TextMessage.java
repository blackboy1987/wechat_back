package com.igomall.entity.wechat.send;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class TextMessage extends BaseMessage {

    @JacksonXmlCData
    @JacksonXmlProperty(localName="Content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
