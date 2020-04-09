
package com.igomall.service.wechat.impl;

import com.igomall.entity.wechat.SubscribeLog;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.SubscribeLogService;
import org.springframework.stereotype.Service;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class SubscribeLogServiceImpl extends BaseServiceImpl<SubscribeLog, Long> implements SubscribeLogService {

    @Override
    public SubscribeLog save(WeChatUser weChatUser, String memo, Integer status) {
        if(weChatUser!=null&&!weChatUser.isNew()){
            SubscribeLog subscribeLog = new SubscribeLog();
            subscribeLog.setMemo(memo);
            subscribeLog.setStatus(status);
            subscribeLog.setWeChatUser(weChatUser);
            return super.save(subscribeLog);
        }
        return null;
    }
}