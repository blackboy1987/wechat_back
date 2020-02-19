
package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.LessonDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.service.course.LessonService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class LessonServiceImpl extends BaseServiceImpl<Lesson, Long> implements LessonService {

    @Autowired
    private LessonDao lessonDao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "lesson")
    public List<Lesson> findList(Course course, Folder folder, Integer count, List<Filter> filters, List<Order> orders) {
        return lessonDao.findList(course,folder,count,filters,orders);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public Lesson save(Lesson lesson) {
        Assert.notNull(lesson,"");
        return super.save(lesson);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public Lesson update(Lesson lesson) {
        Assert.notNull(lesson,"");
        return super.update(lesson);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public Lesson update(Lesson lesson, String... ignoreProperties) {
        return super.update(lesson, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lesson", allEntries = true)
    public void delete(Lesson lesson) {
        super.delete(lesson);
    }
}