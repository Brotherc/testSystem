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


<title>修改考试学生</title>
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
  function ksglStudentSave(){
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
	  jquerySubByFId('ksglStudentForm',ksglStudentSave_callback,toLogin,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function ksglStudentSave_callback(data,add_callback){
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
	    $('#xuuid').combobox({
	    	onSelect: function(data){
	    		var xuuid=data.uuid;
	    		//根据课程名称查询对应章节信息
	    	    $('#zyname').combobox('reload','${baseurl}zy/jsonList.action?zyCustom.xuuid='+xuuid);
	    	}
	    });
	    
		 $('#xuuid').combobox('setValues', '${ksglStudentCustom.xiuuid}');
		 $('#zyname').combobox('setValues', '${ksglStudentCustom.zyname}');
		 $('#njuuid').combobox('setValues', '${ksglStudentCustom.njuuid}');
	});
</script>
</head>
<body>


<form id="ksglStudentForm" action="${baseurl}ksglStudent/edit.action" method="post">
  <input type="hidden" id="ksglStudentUuid" name="uuid" value="${ksglStudentCustom.uuid}" />
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

   <TBODY>
   		<TR>
			<TD background=images/r_0.gif width="100%">
				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;考试学生信息</TD>
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
							<TD height=30 width="15%" align=right >学号：</TD>
							<TD class=category width="35%">
								<input type="text" class="easyui-textbox" data-options="required:true" id="sysuser_userid" name="sysuserCustom.userid" value="${ksglStudentCustom.userid }" />
							</TD>	
						</TR>
						<tr>
							<TD height=30 width="15%" align=right >系名称：</TD>
							<TD class=category width="35%">
								<input id="xuuid" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}xi/jsonList.action',valueField:'uuid',textField:'name'" name="sysuserCustom.xuuid" >
							</TD>						
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >专业名称：</TD>
							<td>
								<input id="zyname" class="easyui-combobox" data-options="required:true,editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="classCustom.zyname" >
							</TD>						
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >年级：</TD>
							<TD class=category width="35%">
								<input id="njuuid" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}nj/jsonList.action',valueField:'uuid',textField:'njnane'" name="classCustom.njuuid" >
							</TD>						
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >班级：</TD>
							<TD class=category width="35%">
									<input type="text" class="easyui-textbox" data-options="required:true" id="class_name" name="classCustom.classname" value="${ksglStudentCustom.classname }"  />
							</TD>						
						</tr>
							
						<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="ksglStudentSave()">提交</a>
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