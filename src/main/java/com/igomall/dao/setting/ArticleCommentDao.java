
package com.igomall.dao.setting;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleComment;

import java.util.List;
import java.util.Map;

/**
 * Dao - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ArticleCommentDao extends BaseDao<ArticleComment, Long> {

	/**
	 * 查找评论
	 * 
	 * @param member
	 *            会员
	 * @param article
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 评论
	 */
	List<ArticleComment> findList(Member member, Article article, ArticleComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找评论分页
	 * 
	 * @param member
	 *            会员
	 * @param article
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 评论分页
	 */
	Page<ArticleComment> findPage(Member member, Article article, ArticleComment.Type type, Boolean isShow, Pageable pageable);

	/**
	 * 查找评论数量
	 * 
	 * @param member
	 *            会员
	 * @param article
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @return 评论数量
	 */
	Long count(Member member, Article article, ArticleComment.Type type, Boolean isShow);

	/**
	 * 计算商品总评分
	 * 
	 * @param article
	 *            商品
	 * @return 商品总评分，仅计算显示评论
	 */
	long calculateTotalScore(Article article);

	/**
	 * 计算商品评分次数
	 * 
	 * @param article
	 *            商品
	 * @return 商品评分次数，仅计算显示评论
	 */
	long calculateScoreCount(Article article);

	List<Map<String,Object>> findListBySQL(Article article);
}