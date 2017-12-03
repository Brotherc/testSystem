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
<title>学生试卷答案管理</title>

<script type="text/javascript">
	//datagrid列定义
	var columns_v = [ [{
		field : 'sjtmid',//对应json中的key
		title : '题号',
		width : 100
	}, {
		field : 'typename',//对应json中的key
		title : '类型 ',
		width : 100
	}, {
		field : 'answer',//对应json中的key
		title : '答案',
		width : 100
	}, {
		field : 'score',//对应json中的key
		title : '得分',
		width : 50
	}, {
		field : 'statusname',//对应json中的key
		title : '状态',
		width : 100
	},{
		field : 'pScore',//对应json中的key
		title : '评分',
		width : 65,
		formatter : function(value, row, index) {
			return "<a href=javascript:pScore('"+row.uuid+"')>评分</a>";
		}
	}  ] ];
	
	function pScore(id){
		createmodalwindow("评分", 950, 220, '${baseurl}studentSjda/pScore.action?studentSjdaUuid='+id);
	}
	var toolbar_v = [ ];
	//加载datagrid
	$(function() {
		var studentSjUuid="${studentSjUuid}";
		var query={
				"studentSjdaCustom.studentsjid":studentSjUuid
			};
		$('#studentSjdalist').datagrid({
			title : '学生考试答案查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}studentSjda/query.action',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			queryParams:query,
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#studentSjdalist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#studentSjdalist').datagrid('clearSelections');
			}
		}); 
	    $('#tmtype').combobox({
	        url: '${baseurl}xitm/typeJsonList.action',
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
	function queryStudentSjda(){
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
		var formdata = $("#studentSjdaQueryForm").serializeJson();
		$('#studentSjdalist').datagrid('load',formdata);
	}
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="studentSjdaQueryForm">
	<!-- 查询条件 -->
	<TABLE class="table_search" align="center">
		<TBODY >
			<tr>
				<TD class="left">题目编号：</td>
				<td>
				<INPUT class="easyui-textbox" type="text" name="studentSjdaCustom.sjtmid" />
				</TD>
				<TD class="left">题目类型：</TD>
				<td class="one">
					<input id="tmtype"  name="studentSjdaCustom.type" >
				</TD>
				<td><a id="btn" href="#" onclick="queryStudentSjda()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
			</tr>
			<TR>

				<TD class="left">分数：</TD>
				<td>
				<input type="text" class="easyui-textbox"  name="studentSjdaCustom.scoreMin"  >
				至
				<input type="text" class="easyui-textbox"  name="studentSjdaCustom.scoreMax" >
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
							value: '未完成评分'
						},{
							key: '2',
							value: '已完成评分'
						}],valueField:'key',textField:'value'" 
					name="studentSjdaCustom.status" >
				</TD>
			</TR>


		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY  align="center">
			<TR>
				<TD>
					<table id="studentSjdalist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="xideleteform" action="${baseurl}xi/delete.action" method="post">
  <input type="hidden" id="delete_id" name="uuid" />
</form>
</body>
</html>