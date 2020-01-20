/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.igomall.service.member;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberDepositLog;
import com.igomall.entity.member.MemberStatistics;
import com.igomall.entity.member.PointLog;
import com.igomall.security.AuthenticationProvider;
import com.igomall.service.BaseService;

import java.math.BigDecimal;

/**
 * Service - 会员
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface MemberStatisticsService extends BaseService<MemberStatistics, Long> {

	/**
	 * 判断用户名是否存在
	 *
	 * @param memberId
	 *            用户id
	 * @return 用户名是否存在
	 */
	boolean memberIdExists(Long memberId);

	MemberStatistics findByMemberId(Long memberId);

}