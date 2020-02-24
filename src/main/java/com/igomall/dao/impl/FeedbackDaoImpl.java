
package com.igomall.dao.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.FeedbackDao;
import com.igomall.entity.Feedback;
import com.igomall.entity.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Dao - 审计日志
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback, Long> implements FeedbackDao {

    public Page<Feedback> findPage(Member member, Pageable pageable) {
        if (member == null) {
            return Page.emptyPage(pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Feedback> criteriaQuery = criteriaBuilder.createQuery(Feedback.class);
        Root<Feedback> root = criteriaQuery.from(Feedback.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
        return super.findPage(criteriaQuery, pageable);
    }
}