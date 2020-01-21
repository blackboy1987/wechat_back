
package com.igomall.controller.common.setting;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.service.setting.ArticleTagService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Iterator;

/**
 * Controller - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonArticleTagController")
@RequestMapping("/api/article_tag")
public class ArticleTagController extends BaseController {

	@Autowired
	private ArticleTagService articleTagService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", articleTagService.findPage(pageable));
		return "admin/article_tag/list";
	}
	@GetMapping("/init")
	public String init() throws Exception{
		Document document = Jsoup.parse(new URL("http://www.zuidaima.com/"),2000);
		Iterator<Element> iterator = document.getElementsByClass("post_tag").iterator();
		while (iterator.hasNext()){
			Element element = iterator.next();
			String name = element.text();
			if(!articleTagService.nameExists(name)){
				ArticleTag articleTag = new ArticleTag();
				articleTag.setName(name);
				articleTagService.save(articleTag);
			}
		}
		return "ok";
	}



	@PostMapping("/save")
	public Message save(ArticleTag articleTag) {
		if (!isValid(articleTag, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		articleTag.setArticles(null);
		articleTagService.save(articleTag);
		return Message.success("操作成功");
	}

	@PostMapping("/edit")
	public ArticleTag edit(Long id) {
		return articleTagService.find(id);
	}

	@PostMapping("/update")
	public Message update(ArticleTag articleTag) {
		if (!isValid(articleTag)) {
			return Message.error("参数错误");
		}
		articleTagService.update(articleTag,  "articles");
		return Message.success("操作成功");
	}

	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		articleTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}