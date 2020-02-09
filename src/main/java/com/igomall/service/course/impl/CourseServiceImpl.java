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
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.LockModeType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course,Long> implements CourseService {

    @Autowired
    private CacheManager cacheManager;

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


    public long viewHits(Long id) {
        Assert.notNull(id,"");

        Ehcache cache = cacheManager.getEhcache(Course.HITS_CACHE_NAME);
        cache.acquireWriteLockOnKey(id);
        try {
            Element element = cache.get(id);
            Long hits;
            if (element != null) {
                hits = (Long) element.getObjectValue() + 1;
            } else {
                Course course = courseDao.find(id);
                if (course == null) {
                    return 0L;
                }
                hits = course.getHits() + 1;
            }
            cache.put(new Element(id, hits));
            return hits;
        } finally {
            cache.releaseWriteLockOnKey(id);
        }
    }

    @Override
    public void addHits(Course course, long amount) {
        Assert.notNull(course,"");
        Assert.state(amount >= 0,"");

        if (amount == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(courseDao.getLockMode(course))) {
            courseDao.flush();
            courseDao.refresh(course, LockModeType.PESSIMISTIC_WRITE);
        }

        Calendar nowCalendar = Calendar.getInstance();
        Calendar weekHitsCalendar = DateUtils.toCalendar(course.getWeekHitsDate());
        Calendar monthHitsCalendar = DateUtils.toCalendar(course.getMonthHitsDate());
        if (nowCalendar.get(Calendar.YEAR) > weekHitsCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekHitsCalendar.get(Calendar.WEEK_OF_YEAR)) {
            course.setWeekHits(amount);
        } else {
            course.setWeekHits(course.getWeekHits() + amount);
        }
        if (nowCalendar.get(Calendar.YEAR) > monthHitsCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.MONTH) > monthHitsCalendar.get(Calendar.MONTH)) {
            course.setMonthHits(amount);
        } else {
            course.setMonthHits(course.getMonthHits() + amount);
        }
        course.setHits(course.getHits() + amount);
        course.setWeekHitsDate(new Date());
        course.setMonthHitsDate(new Date());
        courseDao.flush();
    }
}
