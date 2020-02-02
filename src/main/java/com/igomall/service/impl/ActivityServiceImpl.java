package com.igomall.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.ActivityDao;
import com.igomall.entity.Activity;
import com.igomall.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity,Long> implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Override
    public Page<Activity> findPage(Pageable pageable, String name, String description, Integer status, Date beginDate, Date endDate){
        return activityDao.findPage(pageable,name,description,status,beginDate,endDate);
    }
    @Override
    public List<Activity> findList(String name, String description, Integer status, Date startTime, Date finishTime) {
        return activityDao.findList(name,description,status,startTime,finishTime);
    }
}
