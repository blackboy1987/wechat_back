
package com.igomall.controller.admin;

import com.igomall.common.FileType;
import com.igomall.common.Message;
import com.igomall.entity.Material;
import com.igomall.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 文件
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminFileController")
@RequestMapping("/admin/api/file")
public class FileController extends BaseController {

	@Autowired
	private FileService fileService;

	/**
	 * 上传
	 */
	@PostMapping("/upload")
	public @ResponseBody Map<String, Object> upload(FileType fileType, MultipartFile file,String[] fileUrls,Boolean catchImage) {
		Map<String, Object> data = new HashMap<>();
		if(catchImage==null || !catchImage){
			if (fileType == null || file == null || file.isEmpty()) {
				data.put("message", ERROR_MESSAGE);
				data.put("state", ERROR_MESSAGE);
				return data;
			}
			if (!fileService.isValid(fileType, file)) {
				data.put("message", Message.warn("admin.upload.invalid"));
				data.put("state", message("admin.upload.invalid"));
				return data;
			}
			Material material = fileService.upload(fileType, file, false);

			if (material!=null&&StringUtils.isEmpty(material.getUrl())) {
				data.put("message", Message.warn("admin.upload.error"));
				data.put("state", message("admin.upload.error"));
				return data;
			}
			data.put("message", SUCCESS_MESSAGE);
			data.put("state", "SUCCESS");
			data.put("url", material.getUrl());
			data.put("material",material);
		}else{
			if (fileType == null || fileUrls == null || fileUrls.length==0) {
				data.put("message", ERROR_MESSAGE);
				data.put("state", ERROR_MESSAGE);
				return data;
			}
			List<Map<String,Object>> urls = fileService.catchImage(fileType, fileUrls, false);
			if (urls.isEmpty()) {
				data.put("message", Message.warn("admin.upload.error"));
				data.put("state", message("admin.upload.error"));
				return data;
			}
			data.put("message", SUCCESS_MESSAGE);
			data.put("state", "SUCCESS");
			data.put("list", urls);
		}

		return data;
	}
}