
package com.igomall.service.wechat.impl;

import com.igomall.dao.wechat.SubscribeSummaryLogDao;
import com.igomall.entity.wechat.SubscribeSummaryLog;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.wechat.SubscribeSummaryLogService;
import com.igomall.util.Date8Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class SubscribeSummaryLogServiceImpl extends BaseServiceImpl<SubscribeSummaryLog, Long> implements SubscribeSummaryLogService {

    @Autowired
    private SubscribeSummaryLogDao subscribeSummaryLogDao;

    @Override
    public boolean dateExists(String date) {
        return subscribeSummaryLogDao.exists("date", StringUtils.lowerCase(date));
    }

    @Override
    public SubscribeSummaryLog findByDate(String date) {
        return subscribeSummaryLogDao.find("date", StringUtils.lowerCase(date));
    }

    public void init(Date date){
        if(date == null){
            date = new Date();
        }
        if(!dateExists(Date8Utils.formatDateToString(date,"yyyy-MM-dd"))){
            SubscribeSummaryLog pre = findByDate(Date8Utils.formatDateToString(Date8Utils.getNextDay(date,-1),"yyyy-MM-dd"));
            SubscribeSummaryLog subscribeSummaryLog = new SubscribeSummaryLog();
            if(pre!=null){
                subscribeSummaryLog.setTotalCount(pre.getTotalCount());
            }else{
                subscribeSummaryLog.setTotalCount(0L);
            }
            subscribeSummaryLog.setUnSubscribeCount(0L);
            subscribeSummaryLog.setSubscribeCount(0L);
            subscribeSummaryLog.setNetCount(0L);
            subscribeSummaryLog.setDate(Date8Utils.formatDateToString(date,"yyyy-MM-dd"));
            super.save(subscribeSummaryLog);
        }
    }
}