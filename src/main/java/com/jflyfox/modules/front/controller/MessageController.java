package com.jflyfox.modules.front.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.CommonController;
import com.jflyfox.modules.admin.comment.CommentService;
import com.jflyfox.modules.admin.comment.TbComment;
import com.jflyfox.modules.front.interceptor.FrontInterceptor;
import com.jflyfox.system.user.SysUser;

/**
 * 我的消息
 * 
 * 2015年3月10日 下午5:38:24 flyfox 330627517@qq.com
 */
@ControllerBind(controllerKey = "/front/message")
public class MessageController extends BaseProjectController {

	public static final String path = "/message/";
	
	/**
	 * 我的消息
	 */
	@Before(FrontInterceptor.class)
	public void index() {
		// 活动目录
		setAttr("folders_selected", "message");

		SysUser user = (SysUser) getSessionUser();
		if (user == null) {
			redirect(CommonController.firstPage);
			return;
		}

		String sql = " from tb_comment t " //
				+ " left join tb_article art on art.id = t.article_id " //
				+ " where t.create_id = ? or t.reply_userid = ? order by t.create_time desc ";
		Page<TbComment> pages = TbComment.dao.paginate(getPaginator(), //
				"select t.*,art.title,art.create_id as article_create_id " //
				, sql, user.getUserid(), user.getUserid());
		// 更新状态为已读
		new CommentService().updateCommentStatusRead(user.getUserid());

		setAttr("page", pages);
		renderAuto(path + "show_message.html");
	}
}
