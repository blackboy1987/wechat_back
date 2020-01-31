
package com.igomall.controller.common.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.security.CurrentUser;
import com.igomall.service.SearchService;
import com.igomall.service.member.MemberRankService;
import com.igomall.service.member.MemberService;
import com.igomall.service.member.MemberStatisticsService;
import com.igomall.service.setting.ArticleCategoryService;
import com.igomall.service.setting.ArticleService;
import com.igomall.service.setting.ArticleTagService;
import com.igomall.util.HanLPUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.*;

/**
 * Controller - 文章
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonArticleController")
@RequestMapping("/api/article")
public class ArticleController extends BaseController {
	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 20;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCategoryService articleCategoryService;

	@Autowired
	private MemberStatisticsService memberStatisticsService;

	@Autowired
	private ArticleTagService articleTagService;

	@Autowired
	private SearchService searchService;

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView({Article.ListView.class})
	public Page<Article> list(Pageable pageable,Long categoryId) {
		return articleService.findPage(articleCategoryService.find(categoryId),null,true,pageable);
	}


	@PostMapping("/view")
	public Map<String,Object> view(Long id) {
		Map<String,Object> data = new HashMap<>();
		Article article = articleService.find(id);
		Map<String,Object> articleMap = new HashMap<>();
		Member member = article.getMember();
		articleMap.put("title",article.getTitle());
		articleMap.put("content",article.getContent());
		data.put("article",articleMap);
		if(member!=null){
			Map<String,Object> author = new HashMap<>();
			author.put("username",member.getUsername());
			author.put("lastLoginDate",member.getLastLoginDate());
			author.put("level",member.getMemberRank().getName());
			author.put("point",member.getPoint());
			author.put("extra",memberStatisticsService.findByMemberId(member.getId()));
			data.put("author",author);
		}
		// 文章标签
		data.put("articleTags",article.getArticleTagNames());
		data.put("relationArticles",articleService.findRelationArticleBySql(5));
		data.put("newArticles",articleService.findListBySql(1,article.getMember().getId(),5,null,null));

		return data;
	}


	@PostMapping("/load")
	@JsonView(Article.LoadView.class)
	public Map<String,Object> load() {
		Map<String,Object> data = new HashMap<>();
		data.put("articleTags",articleTagService.findAll());
		data.put("articleCategories",articleCategoryService.findRoots());

		return data;
	}

	/**
	 * 点击数
	 */
	@PostMapping("/hits/{articleId}")
	public Long hits(@PathVariable Long articleId) {
		return articleService.viewHits(articleId);
	}

	@GetMapping(path = "/search")
	@JsonView(BaseEntity.ListView.class)
	public Page<Article> search(String keyword, Integer pageNumber) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return searchService.search(keyword, pageable);
	}
}