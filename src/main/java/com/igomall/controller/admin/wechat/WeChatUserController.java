package com.igomall.controller.admin.wechat;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.wechat.WeChatUser;
import com.igomall.entity.wechat.material.NewsMaterial;
import com.igomall.entity.wechat.material.NewsMaterialResponse;
import com.igomall.service.wechat.WechatUserService;
import com.igomall.service.wechat.material.NewsMaterialService;
import com.igomall.util.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
