
package com.igomall.dao.course;

import com.igomall.dao.BaseDao;
import com.igomall.entity.course.CourseTopic;
import com.igomall.entity.setting.ArticleTopic;

import java.util.List;
import java.util.Map;

/**
 * Dao - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface CourseTopicDao extends BaseDao<CourseTopic, Long> {

    List<Map<String,Object>> findListBySql(Integer count);
}