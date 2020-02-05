
package com.igomall.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Material;

import java.util.Date;

/**
 * Service - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
public interface MaterialService extends BaseService<Material, Long> {

    Page<Material> findPage(Pageable pageable, String title, String memo, Material.Type type, Date beginDate, Date endDate);

}