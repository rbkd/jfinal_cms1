package com.jflyfox.component.util;

import java.io.File;
import java.security.SecureRandom;

import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.jflyfox.util.DateUtils;

public class JFlyfoxUpload {

	public static final int UPLOAD_MAX = 10 * 1024 * 1024;
	
	/**
	 * 上传临时目录
	 */
	public static final String UPLOAD_TMP_PATH = "tmp";

	/**
	 * 文章图片目录
	 */
	public static final String ARICLE_PATH = "download" + File.separator + "image_url";
	/**
	 * 文章图片全目录
	 */
	public static final String UPLOAD_ARICLE_PATH = PathKit.getWebRootPath() + File.separator + ARICLE_PATH;
	
	/**
	 * 栏目滚动图片目录
	 */
	/**
	 * 栏目滚动图片全目录
	 */
	
	/**
	 * 图片目录
	 */
	public static final String IMAGE_PATH = "download" + File.separator + "image";
	
	/**
	 * 图片全目录
	 */
	public static final String UPLOAD_IMAGE_PATH = File.separator+"root"+ File.separator + IMAGE_PATH;
	
	/**
	 * 视频目录
	 */
	public static final String VIDEO_PATH = "download" + File.separator + "video";
	
	/**
	 * 视频全目录
	 */
	public static final String UPLOAD_VIDEO_PATH = PathKit.getWebRootPath() + File.separator + VIDEO_PATH;
	
	/**
	 * 重命名
	 * 
	 * 2015年9月25日 下午10:37:55 flyfox 330627517@qq.com
	 * 
	 * @param uploadFile
	 * @return
	 */
	public static String renameFile(String path, UploadFile uploadFile) {
		File uploadPath = new File(path);
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		String suf = "";
		if (uploadFile.getFileName().lastIndexOf(".") >= 0) {
			suf = uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf("."));
		}
		String fileName = DateUtils.getNow("yyyyMMdd_HHmmss") + "_" + new SecureRandom().nextInt(999999) + suf;
		// 改名,避免重复以及中文问题
		uploadFile.getFile().renameTo(new File(path + File.separator + fileName));
		return fileName;
	}
}