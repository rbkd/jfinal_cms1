package com.jflyfox.modules.admin.accountpasswd;

import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.util.StrUtils;

/**
 * 联系人管理
 * 
 * @author flyfox 2014-2-11
 */
@ControllerBind(controllerKey = "/admin/accountpasswd")
public class accountpasswdController extends BaseProjectController {

	private static final String path = "/pages/admin/accountpasswd/accountpasswd_";

	public void list() {
		TbAccountPasswd model = getModelByAttr(TbAccountPasswd.class);

		SQLUtils sql = new SQLUtils(" from sys_account_passwd t where 1=1 ");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			sql.whereLike("account", model.getStr("account"));
			sql.whereEquals("passwd", model.getStr("passwd"));
		}
		
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by id desc ");
		} else {
			sql.append(" order by ").append(orderBy);
		}

		Page<TbAccountPasswd> page = TbAccountPasswd.dao.paginate(getPaginator(), "select t.* ", //
				sql.toString().toString());

		// 下拉框
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		render(path + "add.html");
	}

	public void view() {
		TbAccountPasswd model = TbAccountPasswd.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		TbAccountPasswd model = new TbAccountPasswd();
		String now = getNow();
		model.put("update_time", now);
		model.deleteById(getParaToInt());
		list();
	}

	public void edit() {
		TbAccountPasswd model = TbAccountPasswd.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		Integer pid = getParaToInt();
		TbAccountPasswd model = getModel(TbAccountPasswd.class);
		String now = getNow();
		model.setUpdateTime(now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.setUpdateTime(now);
			model.setCreateTime(now);
			model.save();
		}
		renderMessage("保存成功");
	}
}
