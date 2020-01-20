
package com.igomall.controller.common.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleCategory;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.security.CurrentUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCategoryService articleCategoryService;

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRankService memberRankService;
	@Autowired
	private MemberStatisticsService memberStatisticsService;
	@Autowired
	private ArticleTagService articleTagService;

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

		data.put("newArticles",articleService.findListBySql(1,article.getMember().getId(),5));

		return data;
	}


	@GetMapping("/init")
	public String init(String url,Integer pages,Long articleCategoryId) throws Exception{
		ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
		saveArticles(url,pages,articleCategory);
		return "ok";
	}


	private void saveArticles(String url,Integer pages,ArticleCategory articleCategory) throws Exception{
		for (int i = 1; i <= pages; i++) {
			Document document = Jsoup.parse(new URL(url +"/"+i+"/"),2000);
			Elements elements = document.getElementsByClass("layui-col-space15").first().getElementsByTag("li");
			Iterator<Element> iterator = elements.iterator();
			while (iterator.hasNext()){
				Element element = iterator.next();
				Element element1 = element.getElementsByTag("h2").last().getElementsByTag("a").last();
				String url1 = element1.attr("href");
				Article article = saveArticle("https://fly.layui.com"+url1);
				if(article!=null){
					article.setArticleCategory(articleCategory);
					articleService.save(article);
					System.out.println(article);
				}
			}
		}
	}

	private Article saveArticle(String url) throws Exception{
		try {
			Article article = new Article();
			Document document = Jsoup.parse(new URL(url),2000);
			Element element = document.getElementsByClass("content").first();
			String title = element.getElementsByTag("h1").first().text();
			String content = element.getElementsByClass("detail-body").first().html();
			article.setTitle(title);
			article.setContent(content);
			// 获取作者信息
			Element element1 = document.getElementsByClass("fly-detail-user").first().getElementsByTag("a").first();
			String author = element1.text();
			String authorUrl = "https://fly.layui.com"+element1.attr("href");
			article.setAuthor(author);
			article.setIsPublication(true);
			article.setIsTop(true);
			article.setHits(RandomUtils.nextLong(100,999999));
			article.setMember(createMember(authorUrl));
			return article;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private Member createMember(String url) throws Exception{
		Member member = new Member();
		member.init();
		System.out.println("memberUrl:"+url);
		Document document = Jsoup.parse(new URL(url),2000);
		Element element = document.getElementsByClass("fly-home").first();
		String avatar = element.getElementsByTag("img").first().attr("src");
		String username = element.getElementsByTag("h1").first().text();
		member.setAvatar(avatar);
		member.setUsername(username);
		member.setName(member.getUsername());
		member.setEmail(member.getUsername()+"@i-gomall.com");
		member.setPassword("123456");
		member.setMemberRank(memberRankService.findDefault());
		Member member11 = memberService.findByUsername(member.getUsername());
		if(member11==null){
			return memberService.save(member);
		}else{
			return member11;
		}
	}

	/**
	 * 列表
	 */
	@GetMapping("/zhaiyao")
	public String zhaiyao() {
		List<ArticleTag> articleTags = articleTagService.findAll();
		for (ArticleTag articleTag:articleTags) {
			List<Article> articles = articleService.findList(null,articleTag,null,null,null,null);
			articleTag.setArticles(new HashSet<>(articles));
			articleTagService.update(articleTag);

		}
		return "ok";
	}

	@PostMapping("/load")
	@JsonView(Article.LoadView.class)
	public Map<String,Object> load() {
		Map<String,Object> data = new HashMap<>();
		data.put("articleTags",articleTagService.findAll());
		data.put("articleCategories",articleCategoryService.findRoots());

		return data;
	}


	@PostMapping("/save")
	public Message save(Article article, Long articleCategoryId, Long[] articleTagIds, @CurrentUser Member member) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
		article.setMember(member);
		article.setAuthor(member.getUsername());
		article.setIsTop(true);
		article.setIsPublication(true);
		if (!isValid(article)) {
			return Message.error("参数错误");
		}
		article.setHits(0L);
		articleService.save(article);
		return Message.success("操作成功");
	}

	@PostMapping("/edit")
	public Article edit(Long id) {
		return articleService.find(id);
	}

	@PostMapping("/update")
	public Message update(Article article, Long articleCategoryId, Long[] articleTagIds,@CurrentUser Member member) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
		article.setMember(member);
		article.setAuthor(member.getUsername());
		article.setIsTop(true);
		article.setIsPublication(true);
		if (!isValid(article)) {
			return Message.error("参数错误");
		}
		articleService.update(article, "hits", "pageNumber");
		return Message.success("操作成功");
	}

	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		articleService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}