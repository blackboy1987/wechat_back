package com.igomall.wechat.util;

import com.igomall.util.JsonUtils;
import com.igomall.wechat.util.response.user.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UserManagementUtils {

    private UserManagementUtils(){}

    /**
     *  创建标签
     * @param name
     *      标签名（30个字符以内）
     * @return
     */
    public static UserTagCreateResponse tagCreate(String name){
        String url="https://api.weixin.qq.com/cgi-bin/tags/create";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        params.put("tag",map);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserTagCreateResponse.class);
    }

    /**
     *  获取公众号已创建的标签
     * @return
     */
    public static UserTagGetResponse tagGet(){
        String url="https://api.weixin.qq.com/cgi-bin/tags/get";
        return JsonUtils.toObject(WechatUtils.get(url,null), UserTagGetResponse.class);
    }

    /**
     *  删除标签
     * @param tagId
     *      标签名（30个字符以内）
     * @return
     */
    public static UserTagDeleteResponse tagDelete(Long tagId){
        String url="https://api.weixin.qq.com/cgi-bin/tags/delete";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",tagId);
        params.put("tag",map);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserTagDeleteResponse.class);
    }

    /**
     *   获取标签下粉丝列表
     * @return
     */
    public static UserTagGetResponse userTagGet(Long tagId,String nextOpenId){
        String url="https://api.weixin.qq.com/cgi-bin/user/tag/get";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("tagid",tagId);
        params.put("next_openid",nextOpenId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserTagGetResponse.class);
    }

    /**
     *    批量为用户打标签
     * @return
     */
    public static UserTagMemberResponse batchtagging(Long tagId, List<String> openIdList){
        String url="https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("tagid",tagId);
        params.put("openid_list",openIdList);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserTagMemberResponse.class);
    }

    /**
     *    批量为用户取消标签
     * @return
     */
    public static UserTagMemberResponse batchuntagging(Long tagId, List<String> openIdList){
        String url="https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("tagid",tagId);
        params.put("openid_list",openIdList);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserTagMemberResponse.class);
    }

    /**
     *    获取用户身上的标签列表
     * @return
     */
    public static UserTagGetListResponse tagGetList(String openId){
        String url="https://api.weixin.qq.com/cgi-bin/tags/getidlist";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("openid",openId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserTagGetListResponse.class);
    }

    /**
     * 设置用户备注名
     * 开发者可以通过该接口对指定用户设置备注名，该接口暂时开放给微信认证的服务号。
     * @param openId
     *      用户标识
     * @param remark
     *      新的备注名，长度必须小于30字符
     * @return
     */
    public static UserInfoUpdateRemarkResponse userInfoUpdateRemark(String openId,String remark){
        String url="https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("openid",openId);
        map.put("remark",remark);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserInfoUpdateRemarkResponse.class);
    }

    /**
     * 获取用户基本信息(UnionID机制)
     */
    public static UserInfoResponse userInfo(String openId){
        String url="https://api.weixin.qq.com/cgi-bin/user/info";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("openid",openId);
        return JsonUtils.toObject(WechatUtils.get(url,params), UserInfoResponse.class);
    }

    /**
     * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
     * UserInfoBatchGetResponse
     */
    public static UserInfoBatchGetResponse userInfoBatchGet(List<String> openIdList){
        String url="https://api.weixin.qq.com/cgi-bin/user/info/batchget";
        List<Map<String,Object>> result = new ArrayList<>();
        for (String openId:openIdList) {
            Map<String,Object> map = new HashMap<>();
            map.put("openid",openId);
            map.put("lang","zh_CN");
            result.add(map);
        }
        Map<String,Object> params = new HashMap<>();
        params.put("user_list",result);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserInfoBatchGetResponse.class);
    }

    /**
     * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
     * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
     *
     */
    public static UserGetResponse userGet(String nextOpenId){
        String url="https://api.weixin.qq.com/cgi-bin/user/get";
        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isNotEmpty(nextOpenId)){
            params.put("next_openid",nextOpenId);
        }
        return JsonUtils.toObject(WechatUtils.get(url,params), UserGetResponse.class);
    }

    /**
     * 获取公众号的黑名单列表
     * {
     *  "total":23000,
     *  "count":10000,
     *  "data":{"
     *     openid":[
     *        "OPENID1",
     *        "OPENID2",
     *        ...,
     *        "OPENID10000"
     *     ]
     *   },
     *   "next_openid":"OPENID10000"
     * }
     */
    public static UserGetBlackListResponse getBlackListResponse(String beginOpenId){
        String url="https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist";
        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isNotEmpty(beginOpenId)){
            params.put("begin_openid",beginOpenId);
        }
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserGetBlackListResponse.class);
    }

    /**
     * 拉黑用户
     * {
     *   "errcode": 0,
     *   "errmsg": "ok"
     * }
     */
    public static UserBatchBlacklistResponse batchBlacklist(List<String> openIdList){
        String url="https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist";
        Map<String,Object> params = new HashMap<>();
        params.put("openid_list",openIdList);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserBatchBlacklistResponse.class);
    }

    /**
     * 取消拉黑用户
     * @param openIdList
     * @return
     */
    public static UserBatchUnBlacklistResponse batchUnBlacklist(List<String> openIdList){
        String url="https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist";
        Map<String,Object> params = new HashMap<>();
        params.put("openid_list",openIdList);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserBatchUnBlacklistResponse.class);
    }





}
