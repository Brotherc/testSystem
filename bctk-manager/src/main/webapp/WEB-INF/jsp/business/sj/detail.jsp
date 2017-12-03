<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>试卷题目信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

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

<script type="text/javascript">

function tminfo(uuid,type){
	var sendUrl = "${baseurl}tm/detail.action?uuid="+uuid+"&&type="+type;
	createmodalwindow("题目信息查看", 900, 500, sendUrl);
}
//题库明细列表的列定义

var columns = [ [{
	field : 'uuid',
	hidden : true,//该列隐藏
	formatter: function(value,row,index){
		//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
		return '<input type="hidden" name="sjTmList['+index+'].uuid" value="'+value+'" />';
	}
},{
	field : 'sjtmid',
	title : '题目编号',
	width : 65
},{
	field : 'typename',
	title : '题目类型',
	width : 100
},{
	field : 'type',
	hidden : true
},{
	field : 'content',
	title : '内容',
	width : 650
},{
	field : 'ndtypename',
	title : '难度',
	width : 40
},{
	field : 'score',
	title : '分数',
	width : 40
},{
	field : 'detail',
	title : '详细',
	width : 40,
	formatter:function(value, row, index){
		return '<a href=javascript:tminfo("'+row.tuuid+'","'+row.type+'")>查看</a>';
	}
}]];

//加载datagrid（题库明细列表）
	 $(function() {
		$('#sjmxlist').datagrid({
			title : '试卷题目列表',
			showFooter:true,//是否显示总计行
			striped : true,
			url : '${baseurl}sjxitm/list.action',//这里边后边带了一个参数，所以form中不需要此参数tkuuid
			queryParams:{//url的参数，初始加载datagrid时使用的参数
				"sjTmCustom.sjid":'${sjid}',
				"sjTmCustom.state":'1'
			}, 
			idField : 'uuid',//题库明细id
			//frozenColumns : frozenColumns,
			columns : columns,
			pagination : true,
			rownumbers : true,
			showFooter:true,//显示总计
			loadMsg:"",
			pageList:[15,30,50,100],
			onClickRow : function(index, field, value) {
				$('#sjmxlist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#sjmxlist').datagrid('clearSelections');
			}
			} );
		
	    $('#zsdname').combobox({
	        url: '${baseurl}zsd/jsonList.action?zsdCustom.kcname=${kcname}',
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

//题库明细查询方法
function sjmxquery(){
	$("input[type=text]").each(function(i){
		var val=$.trim($(this).val());
		$(this).val(val);
	});
	$("input[class=easyui-combobox]").each(function(i){
		var val=$.trim($(this).combobox('getValue'));
		$(this).combobox('setValue',val);
	});
		var val=$.trim($("#zsdname").combobox("getText"));
 		$("#zsdname").combobox("setValue",val);
	var formdata = $("#sjmxForm").serializeJson();
	//alert(formdata);
	$('#sjmxlist').datagrid('unselectAll');//取所有选中，避免重新load保留选中状态
	$('#sjmxlist').datagrid('load', formdata);
}
</script>
</HEAD>
<BODY>
	
	
<!-- 试卷明细查询form -->
<form id="sjmxForm" name="sjmxForm" method="post" >
	<input type="hidden" name="indexs" id="indexs" />
	<!-- 试卷id -->
	<input type="hidden" name="sjTmCustom.sjid" value="${sjid}"/>
	<input type="hidden" name="sjTmCustom.state" value="1"/>
		<TABLE  class="table_search" align="center">
			<TBODY>
				<TR>
						<TD class="left">难度类型：</TD>
						<td class="one">
							<input id="ndtype"  name="sjTmCustom.ndtype" >
						</TD>
						<TD class="left">题目类型：</TD>
						<td class="one">
							<input id="tmtype"  name="sjTmCustom.type" >
						</TD>
						<!-- 自行添加 -->
						<TD class="left">内容：</TD>
						<td><INPUT type="text" class="easyui-textbox"  name="sjTmCustom.content" class="four"/></TD>
					</TR>
					<TR>
						<TD class="left">分数：</td>
						<td ><INPUT type="text" class="easyui-textbox" name="sjTmCustom.score" class="one"/></TD>
						<TD class="left">知识点名称：</td>
						<td>
							<input  name="sjTmCustom.zsdname" id="zsdname">
						</TD>
						<!-- 自行添加 -->
						<td colspan=2>
							<a id="btn" href="#" onclick="sjmxquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				  		</td>
					</tr>
					
				</TBODY>
			</TABLE>
	   
	   <!-- 题库明细列表 -->
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY align="center">
				<TR>
					<TD>
					<!-- 题库明细列表 -->
						<table id="sjmxlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>



</BODY>
</HTML>

