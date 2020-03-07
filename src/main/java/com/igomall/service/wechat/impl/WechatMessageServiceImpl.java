package com.igomall.service.wechat.impl;

import com.igomall.common.Filter;
import com.igomall.dao.active.ShareUrlDao;
import com.igomall.dao.active.ShareUrlRecordDao;
import com.igomall.entity.activity.ShareUrl;
import com.igomall.entity.activity.ShareUrlRecord;
import com.igomall.entity.other.BaiDuResource;
import com.igomall.entity.other.BaiDuTag;
import com.igomall.entity.wechat.WeChatMessage;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.other.BaiDuResourceService;
import com.igomall.service.other.BaiDuTagService;
import com.igomall.service.wechat.WechatMessageService;
import com.igomall.service.wechat.WechatUserService;
import com.igomall.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class WechatMessageServiceImpl extends BaseServiceImpl<WeChatMessage,Long> implements WechatMessageService {

    @Autowired
    private BaiDuResourceService baiDuResourceService;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private ShareUrlRecordDao shareUrlRecordDao;
    @Autowired
    private ShareUrlDao shareUrlDao;


   public String getHelpMessage(String fromUserName){
       return getShareUrl(fromUserName);


       /*StringBuffer sb = new StringBuffer();
       sb.append("请回复:课程+课程关键字 获取课程信息\n");
       sb.append("\n比如:课程html 获取包含html关键字的课程\n");
       sb.append("\n\n回复“wyfx”获取分享文章");
       sb.append("\n\n回复“xxsb”获取信息绑定操作");
       sb.append("\n\n回复“yzm”获取idea全家桶注册码");
       sb.append("\n\n回复“?”显示帮助菜单");
       return sb.toString();*/
    }

    public WeChatMessage saveMessage(Map<String,String> map){
       // 保存一下用户。这样可以把以前已经关注的用户也可以保存进来
        CompletableFuture.runAsync(()->{
            wechatUserService.saveUser(map.get("FromUserName"));
        });


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


    public String getCourseListInfo(String title){
       List<Filter> filters = new ArrayList<>();
       filters.add(new Filter("title", Filter.Operator.like,"%"+title+"%"));
        List<BaiDuResource> baiDuResources = baiDuResourceService.findList(null,filters,null);
        StringBuffer sb = new StringBuffer();
        if(!baiDuResources.isEmpty()){
            sb.append("已为您找到如下课程：\n");
            for (BaiDuResource baiDuResource:baiDuResources) {
                sb.append("\n"+baiDuResource.getCode()+"  "+baiDuResource.getTitle());
            }
            sb.append("\n\n输入课程前面编号获取课程地址");
        }else{
            sb.append("暂未找到相关课程。");
        }


        sb.append("\n\n回复“?”显示帮助菜单");
        return sb.toString();
    }

    @Override
    public String getShareUrl(String fromUserName) {
        Random random = new Random();
        List<ShareUrl> shareUrls = shareUrlDao.findList(null,null,null,null);
        StringBuffer sb = new StringBuffer();
        if(shareUrls!=null&&!shareUrls.isEmpty()){
            Integer index = random.nextInt(shareUrls.size());
            sb.append("正在举办积赞送礼品活动。\n\n");
            sb.append("规则：");
            sb.append("\n1：参与用户必须关注公众号。");
            sb.append("\n2：分享朋友圈集满200赞。");
            sb.append("\n3：分享开始48小时内有效。");
            sb.append("\n4：集满赞将截图发给公众号。");
            sb.append("\n\n分享资源：\n");
            sb.append("<a href=\""+shareUrls.get(index).getUrl()+"\">"+shareUrls.get(index).getTitle()+"</a>");

            // 保存获取的记录
            ShareUrlRecord shareUrlRecord = new ShareUrlRecord();
            shareUrlRecord.setShareUrl(shareUrls.get(index));
            WeChatUser weChatUser = wechatUserService.findByFromUserName(fromUserName);
            if(weChatUser.getStatus()==0){
                sb.append("您未关注公众号，无法参与活动。");
            }else{
                shareUrlRecord.setWeChatUser(weChatUser);
                shareUrlRecordDao.persist(shareUrlRecord);
            }
        }else{
            sb.append("暂未找到相关活动文章。");
        }

        sb.append("\n\n回复“?”显示帮助菜单");
        return sb.toString();
    }
    @Override
    public String getXxsbInfo(){
        StringBuffer sb = new StringBuffer();
        sb.append("发送：微信+您的微信号。绑定微信号");
        sb.append("\n发送：昵称+您的微信昵称。绑定微信昵称");
        sb.append("\n发送：姓名+收件人姓名。绑定收件人信息");
        sb.append("\n发送：地址+您的收货地址。绑定收件地址");
        sb.append("\n发送：电话+您的收货联系电话。绑定收件人的联系电话");
        sb.append("\n\n回复“?”显示帮助菜单");
        return sb.toString();
    }
}
