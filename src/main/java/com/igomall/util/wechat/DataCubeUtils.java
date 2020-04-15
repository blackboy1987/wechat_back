package com.igomall.util.wechat;

import com.igomall.util.Date8Utils;
import com.igomall.util.JsonUtils;
import com.igomall.util.wechat.response.datacube.DataCubeUserCumulateResponse;
import com.igomall.util.wechat.response.datacube.DataCubeUserSummaryResponse;

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
}
