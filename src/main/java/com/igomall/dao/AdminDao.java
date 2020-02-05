
package com.igomall.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Admin;
import com.igomall.entity.Department;

import java.util.Date;

/**
 * Dao - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
public interface AdminDao extends BaseDao<Admin, Long> {

    Page<Admin> findPage(Pageable pageable, String name, String username, String email, Department department, Date beginDate, Date endDate);
}