
package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.FileType;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Material;
import com.igomall.service.MaterialService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Controller - 菜单
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminMaterialController")
@RequestMapping("/admin/api/material")
public class MaterialController extends BaseController {

	@Autowired
	private MaterialService materialService;

	/**
	 * 保存
	 */
	@PostMapping("/update")
	public Message update(Material material) {
		Material pMaterial = materialService.find(material.getId());
		if(pMaterial==null){
			return Message.error("参数错误");
		}
		BeanUtils.copyProperties(material,pMaterial,"createdDate","lastModifiedDate","version","new","id");
		materialService.update(pMaterial);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Material edit(Long id) {
		return materialService.find(id);
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Material.ListView.class)
	public Page<Material> list(Pageable pageable, String title, String memo, Material.Type type, Date beginDate, Date endDate) {
		return materialService.findPage(pageable,title,memo,type,beginDate,endDate);

	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		materialService.delete(ids);
		return Message.success("操作成功");
	}


	private FileType getFileType(Material.Type type){
		switch (type){
			case file:
				return FileType.file;
			case audio:
				return FileType.media;
			case image:
				return FileType.image;
			case video:
				return FileType.media;
				default:
					return FileType.file;
		}
	}
}