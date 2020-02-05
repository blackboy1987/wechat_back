
package com.igomall.dao.setting.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.igomall.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import com.igomall.dao.setting.FriendLinkDao;
import com.igomall.entity.setting.FriendLink;

/**
 * Dao - 友情链接
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, Long> implements FriendLinkDao {

	public List<FriendLink> findList(FriendLink.Type type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FriendLink> criteriaQuery = criteriaBuilder.createQuery(FriendLink.class);
		Root<FriendLink> root = criteriaQuery.from(FriendLink.class);
		criteriaQuery.select(root);
		if (type != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("type"), type));
		}
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Map<String, Object>> findListBySql(Integer count) {
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("name ");
		sb.append("url ");
		sb.append("from edu_friend_link ");
		if(count!=null){
			sb.append("limit "+count);
		}
		return jdbcTemplate.queryForList(sb.toString());
	}
}