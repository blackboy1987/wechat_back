
package com.igomall.service;

import com.igomall.entity.Material;
import org.springframework.web.multipart.MultipartFile;

import com.igomall.common.FileType;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Service - 文件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface FileService {

	/**
	 * 文件验证
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 文件验证是否通过
	 */
	boolean isValid(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @param async
	 *            是否异步
	 * @return 访问URL
	 */
	Material upload(FileType fileType, MultipartFile multipartFile, boolean async);
	Material upload(FileType fileType, MultipartFile multipartFile, boolean async,Integer width,Integer height);

	/**
	 * 文件上传
	 *
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @param async
	 *            是否异步
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile, boolean async,String type);

	/**
	 * 文件上传(异步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 访问URL
	 */
	Material upload(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传至本地(同步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 路径
	 */
	String uploadLocal(FileType fileType, MultipartFile multipartFile);


	/**
	 * 文件上传(异步)
	 *
	 * @param fileType
	 *            文件类型
	 * @param file
	 *            上传文件
	 * @return 访问URL
	 */
	String upload(FileType fileType, File file,Integer index);

	List<Map<String,Object>> catchImage(FileType fileType, String[] fileUrls, boolean async);
}