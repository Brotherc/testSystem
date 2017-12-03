<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用jquery easy ui的js库及css -->
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/jquery-easyui-1.4.1/styles/default.css">

<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js2.jsp"%>
<title>考试</title>

<script type="text/javascript">
function sjSubmit(){

	//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
	_confirm('您确认提交试卷吗？',null,function (){

		//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
		//使用ajax的from提交执行删除
		//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
		//第三个参数是url的参数
		//第四个参数是datatype，表示服务器返回的类型
		jquerySubByFId('kssjform',ks_callback,null,"json");
		
		
	});
}

$(function(){
		sjSubmit();
});

function toFirst(){
	
	if(parent.parent.parent){
		parent.parent.parent.location='${baseurl}first.action';
	}else if(parent.parent){
		parent.parent.location='${baseurl}first.action';
	}else if(parent){
		parent.location='${baseurl}first.action';
	}else{
		window.location='${baseurl}first.action';
	}  
	//window.location='${baseurl}login.action';
}

//ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
function ks_callback(data){
	  //message_alert(data);  
	  //parent.closetabwindow();
	  toFirst();

}
</script>

</head>
<body>
<form id="kssjform" action="${baseurl}sjSubmit.action" method="post">
	<input type="hidden" name="sjuuid" value="${sjid }" />
	<input type="hidden" name="ksgluuid" value="${ksgluuid }" />
</form>


</body>
</html>