
package com.igomall.dao.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.MaterialDao;
import com.igomall.entity.Material;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Dao - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class MaterialDaoImpl extends BaseDaoImpl<Material, Long> implements MaterialDao {

    @Override
    public Page<Material> findPage(Pageable pageable, String title, String memo, Material.Type type, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
        Root<Material> root = criteriaQuery.from(Material.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (StringUtils.isNotEmpty(title)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("title"), "%"+title+"%"));
        }
        if (StringUtils.isNotEmpty(memo)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("memo"), "%"+memo+"%"));
        }
        if (type!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}