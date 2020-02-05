/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.igomall.service.member.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.*;
import com.igomall.entity.User;
import com.igomall.entity.member.*;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.member.MemberService;
import com.igomall.service.member.MemberStatisticsService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Service - 会员
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class MemberStatisticsServiceImpl extends BaseServiceImpl<MemberStatistics, Long> implements MemberStatisticsService {

	@Autowired
	private MemberStatisticsDao memberStatisticsDao;

	@Transactional(readOnly = true)
	public boolean memberIdExists(Long memberId) {
		return memberStatisticsDao.exists("memberId", memberId);
	}

	public MemberStatistics findByMemberId(Long memberId){
		return memberStatisticsDao.find("memberId",memberId);
	}
}