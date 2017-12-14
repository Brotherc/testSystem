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
<title>学生试卷管理</title>

<script type="text/javascript">
	//datagrid列定义
	var columns_v = [ [{
		field:'ck',
		checkbox:true //显示一个复选框
	},{
		field : 'uuid',
		hidden : true,//该列隐藏
		formatter: function(value,row,index){
			//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
			return '<input type="hidden" name="studentSjList['+index+'].uuid" value="'+value+'" />';
		}
	}, {
		field : 'studentId',//对应json中的key
		title : '学号',
		width : 400
	}, {
		field : 'score',//对应json中的key
		title : '分数 ',
		width : 400
	}, {
		field : 'statusname',//对应json中的key
		title : '状态 ',
		width : 50
	},{
		field : 'detail',//对应json中的key
		title : '答题情况',
		width : 65,
		formatter : function(value, row, index) {
			return "<a href=javascript:studentSjDetail('"+row.uuid+"')>查看</a>";
		}
	},{
		field : 'pScore',//对应json中的key
		title : '手动评分',
		width : 65,
		formatter : function(value, row, index) {
			return "<a href=javascript:pScore('"+row.uuid+"')>评分</a>";
		}
	} ] ];

	var autoPScore = function(){
		_confirm('您确定要自动为选中的试卷评分吗?',null,
		  function(){
			var indexs = [];//定义一个数组准备存放删除记录的序号
			//通过jquery easyui的datagrid的getSelections函数，得到当前所有选中的行(对象数组)
			var rows = $('#studentSjlist').datagrid('getSelections');
			//循环遍历选中行
			for(var i=0;i<rows.length;i++){
				//通过jquery easyui的datagrid的getRowIndex方法得行的序号
				var index=$('#studentSjlist').datagrid('getRowIndex',rows[i]);
				//将选中行的序号放入indexs数组
				indexs.push(index);
			}
			//如果存在选中的行
			if(rows.length>0){
				//将选中的行通过indexs.join(',')，将选中行的序号中间以逗号分隔组成一个字符串，调用$("#indexs").val方法，将这个字符串放入indexs对象
				$("#indexs").val(indexs.join(','));
				//执行ajax的form提交
				jquerySubByFId('studentSjQueryForm', xidel_callback, null);
			}else{
				alert_warn("请选择要评分的题目");
			}
			
		  }
		)
		
	};
	
	//定义 datagird工具
	var toolbar_v = [ {//工具栏
		id : 'btnadd',
		text : '自动评分',
		iconCls : 'icon-add',
		handler : autoPScore
	} ];

	//加载datagrid
	$(function() {
		
		$('#studentSjlist').datagrid({
			title : '学生考试查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}studentSj/query.action?studentSjCustom.ksgluuid=${ksgluuid}',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#studentSjlist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#studentSjlist').datagrid('clearSelections');
			}
		});

	        
	});
	
	//查询方法
	function queryStudentSj(){
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
		var formdata = $("#studentSjQueryForm").serializeJson();
		$('#studentSjlist').datagrid('load',formdata);
	}
	//修改系
	function studentSjDetail(id){
		
		//打开修改窗口
		createmodalwindow("学生试卷答题信息", 1080, 480, '${baseurl}studentSjda/Detail.action?studentSjUuid='+id);
	}
	//删除系方法
	function pScore(id){
		
			createmodalwindow("评分", 1080, 480, '${baseurl}studentSjda/List.action?studentSjUuid='+id);
	}
	//删除系的回调
	function xidel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			queryStudentSj();
		}
	}
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="studentSjQueryForm" action="${baseurl}studentSj/autoPScore.action.action" method="post">
  			<input type="hidden" name="indexs" id="indexs" />
	<!-- 查询条件 -->
	<TABLE class="table_search" align="center">
		<TBODY >
			<TR>
				<TD class="left">学号：</td>
				<td>
				<INPUT class="easyui-textbox" type="text" name="studentSjCustom.studentId" />
				</TD>
				<TD class="left">分数：</TD>
				<td>
				<input type="text" class="easyui-textbox"  name="studentSjCustom.scoreMin"  >
				至
				<input type="text" class="easyui-textbox"  name="studentSjCustom.scoreMax" >
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
					name="studentSjCustom.status" >
				</TD>
				<td><a id="btn" href="#" onclick="queryStudentSj()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
			</TR>


		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY  align="center">
			<TR>
				<TD>
					<table id="studentSjlist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="pScoreForm" action="${baseurl}studentSj/pScore.action" method="post">
  <input type="hidden" id="studentSj_id" name="studentSjUuid" />
</form>
</body>
</html>