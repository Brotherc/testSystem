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
<title>考试密码</title>
<script type="text/javascript">
  function ksPwd(){
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});	
	  jquerySubByFId('kspwdform',ksPwd_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function ksPwd_callback(data){
	 
 	  message_alert(data);
 	  
 	  if(data.resultInfo.type=='1'){
			parent.closemodalwindow();
 		 //setTimeout("parent.window.location.reload()", 2000);
			var sjuuid=$("#sjid").val();
			var ksgluuid=$("#ksgluuid").val();
			var sendUrl = "${baseurl}ksglKsSj.action";
			//parent.opentabwindow('试卷',sendUrl);//打开一个新标签

			$.ajax({
				url : sendUrl,
				type : 'POST',
				data: "sjTmCustom.sjid="+sjuuid+"&ksgluuid="+ksgluuid,
				dataType : 'json',
				success : function(data) {
					var _menus = data;
					parent.parent.closemenu("考试管理");
					parent.parent.refreshmenu(_menus);//解析json数据，将菜单生成

				},
				error : function(msg) {
					alert('菜单加载异常!');
				}
			});
 	  }
	  
  }
 
</script>
</head>
<body>


<form id="kspwdform" action="${baseurl}ksgl/ksPwd.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="ksgluuid" id="ksgluuid" value="${ksgluuid}"/>
<input type="hidden" name="sjid" id="sjid" value="${sjid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;考试密码</TD>
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
							<TD height=30 width="15%" align=right >密码：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" id="ks_pwd" class="easyui-textbox" data-options="required:true" name="ksPwd" />
								</div>
								<div id="ks_pwdTip"></div>
							</TD>
						</TR>
						
						<tr>
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="ksPwd()">提交</a>
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