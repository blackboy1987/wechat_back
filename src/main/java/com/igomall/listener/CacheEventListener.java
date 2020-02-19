
package com.igomall.listener;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * Listener - 缓存
 * 
 * @author SHOP++ Team
 * @version 5.0
 */
@Component
public class CacheEventListener extends CacheEventListenerAdapter {

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

	}

}