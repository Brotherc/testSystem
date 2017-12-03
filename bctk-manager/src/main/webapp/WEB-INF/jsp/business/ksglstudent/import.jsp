<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!-- 引用jquery easy ui的js库及css -->
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/jquery-easyui-1.4.1/styles/default.css">
<%-- <LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css"> --%>
<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<script type="text/javascript" src="${baseurl}js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${baseurl}js/jquery.form.min.js"></script>

<script type="text/javascript" src="${baseurl}js/custom.jquery.form.js"></script>
<script type="text/javascript" src="${baseurl}js/custom.box.main.js"></script>
<script type="text/javascript" src="${baseurl}js/jquery.ajax.custom.extend.js"></script>
<script type="text/javascript">
$(function(){
	//***********按钮**************
	$('#submitbtn').linkbutton({   
		iconCls: 'icon-ok'  
	});  
	$('#closebtn').linkbutton({   
		iconCls: 'icon-cancel'  
	});
});
//文件导入提交
  function ksglStudentimport(){
	  //准备使用jquery 提供的ajax Form提交方式
	  //将form的id传入，方法自动将form中的数据组成成key/value数据，通过ajax提交，提交方法类型为form中定义的method，
	  //使用ajax form提交时，不用指定url，url就是form中定义的action
	  //此种方式和原始的post方式差不多，只不过使用了ajax方式
	  
	  //第一个参数：form的id
	  //第二个参数：sysusersave_callback是回调函数，sysusersave_callback当成一个方法的指针
	  //第三个参数：传入的参数， 可以为空
	  //第四个参数：dataType预期服务器返回的数据类型,这里action返回json
	  //根据form的id找到该form的action地址
	  jquerySubByFId('ksglStudentImportForm',ksglStudentSave_callback,null,"json");
	  
  }
  function ksglStudentSave_callback(data,add_callback){
		var result = getCallbackData(data);
		_alert(result);//输出信息
 		 
 	  if(data.resultInfo.type=='1'){
 		 //setTimeout("parent.closemodalwindow()", 1500);
 		parent.queryKsglStudent();
 	  }	  

  }
</script>
<style type="text/css">
	.grid {
		BORDER-BOTTOM: #D3D3D3 1px solid; BORDER-LEFT: #D3D3D3 1px solid; BORDER-COLLAPSE: collapse; BORDER-TOP: #D3D3D3 1px solid; BORDER-RIGHT: #D3D3D3 1px solid
	}
	.toptable {
		WIDTH: 100%; BACKGROUND: #eee
	}
	.category {
		 COLOR: #003373; FONT-WEIGHT: bold
	}

</style>

<form id="ksglStudentImportForm" name="ksglStudentImportForm" action="${baseurl}ksglStudent/import.action" method="post" enctype="multipart/form-data">
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#D3D3D3>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;考试学生信息导入</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							
							<TR>
								<TD height=30 align=right>导入说明：</TD>
								<TD >
								1、导入文件为Excel 97-2003版本，文件扩展名为.xls，如果使用高版本的Excel请另存为Excel 97-2003版本。
								<br>2、点击 <a class="blue" href="${baseurl}template/ypxx_template.xls"><u>考试学生信息模板</u></a> 下载，并按照说明录入考试学生信息。
								<br>3、导入文件内容填写完毕请在下方选择导入文件，点击 导入按钮。
								</TD>
							</TR>
							<TR>
								<TD height=30 align=right>选择导入文件</TD>
								<TD style="background:#fff;">
								<input class="easyui-filebox" data-options="buttonText:'选择'" name="ksglStudentImportFile" />					
								</TD>
							</TR>
							<TR>
								
								<TD colspan=2  align=center style="background:#fff;">
									<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="ksglStudentimport()">提交</a>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>


