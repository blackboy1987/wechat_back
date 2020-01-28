
package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.FileType;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Material;
import com.igomall.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping("/save")
	public Message save(Material material) {
		materialService.update(material);
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
	public Page<Material> list(Pageable pageable,Material.Type type) {
		return materialService.findPage(pageable);

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