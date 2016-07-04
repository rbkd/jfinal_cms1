package com.jflyfox.system.resume.model;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = " personal_experience", key = "id")//个人经历
public class PersonalExperience  extends BaseProjectModel<PersonalExperience>{

	private static final long serialVersionUID = 1L;
	public static final PersonalExperience dao = new PersonalExperience();
	private String ID = "id"; // 主键
	private String NAME="name";//经历名
	private String TIME="time";//经历时间
	private String DESCRIPTION="description";//经历描述
	private String ACHIEVEMENT="achievement";//经历成就
	private String IS_DELETED = "is_deleted"; // 是否已删除
	
	public PersonalExperience setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}
	public java.lang.Integer getId() {
		return get(ID);
	}
	public PersonalExperience setName(java.lang.String value) {
		set(NAME, value);
		return this;
	}
	public java.lang.String getName(){
		return get(NAME);
	}
	public PersonalExperience setTime(java.lang.String value) {
		set(TIME, value);
		return this;
	}
	public java.lang.String getTime(){
		return get(TIME);
	}
	public PersonalExperience setDescription(java.lang.String value) {
		set(DESCRIPTION, value);
		return this;
	}
	public java.lang.String getDescription(){
		return get(DESCRIPTION);
	}
	public PersonalExperience setAchievement(java.lang.String value) {
		set(ACHIEVEMENT, value);
		return this;
	}
	public java.lang.String getAchievement(){
		return get(ACHIEVEMENT);
	}
	public PersonalExperience setIs_deleted(java.lang.Integer value) {
		set(IS_DELETED, value);
		return this;
	}
	public java.lang.Integer getIs_deleted() {
		return get(IS_DELETED);
	}
}
