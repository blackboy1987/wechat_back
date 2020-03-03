
package com.igomall.service.wechat;

import com.igomall.entity.Area;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface WechatUserService extends BaseService<WeChatUser, Long> {

	WeChatUser findByFromUserName(String fromUserName);

	WeChatUser saveUser(String fromUserName);
}