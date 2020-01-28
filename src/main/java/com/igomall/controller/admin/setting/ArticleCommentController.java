
package com.igomall.controller.admin.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.setting.ArticleComment;
import com.igomall.service.setting.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminReviewController")
@RequestMapping("/admin/api/article_comment")
public class ArticleCommentController extends BaseController {

	@Autowired
	private ArticleCommentService articleCommentService;

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public ArticleComment edit(Long id) {
		return articleCommentService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(Long id, @RequestParam(defaultValue = "false") Boolean isShow) {
		ArticleComment articleComment = articleCommentService.find(id);
		if (articleComment == null) {
			return Message.error("参数错误");
		}
		articleComment.setIsShow(isShow);
		articleCommentService.update(articleComment);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(ArticleComment.ListView.class)
	public Page<ArticleComment> list(ArticleComment.Type type, Pageable pageable) {
		return articleCommentService.findPage(null, null, type, null, pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		articleCommentService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}