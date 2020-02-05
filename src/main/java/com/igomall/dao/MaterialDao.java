
package com.igomall.dao;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Material;

import java.util.Date;

/**
 * Dao - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
public interface MaterialDao extends BaseDao<Material, Long> {
    Page<Material> findPage(Pageable pageable, String title, String memo, Material.Type type, Date beginDate, Date endDate);
}