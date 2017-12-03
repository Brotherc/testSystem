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
<script type="text/javascript" src="${baseurl}js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${baseurl}js/jquery.form.min.js"></script>

<script type="text/javascript" src="${baseurl}js/custom.jquery.form.js"></script>
<script type="text/javascript" src="${baseurl}js/custom.box.main.js"></script>
<script type="text/javascript" src="${baseurl}js/jquery.ajax.custom.extend.js"></script>
<title>用户管理</title>

<script type="text/javascript">
var width=parent.document.body.clientWidth;
var height=parent.document.body.clientHeight;
var fixWidth=function(percent){
	return (parent.document.body.clientWidth)*percent;
};

	//datagrid列定义
	var columns_v = [ [ {
		field : 'userid',//对应json中的key
		title : '账号',
		width : fixWidth(0.05)
	}, {
		field : 'username',//对应json中的key
		title : '名称 ',
		width : fixWidth(0.05)
	}, {
		field : 'name',//对应json中的key
		title : '姓名 ',
		width : fixWidth(0.05)
	}, {
		field : 'addr',//对应json中的key
		title : '地址 ',
		width : fixWidth(0.15)
	}, {
		field : 'email',//对应json中的key
		title : '邮箱 ',
		width : fixWidth(0.1)
	}, {
		field : 'genderView',//对应json中的key
		title : '性别 ',
		width : fixWidth(0.03)
	}, {
		field : 'groupname',//对应json中的key
		title : '用户类型',
		width : fixWidth(0.05)
	}, {
		field : 'birthdayView',//对应json中的key
		title : '出生日期',
		width : fixWidth(0.07)
	}, {
		field : 'tele',//对应json中的key
		title : '联系电话',
		width : fixWidth(0.07)
	},{
		field : 'xname',//对应json中的key
		title : '所在单位',
		width : fixWidth(0.1)
	},{
		field : 'userstateView',//对应json中的key
		title : '状态',
		width : fixWidth(0.03)
	},{
		field : 'option1',//对应json中的key
		title : '操作1',
		width : fixWidth(0.03),
		formatter : function(value, row, index) {
			return "<a href=javascript:edituser('"+row.uuid+"')>修改</a>";
		}
	},{
		field : 'option2',//对应json中的key
		title : '操作2',
		width : fixWidth(0.05),
		formatter : function(value, row, index) {
			return "<a href=javascript:deleteuser('"+row.uuid+"')>删除</a>";
		}
	} ] ];

	//定义 datagird工具
	var toolbar_v = [ {//工具栏
		id : 'btnadd',
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			//打开一个窗口，用户添加页面
			//参数：窗口的title、宽、高、url地址
			createmodalwindow("添加用户信息", width*0.6, height*0.8, '${baseurl}sysuserInput.action');
		}
	} ];

	//加载datagrid

	$(function() {
		$('#sysuserlist').datagrid({
			title : '用户查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}sysuser/query.action',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#sysuserlist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#sysuserlist').datagrid('clearSelections');
			}
		});
		
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
        
	    $('#groupid').combobox({
	        url: '${baseurl}sysuser/type.action',
	        valueField: 'dictcode',
	        textField: 'info',
	        editable: false,
	        loadFilter:function(data){
	        	var obj={};
	        	obj.dictcode="";
	        	obj.info="请选择";
	        	data.splice(0,0,obj);
	        	return data;
	        }
	    });
	});
	
	//查询方法
	function queryuser(){
		//datagrid的方法load方法要求传入json数据，最终将 json转成key/value数据传入action
		//将form表单数据提取出来，组成一个json
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});
		var formdata = $("#sysuserqueryForm").serializeJson();
		$('#sysuserlist').datagrid('load',formdata);
	}
	//修改用户
	function edituser(id){
		
		//打开修改窗口
		createmodalwindow("修改用户信息", width*0.6, height*0.8, '${baseurl}sysuserEdit.action?uuid='+id);
	}
	//删除用户方法
	function deleteuser(id){

		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认删除吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#delete_id").val(id);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('sysuserdeleteform',userdel_callback,null,"json");
			
			
		});
	}
	//删除用户的回调
	function userdel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			queryuser();
		}
	}
</script>
<style type="text/css">
	.table_search input {
		width: 80px;
	}
</style>
</head>
<body>

<!-- html的静态布局 -->
<form id="sysuserqueryForm">
	<!-- 查询条件 -->
	<TABLE class="table_search" align=center>
		<TBODY>
			<TR>
				<TD class="left">用户账号：</td>
				<td ><input class="easyui-textbox" type="text" name="sysuserCustom.userid" /></TD>
				<TD class="left">用户名称：</TD>
				<td><INPUT class="easyui-textbox" type="text" name="sysuserCustom.username" /></TD>
				<TD class="left">姓名：</TD>
				<td><INPUT class="easyui-textbox" type="text" name="sysuserCustom.name" /></TD>

				<TD class="left">出生日期：</TD>
				<td>
				<input type="text" class="easyui-datebox" id="date" name="sysuserCustom.birthdayMinView" data-options="editable:false" >
				至
				<input type="text" class="easyui-datebox" id="date" name="sysuserCustom.birthdayMaxView" data-options="editable:false">
				</TD>

				<TD class="left">用户类型：</TD>
				<td class="one">
					<input id="groupid"  name="sysuserCustom.groupid" >
				</TD>
				<TD class="left">状态：</TD>
				<td>
				
					<input class="easyui-combobox" 
					data-options="editable:false,data: [{
							key: '',
							value: '请选择',
							selected:'true'
						},{
							key: '1',
							value: '正常'
						},{
							key: '2',
							value: '暂停'
						}],valueField:'key',textField:'value'" 
					name="sysuserCustom.userstate" >
				</TD>
			</TR>
			<TR>
				<TD class="left">邮箱：</td>
				<td ><INPUT class="easyui-textbox" type="text" name="sysuserCustom.email" /></TD>
				<TD class="left">联系电话：</TD>
				<td><INPUT class="easyui-textbox" type="text" name="sysuserCustom.tele" /></TD>
				<TD class="left">性别：</TD>
				<td>
					<input class="easyui-combobox" 
					data-options="editable:false,data: [{
							key: '',
							value: '请选择',
							selected:'true'
						},{
							key: '2',
							value: '女'
						},{
							key: '1',
							value: '男'
						}],valueField:'key',textField:'value'" 
					name="sysuserCustom.gender" >
				</TD>
				
				<TD class="left">地址：</TD>
				<td><INPUT class="easyui-textbox" type="text" name="sysuserCustom.addr" /></TD>
				<TD class="left">单位名称：</TD>
				<td><INPUT class="easyui-textbox" type="text" name="sysuserCustom.xname" /></TD>
				<td>
					<a id="btn" href="#" onclick="queryuser()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				</td>
			</TR>
		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY align=center>
			<TR>
				<TD>
					<table id="sysuserlist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="sysuserdeleteform" action="${baseurl}sysuser/delete.action" method="post">
  <input type="hidden" id="delete_id" name="uuid" />
</form>
</body>
</html>