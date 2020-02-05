
package com.igomall.controller.admin.course;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.CourseTag;
import com.igomall.service.course.CourseTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Controller - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("adminCourseTagController")
@RequestMapping("/admin/api/course_tag")
public class CourseTagController extends BaseController {

	@Autowired
	private CourseTagService courseTagService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(CourseTag courseTag) {
		if(courseTagService.nameExists(courseTag.getName())){
			return Message.error("名称已存在");
		}
		if (!isValid(courseTag, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		courseTag.setCourses(null);
		courseTagService.save(courseTag);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("articleTag", courseTagService.find(id));
		return "admin/article_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(CourseTag courseTag) {
		if(courseTagService.nameUnique(courseTag.getId(),courseTag.getName())){
			return Message.error("名称已存在");
		}
		if (!isValid(courseTag)) {
			return Message.error("参数错误");
		}
		courseTagService.update(courseTag, "courses");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Page<CourseTag> list(Pageable pageable) {
		return courseTagService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		courseTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}