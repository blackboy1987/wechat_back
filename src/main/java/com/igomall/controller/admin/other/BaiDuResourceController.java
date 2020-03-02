
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.BaiDuResource;
import com.igomall.entity.other.BaiDuTag;
import com.igomall.service.other.BaiDuResourceService;
import com.igomall.service.other.BaiDuTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

/**
 * Controller - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminBaiDuResourceController")
@RequestMapping("/api/admin/baidu_resource")
public class BaiDuResourceController extends BaseController {

	@Autowired
	private BaiDuResourceService baiDuResourceService;
	@Autowired
	private BaiDuTagService baiDuTagService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(BaiDuResource baiDuResource, Long[] baiDuTagIds) {
		if (!isValid(baiDuResource)) {
			return Message.error("参数错误");
		}
		baiDuResource.setBaiDuTags(new HashSet<>(baiDuTagService.findList(baiDuTagIds)));
		baiDuResourceService.save(baiDuResource);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public BaiDuResource edit(Long id) {
		return baiDuResourceService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(BaiDuResource baiDuResource, Long[] baiDuTagIds) {
		if (!isValid(baiDuResource)) {
			return Message.error("参数错误");
		}
		baiDuResource.setBaiDuTags(new HashSet<>(baiDuTagService.findList(baiDuTagIds)));
		baiDuResourceService.update(baiDuResource,"hits","code");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Page<BaiDuResource> list(Pageable pageable) {
		pageable.setPageSize(200);
		return baiDuResourceService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		baiDuResourceService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@PostMapping("/tags")
	@JsonView(BaseEntity.ListView.class)
	public List<BaiDuTag> category() {
		return baiDuTagService.findAll();
	}

}