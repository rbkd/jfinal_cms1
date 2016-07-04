package com.jflyfox.modules;

import com.jfinal.aop.Before;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.util.ImageCode;
import com.jflyfox.component.util.JFlyFoxCache;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.folder.FolderService;
import com.jflyfox.modules.admin.folder.TbFolder;
import com.jflyfox.modules.front.Home;
import com.jflyfox.modules.front.interceptor.FrontInterceptor;
import com.jflyfox.system.dict.DictCache;
import com.jflyfox.system.log.SysLog;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.system.user.UserCache;
import com.jflyfox.util.Config;
import com.jflyfox.util.NumberUtils;
import com.jflyfox.util.StrUtils;

/**
 * CommonController
 */
@ControllerBind(controllerKey = "/")
public class CommonController extends BaseProjectController {

	public static final String loginPage = "/login.html";
	public static final String adminHomePage = "/admin/home";
	public static final String firstPage = "/home";

	/**
	 * 首页，菜单
	 * 
	 * 2015年5月25日 下午11:00:28 flyfox 330627517@qq.com
	 */
	@Before(FrontInterceptor.class)
	public void index() {
		// new FrontService().menu(this);

		String folderStr = getPara();
		Integer folderId = TbFolder.ROOT;
		if (folderStr != null) {
			folderId = NumberUtils.parseInt(FolderService.getMenu(folderStr));
		}
		if (folderId == null || folderId <= 0) {
			folderId = TbFolder.ROOT;
		}
		// 活动目录
		setAttr("folders_selected", folderId);

		TbFolder folder = new FolderService().getFolder(folderId);
		setAttr("folder", folder);

		setAttr("paginator", getPaginator());

		// seo：title优化
		setAttr(JFlyFoxUtils.TITLE_ATTR, folder.getStr("name") + " - " + JFlyFoxCache.getHeadTitle());
			renderAuto(Home.PATH + FolderService.getMenu(folderId + "") + ".html");
	}

	@Before(FrontInterceptor.class)
	public void admin() {
		if (getSessionUser() != null) {
			// 如果session存在，不再验证
			redirect(adminHomePage);
		} else {
			renderAuto(loginPage);
		}

	}

	/**
	 * 登录
	 * 
	 * @author flyfox 2013-11-11
	 */
	@Before(FrontInterceptor.class)
	public void login() {
		// 初始化数据字典Map
				String username = getPara("username");
				String password = getPara("password");
		if (StrUtils.isEmpty(username)) {
			setAttr("msg", "用户名不能为空");
			renderAuto(loginPage);
			return;
		} else if (StrUtils.isEmpty(password)) {
			setAttr("msg", "密码不能为空");
			renderAuto(loginPage);
			return;
		}
		// 获取验证码
		String imageCode = getSessionAttr(ImageCode.class.getName());
		String checkCode = this.getPara("imageCode");

		if (StrUtils.isEmpty(imageCode) || !imageCode.equalsIgnoreCase(checkCode)) {
			setAttr("msg", "验证码错误！");
			renderAuto(loginPage);
			return;
		}

		// 新加入，判断是否有上一个页面
		String prePage = getPara("pre_page");
		String toPage = StrUtils.isEmpty(prePage) || prePage.indexOf("login") >= 0 //
				|| prePage.indexOf("trans") >= 0 ? firstPage : prePage;
		setAttr("pre_page", prePage); // 如果密码错误还需要用到
		String encryptPassword = JFlyFoxUtils.passwordEncrypt(password); // 加密
		SysUser user = SysUser.dao.findFirstByWhere(" where username = ? and password = ? ", username, encryptPassword);
		if (user == null || user.getInt("userid") <= 0) {
			setAttr("msg", "认证失败，请重新输入。");
			renderAuto(loginPage);
			return;
		} else {
			setSessionUser(user);
		}

		// 添加日志
		user.put("update_id", user.getUserid());
		user.put("update_time", getNow());
		saveLog(user, SysLog.SYSTEM_LOGIN);

		redirect(toPage);
	}

	/**
	 * 登出
	 * 
	 * @author flyfox 2013-11-13
	 */
	@Before(FrontInterceptor.class)
	public void logout() {
		SysUser user = (SysUser) getSessionUser();
		if (user != null) {
			// 添加日志
			user.put("update_id", user.getUserid());
			user.put("update_time", getNow());
			saveLog(user, SysLog.SYSTEM_LOGOUT);
			// 删除session
			removeSessionUser();
		}

		setAttr("msg", "您已退出");
		renderAuto(loginPage);
	}

	public void update_cache() {
		DictCache.init();
		UserCache.init();
		renderHtml("1");
	}

	public void trans() {
		String redirectPath = getPara();
		if (StrUtils.isEmpty(redirectPath)) {
			redirectPath = Config.getStr("PAGES.TRANS");
		} else if (redirectPath.equals("auth")) {
			redirectPath = "/pages/error/trans_no_auth.html";
		}
		render(redirectPath);
	}
}
