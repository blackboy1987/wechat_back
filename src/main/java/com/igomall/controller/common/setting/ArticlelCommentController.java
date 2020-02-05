
package com.igomall.controller.common.setting;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.common.Setting;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.Article;
import com.igomall.entity.setting.ArticleComment;
import com.igomall.security.CurrentUser;
import com.igomall.service.SearchService;
import com.igomall.service.member.MemberStatisticsService;
import com.igomall.service.setting.ArticleCategoryService;
import com.igomall.service.setting.ArticleCommentService;
import com.igomall.service.setting.ArticleService;
import com.igomall.service.setting.ArticleTagService;
import com.igomall.util.SystemUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 文章评论
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonArticleCommentController")
@RequestMapping("/api/article_comment")
public class ArticlelCommentController extends BaseController {

	@Autowired
	private ArticleCommentService articleCommentService;
	@Autowired
	private ArticleService articleService;


	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView({ArticleComment.ListView.class})
	public Page<ArticleComment> list(Pageable pageable,Long articleId) {
		return articleCommentService.findPage(null,articleService.find(articleId),null,true,pageable);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Long articleId, Integer score, String content, @CurrentUser Member currentUser, HttpServletRequest request) {
		Setting setting = SystemUtils.getSetting();
		if (!setting.getIsReviewEnabled()) {
			return Message.error("未开放评论");
		}
		Article article = articleService.find(articleId);
		if (article == null || BooleanUtils.isNotTrue(article.getIsPublication())) {
			return Message.error("文章不存在");
		}
		if (!isValid(ArticleComment.class, "score", score) || !isValid(ArticleComment.class, "content", content)) {
			return Message.error("参数错误");
		}
		if (currentUser == null) {
			return Message.error("请先登录");
		}

		ArticleComment articleComment = new ArticleComment();
		articleComment.setScore(score);
		articleComment.setContent(content);
		articleComment.setIp(request.getRemoteAddr());
		articleComment.setMember(currentUser);
		articleComment.setArticle(article);
		articleComment.setReplyReviews(null);
		articleComment.setForReview(null);
		if (setting.getIsReviewCheck()) {
			articleComment.setIsShow(false);
			articleCommentService.save(articleComment);
			return Message.success("评论发布成功，请等待审核");
		} else {
			articleComment.setIsShow(true);
			articleCommentService.save(articleComment);
			return Message.success("评论发布成功");
		}
	}
}