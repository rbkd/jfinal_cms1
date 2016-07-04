package com.jflyfox.modules.admin.folderrollpicture;

import java.io.File;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.component.util.JFlyfoxUpload;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.admin.folder.FolderService;
import com.jflyfox.modules.admin.folder.TbFolder;
import com.jflyfox.util.StrUtils;

/**
 * 栏目轮播图
 * 
 * @author flyfox 2014-4-24
 */
@ControllerBind(controllerKey = "/admin/folderrollpicture")
public class FolderrollpictureController extends BaseProjectController {

	private static final String path = "/pages/admin/folderrollpicture/folderrollpicture_";

	public void list() {
		TbFolderRollPicture model = getModelByAttr(TbFolderRollPicture.class);

		SQLUtils sql = new SQLUtils(" from tb_folder_roll_picture t " //
				+ " left join tb_folder f on f.id = t.folder_id " //
				+ " where is_deleted = " + JFlyFoxUtils.IS_DELETED_NO + " ");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
			sql.whereEquals("folder_id", model.getInt("folder_id"));
		}

		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.folder_id,t.sort,t.id desc ");
		} else {
			sql.append(" order by ").append(orderBy);
		}

		Page<TbFolderRollPicture> page = TbFolderRollPicture.dao.paginate(getPaginator(),
				"select t.*,f.name as folderName ", //
				sql.toString().toString());

		// 查询下拉框
		setAttr("selectFolder", new FolderService().selectFolder(model.getInt("folder_id")));

		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		// 查询下拉框
		setAttr("selectFolder", new FolderService().selectFolder(0));

		render(path + "add.html");
	}

	public void view() {
		TbFolderRollPicture model = TbFolderRollPicture.dao.findById(getParaToInt());
		TbFolder folder = TbFolder.dao.findById(model.getInt("folder_id"));
		model.put("folderName", folder.getStr("name"));

		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		TbFolderRollPicture model = TbFolderRollPicture.dao.findById(getParaToInt());
		Integer userid = getSessionUser().getUserID();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		model.setIsDeleted(JFlyFoxUtils.IS_DELETED_YES);
		model.update();
		// model.deleteById(getParaToInt());

		list();
	}

	public void edit() {
		TbFolderRollPicture model = TbFolderRollPicture.dao.findById(getParaToInt());

		// 查询下拉框
		setAttr("selectFolder", new FolderService().selectFolder(model.getInt("folder_id")));
        
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		UploadFile uploadImage = getFile("model.image_url", JFlyfoxUpload.UPLOAD_TMP_PATH, JFlyfoxUpload.UPLOAD_MAX);
		UploadFile uploadImagehover = getFile("model.hover_image_url", JFlyfoxUpload.UPLOAD_TMP_PATH, JFlyfoxUpload.UPLOAD_MAX);
		Integer pid = getParaToInt();
		PropKit.use(new File(PathKit.getRootClassPath()+"/conf/config.properties"));
		TbFolderRollPicture model = getModel(TbFolderRollPicture.class);
		// 图片附件
		if (uploadImage != null) {
			String fileName = JFlyfoxUpload.renameFile(PropKit.get("fileUrl"), uploadImage);
			model.set("image_url",PropKit.get("fileUrlPrefix")+ fileName);
		}
		if (uploadImagehover != null) {
			String fileName = JFlyfoxUpload.renameFile(PropKit.get("fileUrl"), uploadImagehover);
			model.set("hover_image_url",PropKit.get("fileUrlPrefix") + fileName); 
		}
		Integer userid = getSessionUser().getUserID();
		String now = getNow();
		if(model.getIdentify()==null){
			model.setIdentify("");
		}
		model.setUpdateId(userid);
		model.setUpdateTime(now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.setIsDeleted(JFlyFoxUtils.IS_DELETED_NO);
			model.setCreateId(userid);
			model.setCreateTime(now);
			model.save();
		}
		renderMessage("保存成功");
	}

}
