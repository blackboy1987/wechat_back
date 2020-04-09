
package com.igomall.plugin.ossStorage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.igomall.entity.PluginConfig;
import com.igomall.plugin.StoragePlugin;

/**
 * Plugin - 阿里云存储
 * 
 * @author blackboy
 * @version 1.0
 */
@Component("ossStoragePlugin")
public class OssStoragePlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "阿里云存储";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "SHOP++";
	}

	@Override
	public String getSiteUrl() {
		return "http://www.igomall.xin";
	}

	@Override
	public String getInstallUrl() {
		return "oss_storage/install";
	}

	@Override
	public String getUninstallUrl() {
		return "oss_storage/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "oss_storage/setting";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String endpoint = pluginConfig.getAttribute("endpoint");
			String accessId = pluginConfig.getAttribute("accessId");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String isTransform = pluginConfig.getAttribute("isTransform");
			String pipelineId = pluginConfig.getAttribute("pipelineId");
			String templateId = pluginConfig.getAttribute("templateId");
			InputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.length());
				String path1 = StringUtils.removeStart(path, "/");
				ossClient.putObject(bucketName,path1 , inputStream, objectMetadata);
				ossClient.shutdown();

				// 上传完成之后调用转码服务
				if(StringUtils.isNotEmpty(isTransform)&&StringUtils.equalsAnyIgnoreCase(isTransform,"1")){
					SimpleTranscode.transform(accessId,accessKey,pipelineId,templateId,bucketName,path1,path);
				}

			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

	public void uploadUrl(String path, String url, String contentType){
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String endpoint = pluginConfig.getAttribute("endpoint");
			String accessId = pluginConfig.getAttribute("accessId");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			InputStream inputStream = null;
			try {
				OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				inputStream = new URL(url).openStream();
				ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream,objectMetadata);
				ossClient.shutdown();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}
}