
package com.igomall.service.impl;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.MaterialDao;
import com.igomall.entity.Material;
import com.igomall.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class MaterialServiceImpl extends BaseServiceImpl<Material, Long> implements MaterialService {

    @Autowired
    private MaterialDao materialDao;

    @Override
    public Page<Material> findPage(Pageable pageable, String title, String memo, Material.Type type, Date beginDate, Date endDate) {
        return materialDao.findPage(pageable, title, memo,type, beginDate, endDate);
    }
}