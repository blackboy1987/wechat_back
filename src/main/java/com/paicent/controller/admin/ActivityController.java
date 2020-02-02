
package com.paicent.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.paicent.common.Message;
import com.paicent.common.Page;
import com.paicent.common.Pageable;
import com.paicent.entity.Activity;
import com.paicent.entity.ConfigInfo;
import com.paicent.service.ActivityService;
import com.paicent.service.ConfigInfoService;
import com.paicent.util.Date8Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
	@Autowired
	private ConfigInfoService configInfoService;


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

	/**
	 * 编辑
	 */
	@PostMapping("/view1")
	@JsonView(Activity.ViewView1.class)
	public Map<String,Object> view1(Long id) {
		Map<String,Object> data = new HashMap<>();
		Activity activity = activityService.find(id);
		data.put("id",activity.getId());
		data.put("date", Date8Utils.formatDateToString(activity.getStartTime(),"M月d日"));
		data.put("scene",activity.getName());
		List<Map<String,Object>> data1 = new ArrayList<>();
		activity.setConfigInfos(configInfoService.findList(null,id,null,null));
		for (ConfigInfo configInfo:activity.getConfigInfos()) {
			Map<String,Object> configInfoMap = new HashMap<>();
			configInfoMap.put("name",configInfo.getDistrict());
			configInfoMap.put("time",data.get("date")+Date8Utils.formatDateToString(configInfo.getApplyStartTime(),"HH:mm")+"—"+Date8Utils.formatDateToString(configInfo.getApplyFinishTime(),"HH:mm"));
			configInfoMap.put("stime",Date8Utils.formatDateToString(configInfo.getApplyStartTime(),"yyyy-MM-dd HH:mm:ss"));
			configInfoMap.put("etime",Date8Utils.formatDateToString(configInfo.getApplyFinishTime(),"yyyy-MM-dd HH:mm:ss"));
			data1.add(configInfoMap);
		}
		data.put("data",data1);
		return data;
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