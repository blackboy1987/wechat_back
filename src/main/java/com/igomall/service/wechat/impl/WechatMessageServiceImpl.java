package com.igomall.service.wechat.impl;

import com.igomall.entity.other.BaiDuTag;
import com.igomall.entity.wechat.WeChatMessage;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.other.BaiDuTagService;
import com.igomall.service.wechat.WechatMessageService;
import com.igomall.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WechatMessageServiceImpl extends BaseServiceImpl<WeChatMessage,Long> implements WechatMessageService {

    @Autowired
    private BaiDuTagService baiDuTagService;

   public String getHelpMessage(){
       List<BaiDuTag> baiDuTags = baiDuTagService.findAll();
       StringBuffer sb = new StringBuffer();
       if(!baiDuTags.isEmpty()){
           sb.append("请回复数字选择相关服务\n");
           for (BaiDuTag baiDuTag:baiDuTags) {
               sb.append("\n"+baiDuTag.getCode()+"   "+baiDuTag.getName()+" 课程");
           }
       }
       sb.append("\n\n回复“?”显示帮助菜单");
       return sb.toString();
    }

    public WeChatMessage saveMessage(Map<String,String> map){
        WeChatMessage weChatMessage = JsonUtils.toObject(JsonUtils.toJson(map),WeChatMessage.class);
        return super.save(weChatMessage);
    }
    public WeChatMessage updateMessage(WeChatMessage weChatMessage,String receiveContent){
       try {
           if(weChatMessage!=null&&!weChatMessage.isNew()){
               weChatMessage.setReceiveContent(receiveContent);
               return super.update(weChatMessage);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return weChatMessage;
    }
}
