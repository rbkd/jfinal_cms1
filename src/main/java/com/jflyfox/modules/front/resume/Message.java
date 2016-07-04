package com.jflyfox.modules.front.resume;

public class Message {
	private String id1="";//教育背景类型1  id
	private String applicant_id="";
	private String id2="";//教育背景类型2  id
	private Boolean status=false;//状态
	private String resume_joint_id="";//简历表id
	private String experience_id="";//经历id
	private String message="";//消息
	private String file_id="";//文件id
	private String  count="0";//上传文件数
	private String file_name="";//上传文件名
	private String job="";//工作岗位
	private String content="";//内容
	public String getId1() {
		return id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}
	public String getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(String applicant_id) {
		this.applicant_id = applicant_id;
	}
	public String getId2() {
		return id2;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getResume_joint_id() {
		return resume_joint_id;
	}
	public void setResume_joint_id(String resume_joint_id) {
		this.resume_joint_id = resume_joint_id;
	}
	public String getExperience_id() {
		return experience_id;
	}
	public void setExperience_id(String experience_id) {
		this.experience_id = experience_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
