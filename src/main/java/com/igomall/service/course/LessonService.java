package com.igomall.service.course;

import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.service.BaseService;

import java.util.List;
import java.util.Map;

public interface LessonService extends BaseService<Lesson,Long> {
    /**
     * 判断编号是否存在
     *
     * @param sn
     *            编号(忽略大小写)
     * @return 编号是否存在
     */
    boolean snExists(String sn);

    /**
     * 判断编号是否存在
     *
     * @param bilibiliCid
     *            编号(忽略大小写)
     * @return 编号是否存在
     */
    boolean bilibiliCidExists(Long bilibiliCid);

    /**
     * 根据编号查找商品
     *
     * @param sn
     *            编号(忽略大小写)
     * @return 商品，若不存在则返回null
     */
    Lesson findBySn(String sn);

    List<Lesson> findList(Course course);

    List<Map<String,Object>> findListByCourseSQL(Course course);
}
