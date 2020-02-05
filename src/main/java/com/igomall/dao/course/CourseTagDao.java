
package com.igomall.dao.course;

import com.igomall.dao.BaseDao;
import com.igomall.entity.course.CourseTag;

import java.util.List;
import java.util.Map;

/**
 * Dao - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
public interface CourseTagDao extends BaseDao<CourseTag, Long> {
    List<Map<String,Object>> findListBySql(Integer count);
}