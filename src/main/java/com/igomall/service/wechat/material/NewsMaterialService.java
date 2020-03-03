
package com.igomall.service.wechat.material;

import com.igomall.entity.wechat.material.NewsMaterial;
import com.igomall.service.BaseService;

/**
 * Service - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface NewsMaterialService extends BaseService<NewsMaterial, Long> {

    boolean mediaIdExists(String mediaId);

    NewsMaterial findByMediaId(String mediaId);
}