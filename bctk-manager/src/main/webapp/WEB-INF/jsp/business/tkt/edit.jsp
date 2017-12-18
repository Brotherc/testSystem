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
<title>修改题目</title>
<script type="text/javascript">

//取出所有空格
function removeAllSpace(str){
	return str.replace(/\s+/g,"");
}

  function tktsave(){
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});	
 		var val=$.trim($("#kcname").combobox("getText"));
 		$("#kcname").combobox("setValue",val);
		if($("#isprogram").combobox('getValue')==2){
			$("input[name^='tktCustom.answer']").each(function(i){
				var val=removeAllSpace($(this).val());
				$(this).val(val);
			});
		}
	  jquerySubByFId('tktform',tktsave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function tktsave_callback(data){
	 
 	  message_alert(data);
	  
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
  		 //setTimeout("parent.window.location.reload()", 2000);
  		 var val=$.trim($("#kcname").combobox("getValue"));
  		parent.querytktByKc(val);
 	  }
  }
    
  
  $(function(){
	  
	    $('#zyname').combobox({
	    	onSelect: function(data){
	    		var zyname=data.name;
	    		//根据专业名称查询对应课程信息
	    	    $('#kcname').combobox('reload','${baseurl}kc/jsonList.action?kcCustom.zyname='+zyname);
	    	}
	    });
	    
	    
	    $('#kcname').combobox({
	    	onSelect: function(data){
	    		var kcname=data.name;
	    		//根据课程名称查询对应章节信息
	    		$('#zsd').combobox('clear');
	    	    $('#zsd').combobox('reload','${baseurl}zsd/jsonList.action?zsdCustom.kcname='+kcname);
	    	}
	    });


		    $('#zsd').combobox({
	            url: '${baseurl}zsd/jsonList.action?zsdCustom.kcname=${tktCustom.kcname}',
	            valueField: 'uuid',//绑定字段ID
	            textField: 'name',//绑定字段Name
	            panelHeight: 'auto',//自适应
	            editable:false,
	            multiple: true,
	            formatter: function (row) {
	                var opts = $(this).combobox('options');
	                return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
	            },

	            onShowPanel: function () {
	                var opts = $(this).combobox('options');
	                var target = this;
	                var values = $(target).combobox('getValues');
	                $.map(values, function (value) {
	                    var el = opts.finder.getEl(target, value);
	                    el.find('input.combobox-checkbox')._propAttr('checked', true);
	                })
	            },
	            onLoadSuccess: function () {
	                var opts = $(this).combobox('options');
	                var target = this;
	                var values = $(target).combobox('getValues');
	                $.map(values, function (value) {
	                    var el = opts.finder.getEl(target, value);
	                    el.find('input.combobox-checkbox')._propAttr('checked', true);
	                })
	            },
	            onSelect: function (row) {
	                var opts = $(this).combobox('options');
	                var el = opts.finder.getEl(this, row[opts.valueField]);
	                el.find('input.combobox-checkbox')._propAttr('checked', true);
	            },
	            onUnselect: function (row) {
	                var opts = $(this).combobox('options');
	                var el = opts.finder.getEl(this, row[opts.valueField]);
	                el.find('input.combobox-checkbox')._propAttr('checked', false);
	            }
	        });
		  
		  
		    $('#answernum').combobox({
		    	onSelect: function(data){
		    		var length=$(".del").length;
		    		
		    		var num=parseInt(data.value);
		    		var delNum=num-1;
		    		if(length>num){
	 		    		for(var i=0;i<length-num;i++){
			    			$("#content").prev().remove();
			    		} 
		    		}else {
			    		var html='';
			    		for(var i=length+1;i<=num;i++){
			    			html+='<tr class="del">';
			    			var td1_content='<a  class="answer" data="'+i+'"  href="#" >添加答案</a>';
			    			var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
			    			html+=td1;
			    			var td2='<td class="category" colspan="3"></td>';
			    			html+=td2;	    			
			    			html+='</tr>';
			    		}
			    		$("#content").before(html);
			    		$(".answer").linkbutton();
			    		$(".answer").off("click");
			    		$(".answer").on("click", function(){
			    	    	var name="tktCustom.answer"+$(this).attr('data');
			    	    	//var name="tktCustom.answerMap['"+$(this).attr('data')+"'].answer";
			    	    	var input=$('<input type="text" name="'+name+'"/>');
			    	    	var button=$('<a  href="#" >删除</a>');
			    	    	$(this).parent().next().append(input);
			    	    	$(this).parent().next().append(button);
			    	    	button.click(function(){
			    	    		$(this).prev().remove();
			    	    		$(this).remove();
			    	    	});
			    	    	button.linkbutton();
			    	    	input.textbox();
			    		});
		    		}
		    		
		    		//根据课程名称查询对应章节信息

		    	}
		    });
		    
			  $('#ndtype').combobox('setValues', '${tktCustom.ndtype}');
			  $('#kcname').combobox('setValues', '${tktCustom.kcname}');
			  $('#answernum').combobox('setValues', '${tktCustom.answernum}');
			  $('#isprogram').combobox('setValues', '${tktCustom.isprogram}');
}); 
  

</script>
</head>
<body>


<form id="tktform" action="${baseurl}tkt/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${tktCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;填空题目信息</TD>
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
							<TD height=30 width="15%" align=right >课程名称：</TD>
							<td class=category>
								<input id="kcname" class="easyui-combobox" data-options="required:true,editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action?kcCustom.sysuseruuid=${sysuseruuid }',valueField:'name',textField:'name'" name="tktCustom.kcname" >
							</TD>
							<TD height=30 width="15%" align=right >难度类型：</TD>
							<td class=category>
								<input id="ndtype" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}dxt/ndTypeJsonList.action',valueField:'dictcode',textField:'info'" name="tktCustom.ndtype" >
							</TD>
						</TR>
							
						<tr>
							<TD height=30 width="15%" align=right >题目内容：</TD>
				            <td class=category width="100%" colspan="3">
				            	<input class="easyui-textbox" id="tktcontent" name="tktCustom.content" data-options="required:true,multiline:true,validType:'length[0,150]'" style="height:60px;width: 280px;" value="${tktCustom.content}"></input>
				            </td>
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >答案处理方案：</TD>
							<TD class=category width="35%" >
								<div>
								<input id="isprogram" class="easyui-combobox" 
								data-options="editable:false,data: [{
										key: '1',
										value: '去除前后空格'
									},{
										key: '2',
										value: '去除所有空格'
									},],valueField:'key',textField:'value'" 
								name="tktCustom.isprogram" >
								</div>
								<div id="tkt_isprogramTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >填空题空格格式：</TD>
							<TD class=category width="100%" colspan="3">
								___
							</TD>
						</tr>
										
						<tr>
							<TD height=30 width="15%" align=right >空格数：</TD>
							<TD class=category width="35%">
								<div>
								<input id="answernum" class="easyui-combobox" 
								data-options="editable:false,data: [{
										key: '1',
										value: '1'
									},{
										key: '2',
										value: '2'
									},{
										key: '3',
										value: '3'
									},{
										key: '4',
										value: '4'
									},{
										key: '5',
										value: '5'
									}],valueField:'key',textField:'value'" 
								name="tktCustom.answernum" >
								</div>
								<div id="tkt_answerTip"></div>
							</TD>
							
							<TD height=30 width="15%" align=right >创建用户：</TD>
							<TD class=category width="35%">
								${tktCustom.teachername}
								<div id="tkt_teachernameTip"></div>
							</TD>							
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >创建日期：</TD>
							<TD class=category width="35%">
								${tktCustom.createtimeView}
								<div id="tkt_createtimeViewTip"></div>
							</TD>
							
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >知识点：</TD>
							<td>
     						<input id="zsd" class="easyui-combobox" name="zsdList" />
							</td>
						</tr>
						
						<tr id="content">
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="tktsave()">修改</a>
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

<c:if test="${not empty answer1 }">
<script type="text/javascript">
var html='';
html+='<tr class="del">';
var td1_content='<a  class="answer" data="1"  href="#" >添加答案</a>';
var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
html+=td1;
var td2='<td class="category" colspan="3"></td>';
html+=td2;	    			
html+='</tr>';
$("#content").before(html);
$(".answer").linkbutton();
$(".answer").off("click");
$(".answer").on("click", function(){
var name="tktCustom.answer"+$(this).attr('data');
var input=$('<input type="text" name="'+name+'"/>');
var button=$('<a  href="#" >删除</a>');
$(this).parent().next().append(input);
$(this).parent().next().append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
});
</script>
<c:forEach var="answer" items="${answer1 }">
<script type="text/javascript">

var obj=$(".answer").eq(0).parent().next();

var input=$('<input type="text" name="tktCustom.answer1" value="${answer}" />');
var button=$('<a  href="#" >删除</a>');
obj.append(input);
obj.append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
</script>
</c:forEach>
</c:if>



<c:if test="${not empty answer2 }">
<script type="text/javascript">
var html='';
html+='<tr class="del">';
var td1_content='<a  class="answer" data="2"  href="#" >添加答案</a>';
var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
html+=td1;
var td2='<td class="category" colspan="3"></td>';
html+=td2;	    			
html+='</tr>';
$("#content").before(html);
$(".answer").linkbutton();
$(".answer").off("click");
$(".answer").on("click", function(){
	var name="tktCustom.answer"+$(this).attr('data');
var input=$('<input type="text" name="'+name+'"/>');
var button=$('<a  href="#" >删除</a>');
$(this).parent().next().append(input);
$(this).parent().next().append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
});
</script>
<c:forEach var="answer" items="${answer2 }">
<script type="text/javascript">

var obj=$(".answer").eq(1).parent().next();

var input=$('<input type="text" name="tktCustom.answer2" value="${answer}" />');
var button=$('<a  href="#" >删除</a>');
obj.append(input);
obj.append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
</script>
</c:forEach>
</c:if>


<c:if test="${not empty answer3 }">
<script type="text/javascript">
var html='';
html+='<tr class="del">';
var td1_content='<a  class="answer" data="3"  href="#" >添加答案</a>';
var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
html+=td1;
var td2='<td class="category" colspan="3"></td>';
html+=td2;	    			
html+='</tr>';
$("#content").before(html);
$(".answer").linkbutton();
$(".answer").off("click");
$(".answer").on("click", function(){
	var name="tktCustom.answer"+$(this).attr('data');
var input=$('<input type="text" name="'+name+'"/>');
var button=$('<a  href="#" >删除</a>');
$(this).parent().next().append(input);
$(this).parent().next().append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
});
</script>
<c:forEach var="answer" items="${answer3 }">
<script type="text/javascript">

var obj=$(".answer").eq(2).parent().next();

var input=$('<input type="text" name="tktCustom.answer3" value="${answer}" />');
var button=$('<a  href="#" >删除</a>');
obj.append(input);
obj.append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
</script>
</c:forEach>
</c:if>


<c:if test="${not empty answer4 }">
<script type="text/javascript">
var html='';
html+='<tr class="del">';
var td1_content='<a  class="answer" data="4"  href="#" >添加答案</a>';
var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
html+=td1;
var td2='<td class="category" colspan="3"></td>';
html+=td2;	    			
html+='</tr>';
$("#content").before(html);
$(".answer").linkbutton();
$(".answer").off("click");
$(".answer").on("click", function(){
	var name="tktCustom.answer"+$(this).attr('data');
var input=$('<input type="text" name="'+name+'"/>');
var button=$('<a  href="#" >删除</a>');
$(this).parent().next().append(input);
$(this).parent().next().append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
});
</script>
<c:forEach var="answer" items="${answer4 }">
<script type="text/javascript">

var obj=$(".answer").eq(3).parent().next();

var input=$('<input type="text" name="tktCustom.answer4" value="${answer}" />');
var button=$('<a  href="#" >删除</a>');
obj.append(input);
obj.append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
</script>
</c:forEach>
</c:if>


<c:if test="${not empty answer5 }">
<script type="text/javascript">
var html='';
html+='<tr class="del">';
var td1_content='<a  class="answer" data="5"  href="#" >添加答案</a>';
var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
html+=td1;
var td2='<td class="category" colspan="3"></td>';
html+=td2;	    			
html+='</tr>';
$("#content").before(html);
$(".answer").linkbutton();
$(".answer").off("click");
$(".answer").on("click", function(){
	var name="tktCustom.answer"+$(this).attr('data');
var input=$('<input type="text" name="'+name+'"/>');
var button=$('<a  href="#" >删除</a>');
$(this).parent().next().append(input);
$(this).parent().next().append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
});
</script>
<c:forEach var="answer" items="${answer5 }">
<script type="text/javascript">

var obj=$(".answer").eq(4).parent().next();

var input=$('<input type="text" name="tktCustom.answer5" value="${answer}" />');
var button=$('<a  href="#" >删除</a>');
obj.append(input);
obj.append(button);
button.off("click");
button.click(function(){
	$(this).prev().remove();
	$(this).remove();
});
button.linkbutton();
input.textbox();
</script>
</c:forEach>
</c:if>

<script type="text/javascript">

var s="$('#zsd').combobox('setValues', "+"${zsdList}"+")";
setTimeout(s, 500);
</script>

</body>
</html>