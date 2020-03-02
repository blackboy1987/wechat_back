
package com.igomall.controller.admin.other;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.other.BaiDuResource;
import com.igomall.entity.other.BaiDuTag;
import com.igomall.service.other.BaiDuResourceService;
import com.igomall.service.other.BaiDuTagService;
import com.igomall.util.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminBaiDuTagController")
@RequestMapping("/api/admin/baidu_tag")
public class BaiDuTagController extends BaseController {

	@Autowired
	private BaiDuTagService baiDuTagService;
	@Autowired
	private BaiDuResourceService baiDuResourceService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(BaiDuTag baiDuTag) {
		baiDuTag.setCode(CodeUtils.getNumberCode(3));
		while (baiDuTagService.codeExist(baiDuTag.getCode())){
			baiDuTag.setCode(CodeUtils.getNumberCode(3));
		}
		if (!isValid(baiDuTag, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		baiDuTag.setBaiDuResources(null);
		baiDuTagService.save(baiDuTag);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public BaiDuTag edit(Long id) {
		return baiDuTagService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(BaiDuTag baiDuTag) {
		BaiDuTag baiDuTag1 = baiDuTagService.findByName(baiDuTag.getName());
		if(baiDuTag1!=null){
			BaiDuTag baiDuTag2 = baiDuTagService.find(baiDuTag.getId());
			for (BaiDuResource resource:baiDuTag2.getBaiDuResources()) {
				resource.getBaiDuTags().add(baiDuTag1);
				baiDuResourceService.update(resource);
			}
			baiDuTagService.delete(baiDuTag.getId());
			return Message.success("操作成功");
		}else{
			if (!isValid(baiDuTag)) {
				return Message.error("参数错误");
			}
			baiDuTagService.update(baiDuTag, "baiDuResources","code");
			return Message.success("操作成功");
		}




	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Page<BaiDuTag> list(Pageable pageable) {
		pageable.setPageSize(100);
		return baiDuTagService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		baiDuTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}