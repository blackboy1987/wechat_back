
package com.igomall.controller.admin.setting;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.setting.ArticleTopic;
import com.igomall.service.setting.ArticleTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Controller - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminArticleTopicController")
@RequestMapping("/admin/api/article_topic")
public class ArticleTopicController extends BaseController {

	@Autowired
	private ArticleTopicService articleTopicService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(ArticleTopic articleTopic) {
		if(articleTopicService.nameExists(articleTopic.getName())){
			return Message.error("专题已存在");
		}
		if (!isValid(articleTopic, BaseEntity.Save.class)) {
			return Message.error("参数异常");
		}
		articleTopic.setArticles(null);
		articleTopicService.save(articleTopic);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("articleTopic", articleTopicService.find(id));
		return "admin/article_topic/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(ArticleTopic articleTopic) {
		if (!isValid(articleTopic)) {
			return Message.error("参数异常");
		}
		articleTopicService.update(articleTopic, "articles");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<ArticleTopic> list(Pageable pageable) {
		return articleTopicService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		articleTopicService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}