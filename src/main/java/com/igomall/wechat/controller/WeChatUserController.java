package com.igomall.wechat.controller;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.wechat.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("adminWeChatUserController")
@RequestMapping("/api/admin/we_chat_user")
public class WeChatUserController extends BaseController {

    @Autowired
    private WechatUserService wechatUserService;

    @PostMapping("/list")
    public Page<WeChatUser> list(Pageable pageable, Integer status, Date beginDate,Date endDate){
        return wechatUserService.findPage(pageable,status,beginDate,endDate);
    }
}
