package com.igomall.util.wechat;

import com.igomall.util.JsonUtils;
import com.igomall.util.wechat.response.user.*;

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
}
