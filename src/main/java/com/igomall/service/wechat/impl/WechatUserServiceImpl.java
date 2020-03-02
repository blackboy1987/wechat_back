
package com.igomall.service.wechat.impl;

import com.igomall.dao.AreaDao;
import com.igomall.dao.wechat.WechatUserDao;
import com.igomall.entity.Area;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.service.AreaService;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

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
}