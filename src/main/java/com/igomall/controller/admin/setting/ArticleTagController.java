
package com.igomall.controller.admin.setting;

import com.igomall.common.Page;
import com.igomall.controller.admin.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.igomall.common.Message;
import com.igomall.common.Pageable;
import com.igomall.entity.setting.ArticleTag;
import com.igomall.entity.BaseEntity;
import com.igomall.service.setting.ArticleTagService;

/**
 * Controller - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminArticleTagController")
@RequestMapping("/admin/api/article_tag")
public class ArticleTagController extends BaseController {

	@Autowired
	private ArticleTagService articleTagService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(ArticleTag articleTag) {
		if(articleTagService.nameExists(articleTag.getName())){
			return Message.error("名称已存在");
		}
		if (!isValid(articleTag, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		articleTag.setArticles(null);
		articleTagService.save(articleTag);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("articleTag", articleTagService.find(id));
		return "admin/article_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ArticleTag articleTag) {
		if(articleTagService.nameUnique(articleTag.getId(),articleTag.getName())){
			return Message.error("名称已存在");
		}
		if (!isValid(articleTag)) {
			return Message.error("参数错误");
		}
		articleTagService.update(articleTag, "articles");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<ArticleTag> list(Pageable pageable) {
		return articleTagService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		articleTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}