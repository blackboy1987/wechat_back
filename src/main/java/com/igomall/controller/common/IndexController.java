
package com.igomall.controller.common;

import com.igomall.controller.member.BaseController;
import com.igomall.service.setting.ArticleService;
import com.igomall.service.setting.ArticleTagService;
import com.igomall.service.setting.FriendLinkService;
import com.igomall.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 错误
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("commonIndexController")
@RequestMapping("/api/index")
public class IndexController extends BaseController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleTagService articleTagService;
	@Autowired
	private FriendLinkService friendLinkService;

	/**
	 * 列表
	 */
	@PostMapping
	public Map<String,Object> index(Integer count) {
		Map<String,Object> data = new HashMap<>();
		if(count==null){
			count = 10;
		}
		data.put("newArticles",articleService.findListBySql(null,null,count,null,null));
		data.put("newComments",findListBysql("select id,comment title from ((select id,content,created_date from edu_article_comment ORDER BY created_date DESC) UNION (select id,content,created_date from edu_course_comment ORDER BY created_date DESC)) as comments ORDER BY created_date DESC limit 10"));
		List<Map<String,Object>> showArticleTags = findListBysql("select id,show_name name from edu_article_tag where is_show_index=true order by orders");
		for (Map<String,Object> map:showArticleTags) {
			map.put("articles",articleService.findListBySql(null,null,count,map.get("id")+"",null));
		}
		data.put("tagArticles",showArticleTags);
		data.put("articleTags",articleTagService.findListBySql(50));
		data.put("setting", SystemUtils.getSetting());
		return data;
	}

}