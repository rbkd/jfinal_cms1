package com.jflyfox.modules.admin.importexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.util.JFlyfoxUpload;
import com.jflyfox.jfinal.component.annotation.ControllerBind;

/**
 * 友情链接管理
 * 
 * @author flyfox 2014-4-24
 */
@ControllerBind(controllerKey = "/admin/importexcel")
public class importexcelController extends BaseProjectController {

	private static final String path = "/pages/admin/importexcel/";

	/**
	 * 跳转到操作页面
	 * 
	 * 2015年3月16日 下午5:33:55 flyfox 330627517@qq.com
	 */
	public void index() {
		render(path + "importexcel.html");
	}
    public void importexcel(){
    	UploadFile uploadFile = getFile("excel_url", JFlyfoxUpload.UPLOAD_TMP_PATH, JFlyfoxUpload.UPLOAD_MAX);
		if (uploadFile != null) {
			List<Map<Integer, String>> excelList = dealDataByPath(uploadFile.getFile());
		  for(int i=1;i<excelList.size();i++){
			Record account_passwd = new Record().set("account",excelList.get(i).get(0)).set("passwd", excelList.get(i).get(1))
					.set("update_time", getNow()).set("create_time", getNow()).set("is_deleted", "1");
			Db.save("sys_account_passwd", account_passwd);	
		  }
		  renderJson("success");
		}else{
			 renderJson("no_file");
		}
    }
	/**
	 * 分析excel的内容
	 * 
	 * @param path
	 * @return
	 */
	private List<Map<Integer, String>> dealDataByPath(File file) {
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		// 工作簿
		HSSFWorkbook hwb = null;
		try {
			hwb = new HSSFWorkbook(new FileInputStream(file));

			HSSFSheet sheet = hwb.getSheetAt(0); // 获取到第一个sheet中数据

			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行

				HSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)

				Map<Integer, String> map = new HashMap<Integer, String>();

				for (int j = 0; j < row.getLastCellNum(); j++) {

					HSSFCell cell = row.getCell(j); // 获取到第j行的数据(单元格)

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);

					map.put(j, cell.getStringCellValue());
				}

				list.add(map);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
