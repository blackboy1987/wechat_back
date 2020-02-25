
package com.igomall.listener;

import com.igomall.service.course.CourseService;
import com.igomall.service.other.BookCategoryService;
import com.igomall.service.other.ProjectCategoryService;
import com.igomall.service.other.ToolCategoryService;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
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
	private BookCategoryService bookCategoryService;
	@Autowired
	private ToolCategoryService toolCategoryService;
	@Autowired
	private ProjectCategoryService projectCategoryService;
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
		bookCategoryService.findRoots1();
		toolCategoryService.findRoots1();
		projectCategoryService.findRoots1();
		courseService.addCache();
	}

}