package com.jflyfox.modules.admin.accountpasswd;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "sys_account_passwd")
public class TbAccountPasswd extends BaseProjectModel<TbAccountPasswd> {

	private static final long serialVersionUID = 1L;
	public static final TbAccountPasswd dao = new TbAccountPasswd();

	// columns START
	private String ID = "id"; // id
	private String ACCOUND="account"; //账号
	private String PASSWD="passwd"; //账号
	private String APPLICANT_ID="applicant_id";//简历id
	private String UPDATE_TIME = "update_time"; // 更新时间
	private String CREATE_TIME = "create_time"; // 创建时间

	public TbAccountPasswd setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}
	public java.lang.Integer getId() {
		return get(ID);
	}
	public TbAccountPasswd setAccount(java.lang.String value) {
		set(ACCOUND, value);
		return this;
	}
	public java.lang.String getAccount() {
		return get(ACCOUND);
	}
	public TbAccountPasswd setPasswd(java.lang.String value) {
		set(PASSWD, value);
		return this;
	}
	public java.lang.String getPasswd() {
		return get(PASSWD);
	}
	public TbAccountPasswd setApplicant_id(java.lang.Integer value) {
		set(APPLICANT_ID, value);
		return this;
	}
	public java.lang.Integer getApplicant_id() {
		return get(APPLICANT_ID);
	}
	public TbAccountPasswd setUpdateTime(java.lang.String value) {
		set(UPDATE_TIME, value);
		return this;
	}

	public java.lang.String getUpdateTime() {
		return get(UPDATE_TIME);
	}

	public TbAccountPasswd setCreateTime(java.lang.String value) {
		set(CREATE_TIME, value);
		return this;
	}
	public java.lang.String getCreateTime() {
		return get(CREATE_TIME);
	}
}