package com.jflyfox.system.resume.model;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

	@ModelBind(table = "sys_applicant",key = "id")//应聘者基本信息 
	public class SysApplicant extends BaseProjectModel<SysApplicant> {
		private static final long serialVersionUID = 1L;
		public static final SysApplicant dao = new SysApplicant();
		private String ID = "id"; // 主键
		private String PROVINCE_SITE="province_site";//所属站点                     	
		private String NAME="name"; // 姓名
		private String SEX = "sex"; // 申请人性别
		private String BIRTHDAY = "birthday"; // 生日
		private String EMAIL = "email"; //邮箱
		private String TELEPHONE = "telephone"; // 电话
		private String STANDBY_PHONE = "standby_phone"; //备用电话
		private String JOB="job";//应聘岗位
		private String AWARDS = "awards"; //获奖情况
		private String ADVANTAGES = "advantages"; // 我的特长及优缺点
		private String OTHER = "other"; //其它
		private String UPDATE_TIME = "update_time"; // 更新时间
		private String CREATE_TIME="create_time"; // 创建时间
		private String ACCOUNT_NUMBER = "account_number"; // 答题账号
		private String PASSWORD = "password"; // 答题密码
		private String ISREAD = "isread"; // 是否已读
		private String ISVERIFY = "isverify"; //是否审核
		private String IS_DELETED = "is_deleted"; // 是否已删除
		private String NUMBER="number";//显示页数 
		private String CONTENT="content";//查看内容
		private String SELECT="select";//选择内容
		
		public SysApplicant setNumber(java.lang.String value) {
			set(NUMBER, value);
			return this;
		}
		public java.lang.String getNumber() {
			return get(NUMBER);
		}
		public SysApplicant setContent(java.lang.String value) {
			set(CONTENT, value);
			return this;
		}
		public java.lang.String getContent() {
			return get(CONTENT);
		}
		public SysApplicant setSelect(java.lang.String value) {
			set(SELECT, value);
			return this;
		}
		public java.lang.String getSelect() {
			return get(SELECT);
		}
		
		
		public SysApplicant setId(java.lang.Integer value) {
			set(ID, value);
			return this;
		}
		public java.lang.Integer getId() {
			return get(ID);
		}
		public SysApplicant setProvince_site(java.lang.String value) {
			set(PROVINCE_SITE, value);
			return this;
		}
		public java.lang.String getProvince_site() {
			return get(PROVINCE_SITE);
		}
		public SysApplicant setName(java.lang.String value) {
			set(NAME, value);
			return this;
		}
		public java.lang.String getName() {
			return get(NAME);
		}
		public SysApplicant setSex(java.lang.String value) {
			set(SEX, value);
			return this;
		}
		public java.lang.String getSex() {
			return get(SEX);
		}
		public SysApplicant setBirthday(java.lang.String value) {
			set(BIRTHDAY, value);
			return this;
		}
		public java.lang.String getBirthday() {
			return get(BIRTHDAY);
		}
		public SysApplicant setEmail(java.lang.String value) {
			set(EMAIL, value);
			return this;
		}
		public java.lang.String getEmail() {
			return get(EMAIL);
		}
		public SysApplicant setTelephone(java.lang.String value) {
			set(TELEPHONE, value);
			return this;
		}
		public java.lang.String getTelephone() {
			return get(TELEPHONE);
		}
		public SysApplicant setStandby_phone(java.lang.String value) {
			set(STANDBY_PHONE, value);
			return this;
		}
		public java.lang.String getStandby_phone(){
			return get(STANDBY_PHONE);
		}
		public SysApplicant setJob(java.lang.String value) {
			set(JOB, value);
			return this;
		}
		public java.lang.String getJob(){
			return get(JOB);
		}
		public SysApplicant setAwards(java.lang.String value) {
			set(AWARDS, value);
			return this;
		}
		public java.lang.String getAwards() {
			return get(AWARDS);
		}
		public SysApplicant setAdvantages(java.lang.String value) {
			set(ADVANTAGES, value);
			return this;
		}
		public java.lang.String getAdvantages() {
			return get(ADVANTAGES);
		}
		public SysApplicant setOther(java.lang.String value) {
			set(OTHER, value);
			return this;
		}
		public java.lang.String getOther() {
			return get(OTHER);
		}
		public SysApplicant setUpdate_time(java.lang.String value) {
			set(UPDATE_TIME, value);
			return this;
		}
		public java.lang.String getUpdate_time() {
			return get(UPDATE_TIME);
		}
		public SysApplicant setCreate_time(java.lang.String value) {
			set(CREATE_TIME, value);
			return this;
		}
		public java.lang.String getCreate_time() {
			return get(CREATE_TIME);
		}
		public SysApplicant setAccount_number(java.lang.String value) {
			set(ACCOUNT_NUMBER, value);
			return this;
		}
		public java.lang.String getAccount_number() {
			return get(ACCOUNT_NUMBER);
		}
		public SysApplicant setPassword(java.lang.String value) {
			set(PASSWORD, value);
			return this;
		}
		public java.lang.String getPassword() {
			return get(PASSWORD);
		}
		public SysApplicant setIsread(java.lang.Integer value) {
			set(ISREAD, value);
			return this;
		}
		public java.lang.Integer getIsread() {
			return get(ISREAD);
		}
		public SysApplicant setIsverify(java.lang.Integer value) {
			set(ISVERIFY, value);
			return this;
		}
		public java.lang.Integer getIsverify() {
			return get(ISVERIFY);
		}
		public SysApplicant setIs_deleted(java.lang.Integer value) {
			set(IS_DELETED, value);
			return this;
		}
		public java.lang.Integer getIs_deleted() {
			return get(IS_DELETED);
		}
	}
