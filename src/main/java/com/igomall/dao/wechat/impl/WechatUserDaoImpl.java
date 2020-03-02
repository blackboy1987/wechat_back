
package com.igomall.dao.wechat.impl;

import com.igomall.dao.AreaDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.WechatUserDao;
import com.igomall.entity.Area;
import com.igomall.entity.wechat.WeChatUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class WechatUserDaoImpl extends BaseDaoImpl<WeChatUser, Long> implements WechatUserDao {

}