
package com.igomall.controller.admin.setting;

import java.util.List;
import java.util.Set;

import com.igomall.controller.admin.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.common.Message;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.service.setting.ArticleCategoryService;

/**
 * Controller - 文章分类
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminArticleCategoryController")
@RequestMapping("/admin/article_category")
public class ArticleCategoryController extends BaseController {

	@Autowired
	private ArticleCategoryService articleCategoryService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ArticleCategory articleCategory, Long parentId) {
		articleCategory.setParent(articleCategoryService.find(parentId));
		if (!isValid(articleCategory)) {
			return ERROR_VIEW;
		}
		articleCategory.setTreePath(null);
		articleCategory.setGrade(null);
		articleCategory.setChildren(null);
		articleCategory.setArticles(null);
		articleCategoryService.save(articleCategory);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public ArticleCategory edit(Long id) {
		return articleCategoryService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(ArticleCategory articleCategory, Long parentId) {
		articleCategory.setParent(articleCategoryService.find(parentId));
		if (!isValid(articleCategory)) {
			return ERROR_VIEW;
		}
		if (articleCategory.getParent() != null) {
			ArticleCategory parent = articleCategory.getParent();
			if (parent.equals(articleCategory)) {
				return ERROR_VIEW;
			}
			List<ArticleCategory> children = articleCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		articleCategoryService.update(articleCategory, "treePath", "grade", "children", "articles");
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		return "admin/article_category/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long id) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ArticleCategory> children = articleCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.articleCategory.deleteExistChildrenNotAllowed");
		}
		Set<Article> articles = articleCategory.getArticles();
		if (articles != null && !articles.isEmpty()) {
			return Message.error("admin.articleCategory.deleteExistArticleNotAllowed");
		}
		articleCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}