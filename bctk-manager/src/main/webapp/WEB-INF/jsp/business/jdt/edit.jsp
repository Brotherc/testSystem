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
  function jdtsave(){
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
	  jquerySubByFId('jdtform',jdtsave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function jdtsave_callback(data){
	 
 	  message_alert(data);
	  
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
  		 //setTimeout("parent.window.location.reload()", 2000);
  		 var val=$.trim($("#kcname").combobox("getValue"));
  		parent.querydxtByKc(val);
 	  }
  }
  $(function(){
	  
	    $('#kcname').combobox({
	    	onSelect: function(data){
	    		var kcname=data.name;
	    		//根据课程名称查询对应章节信息
	    		$('#zsd').combobox('clear');
	    	    $('#zsd').combobox('reload','${baseurl}zsd/jsonList.action?zsdCustom.kcname='+kcname);
	    	}
	    });
	    
	    $('#zsd').combobox({
            url: '${baseurl}zsd/jsonList.action?zsdCustom.kcname=${jdttCustom.kcname}',
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
	    
		  $('#ndtype').combobox('setValues', '${jdtCustom.ndtype}');
		  $('#kcname').combobox('setValues', '${jdtCustom.kcname}');
});
</script>
</head>
<body>


<form id="jdtform" action="${baseurl}jdt/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${jdtCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;简答题目信息</TD>
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
								<input id="kcname" class="easyui-combobox" data-options="required:true,editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action?kcCustom.sysuseruuid=${sysuseruuid }',valueField:'name',textField:'name'" name="jdtCustom.kcname" >
							</TD>
							<TD height=30 width="15%" align=right >难度类型：</TD>
							<td class=category>
								<input id="ndtype" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}dxt/ndTypeJsonList.action',valueField:'dictcode',textField:'info'" name="jdtCustom.ndtype" >
							</TD>
						</TR>
							
						<tr>
							<TD height=30 width="15%" align=right >题目内容：</TD>
				            <td class=category width="100%" colspan="3">
				            	<input class="easyui-textbox" id="jdtcontent" name="jdtCustom.content" data-options="required:true,multiline:true,validType:'length[0,150]'" style="height:60px;width: 280px;" value="${jdtCustom.content}"></input>
				            </td>
						</tr>
										
						<tr>
							<TD height=30 width="15%" align=right >答案：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" id="jdt_answer" class="easyui-textbox" data-options="required:true" name="jdtCustom.answer" value="${jdtCustom.answer}"/>
								</div>
								<div id="jdt_answerTip"></div>
							</TD>
							
							<TD height=30 width="15%" align=right >创建用户：</TD>
							<TD class=category width="35%">
								${jdtCustom.teachername}
								<div id="jdt_teachernameTip"></div>
							</TD>							
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >创建日期：</TD>
							<TD class=category width="35%">
								${jdtCustom.createtimeView}
								<div id="jdt_createtimeViewTip"></div>
							</TD>
							
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >知识点：</TD>
							<td>
     						<input id="zsd" class="easyui-combobox" name="zsdList" />
							</td>
						</tr>
						
						<tr>
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="jdtsave()">修改</a>
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

<script type="text/javascript">

var s="$('#zsd').combobox('setValues', "+"${zsdList}"+")";
setTimeout(s, 500);
</script>
</body>
</html>