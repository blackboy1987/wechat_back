
package com.igomall.service.setting.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.setting.ArticleTagDao;
import com.igomall.dao.setting.ArticleTopicDao;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.entity.setting.ArticleTopic;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.setting.ArticleTagService;
import com.igomall.service.setting.ArticleTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class ArticleTopicServiceImpl extends BaseServiceImpl<ArticleTopic, Long> implements ArticleTopicService {

	@Autowired
	private ArticleTopicDao articleTopicDao;

	@Transactional(readOnly = true)
	@Cacheable(value = "articleTopic", condition = "#useCache")
	public List<ArticleTopic> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return articleTopicDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleTopic", allEntries = true)
	public ArticleTopic save(ArticleTopic articleTopic) {
		return super.save(articleTopic);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleTopic", allEntries = true)
	public ArticleTopic update(ArticleTopic articleTopic) {
		return super.update(articleTopic);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleTopic", allEntries = true)
	public ArticleTopic update(ArticleTopic articleTopic, String... ignoreProperties) {
		return super.update(articleTopic, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleTag", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleTag", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "articleTag", allEntries = true)
	public void delete(ArticleTopic articleTopic) {
		super.delete(articleTopic);
	}

	@Override
	public List<Map<String, Object>> findListBySql(Integer count) {
		return articleTopicDao.findListBySql(count);
	}

	@Override
	public boolean nameExists(String name) {
		return articleTopicDao.exists("name",name);
	}
}