
package com.igomall.dao.member.impl;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.igomall.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.PointLogDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;

import java.util.Date;

/**
 * Dao - 积分记录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class PointLogDaoImpl extends BaseDaoImpl<PointLog, Long> implements PointLogDao {

	public Page<PointLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointLog> criteriaQuery = criteriaBuilder.createQuery(PointLog.class);
		Root<PointLog> root = criteriaQuery.from(PointLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public boolean exists(PointLog.Type type, Member member, Date beginDate, Date endDate) {
		if(type==null || member==null || beginDate==null || endDate == null){
			return true;
		}
		String jpql = "select count(*) from PointLog pointLog where pointLog.member = :member and pointLog.type = :type and pointLog.createdDate >= :beginDate and pointLog.createdDate <= :endDate";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).setParameter("type",type).setParameter("beginDate",beginDate).setParameter("endDate",endDate).getSingleResult();
		return count > 0;
	}
}