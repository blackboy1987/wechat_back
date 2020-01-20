
package com.igomall.controller.common;

import com.igomall.service.setting.ArticleService;
import com.igomall.service.setting.ArticleTagService;
import com.igomall.service.setting.FriendLinkService;
import com.igomall.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 错误
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("commonIndexController")
@RequestMapping("/api/index")
public class IndexController {

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
	public Map<String,Object> top(Integer count) {
		Map<String,Object> data = new HashMap<>();
		if(count==null){
			count = 10;
		}
		data.put("articles",articleService.findListBySql(null,null,count));
		data.put("articleTags",articleTagService.findListBySql(50));
		data.put("setting", SystemUtils.getSetting());
		data.put("friendLinks", friendLinkService.findListBySql(1000));
		return data;
	}

}