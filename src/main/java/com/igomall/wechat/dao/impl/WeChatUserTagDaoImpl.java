
package com.igomall.wechat.dao.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.wechat.dao.WeChatUserTagDao;
import com.igomall.wechat.entity.WeChatUserTag;
import org.apache.commons.lang3.StringUtils;
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
public class WeChatUserTagDaoImpl extends BaseDaoImpl<WeChatUserTag, Long> implements WeChatUserTagDao {

    @Override
    public Page<WeChatUserTag> findPage(Pageable pageable,String name, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeChatUserTag> criteriaQuery = criteriaBuilder.createQuery(WeChatUserTag.class);
        Root<WeChatUserTag> root = criteriaQuery.from(WeChatUserTag.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        if (StringUtils.isNotEmpty(name)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}