
package com.igomall.dao.setting;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.entity.setting.ArticleTag;

/**
 * Dao - 文章
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ArticleDao extends BaseDao<Article, Long> {

	/**
	 * 查找文章
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param articleTag
	 *            文章标签
	 * @param isPublication
	 *            是否发布
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 文章
	 */
	List<Article> findList(ArticleCategory articleCategory, ArticleTag articleTag, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找文章
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param isPublication
	 *            是否发布
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 文章
	 */
	List<Article> findList(ArticleCategory articleCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count);

	/**
	 * 查找文章分页
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param articleTag
	 *            文章标签
	 * @param isPublication
	 *            是否发布
	 * @param pageable
	 *            分页信息
	 * @return 文章分页
	 */
	Page<Article> findPage(ArticleCategory articleCategory, ArticleTag articleTag, Boolean isPublication, Pageable pageable);

	/**
	 *
	 * @param type
	 * 		排序的类型
	 * 	1：按添加时间排序
	 * 	2：先isTop 然后添加时间
	 * 	3：先hits 然后添加时间
	 * @param memberId
	 * 		作者id
	 * @param count
	 * 		查询数量
	 * @return
	 */
	List<Map<String,Object>> findListBySql(Integer type ,Long memberId,Integer count, String articleTagIds,Long articleCategoryId);

	List<Map<String,Object>> findRelationArticleBySql(Integer count);;
}