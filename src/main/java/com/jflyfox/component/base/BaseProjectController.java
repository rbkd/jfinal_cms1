/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jflyfox.component.base;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jflyfox.jfinal.base.BaseController;
import com.jflyfox.jfinal.base.SessionUser;
import com.jflyfox.jfinal.component.util.Attr;
import com.jflyfox.system.log.SysLog;
import com.jflyfox.system.menu.SysMenu;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.system.user.UserSvc;
import com.jflyfox.util.NumberUtils;
import com.jflyfox.util.StrUtils;
import com.jflyfox.util.encrypt.DESUtils;

/**
 * 项目BaseControler
 * 
 * @author flyfox
 * @date 20gf15-08-02
 * 
 */
public abstract class BaseProjectController extends BaseController {

	private static final DESUtils COOKIE_DES = new DESUtils("ffcookie");

	/**
	 * 方法重写
	 * 
	 * 2015年8月2日 下午3:17:29 flyfox 330627517@qq.com
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public SessionUser getSessionUser() {
		SysUser sysUser = getSessionAttr(Attr.SESSION_NAME);
		try {
			// 如果session没有，cookie有~那么就设置到Session
			if (sysUser == null) {
				String cookieContent = getCookie(Attr.SESSION_NAME);
				if (cookieContent != null) {
					String key = COOKIE_DES.decryptString(cookieContent);
					if (StrUtils.isNotEmpty(key) && key.split(",").length == 2) {
						int userid = NumberUtils.parseInt(key.split(",")[0]);
						String password = key.split(",")[1];
						sysUser = SysUser.dao.findFirstByWhere(" where userid = ? and password = ? ", userid, password);
						if (sysUser != null)
							setSessionUser(sysUser);
					}
				}
			}
		} catch (Exception e) {
			log.error("cooke user异常:", e);
			return null;
		}
		return sysUser;
	}

	/**
	 * 方法重写
	 * 
	 * 2015年8月2日 下午3:17:29 flyfox 330627517@qq.com
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public SessionUser setSessionUser(SessionUser user) {
		setSessionAttr(Attr.SESSION_NAME, user);
		// 设置cookie，用id+password作为
		SysUser sysUser = (SysUser) user;
		String key = sysUser.getUserid() + "," + user.getStr("password");
		String cookieContent = COOKIE_DES.encryptString(key);
		setCookie(Attr.SESSION_NAME, cookieContent, 7 * 24 * 60 * 60);
		// 如果是管理员 设置菜单权限
		if (user.getInt("usertype") == 1 || user.getInt("usertype") == 2) {
			Map<Integer, List<SysMenu>> map = new UserSvc().getAuthMap(sysUser);
			// 注入菜单
			setSessionAttr("menu", map);
		}
		return user;
	}

	/**
	 * 方法重写
	 * 
	 * 2015年8月2日 下午3:17:29 flyfox 330627517@qq.com
	 * 
	 * @return
	 */
	public void removeSessionUser() {
		removeSessionAttr(Attr.SESSION_NAME);
		// 删除cookie
		removeCookie(Attr.SESSION_NAME);
	}
	
	/**
	 * 用户登录，登出记录
	 * 
	 * 2015年10月16日 下午2:36:39 flyfox 330627517@qq.com
	 * 
	 * @param user
	 * @param operType
	 */
	protected void saveLog(SysUser user, String operType) {
		try {
			String tableName = user.getTable().getName();
			Integer updateId = user.getInt("update_id");
			String updateTime = user.getStr("update_time");
			String sql = "INSERT INTO `sys_log` ( `log_type`, `oper_object`, `oper_table`," //
					+ " `oper_id`, `oper_type`, `oper_remark`, `create_time`, `create_id`) " //
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			Db.update(sql, SysLog.TYPE_SYSTEM, SysLog.getTableRemark(tableName), tableName, //
					updateId, operType, "", updateTime, updateId);
		} catch (Exception e) {
			log.error("添加日志失败", e);
		}
	}
	
}
