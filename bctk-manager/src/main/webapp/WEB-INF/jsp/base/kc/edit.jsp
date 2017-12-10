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
<title>修改课程</title>
<script type="text/javascript">
  function kcsave(){
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});	
	  jquerySubByFId('kcform',kcsave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function kcsave_callback(data){
	 
 	  message_alert(data);
 	  
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
 		 setTimeout("parent.window.location.reload()", 2000);
 	  }
	  
  }
  $(function(){
	     var buttons = $.extend([], $.fn.datebox.defaults.buttons);  
	     buttons.splice(1, 0, {  
	         text: '清除',  
	         handler: function (target) {  
	             $(target).datebox("setValue","");  
	         }  
	     });  
	     $('.easyui-datebox').datebox({  
	         buttons: buttons  
	     }); 
	     
		    $('#teacher').combobox({
	            url: '${baseurl}sysuser/jsonList.action?dictcode=1',//对应的ashx页面的数据源 
	            valueField: 'uuid',//绑定字段ID
	            textField: 'userid',//绑定字段Name
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
	                //$('#teacher').combobox('setValues', ['27','28']);
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
		    
		    
	     
	});
</script>
</head>
<body>


<form id="kcform" action="${baseurl}kc/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${kcCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;课程信息</TD>
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
							<TD class=category width="80%">
								<div>
									<input type="text" class="easyui-textbox" data-options="required:true" id="kc_name" name="kcCustom.name" value="${kcCustom.name }"/>
								</div>
								<div id="kc_nameTip"></div>
							</TD>
						</TR>
						
						<TR>
							<TD height=30 width="15%" align=right >创建日期：</TD>
							<TD class=category width="80%">
								<div>
									<input type="text" class="easyui-datebox" id="date" name="kcCustom.createtimeView" data-options="editable:false" value="${kcCustom.createtimeView }">
								</div>
							</TD>
						</TR>
						
						<TR>
							<TD height=30 width="15%" align=right>专业名称：</TD>
							<TD class=category width="80%">
								<div>
									
									<c:forEach items="${zyList }" var="zy">
										<input type="checkbox" name="zyList" value="${zy.uuid }" <c:if test="${fn:contains(zyuuids, zy.uuid)}">checked</c:if>  >${zy.name }
									</c:forEach>
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
							</TD>							
						</TR>
						<tr>
							<TD height=30 width="15%" align=right>任课教师：</TD>
							<TD class=category width="80%">
								<div>
     								<input id="teacher" class="easyui-combobox" name="teacherList" />
								</div>
							</TD>
						</tr>
						<tr>
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="kcsave()">提交</a>
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

var s="$('#teacher').combobox('setValues', "+"${teacherList}"+")";
setTimeout(s, 500);
</script>

</body>
</html>