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
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${baseurl}js/jquery.form.min.js"></script>

<script type="text/javascript" src="${baseurl}js/custom.jquery.form.js"></script>
<script type="text/javascript" src="${baseurl}js/custom.box.main.js"></script>
<script type="text/javascript" src="${baseurl}js/jquery.ajax.custom.extend.js"></script>
<title>可添加的考试学生管理</title>

<script type="text/javascript">

var ksglStudentAdd = function(){
	//_confirm询问是否继续操作？
	_confirm('您确定要將选择学生分配考试吗?',null,
	//执行添加函数
	  function(){
		//定义一个数组，准备存放选中行的序号
		var indexs=[];
		//获取数据列表中所有选中的行(数组)
		var rows = $('#ksglstudentlist').datagrid('getSelections');
		//便利所有选中的行
		for(var i=0;i<rows.length;i++){
			
			//alert(dataGrid_obj.datagrid('getRowIndex',rows[i]));
			//将返回的选中行的序号加到indexs数组中
			var index = $('#ksglstudentlist').datagrid('getRowIndex',rows[i]);//选中行的下标
			//将选中行的序号设置到数组indexs中
			indexs.push(index);
			//alert(dataGrid_obj.datagrid('getRowIndex',rows[i]));
		}
		//判断如果存在选中的行，indexs数组里边有选中行的序号
		if(rows.length>0){//如果存在选中的行则将indexs数组中的序号格式化为逗号分隔的并赋给indexs控件
			$("#indexs").val(indexs.join(","));//将indexs数组的元素在中间加逗号拼接成一个字符串
		    //提交form，提交数据包括药品信息id(每条记录都 有)，indexs（hidden）
			jquerySubByFId('ksglStudentQueryForm',ksglStudentAdd_callback, null);
		}else{
			//如果没有选中行则提示
			alert_warn("请选择要分配考试的学生");
		}
		
	  } 
	  
	);
	
	
	
}; 

var ksglStudentImport=function(){
	createmodalwindow("考试学生导入", 900, 570, '${baseurl}ksglStudentImport.action');	
};
	//datagrid列定义
	var columns_v = [ [  {
		field:'ck',
		checkbox:true //显示一个复选框
	},{
		field : 'uuid',
		hidden : true,//该列隐藏
		formatter: function(value,row,index){
			//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
			return '<input type="hidden" name="ksglStudentList['+index+'].studentUuid" value="'+value+'" />';
		}
	},{
		field : 'studentId',//对应json中的key
		title : '学号',
		width : 100
	},{
		field : 'studentName',//对应json中的key
		title : '姓名',
		width : 100
	},{
		field : 'xiName',//对应json中的key
		title : '系',
		width : 100
	}, {
		field : 'zyName',//对应json中的key
		title : '专业',
		width : 100
	},{
		field : 'njName',//对应json中的key
		title : '年级',
		width : 100
	},{
		field : 'className',//对应json中的key
		title : '班级',
		width : 100
	} ] ];

	//定义 datagird工具
	var toolbar_v = [ {//工具栏
		id : 'btnadd',
		text : '分配考试',
		iconCls : 'icon-add',
		handler : ksglStudentAdd
	}, {//工具栏
		id : 'btnimport',
		text : '考试学生信息导入',
		iconCls : 'icon-add',
		handler : ksglStudentImport
	} ];
	

		
		function ksglStudentAdd_callback(data) {
			//message_alert(data);
			//改为可以提示错误信息明细
			var result = getCallbackData(data);
			_alert(result);//输出信息
			  if(data.resultInfo.type=='1'){
		 		  setTimeout("parent.closemodalwindow()", 1500);
		 		 setTimeout("refresh()", 1500);
		 	  } 
			/*
			//刷新页面重新查询
			gysypmladdquery();
			
			//添加刷新父窗口代码
			parent.gysypmlquery(); */
		}
		
		function refresh(){
			
			if(parent){
				parent.location.reload();
			}else if(parent.parent){
				parent.parent.location.reload();
			}else if(parent.parent.parent){
				parent.parent.parent.location.reload();
			}else{
				window.location.reload();
			}
			//window.location='${baseurl}login.action';
		}

	//加载datagrid
	$(function() {
		$('#ksglstudentlist').datagrid({
			title : '考试学生查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}ksglStudentAdd/query.action?studentCustom.ksglUuid=${ksgluuid}',//加载数据的连接，引连接请求过来是json数据
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
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="ksglStudentQueryForm" action="${baseurl}ksglStudent/add.action" method="post">
  	<input type="hidden" name="indexs" id="indexs" />
  	<input type="hidden" name="ksgluuid" id="ksgluuid" value="${ksgluuid }"/>
	<!-- 查询条件 -->
	<TABLE class="table_search" align="center">
		<TBODY >
			<TR>
				<TD class="left">学号：</td>
				<td>
				<INPUT class="easyui-textbox" type="text" name="studentCustom.studentId" />
				</TD>
				<TD class="left">姓名：</td>
				<td>
				<input id="studentname" class="easyui-textbox" name="studentCustom.studentName" >
				</TD>
				<TD class="left">系：</td>
				<td>
					<input id="xuuid" class="easyui-combobox" data-options="editable:false,url:'${baseurl}xi/jsonList.action',valueField:'uuid',textField:'name'" name="studentCustom.xiUuid" >
				</TD>
				<td><a id="btn" href="#" onclick="queryKsglStudent()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
			</TR>
			<tr>
				<TD class="left">专业：</td>
				<td>
					<input id="zyname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="studentCustom.zyName" >
				</TD>
				<TD class="left">年级：</td>
				<td>
				<input id="njname" class="easyui-textbox" name="studentCustom.njName" >
				</TD>
				<TD class="left">班级：</td>
				<td>
				<input id="classname" class="easyui-textbox" name="studentCustom.className" >
				</TD>			
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
</body>
</html>