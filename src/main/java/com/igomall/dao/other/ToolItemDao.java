
package com.igomall.dao.other;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.other.ToolCategory;
import com.igomall.entity.other.ToolItem;

import java.util.Date;
import java.util.List;

/**
 * Dao - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ToolItemDao extends BaseDao<ToolItem, Long> {

	/**
	 * 查找文章
	 * 
	 * @param bookCategory
	 *            文章分类
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
	List<ToolItem> findList(ToolCategory bookCategory, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找文章
	 * 
	 * @param bookCategory
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
	List<ToolItem> findList(ToolCategory bookCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count);

	/**
	 * 查找文章分页
	 * 
	 * @param bookCategory
	 *            文章分类
	 * @param isPublication
	 *            是否发布
	 * @param pageable
	 *            分页信息
	 * @return 文章分页
	 */
	Page<ToolItem> findPage(ToolCategory bookCategory, Boolean isPublication, Pageable pageable);

}