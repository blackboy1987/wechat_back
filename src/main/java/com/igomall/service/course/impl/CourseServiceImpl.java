
package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.service.course.CourseService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
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
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements CourseService {


    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public Course save(Course course) {
        Assert.notNull(course,"");
        return super.save(course);
    }

    @Override
    @Transactional
    @CacheEvict(value = "folder", allEntries = true)
    public Course update(Course course) {
        Assert.notNull(course,"");
        return super.update(course);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public Course update(Course course, String... ignoreProperties) {
        return super.update(course, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "course", allEntries = true)
    public void delete(Course course) {
        super.delete(course);
    }
}