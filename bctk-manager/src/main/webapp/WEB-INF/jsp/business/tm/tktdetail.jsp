<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js2.jsp"%>
<title>修改题目</title>
<script type="text/javascript">
</script>
</head>
<body>


<form id="tktform" action="${baseurl}tkt/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${tktCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;填空题目信息</TD>
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
							<TD height=30 width="15%" align=right >课程名称：</TD>
							<TD class=category width="35%">
								<div>
									${tktCustom.kcname}
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="tkt_kcnameTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >难度类型：</TD>
							<TD class=category width="35%">
								<div>
									${tktCustom.ndname}
								</div>
								<div id="tkt_ndtypeTip"></div>
							</TD>
						</TR>
						<tr>
							<TD height=30 width="15%" align=right >题目内容：</TD>
							<TD class=category width="100%" colspan="3">
								<div>
									${tktCustom.content}
								</div>
								<div id="tkt_contentTip"></div>
							</TD>
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >答案：</TD>
							<TD class=category width="35%">
								<div>
									${tktCustom.answer}
								</div>
								<div id="tkt_answerTip"></div>
							</TD>
							
							<TD height=30 width="15%" align=right >创建用户：</TD>
							<TD class=category width="35%">
								<div>
									${tktCustom.teachername}
								</div>
								<div id="tkt_teachernameTip"></div>
							</TD>							
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >创建日期：</TD>
							<TD class=category width="35%" colspan="3">
								<div>
									${tktCustom.createtimeView}
								</div>
								<div id="tkt_createtimeViewTip"></div>
							</TD>
							
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >知识点：</TD>
							<TD class=category width="35%" colspan="3">
									<c:forEach items="${zsdList }" var="zsd" >
										${zsd.name }&nbsp;
									</c:forEach>
							</TD>
						</tr>
						
						<tr>
							<td colspan=4 align=center class=category>
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