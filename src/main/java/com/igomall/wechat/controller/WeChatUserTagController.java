package com.igomall.wechat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.wechat.entity.WeChatUserTag;
import com.igomall.wechat.service.WeChatUserTagService;
import com.igomall.wechat.util.UserManagementUtils;
import com.igomall.wechat.util.response.user.UserTagCreateResponse;
import com.igomall.wechat.util.response.user.UserTagUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("adminWeChatTagController")
@RequestMapping("/api/we_chat/tag")
public class WeChatUserTagController extends BaseController {

    @Autowired
    private WeChatUserTagService weChatUserTagService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Message save(WeChatUserTag weChatUserTag) {
        if(weChatUserTag.getIsEnabled()==null){
            weChatUserTag.setIsEnabled(false);
        }
        if (!isValid(weChatUserTag,BaseEntity.Save.class)) {
            return Message.error("参数错误");
        }
        WeChatUserTag parent = weChatUserTagService.save(weChatUserTag);
        if(parent==null){
            return Message.error("接口调用失败");
        }
        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @JsonView(BaseEntity.EditView.class)
    public WeChatUserTag edit(Long id) {
        return weChatUserTagService.find(id);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Message update(WeChatUserTag weChatUserTag) {
        if(weChatUserTag.getIsEnabled()==null){
            weChatUserTag.setIsEnabled(false);
        }
        if (!isValid(weChatUserTag)) {
            return Message.error("参数错误");
        }

        WeChatUserTag parent = weChatUserTagService.update(weChatUserTag);
        if(parent==null){
            return Message.error("接口调用失败");
        }
        return Message.success("操作成功");
    }

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Page<WeChatUserTag> list(Pageable pageable, String name, Date beginDate, Date endDate){
        return weChatUserTagService.findPage(pageable,name,beginDate,endDate);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Message delete(Long[] ids) {
        for (Long id:ids) {
            WeChatUserTag weChatUserTag = weChatUserTagService.find(id);
            if(weChatUserTag!=null){
                weChatUserTag.setIsEnabled(false);
                weChatUserTagService.update(weChatUserTag);
            }
        }
        return Message.success("操作成果");
    }

}
