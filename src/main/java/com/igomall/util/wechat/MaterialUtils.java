package com.igomall.util.wechat;

import com.igomall.entity.wechat.response.AccessToken;
import com.igomall.util.WebUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材工具类
 */
public final class MaterialUtils {
    /**
     * PoolingHttpClientConnectionManager
     */
    private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;

    /**
     * CloseableHttpClient
     */
    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
        HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
        HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER).setDefaultRequestConfig(requestConfig).build();
    }

    private MaterialUtils(){}

    /**
     * 上传临时素材
     * @param file
     *  文件
     * @param type
     *  媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return
     */
    /**
     * {
     *  "type":"image",
     *  "media_id":"cZswiJGARalhVaymkTmvyXeQt6v7Ux7KM9mq-LYLBeH8xL9OMvD2w6ZJu_SX4Ykr",
     *  "created_at":1586446258,
     *  "item":[]
     * }
     *
     */
    public static String upload(File file, String type ) {
        String result = null;
        AccessToken accessToken = WechatUtils.getAccessToken();
        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/media/upload");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("media",file);
            builder.addTextBody("access_token",accessToken.getAccessToken());
            builder.addTextBody("type",type);

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
                }
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        System.out.println("result:"+result);
        return result;
    }

    /**
     * 获取临时素材
     * @param mediaId
     *  素材id
     * @return
     */
    public static void get(String mediaId) {
        AccessToken accessToken = WechatUtils.getAccessToken();
        Map<String,Object> params = new HashMap<>();
        params.put("media_id",mediaId);
        params.put("access_token",accessToken.getAccessToken());
        InputStream inputStream;
        String url = "https://api.weixin.qq.com/cgi-bin/media/get";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotEmpty(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    inputStream = httpEntity.getContent();
                    File file = new File("/Users/blackboy/Desktop/wechat/wechat_page/public/icons/3.png");
                    try {
                        FileUtils.copyInputStreamToFile(inputStream,file);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(file.getAbsolutePath());
                }
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        String path = "/Users/blackboy/Desktop/wechat/wechat_page/public/icons/icon-512x512.png";
        //upload(new File(path),"image");
        get("cZswiJGARalhVaymkTmvyXeQt6v7Ux7KM9mq-LYLBeH8xL9OMvD2w6ZJu_SX4Ykr");
    }
}
