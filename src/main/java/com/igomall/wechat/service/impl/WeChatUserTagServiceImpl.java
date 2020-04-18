
package com.igomall.wechat.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.wechat.dao.WeChatUserTagDao;
import com.igomall.wechat.entity.WeChatUserTag;
import com.igomall.wechat.service.WeChatUserTagService;
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
public class WeChatUserTagServiceImpl extends BaseServiceImpl<WeChatUserTag, Long> implements WeChatUserTagService {

    @Autowired
    private WeChatUserTagDao wechatUserTagDao;

    public WeChatUserTag findByName(String name){
        return wechatUserTagDao.find("name",name);
    }


    public Page<WeChatUserTag> findPage(Pageable pageable,String name, Date beginDate, Date endDate){
        return wechatUserTagDao.findPage(pageable,name,beginDate,endDate);
    }

}