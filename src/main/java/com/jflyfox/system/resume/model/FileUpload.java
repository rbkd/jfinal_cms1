package com.jflyfox.system.resume.model;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "sys_file", key = "id")//文件
public class FileUpload  extends BaseProjectModel<FileUpload>{
	private static final long serialVersionUID = 1L;
	public static final FileUpload dao = new FileUpload();
	private String ID = "id"; // 主键
	private String FILE_NAME="file_name";//文件名
	private String FILE_URL="file_url";//文件路径
	public FileUpload setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}
	public java.lang.Integer getId() {
		return get(ID);
	}
	public FileUpload setFile_name(java.lang.String value) {
		set(FILE_NAME, value);
		return this;
	}
	public java.lang.String getFile_name() {
		return get(FILE_NAME);
	}
	public FileUpload setFile_url(java.lang.String value) {
		set(FILE_URL, value);
		return this;
	}
	public java.lang.String getFile_url() {
		return get(FILE_URL);
	}

}
