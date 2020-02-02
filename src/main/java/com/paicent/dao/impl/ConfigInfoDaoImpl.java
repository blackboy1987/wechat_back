package com.paicent.dao.impl;

import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.dao.ActivityDao;
import com.paicent.dao.ConfigInfoDao;
import com.paicent.entity.Activity;
import com.paicent.entity.ConfigInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class ConfigInfoDaoImpl extends BaseDaoImpl<ConfigInfo,Long> implements ConfigInfoDao {

    @Override
    public Page<ConfigInfo> findPage(Pageable pageable, String district, Long activityId,Date applyStartTime, Date applyFinishTime){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConfigInfo> criteriaQuery = criteriaBuilder.createQuery(ConfigInfo.class);
        Root<ConfigInfo> root = criteriaQuery.from(ConfigInfo.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(district)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("district"), district));
        }
        if (activityId!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("activityId"), activityId));
        }
        if (applyStartTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("applyStartTime"), applyStartTime));
        }
        if (applyFinishTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("applyFinishTime"), applyFinishTime));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }

    @Override
    public List<ConfigInfo> findList(String district, Long activityId,Date applyStartTime, Date applyFinishTime) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConfigInfo> criteriaQuery = criteriaBuilder.createQuery(ConfigInfo.class);
        Root<ConfigInfo> root = criteriaQuery.from(ConfigInfo.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(district)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("district"), district));
        }
        if (activityId!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("activityId"), activityId));
        }
        if (applyStartTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("applyStartTime"), applyStartTime));
        }
        if (applyFinishTime!=null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("applyFinishTime"), applyFinishTime));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery,null,null,null,null);
    }
}
