
package com.igomall.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.FeedbackDao;
import com.igomall.dao.member.PointLogDao;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;
import com.igomall.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 审计日志
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback, Long> implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    @Transactional(readOnly = true)
    public Page<Feedback> findPage(Member member, Pageable pageable) {
        return feedbackDao.findPage(member, pageable);
    }

}