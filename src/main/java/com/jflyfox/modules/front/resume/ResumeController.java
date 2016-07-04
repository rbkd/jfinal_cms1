package com.jflyfox.modules.front.resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.beelt.BeeltFunctions;
import com.jflyfox.component.util.ImageCode;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.component.util.JFlyfoxUpload;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.folderrollpicture.TbFolderRollPicture;
import com.jflyfox.system.resume.model.EducationBackground;
import com.jflyfox.system.resume.model.FileUpload;
import com.jflyfox.system.resume.model.PersonalExperience;
import com.jflyfox.system.resume.model.ResumeJoint;
import com.jflyfox.system.resume.model.SysApplicant;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.util.StrUtils;

@ControllerBind(controllerKey = "/front/resume")
public class ResumeController extends BaseProjectController {
	public static final String path = "/resume/";
	public void index() {
		SysUser user = (SysUser) getSessionUser();
		String email="0";
		if(user!=null){
			email=user.get("username");
			 SysApplicant applicant= SysApplicant.dao.findFirstByWhere("where email=? and is_deleted =1",email);
		if (applicant!=null) {
			int applicant_id=applicant.getId();
			if(applicant.getIsverify()==1){
				getDetail(applicant_id+"");
				return;
			}else{
				setAttr("applicant", applicant);
				setAttr("applicant_id", applicant_id);
			List<SysApplicant> applicantandeducationList = SysApplicant.dao
					.find("select t.*,e.* from sys_applicant t,education_background e where t.id=e.applicant_id and t.is_deleted =1 and e.is_deleted =1 and t.id="
							+ applicant_id);// 基础信息，教育背景
			for (SysApplicant educationtype2 : applicantandeducationList) {
				if (educationtype2.get("types").toString().equals("2")) {
					setAttr("educationtype2", educationtype2);
					applicantandeducationList.remove(educationtype2);
					break;
				}
			}
			for (SysApplicant educationtype1 : applicantandeducationList) {
				if (educationtype1.get("types").toString().equals("1")) {
					setAttr("educationtype1", educationtype1);
					applicantandeducationList.remove(educationtype1);
					break;
				}
			}
			if (applicantandeducationList.size() > 0) {
				setAttr("educationList", applicantandeducationList);
			}
			ResumeJoint resumeJoint = ResumeJoint.dao.findFirstByWhere("where applicant_id=? and is_deleted =1",
					applicant_id);
			if (resumeJoint != null) {
				String experience_ids = resumeJoint.getExperience_id();// 获取经历id
				String[] strArr;
				strArr = experience_ids.split(",");
				List<PersonalExperience> experienceList = new ArrayList<PersonalExperience>();
				PersonalExperience personalExperience;
				for (String project_id : strArr) {
					personalExperience = PersonalExperience.dao.findFirstByWhere(" where id = ? and is_deleted =1",
							project_id);
					if (personalExperience == null) {
					} else {
						experienceList.add(personalExperience);
					}
				}
				if (experienceList.size() != 0) {
					setAttr("experience1", experienceList.get(0));
					experienceList.remove(0);
					if (experienceList.size() != 0) {
						setAttr("experienceList", experienceList);
					}
				}
				String file_ids = resumeJoint.getFile_id();
				strArr = file_ids.split(",");
				setAttr("count", strArr.length);
				if (strArr.length>=1 && strArr[0] != null) {
					FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[0]);
					if (fileUpload != null) {
						setAttr("file_id1", Integer.parseInt(strArr[0]));
						setAttr("title1", fileUpload.getFile_name());
					}
				}
				if (strArr.length>=2 && strArr[1] != null) {
					FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[1]);
					if (fileUpload != null) {
						setAttr("file_id2", Integer.parseInt(strArr[1]));
						setAttr("title2", fileUpload.getFile_name());
					}
				}
				if (strArr.length>=3 && strArr[2] != null) {
					FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[2]);
					if (fileUpload != null) {
						setAttr("file_id3", Integer.parseInt(strArr[2]));
						setAttr("title3", fileUpload.getFile_name());
					}
				}
			}
			}
	}
	}
		renderAuto(path + "resume_fill.html");
	}

	public void checkemail() {
		String email = getPara("email");
		List<SysApplicant> applicantList = SysApplicant.dao.find("select * from sys_applicant where email=?", email);
		Message message = new Message();
		if (applicantList.size() == 0) {
			message.setStatus(true);
		} else {
			message.setStatus(false);
		}
		renderJson(message);
	}
    public void createNewUser(String email,String telephone,String realname){//添加新用户
    	SysUser newUser = SysUser.dao.findFirstByWhere("where username = ? ", email);
    	if(newUser==null){
    		Record user = new Record().set("username", email).set("password", JFlyFoxUtils.passwordEncrypt(telephone)).set("usertype", "3").set("departid", JFlyFoxUtils.DEPART_REGIST_ID)
    				.set("state", "2").set("realname", realname).set("create_time", getNow()).set("create_id", "1");
    		Db.save("sys_user", user);
    	}
    	
    }
	public void baseMessage() {// 基本信息处理函数
		SysApplicant model = getModel(SysApplicant.class);
		List<SysApplicant> applicantList = SysApplicant.dao.find("select * from sys_applicant where id=?",
				model.getId());
		if (applicantList.size() == 0) {// 表明数据库木有此应聘者的基本信息，则插入
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月d日");// 设置日期格式
			String update_time = df.format(new Date());// 更新时间
			String create_time = df.format(new Date());// 创建时间
			Record applicant = new Record().set("province_site", model.getProvince_site()).set("name", model.getName())
					.set("sex", model.getSex()).set("birthday", model.getBirthday()).set("email", model.getEmail())
					.set("telephone", model.getTelephone()).set("standby_phone", model.getStandby_phone())
					.set("job", model.getJob()).set("update_time", update_time).set("create_time", create_time)
					.set("account_number", "333333").set("password", "3434343").set("isread", "0").set("isverify", "0")
					.set("is_deleted", "1");
			Db.save("sys_applicant", applicant);
			String applicant_id = applicant.get("id").toString();// 得到该应聘者基本表的id
			Record resumeJoint = new Record().set("applicant_id", applicant_id);// 把applicant_id写入表resume_joint中
			Db.save("resume_joint", resumeJoint);
			Message message = new Message();
			message.setApplicant_id(applicant_id);
			message.setStatus(true);
			createNewUser( model.getEmail(),model.getTelephone(),model.getName());
			renderJson(message);
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月d日");// 设置日期格式
			String update_time = df.format(new Date());// 更新时间
			Db.update(// 更新基本信息
					"update sys_applicant set province_site=?,job=?,name=?,sex=?,birthday=?,email=?,telephone=?,standby_phone=?,update_time=? where id=?",
					model.getProvince_site(), model.getJob(), model.getName(), model.getSex(), model.getBirthday(),
					model.getEmail(), model.getTelephone(), model.getStandby_phone(), update_time, model.getId());
			Message message = new Message();
			message.setApplicant_id(model.getId() + "");
			message.setStatus(true);
			createNewUser(model.getEmail(),model.getTelephone(),model.getName());
			renderJson(message);
		}
	}

	public Message baseempty() {// 制作一张空的基本信息表
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月d日");// 设置日期格式
		String update_time = df.format(new Date());// 更新时间
		String create_time = df.format(new Date());// 创建时间
		Record applicant = new Record().set("province_site", "0").set("name", "0").set("sex", "0").set("birthday", "0")
				.set("email", "0").set("telephone", "0").set("standby_phone", "0").set("job", "0")
				.set("update_time", update_time).set("create_time", create_time).set("account_number", "333333")
				.set("password", "3434343").set("isread", "0").set("isverify", "0").set("is_deleted", "1");
		Db.save("sys_applicant", applicant);
		Record resumeJoint = new Record().set("applicant_id", applicant.get("id").toString());// 把applicant_id写入表resume_joint中
		Db.save("resume_joint", resumeJoint);
		Message message = new Message();
		message.setApplicant_id(applicant.get("id").toString());// 返回信息
		message.setMessage("您当前的信息已保存成功，但基本信息未填写或者未保存，请填好保存");
		message.setStatus(false);
		return message;
	}

	public Message insertorupdateeducation(Message message, EducationBackground model, String id2, String school_name,
			String profession, String types, String degree, String admission_time, String graduation_time) {
		if (model.getId() == 0) {// 插入教育背景1
			Record education1 = new Record().set("school_name", model.getSchool_name())
					.set("profession", model.getProfession()).set("types", model.getTypes())
					.set("admission_time", model.getAdmission_time()).set("graduation_time", model.getGraduation_time())
					.set("degree", model.getDegree()).set("applicant_id", message.getApplicant_id())
					.set("is_deleted", "1");
			Db.save("education_background", education1);
			message.setId1(education1.get("id").toString());
		} else {
			// 更新 教育背景1
			Db.update(
					"update education_background set school_name=?,profession=?,admission_time=?,graduation_time=?,degree=? where id=? and applicant_id=?",
					model.getSchool_name(), model.getProfession(), model.getAdmission_time(),
					model.getGraduation_time(), model.getDegree(), model.getId(), message.getApplicant_id());
			message.setId1(model.getId() + "");
		}
		if (id2.equals("0")) {// 插入教育背景2
			Record education2 = new Record().set("school_name", school_name).set("profession", profession)
					.set("types", types).set("admission_time", admission_time).set("graduation_time", graduation_time)
					.set("degree", degree).set("applicant_id", message.getApplicant_id()).set("is_deleted", "1");
			Db.save("education_background", education2);
			message.setId2(education2.get("id").toString());
		} else {
			// 更新//插入教育背景2
			Db.update(
					"update education_background set school_name=?,profession=?,admission_time=?,graduation_time=?,degree=? where id=? and applicant_id=?",
					school_name, profession, admission_time, graduation_time, degree, id2, message.getApplicant_id());
			message.setId2(id2);
		}
		return message;
	}

	public void educationdouble() {// 教育背景一二类型处理函数
		EducationBackground model = getModel(EducationBackground.class);
		Message message = null;
		if (model.getApplicant_id() == 0) {// 如果基本表信息为空先插入空的基本表，提示用户基本表信息未填写
			message = baseempty();// 获得message包含基本表的Applicant_id
		}
		if (message == null) {// 说明基本表有填写
			message = new Message();
			message.setApplicant_id(model.getApplicant_id() + "");
			message = insertorupdateeducation(message, model, getPara("id"), getPara("school_name"),
					getPara("profession"), getPara("types"), getPara("degree"), getPara("admission_time"),
					getPara("graduation_time"));
			message.setStatus(true);
		} else {// 说明基本表木有填写
			message = insertorupdateeducation(message, model, getPara("id"), getPara("school_name"),
					getPara("profession"), getPara("types"), getPara("degree"), getPara("admission_time"),
					getPara("graduation_time"));
		}
		renderJson(message);
	}

	public void education() {// 教育背景动态添加处理函数
		EducationBackground model = getModel(EducationBackground.class);
		if (model.getApplicant_id() == 0) {
			Message message = new Message();
			message.setStatus(false);
			renderJson(message);
		} else {
			Message message = new Message();
			message.setApplicant_id(model.getApplicant_id() + "");
			if (model.getId() == 0) {
				Record education1 = new Record().set("school_name", model.getSchool_name())
						.set("profession", model.getProfession()).set("types", "1")
						.set("admission_time", model.getAdmission_time())
						.set("graduation_time", model.getGraduation_time()).set("degree", model.getDegree())
						.set("applicant_id", model.getApplicant_id()).set("is_deleted", "1");
				Db.save("education_background", education1);
				List<EducationBackground> educations1 = EducationBackground.dao.find(
						"select * from education_background where school_name=? and profession=? and types=? and admission_time=? and graduation_time=? and degree=? and applicant_id=?",
						model.getSchool_name(), model.getProfession(), "1", model.getAdmission_time(),
						model.getGraduation_time(), model.getDegree(), model.getApplicant_id());
				if (educations1.size() == 1) {
					EducationBackground educationBackground = educations1.get(0);
					message.setId1(educationBackground.getId() + "");
				}
			} else {
				// 更新 1
				Db.update(
						"update education_background set getjobmessageschool_name=?,profession=?,admission_time=?,graduation_time=?,degree=? where id=?",
						model.getSchool_name(), model.getProfession(), model.getAdmission_time(),
						model.getGraduation_time(), model.getDegree(), model.getId());
				message.setId1(model.getId() + "");
			}
			message.setStatus(true);
			renderJson(message);
		}
	}

	public void educationdelete() {
		EducationBackground model = getModel(EducationBackground.class);
		if (model.getId().equals("0")) {
			renderJson("失败");
		} else {
			Db.update("update education_background set is_deleted=? where id=?", "0", model.getId());
			renderJson("成功");
		}
	}

	public Message insertorupdateExperence(Message message, PersonalExperience model) {
		List<PersonalExperience> personalList = PersonalExperience.dao
				.find("select * from personal_experience where id=?", model.getId());// 根据id查询数据库中个人经历是否存在
		if (personalList.size() == 0 && Integer.parseInt(message.getApplicant_id()) != 0) {// 插入个人经历
			Record personal = new Record().set("name", model.getName()).set("time", model.getTime())
					.set("description", model.getDescription()).set("achievement", model.getAchievement())
					.set("is_deleted", "1");
			Db.save("personal_experience", personal);
			String personal_id = personal.get("id").toString();// 得到刚插入数据库的经历id
			List<ResumeJoint> joints = ResumeJoint.dao.find("select * from resume_joint where applicant_id=?",
					message.getApplicant_id());// 根据applicant_id查询简历表
			if (joints.size() != 0) {
				ResumeJoint joint = joints.get(0);
				String experience_id = joint.getExperience_id();
				if (experience_id.equals("0")) {
					experience_id = personal_id + ",";// 添加Experience_id 多个,号隔开
				} else {
					experience_id += personal_id + ",";
				}
				Db.update("update resume_joint set experience_id=? where applicant_id=?", experience_id,
						message.getApplicant_id());
			}
			message.setExperience_id(personal_id);
		} else {
			if (personalList.size() != 0) {
				Db.update("update personal_experience set name=?,time=?,description=?,achievement=? where id=?",
						model.getName(), model.getTime(), model.getAchievement(), model.getDescription(),
						model.getId());
				message.setExperience_id(model.getId() + "");
			}
		}
		return message;
	}

	public void personalExperence() {// 个人经历处理函数
		PersonalExperience model = getModel(PersonalExperience.class);
		Message message = null;
		if (Integer.parseInt(getPara("applicant_id")) == 0) {// 如果基本表信息为空先插入空的基本表，提示用户基本表信息未填写
			message = baseempty();// 获得message包含基本表的Applicant_id
		}
		if (message == null) {// 说明基本表有填写
			message = new Message();
			message.setApplicant_id(getPara("applicant_id"));
			message = insertorupdateExperence(message, model);
			message.setStatus(true);
		} else {// 说明基本表木有填写
			message = insertorupdateExperence(message, model);
		}
		renderJson(message);
	}

	public void experiencedelete() {// 删除个人经历
		PersonalExperience model = getModel(PersonalExperience.class);
		if (model.getId().equals("0")) {
			renderJson("失败");
		} else {
			Db.update("update personal_experience set is_deleted=? where id=?", "0", model.getId());
			renderJson("成功");
		}
	}

	public void recommendation() {// 自我推荐处理函数
		SysApplicant model = getModel(SysApplicant.class);
		List<SysApplicant> applicantList = SysApplicant.dao.find("select * from sys_applicant where id=?",
				model.getId());
		if (applicantList.size() == 0) {
			Message message = new Message();
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月d日");// 设置日期格式
			String update_time = df.format(new Date());// 更新时间
			String create_time = df.format(new Date());// 创建时间
			Record applicant = new Record().set("province_site", "0").set("name", "0").set("sex", "0")
					.set("birthday", "0").set("email", "0").set("telephone", "0").set("standby_phone", "0")
					.set("job", "0").set("update_time", update_time).set("create_time", create_time)
					.set("awards", model.getAwards()).set("other", model.getOther())
					.set("advantages", model.getAdvantages()).set("account_number", "333333").set("password", "3434343")
					.set("isread", "0").set("isverify", "0").set("is_deleted", "1");
			Db.save("sys_applicant", applicant);
			Record resumeJoint = new Record().set("applicant_id", applicant.get("id").toString());// 把applicant_id写入表resume_joint中
			Db.save("resume_joint", resumeJoint);
			message.setApplicant_id(applicant.get("id").toString());// 返回信息
			message.setMessage("您当前的信息已保存成功，但简历信息未填写完整，请填写保存!!!");
			message.setStatus(false);
			renderJson(message);
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月d日");// 设置日期格式
			String update_time = df.format(new Date());// 更新时间
			Db.update("update sys_applicant set awards=?,advantages=?,other=?,update_time=? where id=?",
					model.getAwards(), model.getAdvantages(), model.getOther(), update_time, model.getId());
			Message message = new Message();
			String flag = complete(model.getId());
			message.setMessage(flag);
			message.setStatus(true);
			renderJson(message);
		}
	}

	public String complete(int id) {// 后台验证信息是否填写完整
		SysApplicant applicant = SysApplicant.dao.findFirstByWhere("where id=? and is_deleted=1", id);
		if (applicant.getProvince_site().equals("0") && applicant.getEmail().equals("0")
				&& applicant.getTelephone().equals("0")) {
			return "基本信息没填";
		} else {
			List<SysApplicant> applicantList = SysApplicant.dao
					.find("select t.*,e.* from sys_applicant t,education_background e where t.id=e.applicant_id and t.is_deleted =1 and e.is_deleted =1 and t.id="
							+ id);// 基础信息，教育背景
			if (applicantList.size() == 0) {
				return "教育背景没填";
			} else {
				ResumeJoint resumeJoint = ResumeJoint.dao.findFirstByWhere("where applicant_id=? and is_deleted =1",
						id);
				if (resumeJoint != null) {
					String experience_id = resumeJoint.getExperience_id();
					if (experience_id.equals("0")) {
						return "个人经历没填";
					} else {
						return "已填写完整";
					}
				}
			}
		}
		return "已填写完整";
	}

	public void showIframe() {// 显示测评账号
		int id = getParaToInt();// 得到查询id${ctx}/publishMessage/groupsterminals
		List<SysApplicant> applicantList = SysApplicant.dao.find("select * from sys_applicant where id=?", id);
		SysApplicant applicant = applicantList.get(0);
		setAttr("applicant", applicant);
		renderAuto(path + "resume_success.html");
	}

	public Message uploadFile(Message message, UploadFile uploadImage) {// 上传文件处理函数
		String fileName = "";
		String originFileName = "";
		PropKit.use(new File(PathKit.getRootClassPath()+"/conf/config.properties"));
		if (uploadImage != null) {
			originFileName = uploadImage.getFileName();
			message.setFile_name(originFileName);
			fileName = JFlyfoxUpload.renameFile(PropKit.get("fileUrl"), uploadImage);
		}
		if (!(fileName.equals(""))) {// 文件名不为空,说明有文件
			Record fileupload = new Record().set("file_name", originFileName).set("file_url",
					PropKit.get("fileUrlPrefix")+ fileName);
			Db.save("sys_file", fileupload);
			message.setFile_id(fileupload.get("id").toString());
			List<ResumeJoint> joints = ResumeJoint.dao.find("select * from resume_joint where applicant_id=?", // 根据applicant_id查询简历表
																												// 以便添加file_id
					message.getApplicant_id());
			if (joints.size() != 0) {
				ResumeJoint joint = joints.get(0);
				String file_id = joint.getFile_id();
				if (file_id.equals("0")) {
					file_id = fileupload.get("id").toString() + ",";// 添加fileUpload_id
																	// 多个,号隔开
				} else {
					file_id += fileupload.get("id").toString() + ",";
				}
				Db.update("update resume_joint set file_id=? where applicant_id=?", file_id, message.getApplicant_id());
			}
		}
		return message;
	}

	public void upload() {// 文件上传处理
		Message message = null;
		UploadFile uploadImage = getFile("model.file_url", JFlyfoxUpload.UPLOAD_TMP_PATH, JFlyfoxUpload.UPLOAD_MAX);
		if (uploadImage != null) {
			String applicant_id = getPara("applicant_id");// 得到查询id
			int count = Integer.parseInt(getPara("count"));// 得到文件数
			count++;
			if (applicant_id.equals("0")) {
				message = baseempty();// 获得message包含基本表的Applicant_id
			}
			if (message == null) {// 说明基本表有填写
				message = new Message();
				message.setApplicant_id(applicant_id);
				message = uploadFile(message, uploadImage);
				message.setStatus(true);
				renderJson(message);
			} else {// 说明基本表木有填写
				message = uploadFile(message, uploadImage);
			}
			message.setCount(count + "");
			renderJson(message);
		}
	}

	public void cancelfile() {// 删除文件处理函数
		String id = getPara("id");// 得到查询id
		String applicant_id = getPara("applicant_id");// 得到查询id
		Db.update("update sys_file set is_deleted=0 where id=?", id);
		ResumeJoint resumeJoint = ResumeJoint.dao.findFirstByWhere("where applicant_id=?", applicant_id);
		String file_ids = resumeJoint.getFile_id();
		String[] strArr = file_ids.split(",");
		String file_id = "";
		for (String string : strArr) {
			if (!(string.equals(id))) {
				file_id += string + ",";
			}
		}
		Db.update("update resume_joint set file_id=? where applicant_id=?", file_id, applicant_id);
		Message message = new Message();
		message.setStatus(true);
		renderJson(message);
	}
     public void getDetail(String id){
    	 ResumeJoint resumeJoint = ResumeJoint.dao.findFirstByWhere("where applicant_id=?", id);
			String applicant_id = resumeJoint.getApplicant_id();// 获取基础信息表id
			String experience_ids = resumeJoint.getExperience_id();// 获取经历id
			List<SysApplicant> applicantList = SysApplicant.dao
					.find("select t.* from sys_applicant t where  t.is_deleted =1 and t.id=" + applicant_id);// 基础信息
			setAttr("applicantList", applicantList);
			List<SysApplicant> applicantandeducationList = SysApplicant.dao
					.find("select t.*,e.* from sys_applicant t,education_background e where t.id=e.applicant_id and t.is_deleted =1 and e.is_deleted =1 and t.id="
							+ applicant_id);// 基础信息，教育背景
			setAttr("applicantandeducationList", applicantandeducationList);
			PersonalExperience personalExperience;
			String[] strArr;
			strArr = experience_ids.split(",");
			List<PersonalExperience> experienceList = new ArrayList<PersonalExperience>();
			for (String project_id : strArr) {
				personalExperience = PersonalExperience.dao.findFirstByWhere(" where id = ? and is_deleted =1",
						project_id);
				if (personalExperience == null) {
				} else {
					experienceList.add(personalExperience);
				}
			}
			setAttr("experienceList", experienceList);
			String file_ids = resumeJoint.getFile_id();
			strArr = file_ids.split(",");
			if (strArr.length>=1 && strArr[0] != null) {
				FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[0]);
				if (fileUpload != null) {
					setAttr("title1", fileUpload.getFile_name());
				}
			}
			if (strArr.length>=2 && strArr[1] != null) {
				FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[1]);
				if (fileUpload != null) {
					setAttr("title2", fileUpload.getFile_name());
				}
			}
			if (strArr.length>=3 && strArr[2] != null) {
				FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[2]);
				if (fileUpload != null) {
					setAttr("title3", fileUpload.getFile_name());
				}
			}
			renderAuto(path + "resume_detail.html"); 
     }
	// 应聘者的详细信息
	public void detail() {
		String id = getPara("id");// 得到查询id
		if (id.equals("0")) {
			renderAuto(path + "resume_blank.html");
		} else {
			getDetail(id);
		}
	}
	public void getjobmessage(){//岗位要求
		String id = getPara("id");
		TbFolderRollPicture folderRollPicture=TbFolderRollPicture.dao.findFirstByWhere("where id=? and is_deleted =1",id);
		Message message=new Message();
		message.setContent(folderRollPicture.getContent());
		message.setStatus(true);
		message.setJob(folderRollPicture.getJob());
		renderJson(message);
	}
	public void getanswer(){//答案
		String id = getPara("id");
		TbFolderRollPicture folderRollPicture=TbFolderRollPicture.dao.findFirstByWhere("where id=? and is_deleted =1",id);
		Message message=new Message();
		message.setStatus(true);
		message.setContent(folderRollPicture.getContent());
		renderJson(message);
	}
	public void addquestion(){
		Message message=new Message();
 		if(getSessionUser()==null){
 			message.setContent("login");
 			renderJson(message);
 			return;
		}
				String content = this.getPara("content");
				if(content.equals("")){
					message.setContent("content_no_null");
		 			renderJson(message);
		 			return;
				}
				String checkCode = this.getPara("imageCode");
				if(checkCode.equals("")){
					message.setContent("imageCode_no_null");
		 			renderJson(message);
		 			return;
				}
				String imageCode = getSessionAttr(ImageCode.class.getName());
				if (StrUtils.isEmpty(imageCode) || !imageCode.equalsIgnoreCase(checkCode)) {
					message.setContent("checkCode_no_ringht");
		 			renderJson(message);
		 			return;
				}
				Record question = new Record().set("folder_id", 5).set("identify", "")
						.set("title", content).set("content", "").set("job", getSessionUser().get("realname")).set("status", 1).set("position", 1).set("update_time", getNow()).set("create_time", getNow()).set("is_deleted", 1);
				Db.save("tb_folder_roll_picture", question);
				message.setContent("ok");
				renderJson(message);
	}
}
