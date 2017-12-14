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
<title>考试管理</title>

<script type="text/javascript">
	//datagrid列定义
	var columns_v = [ [ {
		field : 'sjname',//对应json中的key
		title : '试卷名称',
		width : 100
	}, {
		field : 'starttimeView',//对应json中的key
		title : '开始时间 ',
		width : 100
	}, {
		field : 'endtimeView',//对应json中的key
		title : '结束时间 ',
		width : 100
	},  {
		field : 'kcname',//对应json中的key
		title : '课程',
		width : 100
	}, {
		field : 'timeView',//对应json中的key
		title : '时长(分钟)',
		width : 100
	}, {
		field : 'statusname',//对应json中的key
		title : '状态',
		width : 50
	}, {
		field : 'teachername',//对应json中的key
		title : '发布教师',
		width : 50
	},{
		field : 'option1',//对应json中的key
		title : '操作1',
		width : 65,
		formatter : function(value, row, index) {
			return "<a href=javascript:editKsgl('"+row.uuid+"')>修改</a>";
		}
	},{
		field : 'option2',//对应json中的key
		title : '操作2',
		width : 65,
		formatter : function(value, row, index) {
			return '<a href=javascript:deleteKsgl("'+row.uuid+'","'+row.classuuid+'")>删除</a>';
		}
	},{
		field : 'option3',//对应json中的key
		title : '操作3',
		width : 65,
		formatter : function(value, row, index) {
			return '<a href=javascript:ksglStudentList("'+row.uuid+'","'+row.sjname+'","'+row.xiuuid+'")>考试人员配置</a>';
		}
	} ,{
		field : 'option4',//对应json中的key
		title : '操作4',
		width : 65,
		formatter : function(value, row, index) {
			if(row.status==1)
			return '<a href=javascript:startKsgl("'+row.uuid+'")>启动考试</a>';
		}
	} ,{
		field : 'option5',//对应json中的key
		title : '考试情况',
		width : 65,
		formatter : function(value, row, index) {
			return '<a href=javascript:studentSj("'+row.uuid+'","'+row.sjname+'")>查看</a>';
		}
	},{
		field : 'option6',//对应json中的key
		title : '监考',
		width : 65,
		formatter : function(value, row, index) {
			return '<a href=javascript:ksglStudent("'+row.uuid+'","'+row.sjname+'")>查看</a>';
		}
	}] ];

	//定义 datagird工具
	var toolbar_v = [ {//工具栏
		id : 'btnadd',
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			//打开一个窗口，用户添加页面
			//参数：窗口的title、宽、高、url地址
			createmodalwindow("添加考试信息", 550, 370, '${baseurl}ksglInput.action');
		}
	} ];

	//加载datagrid
	$(function() {
		$('#ksgllist').datagrid({
			title : '考试查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}ksgl/query.action?ksglCustom.sysuseruuid=${sysuseruuid}',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#ksgllist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#ksgllist').datagrid('clearSelections');
			}
		});
		
		
/*         var buttons = $.extend([], $.fn.datebox.defaults.buttons);  
        buttons.splice(1, 0, {  
            text: '清除',  
            handler: function (target) {  
                $(target).datebox("setValue","");  
            }  
        });  
        $('.easyui-datetimebox').datebox({  
            buttons: buttons  
        });   */

		//class="easyui-combobox" data-options="editable:false,url:'${baseurl}zy/jsonList.action',valueField:'name',textField:'name'"
/* 		$('#zyname').combobox({  
		    url:'${baseurl}zy/jsonList.action?zyCustom.xuuid='+'${xiuuid}',    
		    valueField:'uuid',    
		    textField:'name',
		   	editable:false
		}); */
		
		
	    $('#status').combobox({
	        url: '${baseurl}ksgl/status.action',
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
	function queryksgl(){
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
 		var val=$.trim($("#kcname").combobox("getText"));
 		$("#kcname").combobox("setValue",val);
		var formdata = $("#ksglqueryForm").serializeJson();
		$('#ksgllist').datagrid('load',formdata);
	}
	//修改系
	function editKsgl(id){
		
		//打开修改窗口
		createmodalwindow("修改考试信息", 450, 220, '${baseurl}ksglEdit.action?ksglCustom.uuid='+id);
	}
	
	function ksglStudentList(ksgluuid,sjname,xiuuid){
		var sendUrl = "${baseurl}ksglStudentList.action?ksgluuid="+ksgluuid+"&xiuuid="+xiuuid;
		parent.opentabwindow(sjname, sendUrl);
	}
	
	function startKsgl(id){
		$("#start_id").val(id);
		jquerySubByFId('ksglstartform',ksgldel_callback,null,"json");
	}
	
	//删除系方法
	function deleteKsgl(id,classuuid){

		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认删除吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#delete_id").val(id);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('ksgldeleteform',ksgldel_callback,null,"json");
			
			
		});
	}
	function studentSj(id,sjname){
		//打开考试窗口
		var sendUrl = "${baseurl}studentSjList.action?ksgluuid="+id;
		parent.opentabwindow(sjname, sendUrl);
	}
	
	function ksglStudent(id,sjname){
		var sendUrl = "${baseurl}jkKsglStudentList.action?ksgluuid="+id;
		parent.opentabwindow('监考'+sjname, sendUrl);
	}	
	//删除系的回调
	function ksgldel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			queryksgl();
		}
	}
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="ksglqueryForm">
	<!-- 查询条件 -->
	<TABLE class="table_search" align="center">
		<TBODY >
			<tr>
				<TD class="left">发布老师：</td>
				<td>
				<INPUT class="easyui-textbox" type="text" name="ksglCustom.teachername" />
				</TD>
				<!-- /kczy/jsonList -->
				<TD class="left">课程：</td>
				<td>
					<input id="kcname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action?kcCustom.sysuseruuid=${sysuseruuid}',valueField:'name',textField:'name'" name="ksglCustom.kcname" >
				</TD>			
			</tr>
			<TR>
				<TD class="left">状态：</td>	
				<td class="one">
					<input id="status"  name="ksglCustom.status" >
				</TD>
				<TD class="left">试卷：</td>
				<td>
				<INPUT class="easyui-textbox" type="text" name="ksglCustom.sjname" />
				</TD>	

			</TR>
			
			<tr>
				<TD class="left">考试时间：</TD>
				<td>
				<input type="text" class="easyui-datetimebox" id="date" name="ksglCustom.starttimeView" data-options="editable:false" >
				至
				<input type="text" class="easyui-datetimebox" id="date" name="ksglCustom.endtimeView" data-options="editable:false">
				</TD>
				<td><a id="btn" href="#" onclick="queryksgl()"
					class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
			</tr>


		</TBODY>
	</TABLE>

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY  align="center">
			<TR>
				<TD>
					<table id="ksgllist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form id="ksgldeleteform" action="${baseurl}ksgl/delete.action" method="post">
  <input type="hidden" id="delete_id" name="uuid" />
</form>

<form id="ksglstartform" action="${baseurl}ksgl/start.action" method="post">
  <input type="hidden" id="start_id" name="uuid" />
</form>
</body>
</html>