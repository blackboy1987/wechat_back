
package com.igomall.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;

/**
 * Service - 审计日志
 * 
 * @author blackboy
 * @version 1.0
 */
public interface FeedbackService extends BaseService<Feedback, Long> {

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