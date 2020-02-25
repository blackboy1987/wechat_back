
package com.igomall.service.other.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.member.PointLogDao;
import com.igomall.dao.other.BookItemErrorDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;
import com.igomall.entity.other.BookItemError;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.other.BookItemErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class BookItemErrorServiceImpl extends BaseServiceImpl<BookItemError, Long> implements BookItemErrorService {
    @Autowired
    private BookItemErrorDao bookItemErrorDao;

    @Transactional(readOnly = true)
    public Page<BookItemError> findPage(Member member, Pageable pageable) {
        return bookItemErrorDao.findPage(member, pageable);
    }
}