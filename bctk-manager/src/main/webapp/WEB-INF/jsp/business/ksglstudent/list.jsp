<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用jquery easy ui的js库及css -->
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/jquery-easyui-1.4.1/styles/default.css">
<%-- <LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css"> --%>
<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<script type="text/javascript" src="${baseurl}js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${baseurl}js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseurl}js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${baseurl}js/jquery.form.min.js"></script>

<script type="text/javascript" src="${baseurl}js/custom.jquery.form.js"></script>
<script type="text/javascript" src="${baseurl}js/custom.box.main.js"></script>
<script type="text/javascript" src="${baseurl}js/jquery.ajax.custom.extend.js"></script>
<title>考试学生管理</title>

<script type="text/javascript">
	//datagrid列定义
	var columns_v = [ [ {
		field : 'studentId',//对应json中的key
		title : '学号',
		width : 100
	},{
		field : 'studentName',//对应json中的key
		title : '姓名',
		width : 100
	},{
		field : 'xiname',//对应json中的key
		title : '系',
		width : 100
	}, {
		field : 'zyname',//对应json中的key
		title : '专业',
		width : 100
	}, {
		field : 'statusname',//对应json中的key
		title : '状态',
		width : 100
	},{
		field : 'njname',//对应json中的key
		title : '年级',
		width : 100
	},{
		field : 'classname',//对应json中的key
		title : '班级',
		width : 100
	},{
		field : 'option1',//对应json中的key
		title : '操作1',
		width : 65,
		formatter : function(value, row, index) {
			return "<a href=javascript:editKsglStudent('"+row.uuid+"')>修改</a>";
		}
	},{
		field : 'option2',//对应json中的key
		title : '操作2',
		width : 65,
		formatter : function(value, row, index) {
			return "<a href=javascript:deleteKsglStudent('"+row.uuid+"')>删除</a>";
		}
	} ] ];

	//定义 datagird工具
	var toolbar_v = [ {//工具栏
		id : 'btnadd',
		text : '添加考试学生',
		iconCls : 'icon-add',
		handler : function() {
			//打开一个窗口，用户添加页面
			//参数：窗口的title、宽、高、url地址
			createmodalwindow("可添加考试学生", 1050, 570, '${baseurl}ksglStudentAddList.action?ksgluuid=${ksgluuid}');
		}
	},{//工具栏
		id : 'btnadd',
		text : '自定义添加考试学生',
		iconCls : 'icon-add',
		handler : function() {
			//打开一个窗口，用户添加页面
			//参数：窗口的title、宽、高、url地址
			createmodalwindow("自定义添加学生信息", 900, 570, '${baseurl}ksglStudentInput.action?ksgluuid=${ksgluuid}');
		}
	} ];

	//加载datagrid
	$(function() {
		
		$('#ksglstudentlist').datagrid({
			title : '考试学生查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}ksglStudent/query.action?ksglStudentCustom.ksgluuid=${ksgluuid}',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#ksglstudentlist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#ksglstudentlist').datagrid('clearSelections');
			}
		});
		  
	    $('#xuuid').combobox({
	    	onSelect: function(data){
	    		var xuuid=data.uuid;
	    		//根据课程名称查询对应章节信息
	    	    $('#zyname').combobox('reload','${baseurl}zy/jsonList.action?zyCustom.xuuid='+xuuid);
	    	}
	    });
	});
	
	//查询方法
	function queryKsglStudent(){
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
 		var val=$.trim($("#zyname").combobox("getText"));
 		$("#zyname").combobox("setValue",val);
		var formdata = $("#ksglStudentQueryForm").serializeJson();
		$('#ksglstudentlist').datagrid('load',formdata);
	}
	//修改系
	function editKsglStudent(id){
		
		//打开修改窗口
		createmodalwindow("考试学生信息导入", 850, 520, '${baseurl}ksglStudentEdit.action?uuid='+id);
	}
	//删除系方法
	function deleteKsglStudent(id){

		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认删除吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#delete_id").val(id);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('ksglStudentDeleteForm',ksglStudentDel_callback,null,"json");
			
			
		});
	}
	//删除系的回调
	function ksglStudentDel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			window.location.reload();
		}
	}
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="ksglStudentQueryForm">
	<!-- 查询条件 -->
	<TABLE class="table_search" align="center">
		<TBODY >
			<TR>
				<TD class="left">学号：</td>
				<td>
				<INPUT class="easyui-textbox" type="text" name="ksglStudentCustom.studentId" />
				</TD>
				<TD class="left">姓名：</td>
				<td>
				<input id="studentname" class="easyui-textbox" name="ksglStudentCustom.studentName" >
				</TD>
				<TD class="left">系：</td>
				<td>
					<input id="xuuid" class="easyui-combobox" data-options="editable:false,url:'${baseurl}xi/jsonList.action',valueField:'uuid',textField:'name'" name="ksglStudentCustom.xiuuid" >
				</TD>
				<TD class="left">班级：</td>
				<td>
				<input id="classname" class="easyui-textbox" name="ksglStudentCustom.classname" >
				</TD>
			</TR>
			<tr>
				<TD class="left">专业：</td>
				<td>
					<input id="zyname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="ksglStudentCustom.zyname" >
				</TD>
				<TD class="left">年级：</td>
				<td>
				<input id="njname" class="easyui-textbox" name="ksglStudentCustom.njname" >
				</TD>

				<TD class="left">状态：</td>
				<td>
					<input class="easyui-combobox" 
					data-options="editable:false,data: [{
							key: '',
							value: '请选择',
							selected:'true'
						},{
							key: '1',
							value: '待考'
						},{
							key: '2',
							value: '已考'
						},{
							key: '3',
							value: '进行中'							
						}],valueField:'key',textField:'value'" 
					name="ksglStudentCustom.status" >
				</TD>
				<td><a id="btn" href="#" onclick="queryKsglStudent()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
			</tr>

		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY  align="center">
			<TR>
				<TD>
					<table id="ksglstudentlist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="ksglStudentDeleteForm" action="${baseurl}ksglStudent/delete.action" method="post">
  <input type="hidden" id="delete_id" name="uuid" />
  <input type="hidden" id="delete_ksglid" name="ksgluuid" value="${ksgluuid}"/>
</form>
</body>
</html>