package com.igomall.util.wechat;

import com.igomall.util.Date8Utils;
import com.igomall.util.JsonUtils;
import com.igomall.util.wechat.response.datacube.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据统计工具类
 */
public final class DataCubeUtils {

    private DataCubeUtils(){}

    /**
     * 获取用户增减数据
     * @param beginDate
     *      获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *      获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserSummaryResponse userSummary(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusersummary";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUserSummaryResponse.class);
    }

    /**
     * 获取累计用户数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserCumulateResponse userCumulate(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusercumulate";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubeUserCumulateResponse.class);
    }

    /**
     *获取图文群发每日数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeArticleSummaryResponse getarticlesummary(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getarticlesummary";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubeArticleSummaryResponse.class);
    }

    /**
     *获取图文群发总数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeArticleTotalResponse getarticletotal(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getarticletotal";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubeArticleTotalResponse.class);
    }

    /**
     *获取图文统计数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserReadResponse getuserread(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getuserread";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubeUserReadResponse.class);
    }

    /**
     *获取图文统计分时数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserReadHourResponse getuserreadhour(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getuserreadhour";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubeUserReadHourResponse.class);
    }

    /**
     *获取图文分享转发数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserShareResponse getusershare(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusershare";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params), DataCubeUserShareResponse.class);
    }

    /**
     *获取图文分享转发分时数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserShareHourResponse getusersharehour(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusersharehour";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubeUserShareHourResponse.class);
    }

}
