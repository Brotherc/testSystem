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
$(function(){
	var $radios = $("input:radio");
	// 同时向所有的li元素存储数据
	
	  $('input:radio').click(function(){
		    //
		    var $radio = $(this);
		    // if this was previously checked
		    if ($radio.attr('waschecked') == "1"){
		      $radio.prop('checked', false);
		      $radio.attr("waschecked", "0");
		    } else {
		      $radio.prop('checked', true);
		      $radio.attr('waschecked', "1");
		    }
		});
		//alert($('#nav'));
/* 		var menulist ='';
		menulist +='<ul>';
        	//这里自定义了一些属性存放菜单的内容：title存放菜单名称rel存放菜单地址，这些在属性在点击菜单 时要取出值使用
			menulist += '<li><div>ascasc</div></li> ';

		menulist += '</ul>';
		alert($("#nav >ul"));
 		$('#nav').accordion('add', {
            title: 'sdfsdv',
            content: menulist,//二级菜单的内容
        }); */
/* 		var pp = $('#nav').accordion('getSelected'); // 获取选择的面板 
		alert(pp);
		if (pp){    
		    pp.panel('refresh',menulist);  // 调用'refresh'方法刷新 
		}  */
		//parent.refreshmenu();
		
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