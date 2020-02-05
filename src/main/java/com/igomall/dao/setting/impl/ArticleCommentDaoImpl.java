
package com.igomall.dao.setting.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.setting.ArticleCommentDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleComment;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;


/**
 * Dao - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ArticleCommentDaoImpl extends BaseDaoImpl<ArticleComment, Long> implements ArticleCommentDao {

	@Override
	public List<ArticleComment> findList(Member member, Article article, ArticleComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArticleComment> criteriaQuery = criteriaBuilder.createQuery(ArticleComment.class);
		Root<ArticleComment> root = criteriaQuery.from(ArticleComment.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (article != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("article"), article));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
				break;
				default:
					break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<ArticleComment> findPage(Member member, Article article, ArticleComment.Type type, Boolean isShow, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArticleComment> criteriaQuery = criteriaBuilder.createQuery(ArticleComment.class);
		Root<ArticleComment> root = criteriaQuery.from(ArticleComment.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (article != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("article"), article));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
				break;
				default:
					break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Member member, Article article, ArticleComment.Type type, Boolean isShow) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArticleComment> criteriaQuery = criteriaBuilder.createQuery(ArticleComment.class);
		Root<ArticleComment> root = criteriaQuery.from(ArticleComment.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (article != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("article"), article));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
				break;
				default:
					break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public long calculateTotalScore(Article article) {
		Assert.notNull(article,"");

		String jpql = "select sum(articleComment.score) from ArticleComment articleComment where articleComment.article = :article and articleComment.isShow = :isShow and articleComment.forReview is null";
		Long totalScore = entityManager.createQuery(jpql, Long.class).setParameter("article", article).setParameter("isShow", true).getSingleResult();
		return totalScore != null ? totalScore : 0L;
	}

	@Override
	public long calculateScoreCount(Article article) {
		Assert.notNull(article,"");

		String jpql = "select count(*) from ArticleComment articleComment where articleComment.article = :article and articleComment.isShow = :isShow and articleComment.forReview is null";
		return entityManager.createQuery(jpql, Long.class).setParameter("article", article).setParameter("isShow", true).getSingleResult();
	}

	@Override
	public List<Map<String, Object>> findListBySQL(Article article) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append(" articleComment.id,");
		sb.append(" articleComment.score,");
		sb.append(" articleComment.created_date createdDate,");
		sb.append(" articleComment.content,");
		sb.append(" member.username,");
		sb.append(" member.avatar");
		sb.append(" from");
		sb.append(" edu_article_comment as articleComment,");
		sb.append(" edu_member as member");
		sb.append(" where articleComment.is_show=true and articleComment.member_id=member.id");
		if(article!=null){
			sb.append(" and articleComment.article_id="+article.getId());
		}
		sb.append(" order by articleComment.created_date desc");
		return jdbcTemplate.queryForList(sb.toString());
	}
}