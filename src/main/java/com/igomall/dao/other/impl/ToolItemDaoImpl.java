
package com.igomall.dao.other.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.other.ToolItemDao;
import com.igomall.entity.other.ToolCategory;
import com.igomall.entity.other.ToolItem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ToolItemDaoImpl extends BaseDaoImpl<ToolItem, Long> implements ToolItemDao {

	@Override
	public List<ToolItem> findList(ToolCategory bookCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ToolItem> criteriaQuery = criteriaBuilder.createQuery(ToolItem.class);
		Root<ToolItem> root = criteriaQuery.from(ToolItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<ToolCategory> subquery = criteriaQuery.subquery(ToolCategory.class);
			Root<ToolCategory> subqueryRoot = subquery.from(ToolCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ToolCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + ToolCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("articleCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		criteriaQuery.where(restrictions);
		if (orders == null || orders.isEmpty()) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<ToolItem> findList(ToolCategory bookCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ToolItem> criteriaQuery = criteriaBuilder.createQuery(ToolItem.class);
		Root<ToolItem> root = criteriaQuery.from(ToolItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<ToolCategory> subquery = criteriaQuery.subquery(ToolCategory.class);
			Root<ToolCategory> subqueryRoot = subquery.from(ToolCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ToolCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + ToolCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("articleCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, first, count);
	}

	public Page<ToolItem> findPage(ToolCategory bookCategory, Boolean isPublication, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ToolItem> criteriaQuery = criteriaBuilder.createQuery(ToolItem.class);
		Root<ToolItem> root = criteriaQuery.from(ToolItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (bookCategory != null) {
			Subquery<ToolCategory> subquery = criteriaQuery.subquery(ToolCategory.class);
			Root<ToolCategory> subqueryRoot = subquery.from(ToolCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, bookCategory), criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ToolCategory.TREE_PATH_SEPARATOR + bookCategory.getId() + ToolCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("bookCategory")).value(subquery));
		}
		if (isPublication != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), isPublication));
		}
		criteriaQuery.where(restrictions);
		if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && CollectionUtils.isEmpty(pageable.getOrders()))) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")), criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}