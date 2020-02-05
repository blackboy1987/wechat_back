
package com.igomall.dao.course.impl;

import com.igomall.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import com.igomall.dao.course.CourseTagDao;
import com.igomall.entity.course.CourseTag;

import java.util.List;
import java.util.Map;

/**
 * Dao - 商品标签
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class CourseTagDaoImpl extends BaseDaoImpl<CourseTag, Long> implements CourseTagDao {

    @Override
    public List<Map<String, Object>> findListBySql(Integer count) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append("id, ");
        sb.append("name ");
        sb.append("from edu_course_tag ");
        if(count!=null){
            sb.append("limit "+count);
        }
        return jdbcTemplate.queryForList(sb.toString());
    }
}