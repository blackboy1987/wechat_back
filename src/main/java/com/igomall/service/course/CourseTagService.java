
package com.igomall.service.course;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Service - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface CourseTagService extends BaseService<CourseTag, Long> {

	/**
	 * 查找文章标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 文章标签
	 */
	List<CourseTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	List<Map<String,Object>> findListBySql(Integer count);

	boolean nameExists(String name);
	boolean nameUnique(Long id, String name);

	CourseTag findByName(String name);

}