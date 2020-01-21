
package com.igomall.controller.common.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.service.setting.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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

	@PostMapping("/save")
	public Message save(ArticleCategory articleCategory, Long parentId) {
		articleCategory.setParent(articleCategoryService.find(parentId));
		if (!isValid(articleCategory)) {
			return Message.error("参数错误");
		}
		articleCategory.setTreePath(null);
		articleCategory.setGrade(null);
		articleCategory.setChildren(null);
		articleCategory.setArticles(null);
		articleCategoryService.save(articleCategory);
		return Message.success("操作成功");
	}

	@PostMapping("/edit")
	public ArticleCategory edit(Long id) {
		return articleCategoryService.find(id);
	}

	@PostMapping("/update")
	public Message update(ArticleCategory articleCategory, Long parentId) {
		articleCategory.setParent(articleCategoryService.find(parentId));
		if (!isValid(articleCategory)) {
			return Message.error("参数错误");
		}
		if (articleCategory.getParent() != null) {
			ArticleCategory parent = articleCategory.getParent();
			if (parent.equals(articleCategory)) {
				return Message.error("参数错误");
			}
			List<ArticleCategory> children = articleCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Message.error("参数错误");
			}
		}
		articleCategoryService.update(articleCategory, "treePath", "grade", "children", "articles");
		return Message.success("操作成功");
	}

	@PostMapping("/delete")
	public Message delete(Long id) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ArticleCategory> children = articleCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("存在下级分类，删除失败");
		}
		Set<Article> articles = articleCategory.getArticles();
		if (articles != null && !articles.isEmpty()) {
			return Message.error("该分类下存在文章，删除失败");
		}
		articleCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}