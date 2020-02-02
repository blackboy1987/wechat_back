
package com.paicent.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.paicent.common.Message;
import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.entity.Activity;
import com.paicent.entity.ConfigInfo;
import com.paicent.service.ActivityService;
import com.paicent.service.ConfigInfoService;
import com.paicent.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * Controller - 活动配置
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController
@RequestMapping("/api/config_info")
public class ConfigInfoController extends BaseController {

	@Autowired
	private ActivityService activityService;
	@Autowired
	private ConfigInfoService configInfoService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(@RequestBody String configInfos) {
		Activity activity = JsonUtils.toObject(configInfos,Activity.class);
		Activity activity1 = activityService.find(activity.getId());
		if(activity1==null){
			return Message.error("活动不存在");
		}
		for (ConfigInfo configInfo:activity.getConfigInfos()) {
			configInfo.setActivityId(activity1.getId());
			configInfo.setYyStartTime(activity1.getStartTime());
			configInfo.setYyFinishTime(activity1.getFinishTime());
			if(configInfo.isNew()){
				configInfoService.save(configInfo);
			}else{
				configInfoService.update(configInfo,"createTime","modifyTime");
			}
		}
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

	/**
	 * 编辑
	 */
	@PostMapping("/view")
	@JsonView(Activity.ViewView.class)
	public Activity view(Long id) {
		Activity activity = activityService.find(id);
		activity.setConfigInfos(configInfoService.findList(null,id,null,null));
		return activity;
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