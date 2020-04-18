
package com.igomall.wechat.service.impl;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.wechat.dao.WeChatUserTagDao;
import com.igomall.wechat.entity.WeChatUserTag;
import com.igomall.wechat.service.WeChatUserTagService;
import com.igomall.wechat.util.UserManagementUtils;
import com.igomall.wechat.util.response.user.UserTagCreateResponse;
import com.igomall.wechat.util.response.user.UserTagUpdateResponse;
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

    @Override
    public WeChatUserTag save(WeChatUserTag weChatUserTag){
        weChatUserTag.setCount(0L);
        UserTagCreateResponse userTagCreateResponse = UserManagementUtils.tagCreate(weChatUserTag.getName());
        if(userTagCreateResponse.getErrCode()==null){
            weChatUserTag.setName(userTagCreateResponse.getTag().getName());
            weChatUserTag.setWeChatId(userTagCreateResponse.getTag().getId());
            return super.save(weChatUserTag);
        }
        return null;
    }

    @Override
    public WeChatUserTag update(WeChatUserTag weChatUserTag){
        WeChatUserTag parent = super.find(weChatUserTag.getId());
        parent.setName(weChatUserTag.getName());
        parent.setMemo(weChatUserTag.getMemo());
        parent.setIsEnabled(weChatUserTag.getIsEnabled());
        UserTagUpdateResponse userTagUpdateResponse = UserManagementUtils.tagUpdate(parent.getWeChatId(),parent.getName());
        // 接口调用成果
        if(userTagUpdateResponse.getErrCode()==0){
            return super.update(parent,"count","weChatId");
        }
        return null;
    }

}