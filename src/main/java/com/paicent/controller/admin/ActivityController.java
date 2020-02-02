
package com.paicent.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.paicent.common.Message;
import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.entity.Activity;
import com.paicent.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * Controller - 活动
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Activity activity,Boolean status1) {
		if(status1!=null&&status1){
			activity.setStatus(1);
		}else{
			activity.setStatus(0);
		}

		Map<String,String> validResults = isValid1(activity);
		if(!validResults.isEmpty()){
			return Message.error1("参数错误",validResults);
		}
		activityService.save(activity);
		return Message.success("操作成功");

	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(Activity.EditView.class)
	public Activity edit(Long id) {
		return activityService.find(id);
	}

	@PostMapping("/update")
	public Message update(Activity activity,Boolean status1) {
		if(status1!=null&&status1){
			activity.setStatus(1);
		}else{
			activity.setStatus(0);
		}
		Map<String,String> validResults = isValid1(activity);
		if(!validResults.isEmpty()){
			return Message.error1("参数错误",validResults);
		}
		Activity pActivity = activityService.find(activity.getId());
		if (pActivity == null) {
			return Message.error("活动不存在");
		}
		activityService.update(activity);
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Activity.ListView.class)
	public Page<Activity> list(Pageable pageable, String name, String description, Integer status, Date startTime, Date finishTime) {
		return activityService.findPage(pageable,name,description,status,startTime,finishTime);
	}
}