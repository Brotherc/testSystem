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
function checkTime(i){
	if(i<10){
		i="0"+i;
	}
	return i;
}
$(function(){
	var time="${time}";
	var leftTime =parseInt(time);
	
	
	var h = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
	var m = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟
	var s = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数
	h = checkTime(h); 
	m = checkTime(m); 
	s = checkTime(s);
	$('#time').html(h+"小时" + m+"分"+s+"秒");
	if(leftTime!=0)
		leftTime=leftTime-1000;
	
	setInterval(function(){
		var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
		var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟
		var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数
		hours = checkTime(hours); 
		minutes = checkTime(minutes); 
		seconds = checkTime(seconds);
		$('#time').html(hours+"小时" + minutes+"分"+seconds+"秒");
		if(leftTime!=0)
			leftTime=leftTime-1000;
	},1000); 
		
});
function ks(){
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
	  jquerySubByFId('kssjform',ks_callback,null);
	  
}
//ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
function ks_callback(data){
	  //message_alert(data);  
	  parent.closetabwindow();

}
</script>

</head>
<body>
<form id="kssjform" action="${baseurl}sjTktSubmit.action" method="post">
	<input type="hidden" name="sjuuid" value="${sjCustom.uuid }" />
	<input type="hidden" name="ksgluuid" value="${ksgluuid }" />
${sjCustom.name }<br>
<c:if test="${not empty tktList }">
<c:forEach var="item" items="${tktList}">
<div id="tkt_${item.key}">

							${item.key}:${item.value.content }<br>

<c:forEach var="i" begin="1" end="${item.value.answernum }"> 
<input class="easyui-textbox" type="text" value="" id="${item.key-1}_${i-1}" name="tktList[${item.key-1 }][${i-1 }]"> 
</c:forEach>

</div>
</c:forEach>

<br>
<br>
</c:if>



<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="ks()">保存</a>

</form>

<c:if test="${not empty tktDa }">
<c:forEach items="${tktDa }" var="item">
<c:forEach var="it" items="${item.value }" varStatus="vs" >
<script type="text/javascript">

setTimeout(function(){
	$("#"+"${item.key-1}"+"_"+"${vs.index}").textbox("setValue","${it}");
}, 500);
</script>
</c:forEach>

</c:forEach>
</c:if>

</body>
</html>