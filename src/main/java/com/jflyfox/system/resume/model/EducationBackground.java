package com.jflyfox.system.resume.model;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

	@ModelBind(table = "education_background", key = "id")//教育背景
	public class EducationBackground extends BaseProjectModel<EducationBackground> {

		private static final long serialVersionUID = 1L;
		public static final EducationBackground dao = new EducationBackground();
		private String ID = "id"; // 主键
		private String SCHOOL_NAME = "school_name"; // 学校名称
		private String PROFESSION = "profession"; // 专业名称
		private String ADMISSION_TIME = "admission_time"; //入学时间
		private String TYPES = "types"; //类型
		private String GRADUATION_TIME = "graduation_time"; //毕业时间
		private String DEGREE = "degree"; //学历
		private String APPLICANT_ID = "applicant_id"; //学历
		private String IS_DELETED = "is_deleted"; // 是否已删除
		
		public EducationBackground setId(java.lang.Integer value) {
			set(ID, value);
			return this;
		}
		public java.lang.Integer getId() {
			return get(ID);
		}
		public EducationBackground setSchool_name(java.lang.String value) {
			set(SCHOOL_NAME, value);
			return this;
		}
		public java.lang.String getSchool_name(){
			return get(SCHOOL_NAME);
		}
		public EducationBackground setProfession(java.lang.String value) {
			set(PROFESSION, value);
			return this;
		}
		public java.lang.String getProfession() {
			return get(PROFESSION);
		}
		public EducationBackground setTypes(java.lang.Integer value) {
			set(TYPES, value);
			return this;
		}
		public java.lang.Integer getTypes() {
			return get(TYPES);
		}
		public EducationBackground setAdmission_time(java.lang.String value) {
			set(ADMISSION_TIME, value);
			return this;
		}
		public java.lang.String getAdmission_time() {
			return get(ADMISSION_TIME);
		}
		public EducationBackground setGraduation_time(java.lang.String value) {
			set(GRADUATION_TIME, value);
			return this;
		}
		public java.lang.String getGraduation_time() {
			return get(GRADUATION_TIME);
		}
		public EducationBackground setApplicant_id(java.lang.Integer value) {
			set(APPLICANT_ID, value);
			return this;
		}
		public java.lang.Integer getApplicant_id() {
			return get(APPLICANT_ID);
		}
		public EducationBackground setDegree(java.lang.String value) {
			set(DEGREE, value);
			return this;
		}
		public java.lang.String getDegree() {
			return get(DEGREE);
		}
		public EducationBackground setIs_deleted(java.lang.Integer value) {
			set(IS_DELETED, value);
			return this;
		}
		public java.lang.Integer getIs_deleted() {
			return get(IS_DELETED);
		}
}
