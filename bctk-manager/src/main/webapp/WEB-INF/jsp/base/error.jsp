<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>失败提示信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${baseurl}styles/body.css" type="text/css" />
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
</script>
	</head>
	<body>
		<table class="submit_hint">
			<tr>
				<td>
					<div id="tabBtnContainer" width="100%">
						<ul id="tabBtnUi">
							<li><a href="#"></a>失败提示信息</li>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td style="font-size: 14px;color:#ff0000;padding-top: 2px;" align='center' valign="middle">
					<img alt="" src="${baseurl}images/info.png" align="middle" height="30" width="30" />
					<span style="vertical-align: middle">${exceptionResultInfo.message}</span>
				</td>
			</tr>
		</table>
		<table width='100%'align="center"> 
			<tr>
				<td width="50%" align="right">
					<input type="button" align="middle" class="button" value="确定" onclick="history.go(-1);" />
				</td>
				<c:if test="${exceptionResultInfo.messageCode==106}">
					<td >
						<input type="button" align="left" class="button" value="返回登录" onclick="toLogin();" />
					</td>
				</c:if>
			</tr>
		</table>
	</body>
</html>