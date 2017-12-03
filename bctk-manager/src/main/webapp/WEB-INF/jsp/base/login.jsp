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
<%@ include file="/WEB-INF/jsp/base/common_js2.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	//*****************表单校验******************
	$.formValidator.initConfig({
		formID : "loginform",
		mode:'AlertTip',
		onError : function(msg) {
			alert(msg);
		},
		onAlert : function(msg) {
			alert(msg);
		}
	});
	$("#userid").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		onError:"请输入用户名"
	});
	$("#password").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		onError:"请输入密码"
	});
	$("#randomcode").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		onError:"请输入验证码"
	});
	//*****************表单校验******************
});

	//校验表单输入
	function checkinput() {
		//return $('#loginform').form('validate');
		return $.formValidator.pageIsValid();
	}

	//登录提示方法
	function loginsubmit() {
		if(checkinput()){//校验表单，如果校验通过则执行jquerySubByFId
			//ajax form提交
			jquerySubByFId('loginform', login_commit_callback,null,'json'); 
		}

	}
	//登录提示回调方法
	function login_commit_callback(data) {
		message_alert(data);
		var type = data.resultInfo.type;
		if (1 == type) {//如果登录成功，这里1秒后执行跳转到首页
			setTimeout("tofirst()", 1000);
		} else {
			//登录错误，重新刷新验证码
			randomcode_refresh();
		}

	}
	//刷新验证码
	//实现思路，重新给图片的src赋值，后边加时间，防止缓存 
	function randomcode_refresh() {
		$("#randomcode_img").attr('src',
				'${baseurl}validatecode.jsp?time' + new Date().getTime());
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
	<FORM id="loginform" name="loginform" action="${baseurl}loginsubmit.action"
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
							<TD>账 号：</TD>
							<TD colSpan="2"><input type="text" id="userid"
								name="userid" style="WIDTH: 130px" value="admin"/></TD>
						</TR>
						<TR>
							<TD>密 码：</TD>
							<TD><input type="password" id="password" name="pwd" style="WIDTH: 130px" />
							</TD>
						</TR>
						<TR>
							<TD>验证码：</TD>
							<TD><input id="randomcode" name="validatecode" size="8" /> <img
								id="randomcode_img" src="${baseurl}validatecode.jsp" alt=""
								width="56" height="20" align='absMiddle' /> <a
								href=javascript:randomcode_refresh()>刷新</a></TD>
						</TR>

						<TR>
							<TD colSpan="2" align="center"><input type="button"
								class="btnalink" onclick="loginsubmit()" value="登&nbsp;&nbsp;录" />
								<input type="reset" class="btnalink" value="重&nbsp;&nbsp;置" /></TD>
						</TR>
					</TBODY>
				</TABLE>

			</DIV>
		</DIV>
	</FORM>
</BODY>
</HTML>
