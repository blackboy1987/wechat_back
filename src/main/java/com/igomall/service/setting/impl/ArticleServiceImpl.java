
package com.igomall.service.setting.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.igomall.dao.member.MemberStatisticsDao;
import com.igomall.entity.member.MemberStatistics;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.member.MemberStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.setting.ArticleCategoryDao;
import com.igomall.dao.setting.ArticleDao;
import com.igomall.dao.setting.ArticleTagDao;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.service.setting.ArticleService;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * Service - 文章
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article, Long> implements ArticleService {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleCategoryDao articleCategoryDao;
	@Autowired
	private ArticleTagDao articleTagDao;

	@Autowired
	private MemberStatisticsService memberStatisticsService;

	@Transactional(readOnly = true)
	public List<Article> findList(ArticleCategory articleCategory, ArticleTag articleTag, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		return articleDao.findList(articleCategory, articleTag, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "article", condition = "#useCache")
	public List<Article> findList(Long articleCategoryId, Long articleTagId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		ArticleCategory articleCategory = articleCategoryDao.find(articleCategoryId);
		if (articleCategoryId != null && articleCategory == null) {
			return Collections.emptyList();
		}
		ArticleTag articleTag = articleTagDao.find(articleTagId);
		if (articleTagId != null && articleTag == null) {
			return Collections.emptyList();
		}
		return articleDao.findList(articleCategory, articleTag, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<Article> findPage(ArticleCategory articleCategory, ArticleTag articleTag, Boolean isPublication, Pageable pageable) {
		return articleDao.findPage(articleCategory, articleTag, isPublication, pageable);
	}

	public long viewHits(Long id) {
		Assert.notNull(id,"");

		Ehcache cache = cacheManager.getEhcache(Article.HITS_CACHE_NAME);
		cache.acquireWriteLockOnKey(id);
		try {
			Element element = cache.get(id);
			Long hits;
			if (element != null) {
				hits = (Long) element.getObjectValue() + 1;
			} else {
				Article article = articleDao.find(id);
				if (article == null) {
					return 0L;
				}
				hits = article.getHits() + 1;
			}
			cache.put(new Element(id, hits));
			return hits;
		} finally {
			cache.releaseWriteLockOnKey(id);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article save(Article article) {
		// 更新会员的统计信息
		if(article.getMember()!=null){
			MemberStatistics memberStatistics = memberStatisticsService.findByMemberId(article.getMember().getId());
			if(memberStatistics==null){
				memberStatistics = new MemberStatistics();
				memberStatistics.init();
				memberStatistics.setMemberId(article.getMember().getId());
				memberStatistics.setArticleCount(1);
				memberStatisticsService.save(memberStatistics);
			}else{
				memberStatistics.setArticleCount(memberStatistics.getArticleCount()+1);
				memberStatisticsService.update(memberStatistics);
			}
		}

		return super.save(article);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article update(Article article) {
		return super.update(article);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article update(Article article, String... ignoreProperties) {
		return super.update(article, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Article article) {
		super.delete(article);
	}

	@Override
	public List<Map<String,Object>> findListBySql(Integer type ,Long memberId,Integer count) {
		return articleDao.findListBySql(type,memberId,count);
	}

	@Override
	public List<Map<String, Object>> findRelationArticleBySql(Integer count) {
		return articleDao.findRelationArticleBySql(count);
	}
}