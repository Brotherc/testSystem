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


<title>添加班级</title>
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
  function classSave(){
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
	  jquerySubByFId('classform',classSave_callback,toLogin,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function classSave_callback(data,add_callback){
		var result = getCallbackData(data);
		_alert(result);//输出信息
 		 

 	  if(data.resultInfo.type==1){
 		  setTimeout("parent.closemodalwindow()", 1500);
 		 setTimeout("parent.window.location.reload()", 2000);
 	  }
 	
  }
	$(function(){
	    $('#xuuid').combobox({
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


<form id="classform" action="${baseurl}class/add.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

   <TBODY>
   		<TR>
			<TD background=images/r_0.gif width="100%">
				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;班级信息</TD>
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
						<tr>
							<TD height=30 width="15%" align=right >年级：</TD>
							<TD class=category width="35%">
								<input class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}nj/jsonList.action',valueField:'uuid',textField:'njnane'" name="classCustom.njuuid" >
							</TD>						
						</tr>
						<TR>
							<TD height=30 width="15%" align=right >系名称：</TD>
							<TD class=category width="35%">
								<input class="easyui-combobox" id="xuuid" data-options="editable:false,url:'${baseurl}xi/jsonList.action',valueField:'uuid',textField:'name'">
							</TD>							
						</TR>
						<tr>
							<TD height=30 width="15%" align=right >专业名称：</td>
							<td>
								<input id="zyname" class="easyui-combobox" data-options="required:true,editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="classCustom.zyname" >
							</TD>
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >班级范围：</TD>
							<TD class=category width="35%">
									<input type="text" class="easyui-textbox" data-options="required:true" name="classnameStart"  />
									至
									<input type="text" class="easyui-textbox" data-options="required:true" name="classnameEnd"  />
							</TD>						
						</tr>
							
						<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="classSave()">提交</a>
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