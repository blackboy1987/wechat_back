
package com.igomall.dao.setting.impl;

import com.igomall.dao.impl.BaseDaoImpl;
import com.igomall.dao.setting.ArticleTagDao;
import com.igomall.dao.setting.ArticleTopicDao;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.entity.setting.ArticleTopic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Dao - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class ArticleTopicDaoImpl extends BaseDaoImpl<ArticleTopic, Long> implements ArticleTopicDao {

    @Override
    public List<Map<String, Object>> findListBySql(Integer count) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append("id, ");
        sb.append("name ");
        sb.append("from edu_article_topic ");
        if(count!=null){
            sb.append("limit "+count);
        }
        return jdbcTemplate.queryForList(sb.toString());
    }
}