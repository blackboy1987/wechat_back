package com.paicent.service.impl;

import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.dao.ActivityDao;
import com.paicent.dao.ConfigInfoDao;
import com.paicent.entity.Activity;
import com.paicent.entity.ConfigInfo;
import com.paicent.service.ActivityService;
import com.paicent.service.ConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConfigInfoServiceImpl extends BaseServiceImpl<ConfigInfo,Long> implements ConfigInfoService {

    @Autowired
    private ConfigInfoDao configInfoDao;

    @Override
    public Page<ConfigInfo> findPage(Pageable pageable, String district, Long activityId,Date applyStartTime, Date applyFinishTime){
        return configInfoDao.findPage(pageable,district,activityId,applyStartTime, applyFinishTime);
    }
    @Override
    public List<ConfigInfo> findList(String district, Long activityId,Date applyStartTime, Date applyFinishTime) {
        return configInfoDao.findList(district,activityId,applyStartTime, applyFinishTime);
    }
}
