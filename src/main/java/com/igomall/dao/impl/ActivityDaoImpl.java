package com.igomall.dao.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.ActivityDao;
import com.igomall.entity.Activity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl<Activity,Long> implements ActivityDao {

    @Override
    public Page<Activity> findPage(Pageable pageable, String name, String description,Integer status, Date startTime, Date finishTime){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Activity> criteriaQuery = criteriaBuilder.createQuery(Activity.class);
        Root<Activity> root = criteriaQuery.from(Activity.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(name)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        }
        if (StringUtils.isNotEmpty(description)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("description"), "%"+description+"%"));
        }
        if (status!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (startTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime));
        }
        if (finishTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("finishTime"), finishTime));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }

    @Override
    public List<Activity> findList(String name, String description,Integer status, Date startTime, Date finishTime) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Activity> criteriaQuery = criteriaBuilder.createQuery(Activity.class);
        Root<Activity> root = criteriaQuery.from(Activity.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(name)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        }
        if (StringUtils.isNotEmpty(description)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("username"), "%"+description+"%"));
        }
        if (status!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (startTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime));
        }
        if (finishTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("finishTime"), finishTime));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery,null,null,null,null);
    }
}
