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
<title>修改系</title>
<script type="text/javascript">
  function xisave(){
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});	
	  jquerySubByFId('xiform',xisave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function xisave_callback(data){
	 
 	  message_alert(data);
 	  
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
 		 setTimeout("parent.window.location.reload()", 2000);
 	  }
	  
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
 });
</script>
</head>
<body>


<form id="xiform" action="${baseurl}xi/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${xiCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;系信息</TD>
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
							<TD height=30 width="15%" align=right >系名称：</TD>
							<TD class=category width="35%">
								<div>
									<input  class="easyui-textbox" type="text" data-options="required:true" id="xi_name" name="xiCustom.name" value="${xiCustom.name }"/>
								</div>
								<div id="xi_nameTip"></div>
							</TD>
						</TR>
						
						<TR>
							<TD height=30 width="15%" align=right >创建日期：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" class="easyui-datebox" id="date" name="xiCustom.createtimeView" data-options="editable:false" value="${xiCustom.createtimeView }">
								</div>
								<div id="xi_createtimeTip"></div>
							</TD>
						</TR>
						
						<tr>
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="xisave()">提交</a>
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