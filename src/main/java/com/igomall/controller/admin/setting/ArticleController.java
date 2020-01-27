
package com.igomall.controller.admin.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.FileType;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.controller.admin.setting.wechat.WechatArticle;
import com.igomall.controller.admin.setting.wechat.WechatResponse;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.service.FileService;
import com.igomall.service.member.MemberService;
import com.igomall.service.setting.ArticleCategoryService;
import com.igomall.service.setting.ArticleService;
import com.igomall.service.setting.ArticleTagService;
import com.igomall.util.JsonUtils;
import com.igomall.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
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
@RestController("adminArticleController")
@RequestMapping("/admin/api/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryService articleCategoryService;
	@Autowired
	private ArticleTagService articleTagService;

	@Autowired
	private MemberService memberService;
	@Autowired
	private FileService fileService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Article article, Long articleCategoryId, Long[] articleTagIds,String[] articleTagNames) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
		article.getArticleTags().addAll(createTags(articleTagNames));
		article.setIsTop(true);
		article.setIsPublication(true);
		if (!isValid(article)) {
			return Message.success("参数错误");
		}
		article.setHits(0L);
		articleService.save(article);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(Article.EditView.class)
	public Article edit(Long id) {
		return articleService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(Article article, Long articleCategoryId, Long[] articleTagIds) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
		article.setIsTop(true);
		article.setIsPublication(true);
		if (!isValid(article)) {
			return Message.error("参数错误");
		}
		articleService.update(article, "hits");
		return Message.success("操作成功");
	}

	@PostMapping("/article_topic")
	public Message articleTopic() {
		return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_article_topic order by orders "));
	}

	@PostMapping("/article_category")
	public Message articleCategory() {
		return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_article_category order by orders "));
	}

	@PostMapping("/article_tag")
	public Message articleTag() {
		return Message.success1("操作成功",jdbcTemplate.queryForList("select id,name from edu_article_tag order by orders "));
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Article.ListView.class)
	public Page<Article> list(Pageable pageable) {
		return articleService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		articleService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	private List<ArticleTag> createTags(String[] articleTagNames){
		List<ArticleTag> articleTags = new ArrayList<>();
		for (String name:articleTagNames) {
			ArticleTag articleTag = articleTagService.findByName(name);
			if(articleTag==null){
				articleTag.setName(name);
				articleTag.setArticles(null);
				articleTag = articleTagService.save(articleTag);
			}
			articleTags.add(articleTag);
		}

		return articleTags;
	}

	@PostMapping("/init")
	public WechatResponse init(Integer start,Integer count,Long[] articleTagIds) {
		String url = "https://mp.weixin.qq.com/mp/homepage?__biz=MzUxMzcxMzE5Ng==&hid=7&sn=f96da8d277c5906468c3842b947a0b60&scene=18&begin="+start+"&count="+count+"&action=appmsg_list&f=json&r=0.6459065065226182&appmsg_token=";
		Map<String,Object> params = new HashMap<>();
		WechatResponse wechatResponse = JsonUtils.toObject(WebUtils.post(url,params),WechatResponse.class);
		List<WechatArticle> wechatArticles = wechatResponse.getList();
		List<Article> articles = new ArrayList<>();
		for (WechatArticle wechatArticle:wechatArticles) {
			Article article = articleService.findByTitle(wechatArticle.getTitle());

			if(article==null){
				article = new Article();
				article.setIsPublication(true);
				article.setIsTop(true);
				article.setHits(0L);
				article.setMember(memberService.find(1L));
				article.setAuthor(article.getMember().getUsername());
				article.setArticleCategory(articleCategoryService.find(1L));
				article.setArticleTags(new HashSet<>(articleTagService.findList(articleTagIds)));
				article.setTitle(wechatArticle.getTitle());
				article.setImage(wechatArticle.getCover());
				article.setMemo(wechatArticle.getDigest());
				article.setContent(getContent(wechatArticle.getLink()));
				articles.add(article);
				articleService.save(article);
			}
		}
		if(wechatResponse.getHasMore()){
			return init(start+count,count,articleTagIds);
		}
		return wechatResponse;

	}


	private String getContent(String url){
		try {
			Document document = Jsoup.parse(new URL(url),2000);
			return document.getElementById("js_content").html();
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}


	@PostMapping("/init1")
	public String init1() {
		List<Article> articles = articleService.findAll();
		for (Article article:articles) {
			// article.setImage(getImage(article.getImage()));
			article.setContent(getContent1(article.getContent()));
			System.out.println(article.getContent());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			// articleService.update(article);
		}
		return "ok";

	}

	private String getImage(String url){
		if(!StringUtils.startsWith(url,"http://video.i-gomall.com/")){
			List<Map<String,Object>> urls = fileService.catchImage(FileType.image, new String[]{
					url
			}, false);

			return urls.get(0).get("url").toString();
		}else {
			return url;
		}

	}

	private String getContent1(String content){
		try {
			Document document = Jsoup.parse(content);
			return document.body().html();
		}catch (Exception e){
			e.printStackTrace();
		}
		return content;
	}
}