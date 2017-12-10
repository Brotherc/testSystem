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
<title>修改用户</title>
<script type="text/javascript">
  function sysusersave(){
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});	
	  jquerySubByFId('userform',sysusersave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function sysusersave_callback(data){
	 
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

		    $('#roleList').combobox({
	            url: '${baseurl}sysuser/type.action',//对应的ashx页面的数据源 
	            valueField: 'remark',//绑定字段ID  
	            textField: 'info',//绑定字段Name
	            panelHeight: 'auto',//自适应
	            required:true,
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
	        
	  
	  $('#gender').combobox('setValues', '${sysuserCustom.gender}');
	  $('#xuuid').combobox('setValues', '${sysuserCustom.xuuid}');
	  
  });
</script>
</head>
<body>


<form id="userform" action="${baseurl}sysuser/edit.action" method="post">
<!-- 更新用户的id -->
<input type="hidden" name="uuid" value="${sysuserCustom.uuid}"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

	<TBODY>
		<TR>
   			<TD background=images/r_0.gif width="100%">
   				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;系统用户信息</TD>
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
							<TD height=30 width="15%" align=right >用户账号：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_userid" name="sysuserCustom.userid" value="${sysuserCustom.userid}" />
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="sysuser_useridTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >用户密码：</TD>
							<TD class=category width="35%">
								<div>
									如果要修改密码请在此输入<br/>
									<input class="easyui-textbox" type="password" id="sysuser_password" name="sysuserCustom.pwd" value="${sysuserCustom.pwd}"  />
								</div>
								<div id="sysuser_passwordTip"></div>
							</TD>
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >姓名：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_name" name="sysuserCustom.name" value="${sysuserCustom.name }"/>
								</div>
								<div id="sysuser_nameTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >用户名称：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_username" name="sysuserCustom.username" value="${sysuserCustom.username }" />
								</div>
								<div id="sysuser_usernameTip"></div>
							</TD>
						</TR>
						
						<TR>
							<TD height=30 width="15%" align=right >地址：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_addr" name="sysuserCustom.addr" value="${sysuserCustom.addr}"/>
								</div>
								<div id="sysuser_addrTip"></div>
							</TD>
							<TD height=30 width="15%" align=right>用户类型：</TD>
							
							<TD class=category width="35%">
								<div>
     								<input id="roleList" class="easyui-combobox" name="roleList" />
								</div>
							</TD>
						</TR>
						
						
						<TR>
							<TD height=30 width="15%" align=right >联系电话：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_tele" name="sysuserCustom.tele" value="${sysuserCustom.tele}" />
								</div>
								<div id="sysuser_teleTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >邮箱：</TD>
							<TD class=category width="35%">
								<div>
									<input class="easyui-textbox" type="text" id="sysuser_email" name="sysuserCustom.email" value="${sysuserCustom.email}" />
								</div>
								<div id="sysuser_emailTip"></div>
							</TD>
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >出生日期：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" class="easyui-datebox" id="date" name="sysuserCustom.birthdayView" data-options="editable:false" value="${sysuserCustom.birthdayView }">
								</div>
								<div id="sysuser_birthdayTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >性别：</TD>
							<td class=category>
								<input class="easyui-combobox" id="gender"
								data-options="editable:false,data: [{
										key: '2',
										value: '女'
									},{
										key: '1',
										value: '男'
									}],valueField:'key',textField:'value'" 
								name="sysuserCustom.gender" >
							</TD>
						</TR>
							
						<TR>
							<TD height=30 width="15%" align=right >单位名称(系)：</TD><!-- 用处：根据名称获取单位id -->
							<TD class=category width="35%">
								<input id="xuuid" class="easyui-combobox" data-options="editable:false,url:'${baseurl}xi/jsonList.action',valueField:'uuid',textField:'name'" name="sysuserCustom.xuuid" >
							</TD>
							<TD height=30 width="15%" align=right>用户状态：</TD>
							<TD class=category width="35%">
								<input type="radio" name="sysuserCustom.userstate" value="1" <c:if test="${sysuserCustom.userstate=='1'}">checked</c:if>/>正常
								<input type="radio" name="sysuserCustom.userstate" value="2" <c:if test="${sysuserCustom.userstate=='2'}">checked</c:if>/>暂停
							</TD>
						</TR>
						
						<tr>
							<td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="sysusersave()">提交</a>
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

var s="$('#roleList').combobox('setValues', "+"${roleList}"+")";
setTimeout(s, 500);
</script>
</body>
</html>