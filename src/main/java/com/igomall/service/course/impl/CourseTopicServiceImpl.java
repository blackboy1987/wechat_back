
package com.igomall.service.course.impl;
import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.CourseTopicDao;
import com.igomall.entity.course.CourseTopic;
import com.igomall.service.course.CourseTopicService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class CourseTopicServiceImpl extends BaseServiceImpl<CourseTopic, Long> implements CourseTopicService {

	@Autowired
	private CourseTopicDao courseTopicDao;

	@Transactional(readOnly = true)
	@Cacheable(value = "courseTopic", condition = "#useCache")
	public List<CourseTopic> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return courseTopicDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTopic", allEntries = true)
	public CourseTopic save(CourseTopic courseTopic) {
		return super.save(courseTopic);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTopic", allEntries = true)
	public CourseTopic update(CourseTopic courseTopic) {
		return super.update(courseTopic);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTopic", allEntries = true)
	public CourseTopic update(CourseTopic courseTopic, String... ignoreProperties) {
		return super.update(courseTopic, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTag", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseTopic", allEntries = true)
	public void delete(CourseTopic courseTopic) {
		super.delete(courseTopic);
	}

	@Override
	public List<Map<String, Object>> findListBySql(Integer count) {
		return courseTopicDao.findListBySql(count);
	}

	@Override
	public boolean nameExists(String name) {
		return courseTopicDao.exists("name",name);
	}
}