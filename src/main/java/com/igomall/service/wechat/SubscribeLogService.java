
package com.igomall.service.wechat;

import com.igomall.entity.wechat.SubscribeLog;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.entity.wechat.WeChatUserLog;
import com.igomall.service.BaseService;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface SubscribeLogService extends BaseService<SubscribeLog, Long> {

    SubscribeLog save(WeChatUser weChatUser,String memo,Integer status);
}