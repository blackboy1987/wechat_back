
package com.igomall.controller.admin.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.course.CourseComment;
import com.igomall.service.course.CourseCommentService;
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
@RestController("adminCourseCommentController")
@RequestMapping("/admin/api/course_comment")
public class CourseCommentController extends BaseController {

	@Autowired
	private CourseCommentService courseCommentService;

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public CourseComment edit(Long id) {
		return courseCommentService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(Long id, @RequestParam(defaultValue = "false") Boolean isShow) {
		CourseComment articleComment = courseCommentService.find(id);
		if (articleComment == null) {
			return Message.error("参数错误");
		}
		articleComment.setIsShow(isShow);
		courseCommentService.update(articleComment);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(CourseComment.ListView.class)
	public Page<CourseComment> list(CourseComment.Type type, Pageable pageable) {
		return courseCommentService.findPage(null, null,null, type, null, pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		courseCommentService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}