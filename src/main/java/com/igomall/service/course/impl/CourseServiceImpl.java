package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.course.CourseDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseCategory;
import com.igomall.service.course.CourseService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course,Long> implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Transactional(readOnly = true)
    public boolean snExists(String sn) {
        return courseDao.exists("sn", sn, true);
    }

    @Transactional(readOnly = true)
    public boolean biliSnExists(String biliSn) {
        return courseDao.exists("biliSn", biliSn, true);
    }

    @Transactional(readOnly = true)
    public Course findBySn(String sn) {
        return courseDao.find("sn", sn, true);
    }

    public Page<Course> findPage(Pageable pageable, String title, String description, Date beginDate, Date endDate){
        return courseDao.findPage(pageable,title,description,beginDate,endDate);
    }
    public Page<Course> findPage(CourseCategory courseCategory, Boolean isVip, Pageable pageable){
        return courseDao.findPage(courseCategory, isVip, pageable);
    }

    @Override
    public List<Course> findList(CourseCategory courseCategory, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return courseDao.findList(courseCategory,first,count,filters,orders);
    }
}
