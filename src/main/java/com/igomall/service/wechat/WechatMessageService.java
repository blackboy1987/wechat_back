package com.igomall.service.wechat;

import com.igomall.entity.wechat.WeChatMessage;
import com.igomall.service.BaseService;

import java.util.Map;

public interface WechatMessageService extends BaseService<WeChatMessage,Long> {

    String getHelpMessage(String fromUserName);

    WeChatMessage saveMessage(Map<String,String> map);
    WeChatMessage updateMessage(WeChatMessage weChatMessage,String receiveContent);


    String getCourseListInfo(String title);
    String getShareUrl(String fromUserName);

    String getXxsbInfo();
}
