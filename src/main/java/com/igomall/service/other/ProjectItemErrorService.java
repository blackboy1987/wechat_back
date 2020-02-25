
package com.igomall.service.other;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ProjectItemError;
import com.igomall.service.BaseService;

/**
 * Service - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
public interface ProjectItemErrorService extends BaseService<ProjectItemError, Long> {

    /**
     * 查找积分记录分页
     *
     * @param member
     *            会员
     * @param pageable
     *            分页信息
     * @return 积分记录分页
     */
    Page<ProjectItemError> findPage(Member member, Pageable pageable);
}