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
<title>专业管理</title>

<script type="text/javascript">
var width=parent.document.body.clientWidth;
var height=parent.document.body.clientHeight;
var fixWidth=function(percent){
	return (parent.document.body.clientWidth)*percent;
};

	//datagrid列定义
	var columns_v = [ [ {
		field : 'name',//对应json中的key
		title : '专业名称',
		width : fixWidth(0.25)
	}, {
		field : 'xiname',//对应json中的key
		title : '系名称 ',
		width : fixWidth(0.25)
	}, {
		field : 'createimeView',//对应json中的key
		title : '创建时间 ',
		width : fixWidth(0.23)
	},{
		field : 'option1',//对应json中的key
		title : '操作1',
		width : fixWidth(0.05),
		formatter : function(value, row, index) {
			return "<a href=javascript:editzy('"+row.uuid+"')>修改</a>";
		}
	},{
		field : 'option2',//对应json中的key
		title : '操作2',
		width : fixWidth(0.05),
		formatter : function(value, row, index) {
			return "<a href=javascript:deletezy('"+row.uuid+"')>删除</a>";
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
			createmodalwindow("添加专业信息", width*0.3, height*0.6, '${baseurl}zyInput.action');
		}
	} ];

	//加载datagrid

	$(function() {
		$('#zylist').datagrid({
			title : '专业查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}zy/query.action',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#zylist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#zylist').datagrid('clearSelections');
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
	
	    $('#xuuid').combobox({
	        url: '${baseurl}xi/jsonList.action',
	        valueField: 'uuid',
	        textField: 'name',
	        editable: false,
	        loadFilter:function(data){
	        	var obj={};
	        	obj.uuid="";
	        	obj.name="请选择";
	        	data.splice(0,0,obj);
	        	return data;
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
	function queryzy(){
		//datagrid的方法load方法要求传入json数据，最终将 json转成key/value数据传入action
		//将form表单数据提取出来，组成一个json

 		var val=$.trim($("#zyname").combobox("getText"));
 		$("#zyname").combobox("setValue",val);
		var formdata = $("#zyqueryForm").serializeJson();
		$('#zylist').datagrid('load',formdata);
	}
	//修改专业
	function editzy(id){
		
		//打开修改窗口
		createmodalwindow("修改专业信息", width*0.3, height*0.6, '${baseurl}zyEdit.action?uuid='+id);
	}
	//删除专业方法
	function deletezy(id){

		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认删除吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#delete_id").val(id);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('zydeleteform',zydel_callback,null,"json");
			
			
		});
	}
	//删除专业的回调
	function zydel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			queryzy();
		}
	}
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="zyqueryForm">
	<!-- 查询条件 -->
	<TABLE class="table_search" align="center">
		<TBODY >
			<TR>
				<TD class="left">专业名称：</td>
				<td>
					<input id="zyname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'" name="zyCustom.name" >
				</TD>
				<TD class="left">系名称：</td>
				<td class="one">
					<input id="xuuid"  name="zyCustom.xuuid" >
				</TD>
				<TD class="left">创建日期范围：</TD>
				<td>
				<input type="text" class="easyui-datebox" id="date" name="zyCustom.createimeMinView" data-options="editable:false" >
				至
				<input type="text" class="easyui-datebox" id="date" name="zyCustom.createimeMaxView" data-options="editable:false">
				</TD>
				<td><a id="btn" href="#" onclick="queryzy()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
			</TR>


		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY  align="center">
			<TR>
				<TD>
					<table id="zylist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="zydeleteform" action="${baseurl}zy/delete.action" method="post">
  <input type="hidden" id="delete_id" name="uuid" />
</form>
</body>
</html>