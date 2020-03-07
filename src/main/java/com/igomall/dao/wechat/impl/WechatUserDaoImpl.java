
package com.igomall.dao.wechat.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.wechat.WechatUserDao;
import com.igomall.entity.wechat.WeChatUser;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Dao - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class WechatUserDaoImpl extends BaseDaoImpl<WeChatUser, Long> implements WechatUserDao {

    @Override
    public Page<WeChatUser> findPage(Pageable pageable, Integer status, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeChatUser> criteriaQuery = criteriaBuilder.createQuery(WeChatUser.class);
        Root<WeChatUser> root = criteriaQuery.from(WeChatUser.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (status != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}