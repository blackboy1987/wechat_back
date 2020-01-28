
package com.igomall.service.setting.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.MemberDao;
import com.igomall.dao.setting.ArticleCommentDao;
import com.igomall.dao.setting.ArticleDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleComment;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.setting.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * Service - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleComment, Long> implements ArticleCommentService {

	@Autowired
	private ArticleCommentDao articleCommentDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ArticleDao articleDao;

	@Override
	@Transactional(readOnly = true)
	public List<ArticleComment> findList(Member member, Article article, ArticleComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return articleCommentDao.findList(member, article, type, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "articleComment", condition = "#useCache")
	public List<ArticleComment> findList(Long memberId, Long productId, ArticleComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		Article article = articleDao.find(productId);
		if (productId != null && article == null) {
			return Collections.emptyList();
		}
		return articleCommentDao.findList(member, article, type, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ArticleComment> findPage(Member member, Article article, ArticleComment.Type type, Boolean isShow, Pageable pageable) {
		return articleCommentDao.findPage(member, article, type, isShow, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member, Article article, ArticleComment.Type type, Boolean isShow) {
		return articleCommentDao.count(member, article, type, isShow);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleComment", allEntries = true)
	public ArticleComment save(ArticleComment review) {
		Assert.notNull(review,"");

		ArticleComment pReview = super.save(review);
		Article article = pReview.getArticle();
		if (article != null) {
			articleCommentDao.flush();
			long totalScore = articleCommentDao.calculateTotalScore(article);
			long scoreCount = articleCommentDao.calculateScoreCount(article);
			article.setTotalScore(totalScore);
			article.setScoreCount(scoreCount);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleComment", allEntries = true)
	public ArticleComment update(ArticleComment review) {
		Assert.notNull(review);

		ArticleComment pReview = super.update(review);
		Article article = pReview.getArticle();
		if (article != null) {
			articleCommentDao.flush();
			long totalScore = articleCommentDao.calculateTotalScore(article);
			long scoreCount = articleCommentDao.calculateScoreCount(article);
			article.setTotalScore(totalScore);
			article.setScoreCount(scoreCount);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleComment", allEntries = true)
	public ArticleComment update(ArticleComment review, String... ignoreProperties) {
		return super.update(review, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleComment", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleComment", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleComment", allEntries = true)
	public void delete(ArticleComment review) {
		if (review != null) {
			super.delete(review);
			Article article = review.getArticle();
			if (article != null) {
				articleCommentDao.flush();
				long totalScore = articleCommentDao.calculateTotalScore(article);
				long scoreCount = articleCommentDao.calculateScoreCount(article);
				article.setTotalScore(totalScore);
				article.setScoreCount(scoreCount);
			}
		}
	}

	@Override
	@CacheEvict(value = "articleComment", allEntries = true)
	public void reply(ArticleComment articleComment, ArticleComment replyArticleComment) {
		if (articleComment == null || replyArticleComment == null) {
			return;
		}

		replyArticleComment.setIsShow(true);
		replyArticleComment.setArticle(articleComment.getArticle());
		replyArticleComment.setForReview(articleComment);
		replyArticleComment.setScore(articleComment.getScore());
		replyArticleComment.setMember(articleComment.getMember());
		articleCommentDao.persist(replyArticleComment);
	}

}