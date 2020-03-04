package com.igomall.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.common.Pageable;
import com.igomall.entity.wechat.material.NewsMaterial;
import com.igomall.entity.wechat.material.NewsMaterialResponse;
import com.igomall.entity.wechat.response.AccessToken;
import com.igomall.entity.wechat.response.WeChatUserResponse;
import jdk.nashorn.internal.ir.IdentNode;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WechatUtils {

    private static final String APPID = "wx51e92bcdcb5fc4d7";

    private static final String APPSECRET = "8231f4390de7413d9d844220a09a8008";

    private WechatUtils(){}

    public static Map<String, String> parseXml(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 获取accesstoken
     * @return
     */
    public static AccessToken getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("appid",APPID);
        params.put("secret",APPSECRET);
        String result = WebUtils.get(url,params);
        return JsonUtils.toObject(result, AccessToken.class);
    }

    /**
     * 获取用户基本信息
     * @param openid
     * @return
     */
    public static WeChatUserResponse getUserInfo(String openid){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("access_token",getAccessToken().getAccessToken());
        params.put("openid",openid);
        params.put("lang","zh_CN");
        String result = WebUtils.get(url,params);
        return JsonUtils.toObject(result, WeChatUserResponse.class);

    }

    /**
     * 获取素材，分页展示
     * @param type
     * @param pageable
     * @return
     */
    public static NewsMaterialResponse getMaterial(String type, Pageable pageable){
        String url="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+getAccessToken().getAccessToken();
        Map<String,Object> params = new HashMap<>();
        params.put("type",type);
        params.put("offset",(pageable.getPageNumber()-1)*pageable.getPageSize());
        params.put("count",pageable.getPageSize());
        String result = WebUtils.postJson(url,JsonUtils.toJson(params));
        return JsonUtils.toObject(result,NewsMaterialResponse.class);
    }

    /**
     * 获取评论
     * @param msgDataId
     * @param pageable
     * @return
     */
    public static String getComments(String msgDataId, Integer index, Integer type, Pageable pageable){
        String url="https://api.weixin.qq.com/cgi-bin/comment/list?access_token="+getAccessToken().getAccessToken();
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        params.put("index",index);
        params.put("type",type);
        params.put("begin",(pageable.getPageNumber()-1)*pageable.getPageSize());
        params.put("count",pageable.getPageSize());
        String result = WebUtils.postJson(url,JsonUtils.toJson(params));
        return result;
    }




















































    public static void main(String[] args) {
        Pageable pageable = new Pageable();
        getComments("4PCbTGwo9Nmscuv1McTgoCw0dBE2n8drTaDUixur1J0",0,0,pageable);
    }
}
