
package com.igomall.wechat.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.wechat.entity.WeChatUserTag;

import java.util.Date;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface WeChatUserTagDao extends BaseDao<WeChatUserTag, Long> {

    Page<WeChatUserTag> findPage(Pageable pageable, String name, Date beginDate, Date endDate);

}