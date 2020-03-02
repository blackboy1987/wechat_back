package com.igomall.controller.wechat;

import com.igomall.entity.other.BaiDuResource;
import com.igomall.entity.other.BaiDuTag;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.entity.wechat.WeChatUserLog;
import com.igomall.entity.wechat.event.BaseEvent;
import com.igomall.entity.wechat.response.WeChatUserResponse;
import com.igomall.entity.wechat.send.TextMessage;
import com.igomall.service.other.BaiDuResourceService;
import com.igomall.service.other.BaiDuTagService;
import com.igomall.service.wechat.WechatMessageService;
import com.igomall.service.wechat.WechatUserLogService;
import com.igomall.service.wechat.WechatUserService;
import com.igomall.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController("wechatIndexController")
@RequestMapping("/wechat")
public class IndexController {

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatUserLogService wechatUserLogService;
    @Autowired
    private WechatMessageService messageService;
    @Autowired
    private BaiDuTagService baiDuTagService;
    @Autowired
    private BaiDuResourceService baiDuResourceService;

    @GetMapping
    public String index(String signature,String timestamp,String nonce,String echostr){
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "error";
    }


    @PostMapping
    public String index(HttpServletRequest request){
        Map<String,String> map = WechatUtils.parseXml(request);
        String event = map.get("Event");
        if(StringUtils.isNotEmpty(event)){
            // 处理订阅事件
            return subscribe(map);
        }else{
            return parseMessage(map);
        }
    }


    private String subscribe(Map<String,String> map){
        String event = map.get("Event");
        BaseEvent baseEvent = null;
        TextMessage textMessage = null;
        WeChatUserLog weChatUserLog = new WeChatUserLog();
        weChatUserLog.setEvent(event);
        baseEvent = JsonUtils.toObject(JsonUtils.toJson(map), BaseEvent.class);
        WeChatUser weChatUser = wechatUserService.findByFromUserName(baseEvent.getFromUserName());
        Integer status = 0;
        if(StringUtils.equalsAnyIgnoreCase(event,"unsubscribe")){
            // 取消关注
            status = 0;
            weChatUserLog.setMemo("取消关注");
        }else if(StringUtils.equalsAnyIgnoreCase(event,"subscribe")){
            status = 1;
            weChatUserLog.setMemo("关注");

            StringBuffer sb = new StringBuffer();
            sb.append("感谢您的关注\n\n");
            sb.append(messageService.getHelpMessage());
            // 关注就给回复消息
            textMessage = new TextMessage();
            textMessage.setContent(sb.toString());
            textMessage.setFromUserName(baseEvent.getToUserName());
            textMessage.setToUserName(baseEvent.getFromUserName());
            textMessage.setMsgType("text");
        }
        if(weChatUser==null){
            // 创建用户
            weChatUser = new WeChatUser();
            WeChatUserResponse weChatUserResponse = WechatUtils.getUserInfo(baseEvent.getFromUserName());
            BeanUtils.copyProperties(weChatUserResponse,weChatUser,"id","fromUserName","status","updateTime");
            weChatUser.setFromUserName(baseEvent.getFromUserName());
            weChatUser.setStatus(status);
            weChatUser.setUpdateTime(new Date(baseEvent.getCreateTime()));
            wechatUserService.save(weChatUser);

        }else{
            WeChatUserResponse weChatUserResponse = WechatUtils.getUserInfo(baseEvent.getFromUserName());
            BeanUtils.copyProperties(weChatUserResponse,weChatUser,"id","fromUserName","status","updateTime");

            weChatUser.setStatus(status);
            weChatUser.setUpdateTime(new Date(baseEvent.getCreateTime()));
            wechatUserService.update(weChatUser);
        }
        weChatUserLog.setContent(JsonUtils.toJson(baseEvent));
        weChatUserLog.setWeChatUser(weChatUser);
        wechatUserLogService.save(weChatUserLog);
        if(textMessage!=null){
            return XmlUtils.toXml(textMessage);
        }
        return null;
    }

    private String parseMessage(Map<String,String> map){
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        if(StringUtils.equalsAnyIgnoreCase(msgType,"text")){
            TextMessage textMessage = null;
            if(StringUtils.equalsAnyIgnoreCase("?",content)||StringUtils.equalsAnyIgnoreCase("？",content)){
                StringBuffer sb = new StringBuffer();
                sb.append(messageService.getHelpMessage());
                // 关注就给回复消息
                textMessage = new TextMessage();
                textMessage.setContent(sb.toString());
                textMessage.setFromUserName(map.get("ToUserName"));
                textMessage.setToUserName(map.get("FromUserName"));
                textMessage.setMsgType("text");
                return XmlUtils.toXml(textMessage);
            }else{
                String regEx = "^\\d{3}$";
                String regEx1 = "^\\d{5}$";
                if(content.matches(regEx)){
                    // 分类
                    BaiDuTag baiDuTag = baiDuTagService.findByCode(content);
                    if(baiDuTag==null || baiDuTag.getBaiDuResources().isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        // 关注就给回复消息
                        textMessage = new TextMessage();
                        textMessage.setContent("无相关课程");
                        textMessage.setFromUserName(map.get("ToUserName"));
                        textMessage.setToUserName(map.get("FromUserName"));
                        textMessage.setMsgType("text");
                        return XmlUtils.toXml(textMessage);
                    }

                    StringBuffer sb = new StringBuffer();
                    sb.append("已为您找到如下课程：\n");
                    for (BaiDuResource baiDuResource:baiDuTag.getBaiDuResources()) {
                        sb.append("\n"+baiDuResource.getCode()+"  "+baiDuResource.getTitle());
                    }
                    sb.append("\n\n输入课程前面编号获取课程地址");
                    sb.append("\n\n回复“?”显示帮助菜单");
                    // 关注就给回复消息
                    textMessage = new TextMessage();
                    textMessage.setContent(sb.toString());
                    textMessage.setFromUserName(map.get("ToUserName"));
                    textMessage.setToUserName(map.get("FromUserName"));
                    textMessage.setMsgType("text");
                    return XmlUtils.toXml(textMessage);



                }else if(content.matches(regEx1)){
                    // 课程
                    BaiDuResource baiDuResource = baiDuResourceService.findByCode(content);
                    if(baiDuResource==null) {
                        StringBuffer sb = new StringBuffer();
                        // 关注就给回复消息
                        textMessage = new TextMessage();
                        textMessage.setContent("无课程");
                        textMessage.setFromUserName(map.get("ToUserName"));
                        textMessage.setToUserName(map.get("FromUserName"));
                        textMessage.setMsgType("text");
                        return XmlUtils.toXml(textMessage);
                    }

                    StringBuffer sb = new StringBuffer();
                    sb.append("课程信息如下：\n");
                    sb.append("\n课程名称："+baiDuResource.getTitle());
                    sb.append("\n课程地址："+baiDuResource.getBaiDuUrl());
                    sb.append("\n\n回复“?”显示帮助菜单");
                    // 关注就给回复消息
                    textMessage = new TextMessage();
                    textMessage.setContent(sb.toString());
                    textMessage.setFromUserName(map.get("ToUserName"));
                    textMessage.setToUserName(map.get("FromUserName"));
                    textMessage.setMsgType("text");
                    return XmlUtils.toXml(textMessage);
                }else{
                    StringBuffer sb = new StringBuffer();
                    sb.append(messageService.getHelpMessage());
                    // 关注就给回复消息
                    textMessage = new TextMessage();
                    textMessage.setContent(sb.toString());
                    textMessage.setFromUserName(map.get("ToUserName"));
                    textMessage.setToUserName(map.get("FromUserName"));
                    textMessage.setMsgType("text");
                    return XmlUtils.toXml(textMessage);
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(messageService.getHelpMessage());
        // 关注就给回复消息
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(sb.toString());
        textMessage.setFromUserName(map.get("ToUserName"));
        textMessage.setToUserName(map.get("FromUserName"));
        textMessage.setMsgType("text");
        return XmlUtils.toXml(textMessage);
    }

}
