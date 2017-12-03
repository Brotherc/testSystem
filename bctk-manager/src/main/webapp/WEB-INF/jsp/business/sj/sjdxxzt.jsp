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
	  alert(parent.closetabwindow());
	  parent.closetabwindow();

}
</script>

</head>
<body>
<form id="kssjform" action="${baseurl}ksgl/ks.action" method="post">
	<input type="hidden" name="sjuuid" value="${sjCustom.uuid }" />
	<input type="hidden" name="ksgluuid" value="${ksgluuid }" />
${sjCustom.name }<br>
<c:if test="${not empty dxtList }">
${dxtListTypeId}丶单选题(${sjmb.dxtscore })<br>
							<c:forEach items="${dxtList}" var="dxt" varStatus="vs" > 
							${vs.count}:${dxt.content }<br>
									<input type="hidden" name="dxtList[${vs.count}].type" value="1" />
									<input type="hidden" name="dxtList[${vs.count}].sjtmid" value="${vs.count}" />
									<input type="radio" name="dxtList[${vs.count}].answer" value="A" />A:${dxt.optiona}
									<input type="radio" name="dxtList[${vs.count}].answer" value="B" />B:${dxt.optionb}<br>
									<input type="radio" name="dxtList[${vs.count}].answer" value="C" />C:${dxt.optionc}
									<input type="radio" name="dxtList[${vs.count}].answer" value="D" />D:${dxt.optiond}
							</c:forEach>
<br>
<br>
</c:if>

<c:if test="${not empty dxxztList }">
${dxxztListTypeId}丶多项选择题(${sjmb.dxxztscore })<br>
							<c:forEach items="${dxxztList}" var="dxxzt" varStatus="vs" > 
							${vs.count}:${dxxzt.content }<br>
									<input type="hidden" name="dxxztList[${vs.count}].type" value="2" />
									<input type="hidden" name="dxxztList[${vs.count}].sjtmid" value="${vs.count}" />
									<input type="radio" name="dxxztList[${vs.count}].dxxztAnswerList[0]" value="A" />A:${dxxzt.optiona}
									<input type="radio" name="dxxztList[${vs.count}].dxxztAnswerList[1]" value="B" />B:${dxxzt.optionb}<br>
									<input type="radio" name="dxxztList[${vs.count}].dxxztAnswerList[2]" value="C" />C:${dxxzt.optionc}
									<input type="radio" name="dxxztList[${vs.count}].dxxztAnswerList[3]" value="D" />D:${dxxzt.optiond}<br>
									<input type="radio" name="dxxztList[${vs.count}].dxxztAnswerList[4]" value="E" />C:${dxxzt.optione}
									<input type="radio" name="dxxztList[${vs.count}].dxxztAnswerList[5]" value="F" />D:${dxxzt.optionf}
							</c:forEach>
<br>
<br>
</c:if>

<c:if test="${not empty tktList }">
${tktListTypeId}丶填空题(${sjmb.tktscore })<br>
							<c:forEach items="${tktList}" var="tkt" varStatus="vs" > 
							${vs.count}:${tkt.content }<br>
									<input type="hidden" name="tktList[${vs.count}].type" value="3" />
									<input type="hidden" name="tktList[${vs.count}].sjtmid" value="${vs.count}" />
							答案<input class="easyui-textbox" type="text" name="tktList[${vs.count}].answer">
							</c:forEach>
<br>
<br>
</c:if>


<c:if test="${not empty jdtList }">
${jdtListTypeId}丶简答题(${sjmb.jdtscore })<br>
							<c:forEach items="${jdtList}" var="jdt" varStatus="vs" > 
							${vs.count}:${jdt.content }<br>
									<input type="hidden" name="jdtList[${vs.count}].type" value="4" />
									<input type="hidden" name="jdtList[${vs.count}].sjtmid" value="${vs.count}" />
							答案<input class="easyui-textbox" type="text" name="jdtList[${vs.count}].answer">
							</c:forEach>
<br>
<br>
</c:if>

<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="ks()">保存</a>

</form>
</body>
</html>