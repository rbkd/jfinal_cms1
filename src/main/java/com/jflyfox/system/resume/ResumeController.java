package com.jflyfox.system.resume;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.base.Paginator;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.system.resume.model.FileUpload;
import com.jflyfox.system.resume.model.PersonalExperience;
import com.jflyfox.system.resume.model.ResumeJoint;
import com.jflyfox.system.resume.model.SysApplicant;
import com.jflyfox.util.StrUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 应聘者信息管理 控制器
 * 
 * @author
 */
@ControllerBind(controllerKey = "/system/resume")
public class ResumeController extends BaseProjectController {

	private static final String path = "/pages/system/resume/resume_"; // 基本路径
	public static List<SysApplicant> SysApplicantList; // 基本信息全局类

	public void list() { // 按照条件查询应聘者的基本信息、教育背景
		SysApplicant model = getModelByAttr(SysApplicant.class);
		String number = model.getStr("number"); // 得到每页显示条数
		if (number == null) {
			number = 30 + "";// 默认30条
		}
		SQLUtils sql = new SQLUtils(
				"from sys_applicant t,education_background e where t.id=e.applicant_id and t.is_deleted =1 and e.is_deleted =1");
		if (model.getAttrValues().length != 0) {
			// 查询条件
			if (!(model.getStr("province_site").equals("-1"))) {// 站点
				sql.whereEquals("province_site", model.getStr("province_site"));
			}
			if (!(model.getStr("job").equals("-1"))) {
				sql.whereEquals("job", model.getStr("job"));// 应聘职位
			}
			if (!(model.getStr("select").equals("-1")) && model.getStr("content")!=null) {
				sql.whereLike(model.getStr("select"), model.getStr("content")); // 模糊匹配
			}
		}
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.id desc ");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Paginator paginator = getPaginator(); // 分页
 		paginator.setPageSize(Integer.parseInt(number)); // 设置每页条数
 		List<SysApplicant> SysApplicants=SysApplicant.dao.find("select t.id,t.province_site,t.job,t.name,e.degree,e.school_name,e.profession,t.telephone,t.account_number,t.isverify,t.isread,t.create_time "+sql.toString().toString());
		SysApplicants.add(new SysApplicant().setId(-1));
		List<SysApplicant> SysApplicantEnd=new ArrayList<SysApplicant>();
		List<SysApplicant> SysApplicanttemp=new ArrayList<SysApplicant>();
		int id=0;
		int totalpage=0;
		for (SysApplicant sysApplicant : SysApplicants) {//取学历最高的
			if(id==0){
				id=sysApplicant.getId();
			}
			if(sysApplicant.getId()==id){
				SysApplicanttemp.add(sysApplicant);
			}else{
				int postdoctor=checkDegree(SysApplicanttemp,"博士后");
				if(postdoctor!=-1){
					SysApplicantEnd.add(SysApplicanttemp.get(postdoctor));
					SysApplicanttemp.clear();
					totalpage++;
					id=sysApplicant.getId();
					SysApplicanttemp.add(sysApplicant);
				}else{
					int doctor=checkDegree(SysApplicanttemp,"博士");
					if(doctor!=-1){
						SysApplicantEnd.add(SysApplicanttemp.get(doctor));
						SysApplicanttemp.clear();
						totalpage++;
						id=sysApplicant.getId();
						SysApplicanttemp.add(sysApplicant);
					}else{
						int master=checkDegree(SysApplicanttemp,"硕士");
						if(master!=-1){
							SysApplicantEnd.add(SysApplicanttemp.get(master));
							SysApplicanttemp.clear();
							totalpage++;
							id=sysApplicant.getId();
							SysApplicanttemp.add(sysApplicant);
						}else{
							int undergraduate=checkDegree(SysApplicanttemp,"本科");
							if(undergraduate!=-1){
								SysApplicantEnd.add(SysApplicanttemp.get(undergraduate));
								SysApplicanttemp.clear();
								totalpage++;
								id=sysApplicant.getId();
								SysApplicanttemp.add(sysApplicant);
							}else{
								int juniorcollege=checkDegree(SysApplicanttemp,"大专");
								if(juniorcollege!=-1){
									SysApplicantEnd.add(SysApplicanttemp.get(juniorcollege));
									SysApplicanttemp.clear();
									totalpage++;
									id=sysApplicant.getId();
									SysApplicanttemp.add(sysApplicant);
								}else{
									int senior=checkDegree(SysApplicanttemp,"高中");
									if(senior!=-1){
										SysApplicantEnd.add(SysApplicanttemp.get(senior));
										SysApplicanttemp.clear();
										totalpage++;
										id=sysApplicant.getId();
										SysApplicanttemp.add(sysApplicant);
									}
								}
							}
						}
					}
				}
			}
			
		}
		SysApplicanttemp.clear();
		for(int i=(paginator.getPageNo()-1)*paginator.getPageSize();i<(paginator.getPageNo()-1)*paginator.getPageSize()+paginator.getPageSize() && i<SysApplicantEnd.size();i++){
			SysApplicanttemp.add(SysApplicantEnd.get(i));
		}
		Page<SysApplicant> p=new Page<SysApplicant>(SysApplicanttemp, paginator.getPageNo(), paginator.getPageSize(), totalpage, totalpage);
		ResumeController.SysApplicantList = SysApplicantEnd;
		setAttr("page", p);
		setAttr("attr", model);
		render(path + "list.html");
	}
    public int checkDegree(List<SysApplicant> SysApplicants,String degree){//检验学历
    	for (int i = 0; i < SysApplicants.size(); i++) {
			SysApplicant temp=SysApplicants.get(i);
			if(temp.get("degree").equals(degree)){
				return i;
			}
		}
		return -1;
    }
	// 应聘者的详细信息
	public void detail() {
		int id = getParaToInt();
		Db.update("update sys_applicant set isread=1 where id=?", id);
		ResumeJoint resumeJoint = ResumeJoint.dao.findFirstByWhere("where applicant_id=?", id);
		String applicant_id = resumeJoint.getApplicant_id();// 获取基础信息表id
		String experience_ids = resumeJoint.getExperience_id();// 获取经历id
		List<SysApplicant> applicantList = SysApplicant.dao
				.find("select t.*,e.* from sys_applicant t,education_background e where t.id=e.applicant_id and t.is_deleted =1 and e.is_deleted =1 and t.id="
						+ applicant_id);// 基础信息，教育背景
		setAttr("applicantList", applicantList);
		PersonalExperience personalExperience;
		String[] strArr;
		strArr = experience_ids.split(",");
		List<PersonalExperience> experienceList = new ArrayList<PersonalExperience>();
		for (String project_id : strArr) {
			personalExperience = PersonalExperience.dao.findFirstByWhere(" where id = ?", project_id);
			if(personalExperience==null){}else{
			experienceList.add(personalExperience);
			}
		}
		setAttr("experienceList", experienceList);
		String file_ids = resumeJoint.getFile_id();
		strArr = file_ids.split(",");
		if (strArr.length>=1 && strArr[0] != null) {
			FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[0]);
			if (fileUpload != null) {
				setAttr("file4", fileUpload);
			}
		}
		if (strArr.length>=2 && strArr[1] != null) {
			FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[1]);
			if (fileUpload != null) {
				setAttr("file5", fileUpload);
			}
		}
		if (strArr.length>=3 && strArr[2] != null) {
			FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1", strArr[2]);
			if (fileUpload != null) {
				setAttr("file6", fileUpload);
			}
		}
		render(path + "detail.html");
	}

	public void delete() {
		int id = getParaToInt();
		Db.update("update education_background set is_deleted=0 where applicant_id=?", id);
		Db.update("update sys_applicant set is_deleted=0 where id=?", id);
		Db.update("update resume_joint set is_deleted=0 where applicant_id=?", id);
		list();
	}

	public void deletemore() {// 删除多个
		String ids = getPara();
		String[] strArr;
		strArr = ids.split(",");
		for (String id : strArr) {
			Db.update("update education_background set is_deleted=0 where applicant_id=?", id);
			Db.update("update sys_applicant set is_deleted=0 where id=?", id);
			Db.update("update resume_joint set is_deleted=0 where applicant_id=?", id);
		}
		list();
	}

	public void verifymore() { // 审查通过(多个)函数
		String ids = getPara();
		String[] strArr;
		strArr = ids.split(",");
		for (String id : strArr) {
			Db.update("update sys_applicant set isverify=1 where id=?", id);
		}
		list();
	}

	public void verifyonly() {// 审查通过(单个)函数
		String id = getPara("applicant_id");
		String verify = getPara("verify");
		Db.update("update sys_applicant set isverify=" + verify + " where id=?", id);
		list();
	}

	public void ExportToExcel() {// 导出为excel
		// 得到原始模板文件路径
		String templateFilePath = JFinal.me().getServletContext().getRealPath("exceltemplate") + File.separator
				+ "xtask_export_template.xls";
		// 导出excel的标题
		String title = "简历信息表";
		// 数据
		// 将导出excel的数据和导出excel的标题放到集合中
		/**
		 * 这儿申明的map集合是为了后面transformXLS方法转化需要传入Excel里面的一个Map，
		 * jxls根据Template里面的定义和Map里面的对象对Template进行解析， 将Map里面的对象值填入到Excel文件中。
		 */
		Map<String, Object> datamap = new HashMap<String, Object>();
		String ids = getPara();
		String[] strArr;
		List<SysApplicant> sysApplicants=new ArrayList<SysApplicant>();
		strArr = ids.split(",");
		for (String id : strArr) {
			for (SysApplicant sysApplicant : ResumeController.SysApplicantList) {
				if(sysApplicant.getId()==Integer.parseInt(id)){
					sysApplicants.add(sysApplicant);
				}
			}
		}
		datamap.put("list", sysApplicants);// 导出excel的数据
		datamap.put("title", title); // 导出excel的标题

		InputStream in = null;
		try {
			in = new FileInputStream(templateFilePath); // 将模板文件转换为文件流
			XLSTransformer transformer = new XLSTransformer(); // jxls生成excel
			HSSFWorkbook wb = (HSSFWorkbook) transformer.transformXLS(in, datamap); // 将excel流转换为Workbook
			Sheet sheet = wb.getSheetAt(0); // 取第一个sheet
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0)); // 四个参数分别是：起始行，结束行，起始列，结束列
			writeStream(title, wb, getResponse()); // 返回excel
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
		renderNull();
	}
	public void resume(){
		render(path + "fill.html");
	}
	/**
	 * 输出文件流
	 * 
	 * @param filename
	 * @param wb
	 * @param response
	 */
	public static void writeStream(String filename, Workbook wb, HttpServletResponse response) {
		try {
			filename += ".xls";
			filename.replaceAll("/", "-");
			filename = URLEncoder.encode(filename, "UTF-8");
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.setContentType("application/octet-stream;charset=UTF-8");

			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

			wb.write(outputStream);

			outputStream.flush();
			outputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void fileDownload() throws IOException{
	    String filename = getPara("file_name");
	    String fileurl = getPara("file_url");
	    String[] strArr;
		strArr = fileurl.split("/");
		fileurl=strArr[4];
		PropKit.use(new File(PathKit.getRootClassPath()+"/conf/config.properties"));
        //设置文件MIME类型  
        getResponse().setContentType(JFinal.me().getServletContext().getMimeType(filename));  
        //设置Content-Disposition  
        getResponse().setHeader("Content-Disposition", "attachment;filename="+filename);  
        //读取目标文件，通过response将目标文件写到客户端  
        //读取文件  
        InputStream in = new FileInputStream(PropKit.get("fileUrl")+File.separator + fileurl);  
        OutputStream out = getResponse().getOutputStream();  
        //写文件  
        int b;  
        while((b=in.read())!= -1)  
        {  
            out.write(b);  
        }  
          
        in.close();  
        out.close();  
	}
	 public void wordDownload(){//word文档 下载
		 Configuration  configuration = new Configuration();  
		 configuration.setDefaultEncoding("utf-8");
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 String id = getPara();
		 SysApplicant applicant= SysApplicant.dao.findFirstByWhere("where id=? and is_deleted =1",id);
		  dataMap.put("applicant",applicant);
		  List<SysApplicant> applicantandeducationList = SysApplicant.dao
					.find("select t.*,e.* from sys_applicant t,education_background e where t.id=e.applicant_id and t.is_deleted =1 and e.is_deleted =1 and t.id="
							+ id);// 基础信息，教育背景
			for (SysApplicant educationtype2 : applicantandeducationList) {
				if (educationtype2.get("types").toString().equals("2")) {
					dataMap.put("educationtype2",educationtype2);
					applicantandeducationList.remove(educationtype2);
					break;
				}
			}
			for (SysApplicant educationtype1 : applicantandeducationList) {
				if (educationtype1.get("types").toString().equals("1")) {
					dataMap.put("educationtype1",educationtype1);
					applicantandeducationList.remove(educationtype1);
					break;
				}
			}
			if (applicantandeducationList.size() > 0) {
				dataMap.put("educationList",applicantandeducationList);
			}
			ResumeJoint resumeJoint = ResumeJoint.dao.findFirstByWhere("where applicant_id=? and is_deleted =1",
					id);
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
			dataMap.put("experienceList",experienceList);
			String file_ids = resumeJoint.getFile_id();
			strArr = file_ids.split(",");
			List<FileUpload> fileList = new ArrayList<FileUpload>();
			for (String file_id : strArr) {
				FileUpload fileUpload = FileUpload.dao.findFirstByWhere(" where id = ? and is_deleted =1",file_id);
				if (fileUpload == null) {
				} else {
					fileList.add(fileUpload);
				}
			}
			dataMap.put("fileList",fileList);
		}
			createDoc(applicant.getName()+"简历表",dataMap,configuration,getResponse());
			renderNull();
	 }
	 public void createDoc(String filename,Map dataMap,Configuration configuration,HttpServletResponse response){  
		    filename += ".doc";
			filename.replaceAll("/", "-");
			try {
				filename = URLEncoder.encode(filename, "UTF-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.setContentType("application/octet-stream;charset=UTF-8");
			Writer writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		 // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，   
	      configuration.setClassForTemplateLoading(this.getClass(),  
	    		  "/com/jflyfox/system/resume");  
	      Template t = null;  
	      try {  
	         // wordtemplate.ftl为要装载的模板   
	         t = configuration.getTemplate("wordtemplate.ftl");
	         t.setEncoding("utf-8");  
	      } catch (IOException e) {  
	         e.printStackTrace();  
	      }  
	      try {  
	         t.process(dataMap, writer);  
	         writer.flush();
	         writer.close();  
	      } catch (TemplateException e) {  
	         e.printStackTrace();  
	      } catch (IOException e) {  
	         e.printStackTrace();  
	      }  
	   }  
}
