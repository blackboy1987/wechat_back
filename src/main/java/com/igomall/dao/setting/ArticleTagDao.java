
package com.igomall.dao.setting;

import com.igomall.dao.BaseDao;
import com.igomall.entity.setting.ArticleTag;

import java.util.List;
import java.util.Map;

/**
 * Dao - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ArticleTagDao extends BaseDao<ArticleTag, Long> {

    List<Map<String,Object>> findListBySql(Integer count);
}