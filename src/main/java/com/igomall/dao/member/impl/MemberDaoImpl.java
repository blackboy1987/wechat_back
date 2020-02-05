
package com.igomall.dao.member.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.igomall.dao.impl.BaseDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.MemberDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberAttribute;

/**
 * Dao - 会员
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao {

	public Page<Member> findPage(Member.RankingType rankingType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		if (rankingType != null) {
			switch (rankingType) {
			case point:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("point")));
				break;
			case balance:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("balance")));
				break;
			case amount:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("amount")));
				break;
			}
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Long registerMemberCount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public void clearAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null || memberAttribute.getType() == null || memberAttribute.getPropertyIndex() == null) {
			return;
		}

		String propertyName;
		switch (memberAttribute.getType()) {
		case text:
		case select:
		case checkbox:
			propertyName = Member.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			break;
		default:
			propertyName = String.valueOf(memberAttribute.getType());
			break;
		}
		String jpql = "update Member mem set mem." + propertyName + " = null";
		entityManager.createQuery(jpql).executeUpdate();
	}


	@Override
	public Page<Member> findPage(Pageable pageable, String username, String name, String mobile, Integer status, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		if(status!=null){
			if(status==0){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), true));
			}else if(status==1){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), false));
			}else if(status==2){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isLocked"), true));
			}
		}

		if (StringUtils.isNotEmpty(username)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("username"), "%"+username+"%"));
		}
		if (StringUtils.isNotEmpty(name)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
		}
		if (StringUtils.isNotEmpty(mobile)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("mobile"), "%"+mobile+"%"));
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