package com.paicent.service;

import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.entity.Activity;
import com.paicent.entity.ConfigInfo;

import java.util.Date;
import java.util.List;

public interface ConfigInfoService extends BaseService<ConfigInfo,Long> {

    Page<ConfigInfo> findPage(Pageable pageable, String district, Long activityId,Date applyStartTime, Date applyFinishTime);

    List<ConfigInfo> findList(String district, Long activityId,Date applyStartTime, Date applyFinishTime);
}
