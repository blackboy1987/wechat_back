
package com.igomall.wechat.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.service.BaseService;
import com.igomall.wechat.entity.WeChatUserTag;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface WeChatUserTagService extends BaseService<WeChatUserTag, Long> {

	WeChatUserTag findByName(String name);

	Page<WeChatUserTag> findPage(Pageable pageable, String name, Date beginDate, Date endDate);
}