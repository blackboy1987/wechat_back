
package com.igomall.dao.member;

import java.util.Date;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.BaseDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberAttribute;

/**
 * Dao - 会员
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 查找会员分页
	 * 
	 * @param rankingType
	 *            排名类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType, Pageable pageable);

	/**
	 * 查询会员注册数
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员注册数
	 */
	Long registerMemberCount(Date beginDate, Date endDate);

	/**
	 * 清除会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	void clearAttributeValue(MemberAttribute memberAttribute);

	Page<Member> findPage(Pageable pageable, String username, String name, String mobile, Integer status, Date beginDate, Date endDate);
}