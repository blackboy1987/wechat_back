package com.igomall.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityDao extends BaseDao<Activity,Long> {

    Page<Activity> findPage(Pageable pageable, String name, String description,Integer status, Date startTime, Date finishTime);

    List<Activity> findList(String name, String description,Integer status, Date startTime, Date finishTime);
}
