
package com.igomall.service.other.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.other.ProjectItemErrorDao;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ProjectItemError;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.other.ProjectItemErrorService;
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
public class ProjectItemErrorServiceImpl extends BaseServiceImpl<ProjectItemError, Long> implements ProjectItemErrorService {
    @Autowired
    private ProjectItemErrorDao projectItemErrorDao;

    @Transactional(readOnly = true)
    public Page<ProjectItemError> findPage(Member member, Pageable pageable) {
        return projectItemErrorDao.findPage(member, pageable);
    }
}