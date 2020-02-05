
package com.igomall.service;

import java.math.BigDecimal;
import java.util.List;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.setting.Article;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;

/**
 * Service - 搜索
 * 
 * @author blackboy
 * @version 1.0
 */
public interface SearchService {

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void index(Class<?> type);

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 * @param purgeAll
	 *            是否清空已存在索引
	 */
	void index(Class<?> type, boolean purgeAll);

	/**
	 * 搜索文章分页
	 * 
	 * @param keyword
	 *            关键词
	 * @param pageable
	 *            分页信息
	 * @return 文章分页
	 */
	Page<Article> search(String keyword, Pageable pageable);

	/**
	 * 搜索商品分页
	 * 
	 * @param keyword
	 *            关键词
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Course> search(String keyword, Lesson lesson, BigDecimal startPrice, BigDecimal endPrice,Course.OrderType orderType, Pageable pageable);

	/**
	 * 搜索店铺集合
	 * 
	 * @param keyword
	 *            关键词
	 * @return 店铺集合
	 */
	List<Lesson> searchLesson(String keyword);

}