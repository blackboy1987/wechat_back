package com.igomall.dao.course.impl;

import com.igomall.dao.course.LessonDao;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class LessonDaoImpl extends BaseDaoImpl<Lesson,Long> implements LessonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Lesson> findList(Course course){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
        Root<Lesson> root = criteriaQuery.from(Lesson.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (course!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("course"), course));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }


    @Override
    public List<Map<String, Object>> findListByCourseSQL(Course course) {
        if(course==null){
            return Collections.emptyList();
        }
        String sql = "select sn,title,video_url videoUrl,video_image videoImage from edu_lesson where course_id="+course.getId();
        return jdbcTemplate.queryForList(sql);
    }
}
