
package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Pageable;
import com.igomall.common.Results;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.article.Article;
import com.igomall.entity.article.ArticleCategory;
import com.igomall.entity.other.BaiDuTag;
import com.igomall.exception.ResourceNotFoundException;
import com.igomall.service.SearchService;
import com.igomall.service.article.ArticleCategoryService;
import com.igomall.service.article.ArticleService;
import com.igomall.service.other.BaiDuTagService;
import com.igomall.util.CodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("shopBaiDuTagController")
@RequestMapping("/baidu_tag")
public class BaiDuTagController extends BaseController {

	@Autowired
	private BaiDuTagService baiDuTagService;

	/**
	 * 点击数
	 */
	@GetMapping("create")
	public String create(BaiDuTag baiDuTag) {
		baiDuTag.setCode(CodeUtils.getNumberCode(3));
		while (baiDuTagService.codeExist(baiDuTag.getCode())){
			baiDuTag.setCode(CodeUtils.getNumberCode(3));
		}
		baiDuTagService.save(baiDuTag);
		return "ok";
	}

}