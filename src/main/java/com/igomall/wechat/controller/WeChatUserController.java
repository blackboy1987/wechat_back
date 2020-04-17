package com.igomall.wechat.controller;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.wechat.service.WechatUserService;
import com.igomall.wechat.util.UserManagementUtils;
import com.igomall.wechat.util.response.user.UserGetResponse;
import com.igomall.wechat.util.response.user.UserInfoBatchGetResponse;
import com.igomall.wechat.util.response.user.UserInfoResponse;
import com.igomall.wechat.util.response.user.UserInfoUpdateRemarkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController("adminWeChatUserController")
@RequestMapping("/api/wechat/user")
public class WeChatUserController extends BaseController {

    @Autowired
    private WechatUserService wechatUserService;

    @PostMapping("/list")
    public Page<WeChatUser> list(Pageable pageable, Integer status, Date beginDate,Date endDate){
        return wechatUserService.findPage(pageable,status,beginDate,endDate);
    }

    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/get")
    public UserGetResponse get(){
        UserGetResponse userGetResponse = UserManagementUtils.userGet(null);
        return userGetResponse;

    }

    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/info")
    public UserInfoUpdateRemarkResponse get(String openId){
        UserInfoUpdateRemarkResponse userInfoResponse = UserManagementUtils.userInfoUpdateRemark(openId,"aaa");
        return userInfoResponse;

    }
}
