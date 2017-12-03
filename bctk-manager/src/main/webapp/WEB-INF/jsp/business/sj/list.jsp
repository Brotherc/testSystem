<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>试卷查询</title>
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


function sjdetail(uuid,name){
	var sendUrl = "${baseurl}sjdetail.action?sjuuid="+uuid;
	parent.opentabwindow(name+'试卷明细',sendUrl);//打开一个新标签
}


//工具栏

var toolbar = [];

var frozenColumns;

var columns = [ [
{
	field : 'uuid',
	hidden : true
},{
	field : 'name',
	title : '试卷名称',
	width : 300
},{
	field : 'score',
	title : '试卷分数',
	width : 100
},{
	field : 'xiName',
	title : '系',
	width : 150
},{
	field : 'kcname',
	title : '课程',
	width : 120
},{
	field : 'ndtypename',
	title : '难度',
	width : 55
},{
	field : 'teachername',
	title : '老师',
	width : 100
},{
	field : 'opt1',
	title : '明细',
	width : 55,
	formatter:function(value, row, index){
		return '<a href=javascript:sjdetail("'+row.uuid+'","'+row.name+'")>查看</a>';
	}
}]];

function initGrid(){
	$('#sjlist').datagrid({
		title : '试卷列表',
		//nowrap : false,
		striped : true,
		//collapsible : true,
		url : '${baseurl}sj/query.action',
		//sortName : 'code',
		//sortOrder : 'desc',
		//remoteSort : false, 
		idField : 'uuid',//查询结果集主键采购单id 
		//frozenColumns : frozenColumns,
		columns : columns,
		autoRowHeight:true,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg:"",
		pageList:[15,30,50,100],
		onClickRow : function(index, field, value) {
			$('#sjlist').datagrid('unselectRow', index);
		},
		//将加载成功后执行：清除选中的行
		onLoadSuccess:function(){
			$('#sjlist').datagrid('clearSelections');
		}
		}
	
	);

	}
	$(function() {
		initGrid();
		
	    $('#xuuid').combobox({
	    	onSelect: function(data){
	    		var xuuid=data.uuid;
	    		//根据课程名称查询对应章节信息
	    	    $('#zyname').combobox('reload','${baseurl}zy/jsonList.action?zyCustom.xuuid='+xuuid);
	    	}
	    });
	});

	function sjquery() {
		$("input[type=text]").each(function(i){
			var val=$.trim($(this).val());
			$(this).val(val);
		});
		$("input[class=easyui-combobox]").each(function(i){
			var val=$.trim($(this).combobox('getValue'));
			$(this).combobox('setValue',val);
		});
		var formdata = $("#sjqueryForm").serializeJson();//将form中的input数据取出来
		//alert(formdata);
		$('#sjlist').datagrid('load', formdata);
	}
	

	

</script>
</HEAD>
<BODY>
    <form id="sjqueryForm" name="sjqueryForm" method="post" >
			<TABLE  class="table_search" align="center">
				<TBODY>
					<TR>
						<TD class="left">试卷名称：</TD>
						<td ><INPUT type="text" class="easyui-textbox" name="sjCustom.name" /></td>

						<TD class="left">试卷分数：</TD>
						<td><INPUT type="text" class="easyui-textbox"  name="sjCustom.score" /></TD>
						
						<TD class="left">课程名称：</TD>	
						<td>
							<input id="kcname" class="easyui-combobox" data-options="editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action',valueField:'name',textField:'name'" name="sjCustom.kcname" >
						</TD>				
						
					</TR>
					<TR> 
						<TD class="left">创建老师名称：</td>
						<td><INPUT type="text" class="easyui-textbox"  name="sjCustom.teachername" /></TD>
						
					<TD class="left">难度类型：</TD>
							<td class="one">
								<input class="easyui-combobox" data-options="editable:false,url:'${baseurl}sj/ndTypeJsonList.action',valueField:'dictcode',textField:'info'" name="sjCustom.ndtype" >
							</TD>
					
						<td >
							<a id="btn" href="#" onclick="sjquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>	
						</td>
						
						
						
					</tr>
					
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY align="center">
				<TR>
					<TD>
						<table id="sjlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		 </form>


</BODY>
</HTML>

