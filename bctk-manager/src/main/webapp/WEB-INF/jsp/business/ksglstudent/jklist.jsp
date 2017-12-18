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
		field : 'lastLoginTimeView',//对应json中的key
		title : '最后登录时间',
		width : 100
	} ,{
		field : 'lastLoginIp',//对应json中的key
		title : '最后登录ip',
		width : 100
	},{
		field : 'leftTime',//对应json中的key
		title : '剩余时间',
		width : 100,
		formatter: function(value,row,index){
			//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
			if(row.status==3)
				return '<div class="time"></div>';
			else 
				return '<div class="notime">0</div>';
		}
	}  ] ];
	function checkTime(i){
		if(i<10){
			i="0"+i;
		}
		return i;
	}
	//加载datagrid
	$(function() {
		
		$('#ksglstudentlist').datagrid({
			title : '考试学生查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}jkKsglStudent/query.action?ksglStudentCustom.ksgluuid=${ksgluuid}',//加载数据的连接，引连接请求过来是json数据
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
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
	    
		var time="${leftTime}";
		var leftTime =parseInt(time);

		
		var h = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
		var m = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟
		var s = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数
		h = checkTime(h); 
		m = checkTime(m); 
		s = checkTime(s);
		$('.time').html(h+"小时" + m+"分"+s+"秒");
		if(leftTime!=0)
			leftTime=leftTime-1000;
		
		setInterval(function(){
			var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
			var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟
			var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数
			hours = checkTime(hours); 
			minutes = checkTime(minutes); 
			seconds = checkTime(seconds);
			$('.time').html(hours+"小时" + minutes+"分"+seconds+"秒");
			if(leftTime!=0)
				leftTime=leftTime-1000;
		},1000); 
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
</body>
</html>