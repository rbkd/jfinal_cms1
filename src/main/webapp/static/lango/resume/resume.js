var oper;
	jQuery(function($) {
		function checkbeforeform(o) {//提交form表单之前验证input信息是否填写
			o.blur();
			var status = "1";
			o.each(function() {
				if($(this).hasClass('borderred')){
					status = "0";
					return false;
				}
			});
			return status;
		}
		function checktextarea(o) {//提交form表单之前验证textarea信息是否填写
			o.blur();
			var status = "1";
			o.each(function() {
				if($(this).hasClass('borderred')){
					status = "0";
					return false;
				}
			});
			return status;
		}
		oper = {
			saveeducation : function(o) {//保存教育背景用户
				var status = checkbeforeform(o.find(".check"));
				if (status == "1") {
					//ajax提交form表单的方式
					o.find("form").ajaxSubmit({
						type : "post",
						async : false,
						url : "front/resume/education",
						dataType : "json",
						success : function(data) {
							if (data.status) {
								o.find("input").eq(0).val(data.id1);
							} else {
								alert("失败");
							}
						}
					});
					o.next().find("td").eq(1).html(o.find("input").eq(2).val());//学校名称
					o.next().find("td").eq(5).html(o.find("input").eq(3).val());//专业名称
					o.next().find("td").eq(7).html(o.find("select").eq(0).val());//学历
					o.next().find("td").eq(9).html(o.find("input").eq(4).val());//入学时间毕业时间
					o.next().find("td").eq(11).html(o.find("input").eq(5).val());//毕业时间
					o.css('display', 'none');
					o.next().css('display', 'block');
					o.next().css('border-bottom', '0px');
					if (o.next().next().hasClass("add")) {
						o.next().next().css('display', 'block');
					}
				}
			},
			telephonecheck:function(o){//验证手机号函数
			      if(o.val()== ''){
			    	  o.addClass('borderred');
			      }else if(o.val().length !=11){
			    	  o.addClass('borderred');
			      }else{
			    	  o.removeClass('borderred');
			      }
	           var status = "1";
	           if(o.hasClass('borderred')){
					status = "0";
				}
				  return status;
			},
			checkemail:function(o){//验证邮箱函数
				if(o.val()==""){
					 o.addClass('borderred');
				}else{
					if (/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(o.val()) == false) { 
						 o.addClass('borderred');
					}else{
						$.ajax({//验证邮箱是否存在
							async:false,
							url:"front/resume/checkemail?email="+$(".checkemail").val()+"",
							type:'post',
							dataType:'json',
							success:function(data){
						   if (data.status == true) {
							   o.removeClass('borderred');
							}else{
								 o.addClass('borderred');
							}
							},error:function(){
							}
							});
					}
				}
				var status = "1";
				 if(o.hasClass('borderred')){
						status = "0";
					}
				return status;
			},
			canceleducation : function(o) {//删除教育背景
				o.find("form").ajaxSubmit({
					type : "post",
					async : false,
					url : "front/resume/educationdelete",
					dataType : "json",
					success : function(data) {
					}
				});
				if (o.next().next().hasClass("add")) {
					o.next().next().css('display', 'block');
				}
				$(o).next().remove();
				$(o).remove();
			},
			editeducation : function(o) {//编辑教育背景
				if (o.next().hasClass("add")) {
					o.next().css('display', 'none');
				}
				o.css('display', 'none');
				o.prev().css('display', 'block');
			},
			addeducationone : function(o) { //添加教育背景
				var education_background = $("<div id=\"educationmessage\" class=\"educationdiv\" style=\"height: 227px;border-top: 2px solid #ddd;display:none; border-bottom:0px;\">"
						+ "	<br />"
						+ "		<div class=\"topdiv\">"
						+ "			<form action=\"\">"
						+ "			   <input type=\"hidden\" value=\"0\" name=\"model.id\"/>"
						+ "			    <input type=\"hidden\" value=\"0\" name=\"model.applicant_id\"/>"
						+ "				<table>"
						+ "					<tbody>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">学校名称</td>"
						+ "							<td class=\"tdtwo\"><input class=\"check\" maxlength=\"40\" type=\"text\" name=\"model.school_name\""+
						"								/></td>"
						+ "							<td class=\"tdthree\"></td>"
						+ "							<td class=\"tdfour\"></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">专业名称</td>"
						+ "							<td class=\"tdtwo\"><input maxlength=\"40\" class=\"check\" type=\"text\" name=\"model.profession\"  /></td>"
						+ "							<td class=\"tdthree\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">学历</td>"
						+ "							<td class=\"tdfour\"><select name=\"model.degree\" class=\"selectstyle\">"
						+ "									<option value=\"高中\">高中</option>"
						+ "									<option value=\"大专\">大专</option>"
						+ "									<option value=\"本科\">本科</option>"
						+ "									<option value=\"硕士\">硕士</option>"
						+ "									<option value=\"博士\">博士</option>"
						+ "									<option value=\"博士后\">博士后</option>"
						+ "							</select></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">入学时间</td>"
						+ "							<td class=\"tdtwo\"><input class=\"check\" type=\"text\" name=\"model.admission_time\""
						+ "							onclick=\"WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});\"/></td>"
						+ "							<td class=\"tdthree\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">毕业时间</td>"
						+ "							<td class=\"tdfour\"><input class=\"check\" type=\"text\" name=\"model.graduation_time\""
						+ "								onclick=\"WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false});\"/></td>"
						+ "						</tr>"
						+ "					</tbody>"
						+ "				</table>"
						+ "				</form>"
						+ "				</div>"
						+ "				<div class=\"bottomdiv\">"
						+ "			<button class=\"bottonstyle\" onclick=\"oper.canceleducation($(this).parent().parent());\" style=\"margin-right:-230px;position: relative;\">取消</button>"
						+ "			<button onclick=\"oper.saveeducation($(this).parent().parent());\" class=\"bottonstyle\">保存</button>"
						+ "		</div>" + "		</div>" + "	</div>");
				var education_editonly = $("<div id=\"educationedit\"   class=\"maindiv2\" style=\"height:210px;display: none;border-top: 2px solid #ddd;border-bottom:0px;\">"
						+ "	<br />"
						+ "		<div class=\"topdiv\">"
						+ "				<table>"
						+ "					<tbody>"
						+ "						<tr>"
						+ "							<td class=\"tdedit1\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">学校名称</td>"
						+ "							<td class=\"tdedit2\"></td>"
						+ "							<td class=\"tdedit3\"></td>"
						+ "							<td class=\"tdedit4\"></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdedit1\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">专业名称</td>"
						+ "							<td class=\"tdedit2\"></td>"
						+ "							<td class=\"tdedit3\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">学历</td>"
						+ "							<td class=\"tdedit4\"></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdedit1\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">入学时间</td>"
						+ "							<td class=\"tdedit2\"></td>"
						+ "							<td class=\"tdedit3\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">毕业时间</td>"
						+ "							<td class=\"tdedit4\"></td>"
						+ "						</tr>"
						+ "					</tbody>"
						+ "				</table>"
						+ "		</div>"
						+ "		<div class=\"bottomdiv\" style=\"width: 100px;\">"
						+ "			<img alt=\"\" onclick=\"oper.editeducation($(this).parent().parent());\" src=\"static/lango/resume/image/bianji.png\">"
						+ "		</div>" + "	</div>" + "</div>");

				var adddivcode = $("<div id=\"adddiveducation\" onclick=\"oper.addeducationone(this)\" class=\"add\" style=\"display: none;\">"
						+ "	 <div class=\"addtop\">"
						+ "	      <span class=\"addcontent\">添加教育背景+</span>"
						+ "	   </div>" + "    </div>");
				$(o).prev().after(education_background);
				$(o).remove();
				education_background.after(education_editonly);
				education_editonly.after(adddivcode);
				adddivcode.prev().remove();
				education_background.find("input").eq(1).val(
						$("#basemessage1").find("input").eq(0).val());
				education_background.slideDown();
				oper.checkinput();//添加绑定事件
			},
			saveexperience : function(o) {//保存个人经历动态
				var status = checkbeforeform(o.find(".check"));
				var statusta = checktextarea(o.find(".checkep"));
				if (status == "1" && statusta == "1") {
					//ajax提交form表单的方式
					o.find("form").ajaxSubmit({
						type : "post",
						async : false,
						url : "front/resume/personalExperence",
						dataType : "json",
						success : function(data) {
							if (data.status == true) {
								o.find("input").eq(0).val(data.experience_id);
							}
						}
					});
					o.next().find("td").eq(1).html(o.find("input").eq(2).val());//经历名称
					o.next().find("td").eq(3).html(o.find("input").eq(3).val());//经历时间
					o.next().find("td").eq(5).find("div").html(o.find("textarea").eq(0).val());//经历介绍
					o.next().find("td").eq(7).find("div").html(o.find("textarea").eq(1).val());//经历成就
					o.css('display', 'none');
					o.next().css('display', 'block');
					if (o.next().next().hasClass("add")) {
						o.next().next().css('display', 'block');
					}
				}
			},
			cancelexperience : function(o) {//删除个人经历动态
				o.find("form").ajaxSubmit({
					type : "post",
					async : false,
					url : "front/resume/experiencedelete",
					dataType : "json",
					success : function(data) {
					}
				});
				if (o.next().next().hasClass("add")) {
					o.next().next().css('display', 'block');
				}
				$(o).next().remove();
				$(o).remove();
			},
			editpersonal : function(o) {//编辑个人经历动态
				if (o.next().hasClass("add")) {
					o.next().css('display', 'none');
				}
				o.css('display', 'none');
				o.prev().css('display', 'block');
			},
			addexperience : function(o) { //添加经历
				var personalexperience = $("<div id=\"personal_experience\" class=\"experiencediv\" style=\"height: 390px;border-top: 2px solid #ddd;border-bottom:0px;\">"
						+ "		<div class=\"topdiv\" style=\"margin-top: 45px;\">"
						+ "			<form id=\"personal_form\" action=\"\">"
						+ "			 <input type=\"hidden\" value=\"0\" name=\"model.id\"/>"
						+ "			 <input type=\"hidden\" value=\"0\" name=\"applicant_id\"/>"
						+ "				<table>"
						+ "					<tbody>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">名称</td>"
						+ "							<td class=\"tdtwo\"><input maxlength=\"40\"  class=\"check\" type=\"text\" name=\"model.name\""+
						"								/></td>"
						+ "							<td class=\"tdthree\"><img alt=\"\""+
						"								src=\"static/lango/resume/image/bitian.png\">开始结束时间</td>"
						+ "							<td class=\"tdfour\"><input placeholder=\"2016.01.02-2016.03.05\" class=\"check\" type=\"text\" name=\"model.time\""+
						"								 /></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">介绍</td>"
						+ "							<td><textarea class=\"checkep\" maxlength=\"300\"  name=\"model.description\" "
						+ "									style=\"width: 660px;height: 90px;margin-left: 100px;\"></textarea></td>"
						+ "                        <td></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">成果或心得</td>"
						+ "							<td ><textarea class=\"checkep\" maxlength=\"300\"  name=\"model.achievement\""
						+ "								style=\"width: 660px;height: 90px;margin-left: 100px;\"></textarea></td>"
						+ "                       <td></td>"
						+ "						</tr>"
						+ "					</tbody>"
						+ "				</table>"
						+ "			</form>"
						+ "		</div>"
						+ "		<div class=\"bottomdiv\">	"
						+ "            <button class=\"bottonstyle\" onclick=\"oper.cancelexperience($(this).parent().parent());\" style=\"margin-right:-230px;margin-top: 15px;position: relative;\">取消</button>			"
						+ "            <button onclick=\"oper.saveexperience($(this).parent().parent());\" class=\"bottonstyle\">保存</button>	"
						+ "     	</div>" + " </div>");
				var personaledit = $("<div id=\"personal_edit\"class=\"maindiv2\" style=\"height:auto;display: none;border-top: 2px solid #ddd;border-bottom:0px;\">"
						+ "		<div class=\"topdiv\" style=\"margin-top: 45px;\">"
						+ "				<table>"
						+ "					<tbody>"
						+ "					<tr>"
						+ "							<td class=\"tdedit1\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">名称</td>"
						+ "							<td class=\"tdedit2\"></td>"
						+ "							<td class=\"tdedit3\"><img alt=\"\"src=\"static/lango/resume/image/bitian.png\">开始结束时间</td>"
						+ "							<td class=\"tdedit4\"></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdedit1\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">介绍</td>"
						+ "							<td><div style=\"text-align: left;word-break: break-word;width: 640px;height:auto; margin-left: 90px;\"></div></td>"
						+ "						</tr>"
						+ "						<tr>"
						+ "							<td class=\"tdone\"><img alt=\"\" src=\"static/lango/resume/image/bitian.png\">成果或心得</td>"
						+ "							<td><div style=\"text-align: left;word-break: break-word;width: 640px;height:auto; margin-left: 90px;\"></div></td>"
						+ "						</tr>"
						+ "					</tbody>"
						+ "				</table>"
						+ "		</div>"
						+ "		<div class=\"bottomdiv\" style=\"width: 100px;\">"
						+ "	           <img alt=\"\" style=\"margin-top: 15px;\" onclick=\"oper.editpersonal($(this).parent().parent());\" src=\"static/lango/resume/image/bianji.png\">"
						+ "		</div>"
						+ "     <div style=\"clear: both;\"></div>"
						+ "     <br/><br/>" + "    </div>");
				var adddivcode = $("<div id=\"adddivexperence\" onclick=\"oper.addexperience(this)\" class=\"add\" style=\"display: none;\">"
						+ "	  <div class=\"addtop\">"
						+ "	      <span  class=\"addcontent\">添加活动经历+</span>"
						+ "	   </div>" + "    </div>");
				$(o).prev().after(personalexperience);
				$(o).remove();
				personalexperience.after(personaledit);
				personaledit.after(adddivcode);
				personalexperience.find("input").eq(1).val($("#basemessage1").find("input").eq(0).val());
				personalexperience.slideDown();
				oper.checkinput();//添加绑定事件
			},
			cancelfile : function(o,id,applicant_id){    //删除上传文件
				$.ajax({
					async : false,
					url : "front/resume/cancelfile?id=" + id + "&applicant_id="+applicant_id+"",
					type:'post',
					success:function(data){
						if (data.status == true) {
						if(id==$("#file_upload").find("input").eq(2).val()){//删除第一个文件
							$("#file_upload").find("input").eq(2).val($("#file_upload").find("input").eq(3).val());
							$("#file_upload").find("input").eq(3).val($("#file_upload").find("input").eq(4).val());
							if(!($("#addfile").next().next().next().hasClass("filekuang"))){//有第3个文件情况
								$("#file1").prop('title',$("#file2").prop('title'));
								$("#filecontent1").html($("#filecontent2").html());
								$("#file2").prop('title',$("#file3").prop('title'));
								$("#filecontent2").html($("#filecontent3").html());
								$("#addfile").next().next().next().addClass('filekuang');
								$("#upload").prop('disabled',false);//设置按钮可用
							}else{
							  if(!($("#addfile").next().next().hasClass("filekuang"))){ //有第二个文件，没有第3个文件情况
								$("#file1").prop('title',$("#file2").prop('title'));
								$("#filecontent1").html($("#filecontent2").html());
								$("#addfile").next().next().addClass('filekuang');
							  }else{  //只有第一个文件情况
								  $("#addfile").next().addClass('filekuang');
							  }
							}
							$("#file_upload").find("input").eq(4).val("0");
							$("#file_upload").find("input").eq(1).val($("#file_upload").find("input").eq(1).val()-1);
						}
						if(id==$("#file_upload").find("input").eq(3).val()){//删除第二个文件
							$("#file_upload").find("input").eq(3).val($("#file_upload").find("input").eq(4).val());
							$("#file_upload").find("input").eq(4).val("0");
							if(!($("#addfile").next().next().next().hasClass("filekuang"))){//有第3个文件情况
								$("#file2").prop('title',$("#file3").prop('title'));
								$("#filecontent2").html($("#filecontent3").html());
								$("#addfile").next().next().next().addClass('filekuang');
								$("#upload").prop('disabled',false);//设置按钮可用
						    }else{
						    	$("#addfile").next().next().addClass('filekuang');
						    }
							 $("#file_upload").find("input").eq(1).val($("#file_upload").find("input").eq(1).val()-1);
						}
						if(id==$("#file_upload").find("input").eq(4).val()){//删除第三个文件
							$("#file_upload").find("input").eq(4).val("0");
							$("#file_upload").find("input").eq(1).val($("#file_upload").find("input").eq(1).val()-1);
							$("#addfile").next().next().next().addClass('filekuang');
							$("#upload").prop('disabled',false);//设置按钮可用
						}
					}
					},error:function(){
					}
					});
			},
			checkinput : function() {//重新绑定事件
				$(".checkep").blur(
						function() {//输入为空 区域框变红
							if ($(this).val() == "") {
								$(this).addClass('borderred');
							} else {
								$(this).removeClass('borderred');
							}
						});
				$(".check").on("blur", function() { //输入为空 区域框变红
					if ($(this).val() == "") {
						$(this).addClass('borderred');
					} else {
						$(this).removeClass('borderred');
					}
				});
			}
		};
		$("#telephonecheck").blur(function(){//验证手机
			oper.telephonecheck($(this));
		});
		$("#standby_phonecheck").blur(function(){//验证手机
			oper.telephonecheck($(this));
		});
		$(".checkemail").blur(function(){//验证邮箱
			oper.checkemail($(this));
		});
		$(".check").blur(function() {//输入为空 区域框变红
			if ($(this).val() == "") {
				$(this).addClass('borderred');
			} else {
				$(this).removeClass('borderred');
			}
		});
		$(".checkep").blur(
				function() {////输入为空 区域框变红
					if ($(this).val() == "") {
						$(this).addClass('borderred');
					} else {
						$(this).removeClass('borderred');
					}
				});
		$("#basesave")
				.click(
						function() { //基本信息按钮响应事件
						  var status = checkbeforeform($("#basemessage1 .check"));
							 var email=oper.checkemail($(".checkemail"));
							var telephone=oper.telephonecheck($("#telephonecheck"));
							var standby_phone=oper.telephonecheck($("#standby_phonecheck"));
							if (status == "1" && email=="1" && telephone=="1" && standby_phone=="1") {
								//ajax提交form表单的方式
								$("#baseform")
										.ajaxSubmit(
												{
													type : "post",
													async : false,
													url : "front/resume/baseMessage",
													dataType : "json",
													success : function(data) {
														if (data.status == true) {
															$("#basemessage1").find("input").eq(5).prop("readonly","readonly");//邮箱唯一设为只读
															$("#basemessage1").find("input").eq(5).css("border","0px");//邮箱唯一去框
															$("#basemessage1").find("input").eq(5).removeClass("checkemail");
															$("#basemessage1").find("input").eq(0).val(data.applicant_id);
															$("#educationmessage1").find("input").eq(1).val(data.applicant_id);
															$("#personal_experience1").find("input").eq(1).val(data.applicant_id);
															$("#self_recommendation1").find("input").eq(0).val(data.applicant_id);
															$("#file_upload").find("input").eq(0).val(data.applicant_id);
														}
													}
												});
								$("#basemessage1").css('display', 'none');
								$("#basemessage2").find("td").eq(1).html($("#basemessage1").find("select").eq(0).val());//站点
								$("#basemessage2").find("td").eq(3).html($("#basemessage1").find("select").eq(1).val());//应聘职位
								$("#basemessage2").find("td").eq(5).html($("#basemessage1").find("input").eq(1).val());//姓名
								$("#basemessage2").find("td").eq(7).html($('#basemessage1 input[name="model.sex"]:checked').val());//性别
								$("#basemessage2").find("td").eq(9).html($("#basemessage1").find("input").eq(4).val());//生日
								$("#basemessage2").find("td").eq(11).html($("#basemessage1").find("input").eq(5).val());//邮箱
								$("#basemessage2").find("td").eq(13).html($("#basemessage1").find("input").eq(6).val());//电话
								$("#basemessage2").find("td").eq(15).html(
								$("#basemessage1").find("input").eq(7).val());//备用电话
							   $("#basemessage2").css('display', 'block');
							}
						});
		$("#baseedit").click(function() { //基本信息编辑响应事件
			$("#basemessage1").css('display', 'block');
			$("#basemessage2").css('display', 'none');
		});
		$("#education_save")
				.click(
						function() { //教育背景一二专业保存响应事件
							var status = checkbeforeform($("#educationmessage1 .check"));
							if (status == "1") {
								//ajax提交form表单的方式
								$("#educationform")
										.ajaxSubmit(
												{
													type : "post",
													async : false,
													url : "front/resume/educationdouble",
													dataType : "json",
													success : function(data) {
														if (data.status == true) {
															$("#educationmessage1").find("input").eq(0).val(data.id1);
															$("#educationmessage1").find("input").eq(11).val(data.id2);
															if($("#basemessage2").css("display")=="none"){
																$("#basesave").click();
															}
														}else{
															$("#educationmessage1").find("input").eq(0).val(data.id1);
															$("#educationmessage1").find("input").eq(11).val(data.id2);
															$("#basemessage1").find("input").eq(0).val(data.applicant_id);
															$("#educationmessage1").find("input").eq(1).val(data.applicant_id);
															$("#personal_experience1").find("input").eq(1).val(data.applicant_id);
															$("#self_recommendation1").find("input").eq(0).val(data.applicant_id);
															$("#file_upload").find("input").eq(0).val(data.applicant_id);
															if($("#basemessage2").css("display")=="none"){
																$("#basesave").click();
															}
														}
													}
												});
								$("#educationmessage2").find("td").eq(2).html($("#educationmessage1").find("input").eq(3).val());//学校名称
								$("#educationmessage2").find("td").eq(6).html($("#educationmessage1").find("input").eq(4).val());//专业名称
								$("#educationmessage2").find("td").eq(8).html($("#educationmessage1").find("select").eq(0).val());//学历
								$("#educationmessage2").find("td").eq(10).html($("#educationmessage1").find("input").eq(5).val());//入学时间
								$("#educationmessage2").find("td").eq(12).html($("#educationmessage1").find("input").eq(6).val());//毕业时间
								$("#educationmessage2").find("td").eq(15).html($("#educationmessage1").find("input").eq(7).val());//第二专业学校名称
								$("#educationmessage2").find("td").eq(19).html($("#educationmessage1").find("input").eq(8).val());//第二专业专业名称
								$("#educationmessage2").find("td").eq(21).html($("#educationmessage1").find("select").eq(1).val());//第二专业学历
								$("#educationmessage2").find("td").eq(23).html($("#educationmessage1").find("input").eq(9).val());//第二专业入学时间
								$("#educationmessage2").find("td").eq(25).html($("#educationmessage1").find("input").eq(10).val());//第二专业毕业时间
								$("#educationmessage2").css('display', 'block');
				             $("#educationmessage1").css('display', 'none');
								if ($("#educationmessage2").next().hasClass("add")) {
									$("#adddiveducation").css('display', 'block');
								}
							}
						});
		$("#education_edit").click(
				function() { //教育背景一二编辑响应事件
					if ($("#educationmessage2").next().hasClass("add")) {
						$("#adddiveducation").css('display', 'none');
					}
					$("#educationmessage2").css('display', 'none');
					$("#educationmessage1").css('display', 'block');
				});
		$("#personal_save")
				.click(
						function() { //个人经历一二保存响应事件
							var status = checkbeforeform($("#personal_experience1 .check"));
							var statusta = checktextarea($("#personal_experience1 .checkep"));
							if (status == "1" && statusta == "1") {
								//ajax提交form表单的方式
								$("#personal_form")
										.ajaxSubmit(
												{
													type : "post",
													async : false,
													url : "front/resume/personalExperence",
													dataType : "json",
													success : function(data) {
														if (data.status == true) {
															$("#personal_experience1").find("input").eq(0).val(data.experience_id);
															if($("#basemessage2").css("display")=="none"){
																$("#basesave").click();
															}
															if($("#educationmessage2").css("display")=="none"){
																$("#education_save").click();
															}
															$(".educationdiv").each(function() {
																if($(this).css("display")!="none"){
																$(this).find("button").eq(1).click();
																}
															});
														}else{//用户基本信息表没有填的情况
															$("#personal_experience1").find("input").eq(0).val(data.experience_id);
															$("#basemessage1").find("input").eq(0).val(data.applicant_id);
															$("#educationmessage1").find("input").eq(1).val(data.applicant_id);
															$("#personal_experience1").find("input").eq(1).val(data.applicant_id);
															$("#self_recommendation1").find("input").eq(0).val(data.applicant_id);
															$("#file_upload").find("input").eq(0).val(data.applicant_id);
															if($("#basemessage2").css("display")=="none"){
																$("#basesave").click();
															}
															if($("#educationmessage2").css("display")=="none"){
																$("#education_save").click();
															}
															$(".educationdiv").each(function() {
																if($(this).css("display")!="none"){
																$(this).find("button").eq(1).click();
																}
															});
														}
													}
												});
								$("#personal_experience2").find("td").eq(1).html($("#personal_experience1").find("input").eq(2).val());//经历名称
								$("#personal_experience2").find("td").eq(3).html($("#personal_experience1").find("input").eq(3).val());//经历时间
								$("#personal_experience2").find("td").eq(5).find("div").html($("#personal_experience1").find("textarea").eq(0).val());//经历介绍
								$("#personal_experience2").find("td").eq(7).find("div").html($("#personal_experience1").find("textarea").eq(1).val());//经历成就
								$("#personal_experience2").css('display','block');
								$("#personal_experience1").css('display','none');
								if ($("#personal_experience2").next().hasClass("add")) {
									$("#adddivexperence").css('display', 'block');
								}
							}
						});
		$("#personal_edit").click(
				function() { //个人经历一二编辑响应事件
					if ($("#personal_experience2").next().hasClass("add")) {
						$("#adddivexperence").css('display', 'none');
					}
					$("#personal_experience2").css('display', 'none');
					$("#personal_experience1").css('display', 'block');
				});
		$("#consummation")
				.click(
						function() {
							var statusta = checktextarea($("#self_recommendation1 .checkep"));
							if (statusta == "1") {
								if($("#basemessage2").css("display")=="none"){
									$("#basesave").click();
								}
								if($("#educationmessage2").css("display")=="none"){
									$("#education_save").click();
								}
								if($("#personal_experience2").css("display")=="none"){
									$("#personal_save").click();
								}
								$(".educationdiv").each(function() {
									if($(this).css("display")!="none"){
									$(this).find("button").eq(1).click();
									}
								});
								$(".experiencediv").each(function() {
									if($(this).css("display")!="none"){
									$(this).find("button").eq(1).click();
									}
								});
								//ajax提交form表单的方式
								$("#recommendation_form")
										.ajaxSubmit(
												{
													type : "post",
													async : false,
													url : "front/resume/recommendation",
													dataType : "json",
													success : function(data) {
														if (data.status === true) {
															if($(".borderred").length==0){
																var url = 'front/resume/showIframe/'+ $("#self_recommendation1").find("input").eq(0).val();
																var title = '提示信息';
																Iframe(url, 550,260, title,false,false,false,EmptyFunc);
															}
														}else{
															$("#basemessage1").find("input").eq(0).val(data.applicant_id);//基本信息表未填写
															$("#educationmessage1").find("input").eq(1).val(data.applicant_id);
															$("#personal_experience1").find("input").eq(1).val(data.applicant_id);
															$("#self_recommendation1").find("input").eq(0).val(data.applicant_id);
															$("#file_upload").find("input").eq(0).val(data.applicant_id);
														}
													   $("#self_recommendation2").find("td").eq(1).find("div").html($("#self_recommendation1").find("textarea").eq(0).val());
												      $("#self_recommendation2").find("td").eq(3).find("div").html($("#self_recommendation1").find("textarea").eq(1).val());
												      $("#self_recommendation2").find("td").eq(5).find("div").html($("#self_recommendation1").find("textarea").eq(2).val());
												  	if(!($("#addfile").next().hasClass("filekuang"))){//文件上传处理
												  		$("#file4").prop('title',$("#file1").prop('title'));
												  		$("#filecontent4").html($("#filecontent1").html());
												  		$("#file4").parent().removeClass('filekuang'); 
												  	}else{
												  		$("#file4").parent().addClass('filekuang');
												  	}
													if(!($("#addfile").next().next().hasClass("filekuang"))){
												  		$("#file5").prop('title',$("#file2").prop('title'));
												  		$("#filecontent5").html($("#filecontent2").html());
												  		$("#file5").parent().removeClass('filekuang');
												  	}else{
												  		$("#file5").parent().addClass('filekuang');
												  	}
													if(!($("#addfile").next().next().next().hasClass("filekuang"))){
												  		$("#file6").prop('title',$("#file3").prop('title'));
												  		$("#filecontent6").html($("#filecontent3").html());
												  		$("#file6").parent().removeClass('filekuang');
												  	}else{
												  		$("#file6").parent().addClass('filekuang');
												  	}
												      $("#self_recommendation2").css('display','block');
												      $("#self_recommendation1").css('display','none');
													}
												});
							}
						});
		$("#recommendation_edit").click(function() {
			$("#self_recommendation2").css('display', 'none');
			$("#self_recommendation1").css('display', 'block');
		});
		$("#file_upload").submit(
				function() {
					var options = {
						url : "front/resume/upload",
						success : function(data) {
							var file = $("#fileinput"); //清除文件上传的内容
							file.after(file.clone().val("")); 
							file.remove(); 
							if(data.status === true){
								$("#file_upload").find("input").eq(0).val(data.applicant_id);
								if(data.count==1){
									$("#file_upload").find("input").eq(1).val(data.count);
									$("#file_upload").find("input").eq(2).val(data.file_id);
									$("#file1").prop('title',data.file_name);
									$("#filecontent1").html(data.file_name);
									$("#addfile").next().removeClass('filekuang');
								}
								if(data.count==2){
									$("#file_upload").find("input").eq(1).val(data.count);
									$("#file_upload").find("input").eq(3).val(data.file_id);
									$("#file2").prop('title',data.file_name);
									$("#filecontent2").html(data.file_name);
									$("#addfile").next().next().removeClass('filekuang');
								}
								if(data.count==3){
									$("#file_upload").find("input").eq(1).val(data.count);
									$("#file_upload").find("input").eq(4).val(data.file_id);
									$("#file3").prop('title',data.file_name);
									$("#filecontent3").html(data.file_name);
									$("#addfile").next().next().next().removeClass('filekuang');
									$("#upload").prop('disabled',true);//由于限制用户只能上传3个文件，当上传了3个时，设置按钮不可用
								}
							}
							if(data.status === false){
								$("#basemessage1").find("input").eq(0).val(data.applicant_id);
								$("#educationmessage1").find("input").eq(1).val(data.applicant_id);
								$("#personal_experience1").find("input").eq(1).val(data.applicant_id);
								$("#self_recommendation1").find("input").eq(0).val(data.applicant_id);
								$("#file_upload").find("input").eq(0).val(data.applicant_id);
								if(data.count==1){
									$("#file_upload").find("input").eq(1).val(data.count);
									$("#file_upload").find("input").eq(2).val(data.file_id);
									$("#file1").prop('title',data.file_name);
									$("#filecontent1").html(data.file_name);
									$("#addfile").next().removeClass('filekuang');
								}
								if(data.count==2){
									$("#file_upload").find("input").eq(1).val(data.count);
									$("#file_upload").find("input").eq(3).val(data.file_id);
									$("#file2").prop('title',data.file_name);
									$("#filecontent2").html(data.file_name);
									$("#addfile").next().next().removeClass('filekuang');
								}
								if(data.count==3){
									$("#file_upload").find("input").eq(1).val(data.count);
									$("#file_upload").find("input").eq(4).val(data.file_id);
									$("#file3").prop('title',data.file_name);
									$("#filecontent3").html(data.file_name);
									$("#addfile").next().next().next().removeClass('filekuang');
									$("#upload").prop('disabled',true);//由于限制用户只能上传3个文件，当上传了3个时，设置按钮不可用
								}
							}
						},
						error : function() {
						}
					};
					$('#file_upload').ajaxSubmit(options);
					return false;
				});
		$("#upload").click(function() { //文件上传响应事件
			//ajax提交form表单的方式
			$("#file_upload").submit();
		});
	});
	function yulang(link){
		var id = $('#basemessage1').find('input').eq(0).val();
		var url =link+"front/resume/detail?id=" + id + "";
		window.open(url);//新打开一个窗口
	}