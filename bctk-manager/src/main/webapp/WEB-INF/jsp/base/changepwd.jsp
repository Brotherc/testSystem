<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<TITLE><fmt:message key="title" bundle="${messagesBundle}" /></TITLE>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<LINK rel="stylesheet" type="text/css" href="${baseurl}styles/style.css">
<LINK rel="stylesheet" type="text/css" href="${baseurl}styles/login.css">
<LINK rel="stylesheet" type="text/css"	href="${baseurl}js/easyui/themes/default/easyui.css">
<LINK rel="stylesheet" type="text/css"	href="${baseurl}js/easyui/themes/icon.css">

<STYLE type="text/css">
.btnalink {
	cursor: hand;
	display: block;
	width: 80px;
	height: 29px;
	float: left;
	margin: 12px 28px 12px auto;
	line-height: 30px;
	background: url('${baseurl}images/login/btnbg.jpg') no-repeat;
	font-size: 14px;
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}
</STYLE>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	//*****************表单校验******************
	$.formValidator.initConfig({
		formID : "pwdform",
		mode:'AlertTip',
		onError : function(msg) {
			alert(msg);
		},
		onAlert : function(msg) {
			alert(msg);
		}
	});
	$("#pwd").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		onError:"请输入原始密码"
	});
	$("#newpwd").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		onError:"请输入密码"
	});
	$("#newpwdtwo").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		onError:"请再次输入新密码"
	});
	//*****************表单校验******************
});

	//校验表单输入
	function checkinput() {
		//return $('#loginform').form('validate');
		return $.formValidator.pageIsValid();
	}

	//修改密码方法
	function changepwd() {
		if(checkinput()){//校验表单，如果校验通过则执行jquerySubByFId
			//ajax form提交
			jquerySubByFId('pwdform', changepwd_callback,null,'json'); 
		}

	}
	//修改密码回调方法
	function changepwd_callback(data) {
		message_alert(data);
		var type = data.resultInfo.type;
		if (1 == type) {//如果登录成功，这里1秒后执行跳转到首页
			setTimeout("tologin()", 1000);
		}

	}
	//回登录页面
	function tologin(){
		
		if(parent.parent.parent){
			parent.parent.parent.location='${baseurl}logout.action';
		}else if(parent.parent){
			parent.parent.location='${baseurl}logout.action';
		}else if(parent){
			parent.location='${baseurl}logout.action';
		}else{
			window.location='${baseurl}logout.action';
		}  
		//window.location='${baseurl}logout.action';
	}
	//回首页
	function tofirst(){
		
		if(parent.parent.parent){
			parent.parent.parent.location='${baseurl}first.action';
		}else if(parent.parent){
			parent.parent.location='${baseurl}first.action';
		}else if(parent){
			parent.location='${baseurl}first.action';
		}else{
			window.location='${baseurl}first.action';
		}  
		//window.location='${baseurl}first.action';
	}
</SCRIPT>
</HEAD>
<BODY style="background: #f6fdff url(${baseurl}images/login/bg1.jpg) repeat-x;">
	<FORM id="pwdform" name="pwdform" action="${baseurl}changepwd.action"
		method="post">
		<DIV class="logincon">

			<DIV class="title">
				<IMG alt="" src="${baseurl}images/login/logo.png">
			</DIV>

			<DIV class="cen_con">
				<IMG alt="" src="${baseurl}images/login/bg2.png">
			</DIV>

			<DIV class="tab_con">

				<input type="password" style="display:none;" />
				<TABLE class="tab" border="0" cellSpacing="6" cellPadding="8">
					<TBODY>
						<TR>
							<TD align="right">原始密码：</TD>
							<TD colSpan="2"><input type="password" id="pwd"
								name="pwd" style="WIDTH: 130px" /></TD>
						</TR>
						<TR>
							<TD align="right">新 密 码：</TD>
							<TD><input type="password" id="newpwd" name="newpwd" style="WIDTH: 130px" />
							</TD>
						</TR>
						<TR>
							<TD align="right">再次输入新密码：</TD>
							<TD><input type="password" id="newpwdtwo" name="newpwdtwo" style="WIDTH: 130px" /> </TD>
						</TR>

						<TR>
							<TD colSpan="1" align="center"><input type="button"
								class="btnalink" onclick="changepwd()" value="确&nbsp;&nbsp;认" />
								</TD>
							<TD colSpan="1" align="left"><input type="button"
								class="btnalink" onclick="tofirst()" value="返&nbsp;&nbsp;回" />
								</TD>
						</TR>
					</TBODY>
				</TABLE>

			</DIV>
		</DIV>
	</FORM>
</BODY>
</HTML>
