
package com.igomall.controller.admin.course;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.CourseTopic;
import com.igomall.service.course.CourseTopicService;
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
public class CourseTopicController extends BaseController {

	@Autowired
	private CourseTopicService courseTopicService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(CourseTopic courseTopic) {
		if(courseTopicService.nameExists(courseTopic.getName())){
			return Message.error("专题已存在");
		}
		if (!isValid(courseTopic, BaseEntity.Save.class)) {
			return Message.error("参数异常");
		}
		courseTopic.setCourses(null);
		courseTopicService.save(courseTopic);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("courseTopic", courseTopicService.find(id));
		return "admin/article_topic/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(CourseTopic courseTopic) {
		if (!isValid(courseTopic)) {
			return Message.error("参数异常");
		}
		courseTopicService.update(courseTopic, "courses");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<CourseTopic> list(Pageable pageable) {
		return courseTopicService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		courseTopicService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}