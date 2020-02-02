package com.paicent.service;

import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.entity.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityService extends BaseService<Activity,Long> {

    Page<Activity> findPage(Pageable pageable, String name, String description, Integer status, Date startTime, Date finishTime);

    List<Activity> findList(String name, String description,Integer status, Date startTime, Date finishTime);
}
