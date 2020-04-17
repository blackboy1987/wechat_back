
package com.igomall.wechat.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.wechat.dao.WechatUserDao;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.wechat.service.WechatUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class WechatUserServiceImpl extends BaseServiceImpl<WeChatUser, Long> implements WechatUserService {

    @Autowired
    private WechatUserDao wechatUserDao;

    public WeChatUser findByFromUserName(String fromUserName){
        return wechatUserDao.find("fromUserName",fromUserName);
    }

    public WeChatUser saveUser(String fromUserName){
        if(StringUtils.isNotEmpty(fromUserName)){
            WeChatUser weChatUser = findByFromUserName(fromUserName);
            if(weChatUser==null){
                weChatUser = new WeChatUser();
                weChatUser.setFromUserName(fromUserName);
                return super.save(weChatUser);
            }

            return weChatUser;
        }
        return null;
    }

    public Page<WeChatUser> findPage(Pageable pageable, Integer status, Date beginDate, Date endDate){
        return wechatUserDao.findPage(pageable,status,beginDate,endDate);
    }

    public String updateInfo(String openId,String info,String type){
        WeChatUser weChatUser = wechatUserDao.find("fromUserName",openId);
        if(weChatUser==null){
            weChatUser = saveUser(openId);
        }
        if(weChatUser!=null){
            if(StringUtils.equalsAnyIgnoreCase(type,"weChatId")){
                weChatUser.setWeChatId(info.substring(2));
            }else if(StringUtils.equalsAnyIgnoreCase(type,"nickname")){
                weChatUser.setNickname(info.substring(2));
            }else if(StringUtils.equalsAnyIgnoreCase(type,"name")){
                weChatUser.setName(info.substring(2));

            }else if(StringUtils.equalsAnyIgnoreCase(type,"address")){
                weChatUser.setAddress(info.substring(2));

            }else if(StringUtils.equalsAnyIgnoreCase(type,"mobile")){
                weChatUser.setMobile(info.substring(2));
            }else{
                return "输入数据不符合规则。绑定失败";
            }
            super.update(weChatUser);
            return "绑定成功";
        }

        return "绑定失败";
    }
}