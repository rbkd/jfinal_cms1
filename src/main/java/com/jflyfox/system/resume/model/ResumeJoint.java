package com.jflyfox.system.resume.model;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "resume_joint",key = "id")//简历表 链接
public class ResumeJoint extends BaseProjectModel<ResumeJoint>{
	private static final long serialVersionUID = 1L;
	public static final ResumeJoint dao = new ResumeJoint();
	
	private String ID = "id"; // 主键
	private String APPLICANT_ID="applicant_id";//基础信息表id                  	
	private String EXPERIENCE_ID="experience_id"; // 经历id
	private String FILE_ID="file_id"; // 文件id
	private String IS_DELETED = "is_deleted"; // 是否已删除
	
	public ResumeJoint setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}
	public java.lang.Integer getId() {
		return get(ID);
	}
	public ResumeJoint setApplicant_id(java.lang.String value) {
		set(APPLICANT_ID, value);
		return this;
	}
	public java.lang.String getApplicant_id() {
		return get(APPLICANT_ID);
	}
	public ResumeJoint setFile_id(java.lang.String value) {
		set(FILE_ID, value);
		return this;
	}
	public java.lang.String getFile_id() {
		return get(FILE_ID);
	}
	public ResumeJoint setExperience_id(java.lang.String value) {
		set(EXPERIENCE_ID, value);
		return this;
	}
	public java.lang.String getExperience_id() {
		return get(EXPERIENCE_ID);
	}
	public ResumeJoint setIs_deleted(java.lang.Integer value) {
		set(IS_DELETED, value);
		return this;
	}
	public java.lang.Integer getIs_deleted() {
		return get(IS_DELETED);
	}
}
