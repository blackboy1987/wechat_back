
package com.igomall.controller.common.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.service.setting.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller - 文章分类
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonArticleCategoryController")
@RequestMapping("/api/article_category")
public class ArticleCategoryController extends BaseController {

	@Autowired
	private ArticleCategoryService articleCategoryService;

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(ArticleCategory.ListView.class)
	public List<ArticleCategory> list() {
		return articleCategoryService.findTree();
	}

	@PostMapping("/tree")
	@JsonView(ArticleCategory.TreeView.class)
	public List<ArticleCategory> tree() {
		return articleCategoryService.findRoots();
	}
}