package com.jflyfox.modules.admin.recruitingtrip;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "recruiting_trip")
public class TbRecruitingTrip extends BaseProjectModel<TbRecruitingTrip> {

	private static final long serialVersionUID = 1L;
	public static final TbRecruitingTrip dao = new TbRecruitingTrip();

	// columns START
	private String ID = "id"; // 主键
	private String FOLDER_ID = "folder_id"; // 目录id
	private String IDENTIFY="identify";//文字标识
	private String CITY = "city"; // 城市
	private String SCHOOL = "school"; // 宣讲学校
	private String DATE = "date"; // 宣讲日期
	private String PREACH_SITE = "preach_site"; // 宣讲场地
	private String CONTENT = "content"; // 招聘详情
	private String SORT = "sort"; // 排序
	private String STATUS = "status"; // 状态//radio/2,隐藏,1,显示
	private String IS_DELETED = "is_deleted"; // 是否已删除
	private String UPDATE_TIME = "update_time"; // 更新时间
	private String UPDATE_ID = "update_id"; // 更新人
	private String CREATE_TIME = "create_time"; // 创建时间
	private String CREATE_ID = "create_id"; // 创建者

	public TbRecruitingTrip setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}

	public java.lang.Integer getId() {
		return get(ID);
	}

	public TbRecruitingTrip setFolderId(java.lang.Integer value) {
		set(FOLDER_ID, value);
		return this;
	}

	public java.lang.Integer getFolderId() {
		return get(FOLDER_ID);
	}
	public TbRecruitingTrip setIdentify(java.lang.String value) {
		set(IDENTIFY, value);
		return this;
	}

	public java.lang.String getIdentify() {
		return get(IDENTIFY);
	}
	public TbRecruitingTrip setCity(java.lang.String value) {
		set(CITY, value);
		return this;
	}

	public java.lang.String getCity() {
		return get(CITY);
	}

	public TbRecruitingTrip setSchool(java.lang.String value) {
		set(SCHOOL, value);
		return this;
	}

	public java.lang.String getSchool() {
		return get(SCHOOL);
	}

	public TbRecruitingTrip setDate(java.lang.String value) {
		set(DATE, value);
		return this;
	}

	public java.lang.String getDate() {
		return get(DATE);
	}

	public TbRecruitingTrip setPreach_site(java.lang.String value) {
		set(PREACH_SITE, value);
		return this;
	}

	public java.lang.String getPreach_site() {
		return get(PREACH_SITE);
	}
	
	public TbRecruitingTrip setContent(java.lang.String value) {
		set(CONTENT, value);
		return this;
	}

	public java.lang.String getContent() {
		return get(CONTENT);
	}
	

	public TbRecruitingTrip setSort(java.lang.Integer value) {
		set(SORT, value);
		return this;
	}

	public java.lang.Integer getSort() {
		return get(SORT);
	}

	public TbRecruitingTrip setStatus(java.lang.Integer value) {
		set(STATUS, value);
		return this;
	}

	public java.lang.Integer getStatus() {
		return get(STATUS);
	}

	public TbRecruitingTrip setIsDeleted(java.lang.Integer value) {
		set(IS_DELETED, value);
		return this;
	}

	public java.lang.Integer getIsDeleted() {
		return get(IS_DELETED);
	}

	public TbRecruitingTrip setUpdateTime(java.lang.String value) {
		set(UPDATE_TIME, value);
		return this;
	}

	public java.lang.String getUpdateTime() {
		return get(UPDATE_TIME);
	}

	public TbRecruitingTrip setUpdateId(java.lang.Integer value) {
		set(UPDATE_ID, value);
		return this;
	}

	public java.lang.Integer getUpdateId() {
		return get(UPDATE_ID);
	}

	public TbRecruitingTrip setCreateTime(java.lang.String value) {
		set(CREATE_TIME, value);
		return this;
	}

	public java.lang.String getCreateTime() {
		return get(CREATE_TIME);
	}

	public TbRecruitingTrip setCreateId(java.lang.Integer value) {
		set(CREATE_ID, value);
		return this;
	}

	public java.lang.Integer getCreateId() {
		return get(CREATE_ID);
	}

	@Override
	public String toString() {
		String log = "";
		log += "[id:" + getId() + "]";
		log += "[folderId:" + getFolderId() + "]";
		log += "[city:" + getCity() + "]";
		log += "[school:" + getSchool()+ "]";
		log += "[date:" + getDate() + "]";
		log += "[preach_site:" + getPreach_site() + "]";
		log += "[sort:" + getSort() + "]";
		log += "[status:" + getStatus() + "]";
		log += "[isDeleted:" + getIsDeleted() + "]";
		log += "[updateTime:" + getUpdateTime() + "]";
		log += "[updateId:" + getUpdateId() + "]";
		log += "[createTime:" + getCreateTime() + "]";
		log += "[createId:" + getCreateId() + "]";
		return log;
	}
}