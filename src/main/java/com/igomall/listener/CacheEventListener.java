
package com.igomall.listener;

import com.igomall.entity.course.Course;
import com.igomall.entity.setting.Article;
import com.igomall.service.course.CourseService;
import com.igomall.service.setting.ArticleService;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener - 缓存
 * 
 * @author SHOP++ Team
 * @version 5.0
 */
@Component
public class CacheEventListener extends CacheEventListenerAdapter {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CourseService courseService;

	/**
	 * 元素过期调用
	 * 
	 * @param ehcache
	 *            缓存
	 * @param element
	 *            元素
	 */
	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		String cacheName = ehcache.getName();
		if (StringUtils.equals(cacheName, Article.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			Article article = articleService.find(id);
			if (article != null && hits != null && hits > 0 && hits > article.getHits()) {
				article.setHits(hits);
				articleService.update(article);
			}
		} else if (StringUtils.equals(cacheName, Course.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			Course course = courseService.find(id);
			if (course != null && hits != null && hits > 0) {
				long amount = hits - course.getHits();
				if (amount > 0) {
					courseService.addHits(course, amount);
				}
			}
		}
	}

}