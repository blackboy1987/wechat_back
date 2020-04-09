
package com.igomall.dao.wechat;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.wechat.WeChatMessage;

import java.util.Date;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface WechatMessageDao extends BaseDao<WeChatMessage, Long> {

    Page<WeChatMessage> findPage(Pageable pageable, String content, String toUserName, String fromUserName, String msgType, Date beginDate, Date endDate);
}