<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/jquery-easyui-1.4.1/styles/default.css">

<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js2.jsp"%>
<title>修改考试</title>
<script type="text/javascript">
  function ksglsave(){
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});	
	  jquerySubByFId('ksglform',ksglsave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function ksglsave_callback(data){
	 
 	  message_alert(data);
 	  
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
 		 setTimeout("parent.window.location.reload()", 2000);
 	  }
	  
  }
	$(function(){
		$('#kcname').combobox({  
		    url:'${baseurl}kczy/jsonList.action?kcZyCustom.xuuid='+'${xi.uuid}',    
		    valueField:'kcname',    
		    textField:'kcname',
		   	editable:true
		});
		$('#kcname').combobox('setValues', '${ksglCustom.kcname}');
		
		$('#sjname').combobox({  
		    url:'${baseurl}sj/jsonList.action?sjCustom.xid='+'${xi.uuid}',    
		    valueField:'name',    
		    textField:'name',
		   	editable:true
		});
		$('#sjname').combobox('setValues', '${ksglCustom.sjname}');
		$('#njuuid').combobox('setValues', '${ksglCustom.njuuid}');
	});
</script>
</head>
<body>


<form id="ksglform" action="${baseurl}ksgl/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${ksglCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;考试信息</TD>
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
							<TD height=30 width="15%" align=right >考试开始时间：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" class="easyui-datetimebox" id="date" name="ksglCustom.starttimeView" data-options="editable:false" value="${ksglCustom.starttimeView }">
								</div>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right >考试结束时间：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" class="easyui-datetimebox" id="date" name="ksglCustom.endtimeView" data-options="editable:false" value="${ksglCustom.endtimeView }">
								</div>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right >系：</TD>
							<TD class=category width="35%">
								<div>
									${xi.name }
								</div>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right >课程：</TD>
							<TD class=category width="35%">
								<div>
									<input id="kcname" name="ksglCustom.kcname" >
								</div>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right >试卷：</TD>
							<TD class=category width="35%">
								<div>
									<input id="sjname" name="ksglCustom.sjname" >
								</div>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right >年级：</TD>
							<TD class=category width="35%">
								<input id="njuuid" class="easyui-combobox" data-options="editable:false,url:'${baseurl}nj/jsonList.action',valueField:'uuid',textField:'njnane'" name="ksglCustom.njuuid" value="ksglCustom.njuuid" >
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right >专业：</TD>
							<TD class=category width="35%">
									<c:forEach items="${zyList }" var="zy">
										<input type="checkbox" name="zyList" value="${zy.uuid }" <c:if test="${fn:contains(zyuuids, zy.uuid)}">checked</c:if>  >${zy.name }
									</c:forEach>
							</TD>							
						</TR>
						
						<tr>
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="ksglsave()">提交</a>
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