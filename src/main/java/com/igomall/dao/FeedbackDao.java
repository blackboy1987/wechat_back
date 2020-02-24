
package com.igomall.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;

/**
 * Dao - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
public interface FeedbackDao extends BaseDao<Feedback, Long> {
    /**
     * 查找积分记录分页
     *
     * @param member
     *            会员
     * @param pageable
     *            分页信息
     * @return 积分记录分页
     */
    Page<Feedback> findPage(Member member, Pageable pageable);
}