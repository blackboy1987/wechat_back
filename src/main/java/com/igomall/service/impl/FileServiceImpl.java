
package com.igomall.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.igomall.entity.Material;
import com.igomall.plugin.ossStorage.OssStoragePlugin;
import com.igomall.service.MaterialService;
import com.igomall.util.Image1Utils;
import com.igomall.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.igomall.common.FileType;
import com.igomall.common.Setting;
import com.igomall.plugin.StoragePlugin;
import com.igomall.service.FileService;
import com.igomall.service.PluginService;
import com.igomall.util.SystemUtils;

/**
 * Service - 文件
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private PluginService pluginService;

	@Autowired
	private MaterialService materialService;

	/**
	 * 添加文件上传任务
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void addUploadTask(final StoragePlugin storagePlugin, final String path, final File file, final String contentType) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				upload(storagePlugin, path, file, contentType);
			}
		});
	}

	/**
	 * 上传文件
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void upload(StoragePlugin storagePlugin, String path, File file, String contentType) {
		Assert.notNull(storagePlugin,"");
		Assert.hasText(path,"");
		Assert.notNull(file,"");
		Assert.hasText(contentType,"");

		try {
			storagePlugin.upload(path, file, contentType);
		} finally {
			FileUtils.deleteQuietly(file);
		}
	}

	@Override
	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType,"");
		Assert.notNull(multipartFile,"");
		Assert.state(!multipartFile.isEmpty(),"");

		Setting setting = SystemUtils.getSetting();
		if (setting.getUploadMaxSize() != null && setting.getUploadMaxSize() != 0 && multipartFile.getSize() > setting.getUploadMaxSize() * 1024L * 1024L) {
			return false;
		}
		String[] uploadExtensions;
		switch (fileType) {
		case media:
			uploadExtensions = setting.getUploadMediaExtensions();
			break;
		case file:
			uploadExtensions = setting.getUploadFileExtensions();
			break;
		default:
			uploadExtensions = setting.getUploadImageExtensions();
			break;
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			return FilenameUtils.isExtension(StringUtils.lowerCase(multipartFile.getOriginalFilename()), uploadExtensions);
		}
		return false;
	}

	@Override
	public Material upload(FileType fileType, MultipartFile multipartFile, boolean async) {
		Assert.notNull(fileType,"");
		Assert.notNull(multipartFile,"");
		Assert.state(!multipartFile.isEmpty(),"");

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", UUID.randomUUID().toString());
		switch (fileType) {
		case media:
			uploadPath = setting.resolveMediaUploadPath(model);
			break;
			case video:
				uploadPath = setting.resolveMediaUploadPath(model);
				break;
			case audio:
				uploadPath = setting.resolveMediaUploadPath(model);
				break;
		case file:
			uploadPath = setting.resolveFileUploadPath(model);
			break;
		default:
			uploadPath = setting.resolveImageUploadPath(model);
			break;
		}
		try {
			String destPath = uploadPath + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				String contentType = multipartFile.getContentType();
				if (async) {
					addUploadTask(storagePlugin, destPath, tempFile, contentType);
				} else {
					upload(storagePlugin, destPath, tempFile, contentType);
				}
				Material material = new Material();
				material.setSize(multipartFile.getSize());
				material.setContentType(contentType);
				material.setType(Material.Type.valueOf(fileType.name()));
				material.setUrl(storagePlugin.getUrl(destPath));
				material = materialService.save(material);
				return material;
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}


	@Override
	public String upload(FileType fileType, MultipartFile multipartFile, boolean async,String type) {
		Assert.notNull(fileType,"");
		Assert.notNull(multipartFile,"");
		Assert.state(!multipartFile.isEmpty(),"");

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", UUID.randomUUID().toString());
		switch (fileType) {
			case media:
				uploadPath = setting.resolveMediaUploadPath(model);
				break;
			case file:
				uploadPath = setting.resolveFileUploadPath(model);
				break;
			default:
				uploadPath = setting.resolveImageUploadPath(model);
				break;
		}
		try {
			String destPath = uploadPath + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				if(StringUtils.equalsIgnoreCase("avatar",type)){
					File largeTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
					Image1Utils.zoom(tempFile,largeTempFile,100,100);
					FileUtils.deleteQuietly(tempFile);
					String contentType = multipartFile.getContentType();
					if (async) {
						addUploadTask(storagePlugin, destPath, largeTempFile, contentType);
					} else {
						upload(storagePlugin, destPath, largeTempFile, contentType);
					}
					return storagePlugin.getUrl(destPath);
				}else {
					String contentType = multipartFile.getContentType();
					if (async) {
						addUploadTask(storagePlugin, destPath, tempFile, contentType);
					} else {
						upload(storagePlugin, destPath, tempFile, contentType);
					}
					return storagePlugin.getUrl(destPath);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}


	@Override
	public Material upload(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType,"");
		Assert.notNull(multipartFile,"");
		Assert.state(!multipartFile.isEmpty(),"");

		return upload(fileType, multipartFile, true);
	}

	@Override
	public String uploadLocal(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType,"");
		Assert.notNull(multipartFile,"");
		Assert.state(!multipartFile.isEmpty(),"");

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", UUID.randomUUID().toString());
		switch (fileType) {
		case media:
			uploadPath = setting.resolveMediaUploadPath(model);
			break;
		case file:
			uploadPath = setting.resolveFileUploadPath(model);
			break;
		default:
			uploadPath = setting.resolveImageUploadPath(model);
			break;
		}
		try {
			String destPath = uploadPath + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File destFile = new File(servletContext.getRealPath(destPath));
			File destDir = destFile.getParentFile();
			if (destDir != null) {
				destDir.mkdirs();
			}
			multipartFile.transferTo(destFile);
			return destPath;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String upload(FileType fileType, File file,Integer index) {
		Assert.notNull(fileType,"");

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", UUID.randomUUID().toString());
		switch (fileType) {
			case media:
				uploadPath = setting.resolveMediaUploadPath(model);
				break;
			case file:
				uploadPath = setting.resolveFileUploadPath(model);
				break;
			default:
				uploadPath = setting.resolveImageUploadPath(model);
				break;
		}
		try{
			String destPath = uploadPath +(index<10?("00"+index):index<100?("0"+index):index)+"_"+UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getName());
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
				FileUtils.copyFile(file,tempFile);
				upload(storagePlugin, destPath, tempFile, "video/mp4");
				return storagePlugin.getUrl(destPath);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> catchImage(FileType fileType, String[] fileUrls, boolean async) {
		Assert.notNull(fileType,"");
		Assert.notNull(fileUrls,"");

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", UUID.randomUUID().toString());
		switch (fileType) {
			case media:
				uploadPath = setting.resolveMediaUploadPath(model);
				break;
			case file:
				uploadPath = setting.resolveFileUploadPath(model);
				break;
			default:
				uploadPath = setting.resolveImageUploadPath(model);
				break;
		}
		try {
			List<Map<String,Object>> urls = new ArrayList<>();
			String destPath = uploadPath + UUID.randomUUID() + ".png" ;
			OssStoragePlugin ossStoragePlugin = (OssStoragePlugin)pluginService.getStoragePlugin("ossStoragePlugin");
			for (String url:fileUrls) {
				Map<String,Object> data = new HashMap<>();
				ossStoragePlugin.uploadUrl(destPath, url, null);
				data.put("url",ossStoragePlugin.getUrl(destPath));
				data.put("source",url);
				data.put("state","SUCCESS");
				urls.add(data);
			}
			return urls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}