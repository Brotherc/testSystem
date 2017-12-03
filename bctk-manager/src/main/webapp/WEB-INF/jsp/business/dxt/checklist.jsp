<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<title>单选题目录审核</title>

<script type="text/javascript">
var width=parent.document.body.clientWidth;
var height=parent.document.body.clientHeight;
var fixWidth=function(percent){
	return (parent.document.body.clientWidth)*percent;
};

	//datagrid列定义
	var columns_v = [ [ {
		field : 'kcname',//对应json中的key
		title : '课程名称 ',
		width : fixWidth(0.08)
	}, {
		field : 'content',//对应json中的key
		title : '题目内容',
		width : fixWidth(0.2)
	}, {
		field : 'optiona',//对应json中的key
		title : '选项A ',
		width : fixWidth(0.05)
	}, {
		field : 'optionb',//对应json中的key
		title : '选项B ',
		width : fixWidth(0.05)
	}, {
		field : 'optionc',//对应json中的key
		title : '选项C',
		width : fixWidth(0.05)
	}, {
		field : 'optiond',//对应json中的key
		title : '选项D',
		width : fixWidth(0.05)
	}, {
		field : 'answer',//对应json中的key
		title : '答案',
		width : fixWidth(0.05)
	},{
		field : 'ndname',//对应json中的key
		title : '难度类别',
		width : fixWidth(0.05)
	},{
		field : 'teachername',//对应json中的key
		title : '创建用户',
		width : fixWidth(0.05),
	},{
		field : 'statusname',//对应json中的key
		title : '状态',
		width : fixWidth(0.05),
	},{
		field : 'detail',//对应json中的key
		title : '详情',
		width : fixWidth(0.03),
		formatter : function(value, row, index) {
			return '<a href=javascript:tminfo("'+row.uuid+'","'+row.type+'")>查看</a>';
		}
	},{
		field : 'option1',//对应json中的key
		title : '审核',
		width : fixWidth(0.045),
		formatter : function(value, row, index) {
			if(row.status==1){
				//审核通过
				return '<a href=javascript:outcheckdxt("'+row.uuid+'","'+row.type+'")>撤销通过</a>';
			}
			else  if(row.status==2){
				//审核未通过
				return '<a href=javascript:checkdxt("'+row.uuid+'","'+row.type+'")>审核通过</a>';
			}
		}
	} ] ];

	//定义 datagird工具
	var toolbar_v = [ ];

	//加载datagrid

	$(function() {
		$('#dxtlist').datagrid({
			title : '单选题查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}dxt/query.action',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#dxtlist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#dxtlist').datagrid('clearSelections');
			}
		});
		
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
	    	    $('#zsdname').combobox('reload','${baseurl}zsd/jsonList.action?zsdCustom.kcname='+kcname);
	    	}
	    });
	    
	    $('#zsdname').combobox({
	        url: '${baseurl}zsd/jsonList.action',
	        valueField: 'name',
	        textField: 'name',
	        editable: true,
	        mode:'remote',
	    });
	    
	    $('#ndtype').combobox({
	        url: '${baseurl}dxt/ndTypeJsonList.action',
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
	function querydxt(){
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
 		var val=$.trim($("#kcname").combobox("getText"));
 		$("#kcname").combobox("setValue",val);
 		var val=$.trim($("#zsdname").combobox("getText"));
 		$("#zsdname").combobox("setValue",val);
		var formdata = $("#dxtqueryForm").serializeJson();
		$('#dxtlist').datagrid('load',formdata);
	}

	function tminfo(uuid,type){
		
		//打开修改窗口
			var sendUrl = "${baseurl}tm/detail.action?uuid="+uuid+"&&type="+type;
		createmodalwindow("题目详细信息", width*0.6, height*0.7, sendUrl);
	}

	//审核通过题目方法
	function checkdxt(id,type){

		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认要审核通过该题目吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#check_id").val(id);
			$("#check_type").val(type);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('dxtdeleteform',dxtdel_callback,null,"json");
			
			
		});
	}
	
	//审核撤销通过题目方法 
	function outcheckdxt(id,type){

		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认要撤销通过该题目吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#outcheck_id").val(id);
			$("#outcheck_type").val(type);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('outcheckform',dxtdel_callback,null,"json");
			
			
		});
	}
	//删除用户的回调
	function dxtdel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			querydxt();
		}
	}
</script>
<style type="text/css">
.one{
 width: 75px;
}
.two{
 width: 100px;
}
.three{
 width: 230px;
}
.four{
 width: 400px;
}
.dateScope{
 width: 80px;
}
</style>
</head>
<body>

<!-- html的静态布局 -->
<form id="dxtqueryForm">
	<!-- 查询条件 -->
	<TABLE class="table_search" align=center>
		<TBODY>
			<TR>
				<TD class="left">难度类型：</TD>
				<td class="one">
					<input id="ndtype"  name="dxtCustom.ndtype" >
				</TD>
				<TD class="left">知识点名称：</td>
				<td>
					<input  name="dxtCustom.zsdname" id="zsdname">
				</TD>

				<TD class="left">专业名称：</TD>
				<td>
					<input id="zyname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="dxtCustom.zyname" >
				</TD>
				<TD class="left">内容：</TD>
				<td><INPUT class="easyui-textbox" type="text" name="dxtCustom.content"/></TD>

				<td>
					<a id="btn" href="#" onclick="querydxt()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				</td>
			</TR>
			<TR>
				<TD class="left">创建用户：</td>
				<td ><INPUT class="easyui-textbox" type="text" name="dxtCustom.teachername" class="one"/></TD>

				<TD class="left">课程：</td>
				<td>
					<input id="kcname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action',valueField:'name',textField:'name'" name="dxtCustom.kcname" >
				</TD>
				
				<TD class="left">答案：</TD>
				<td><INPUT type="text" class="easyui-textbox" name="dxtCustom.answer"/></TD>
				
				<TD class="left">状态：</TD>
				<td>
				
					<input class="easyui-combobox" 
					data-options="editable:false,data: [{
							key: '',
							value: '请选择',
							selected:'true'
						},{
							key: '1',
							value: '审核通过'
						},{
							key: '2',
							value: '审核未通过'
						}],valueField:'key',textField:'value'" 
					name="dxtCustom.status" >
				</TD>
				
			</TR>
		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY align="center">
			<TR>
				<TD>
					<table id="dxtlist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="dxtdeleteform" action="${baseurl}dxt/check.action" method="post">
  <input type="hidden" id="check_id" name="dxtCustom.uuid" />
  <input type="hidden" id="check_type" name="dxtCustom.type" />
</form>
<form id="outcheckform" action="${baseurl}dxt/outcheck.action" method="post">
  <input type="hidden" id="outcheck_id" name="dxtCustom.uuid" />
  <input type="hidden" id="outcheck_type" name="dxtCustom.type" />
</form>
</body>
</html>