<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/jquery-easyui-1.4.1/styles/default.css">

<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js2.jsp"%>
<title>添加用户</title>
<script type="text/javascript">
//回登录页面
function toLogin(){
	
	if(parent.parent.parent){
		parent.parent.parent.location='${baseurl}login.action';
	}else if(parent.parent){
		parent.parent.location='${baseurl}login.action';
	}else if(parent){
		parent.location='${baseurl}login.action';
	}else{
		window.location='${baseurl}login.action';
	}  
	//window.location='${baseurl}login.action';
}
  function sysusersave(){
	  //准备使用jquery 提供的ajax Form提交方式
	  //将form的id传入，方法自动将form中的数据组成成key/value数据，通过ajax提交，提交方法类型为form中定义的method，
	  //使用ajax form提交时，不用指定url，url就是form中定义的action
	  //此种方式和原始的post方式差不多，只不过使用了ajax方式
	  
	  //第一个参数：form的id
	  //第二个参数：sysusersave_callback是回调函数，sysusersave_callback当成一个方法的指针
	  //第三个参数：传入的参数， 可以为空
	  //第四个参数：dataType预期服务器返回的数据类型,这里action返回json
	  //根据form的id找到该form的action地址
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});
 		var val=$.trim($("#zyname").combobox("getText"));
 		$("#zyname").combobox("setValue",val);
	  jquerySubByFId('userform',sysusersave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function sysusersave_callback(data){
 	  message_alert(data);
 		 
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
 		 setTimeout("parent.window.location.reload()", 2000);
 	  }
 	  
	 // alert(data.message);
	  /* if(data.type=='0'){
		  $.messager.alert('提示信息',data.message,'success');
	  }else{
		  $.messager.alert('提示信息',data.message,'error');
	  } */
	 
	  //action返回的是json数据
	  //如果是成功显示一个对号
	  
	  //如果是错误信息
	  
	//提交结果类型
	//data中存放的是action返回Resultinfo，有一个type
/* 		var type=data.resultInfo.type;
		//结果提示信息
		var message=data.resultInfo.message;
		//alert(message);
		if(type==0){
			//如果type等于0表示失败，调用 jquery easyui的信息提示组件
			$.messager.alert('提示信息',message,'error');
		}else if(type==1){
			$.messager.alert('提示信息',message,'success');
		}else if(type==2){
			$.messager.alert('提示信息',message,'warning');
		}else if(type==3){
			$.messager.alert('提示信息',message,'info');
		} */
  }
 $(function(){
        var buttons = $.extend([], $.fn.datebox.defaults.buttons);  
        buttons.splice(1, 0, {  
            text: '清除',  
            handler: function (target) {  
                $(target).datebox("setValue","");  
            }  
        });  
        $('.easyui-datebox').datebox({  
            buttons: buttons  
        }); 
        
	    $('#sysuser_xiname').combobox({
	    	onSelect: function(data){
	    		var xuuid=data.uuid;
	    		//根据课程名称查询对应章节信息
	    	    $('#zyname').combobox('reload','${baseurl}zy/jsonList.action?zyCustom.xuuid='+xuuid);
	    	}
	    });
 });
</script>
</head>
<body>


<form id="userform" action="${baseurl}sysuser/add.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

   <TBODY>
   		<TR>
			<TD background=images/r_0.gif width="100%">
				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;系统用户信息</TD>
							<TD align=right>&nbsp;</TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
			
		<TR>
			<TD>
				<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4 align=center>
					<TBODY>
						<TR>
							<TD height=30 width="15%" align=right >用户账号：</TD>
							<TD class=category width="35%">
								<div>
									<input  class="easyui-textbox" type="text" id="sysuser_userid" name="sysuserCustom.userid" data-options="required:true" />
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="sysuser_useridTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >用户密码：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="password" id="sysuser_password" name="sysuserCustom.pwd" data-options="required:true" />
								</div>
								<div id="sysuser_passwordTip"></div>
							</TD>							
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >姓名：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_name" name="sysuserCustom.name"  />
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="sysuser_nameTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >用户名称：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_username" name="sysuserCustom.username" data-options="required:true" />
								</div>
								<div id="sysuser_usernameTip"></div>
							</TD>
						</TR>							
							
						<TR>
							<TD height=30 width="15%" align=right >地址：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_addr" name="sysuserCustom.addr"  />
								</div>
								<div id="sysuser_addrTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >用户类型：</TD>
							<TD class=category width="35%">
								<div>
									<input id="sysuser_groupid" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}sysuser/type.action',valueField:'dictcode',textField:'info'" name="sysuserCustom.groupid" >
								</div>
								<div id="sysuser_groupidTip"></div>
							</TD>
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >联系电话：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_tele" name="sysuserCustom.tele" />
								</div>
								<div id="sysuser_teleTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >邮箱：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_email" name="sysuserCustom.email" />
								</div>
								<div id="sysuser_emailTip"></div>
							</TD>
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >出生日期：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" class="easyui-datebox" id="date" name="sysuserCustom.birthdayView" data-options="editable:false" }">
								</div>
								<div id="sysuser_birthdayTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >性别：</TD>
							<td class=category>
								<input class="easyui-combobox" id="gender"
								data-options="editable:false,data: [{
										key: '0',
										value: '女'
									},{
										key: '1',
										value: '男'
									}],valueField:'key',textField:'value'" 
								name="sysuserCustom.gender" >
							</TD>
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >单位名称(系)：</TD><!-- 用处：根据名称获取单位id -->
							<TD class=category width="35%">
								<input id="sysuser_xiname" class="easyui-combobox" data-options="editable:false,url:'${baseurl}xi/jsonList.action',valueField:'uuid',textField:'name'" name="sysuserCustom.xuuid" >
							</TD>
							<TD height=30 width="15%" align=right>用户状态：</TD>
							<TD class=category width="35%">
								<c:forEach items="${sysuserStateTypeMap }" var="sysuserStateType">
									<input type="radio" name="sysuserCustom.userstate" value="${sysuserStateType.key }" />${sysuserStateType.value}
								</c:forEach>
							</TD>
						</TR>
						<tr>
							<TD height=30 width="15%" align=right >专业：</TD><!-- 用处：根据名称获取单位id -->
							<td>
								<input id="zyname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="classCustom.zyname" >
							</TD>
							
							<TD height=30 width="15%" align=right >年级：</TD>
							<TD class=category width="35%">
								<input class="easyui-combobox" data-options="editable:false,url:'${baseurl}nj/jsonList.action',valueField:'uuid',textField:'njnane'" name="classCustom.njuuid" >
							</TD>
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >班级：</TD>
							<TD class=category width="35%">
								<input class="easyui-textbox" type="text" id="sysuser_class" name="classCustom.classname"  />
							</TD>						
						</tr>
						
						<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="sysusersave()">提交</a>
								<a id="closebtn"  class="easyui-linkbutton" iconCls="icon-cancel" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
						</tr>
						
					</TBODY>
				</TABLE>
			</TD>
		</TR>
   </TBODY>
</TABLE>
</form>
</body>
</html>