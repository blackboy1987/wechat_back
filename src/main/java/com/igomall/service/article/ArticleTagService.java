
package com.igomall.service.article;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.entity.article.ArticleTag;
import com.igomall.service.BaseService;

import java.util.List;

/**
 * Service - 文章标签
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ArticleTagService extends BaseService<ArticleTag, Long> {

	/**
	 * 查找文章标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 文章标签
	 */
	List<ArticleTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}