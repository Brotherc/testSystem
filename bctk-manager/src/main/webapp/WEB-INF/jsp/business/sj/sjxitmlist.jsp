<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>系题目目录查询</title>
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

function xitmadd_callback(data){
 	var result = getCallbackData(data);
	_alert(result);//显示失败明细的 
 	var type=data.resultInfo.type;
 	if(type==1){
		//成功结果
		setTimeout("parent.closemodalwindow()", 2000);
		setTimeout("parent.addXitmCallBack()", 2000);
		//setTimeout("parent.window.location.reload()", 2000);
	}
}

function tminfo(uuid,type){
	var sendUrl = "${baseurl}tm/detail.action?uuid="+uuid+"&&type="+type;
	createmodalwindow("题目信息查看", 900, 500, sendUrl);
}

var sjxitmadd = function(){
	//_confirm询问是否继续操作？
	_confirm('您确定要將选择题目添加至试卷吗?',null,
	//执行添加函数
	  function(){
		var dataGrid_obj=$('#xitmlist');
		//定义一个数组，准备存放选中行的序号
		var indexs=[];
		//获取数据列表中所有选中的行(数组)
		var rows = dataGrid_obj.datagrid('getSelections');
		//便利所有选中的行
 		var scores=[];
		var scoresTemp=[];
		$(".addScore").each(function(index){
			scoresTemp[index]=$(this).val();
		});
		
		var go=true;
		for(var i=0;i<rows.length;i++){
			
			//alert(dataGrid_obj.datagrid('getRowIndex',rows[i]));
			//将返回的选中行的序号加到indexs数组中
			var index = dataGrid_obj.datagrid('getRowIndex',rows[i]);//选中行的下标
			//将选中行的序号设置到数组indexs中
			var score=scoresTemp[index];
 			if(score==null||score==""){
 				alert_warn("添加的试卷题目分数未补充完整");
 				go=false;
 				break;
 			}
			else scores.push(score);
			indexs.push(index);
			//alert(dataGrid_obj.datagrid('getRowIndex',rows[i]));
		}
		//判断如果存在选中的行，indexs数组里边有选中行的序号
		if(go)
		if(rows.length>0){//如果存在选中的行则将indexs数组中的序号格式化为逗号分隔的并赋给indexs控件
			

			$("#scores").val(scores.join(","));
			$("#indexs").val(indexs.join(","));//将indexs数组的元素在中间加逗号拼接成一个字符串
		    //提交form，提交数据包括药品信息id(每条记录都 有)，indexs（hidden）
			jquerySubByFId('xitmdeleteForm',xitmadd_callback, null);
		}else{
			//如果没有选中行则提示
			alert_warn("请选择要添加的题目");
		}
		
	  } 
	  
	)
	
	
	
}; 


//工具栏

var toolbar = [ {
	id : 'xitmadd',
	text : '题目添加',
	iconCls : 'icon-add',
	handler : sjxitmadd
	}];

var frozenColumns;

var columns = [ [{
	field : 'ck',
	checkbox:true //显示一个复选框
},{
	field : 'uuid',
	hidden : true,//该列隐藏
	formatter: function(value,row,index){
		//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
		return '<input type="hidden" name="sjTmList['+index+'].tuuid" value="'+value+'" />';
	}
},{
	field : 'type',
	title : '类型',
	hidden : true,
	formatter: function(value,row,index){
		//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
		return '<input type="hidden" name="sjTmList['+index+'].type" value="'+value+'" />';
	}
},{
	field : 'typename',
	title : '题目类型',
	width : 80
},{
	field : 'kcname',
	title : '课程',
	width : 100
},{
	field : 'content',
	title : '内容',
	width : 210
},{
	field : 'teachername',
	title : '创建用户名',
	width : 70
},{
	field : 'ndtypename',
	title : '难度',
	width : 40
},{
	field : 'detail',
	title : '详细',
	width : 40,
	formatter:function(value, row, index){
		return '<a href=javascript:tminfo("'+row.uuid+'","'+row.type+'")>查看</a>';
	}
},{
	field : 'score',
	title : '分数',
	width : 40,
	formatter: function(value,row,index){
		//gysypmls对应action接收对象中list的名称，[]括号中是从0开始序号,id是list中对象属性
		if(row.type==1)
			return '<input type="text" class="addScore" name="sjTmList['+index+'].score" value="${sjmb.dxtscoreA}" />';
		if(row.type==3)
			return '<input type="text" class="addScore" name="sjTmList['+index+'].score" value="${sjmb.tktscoreA}" />';
		if(row.type==5)
			return '<input type="text" class="addScore" name="sjTmList['+index+'].score" value="${sjmb.pdtscoreA}" />';
	}
}]];

//datagrid加载
function initGrid(){
	var sjuuid="${sjuuid}";
	var kcname="${kcname}";
	var query={
		"sjTmCustom.sjid":sjuuid,
		"sjTmCustom.kcname":kcname
	};
	
	$('#xitmlist').datagrid({
		title : '题目列表',
		striped : true,
		url : '${baseurl}sjXitmAdd/list.action',
		queryParams:query,
		idField : 'uuid',//json数据集的主键
		columns : columns,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg:"",
		pageList:[15,30,50,100] ,//设置每页显示个数
		onClickRow : function(index, field, value) {
			$('#xitmlist').datagrid('unselectRow', index);
		},
		//将加载成功后执行：清除选中的行
		onLoadSuccess:function(){
			$('#xitmlist').datagrid('clearSelections');
		}
		});

	}
	$(function() {
		initGrid();
		
		    $('#zsdname').combobox({
		        url: '${baseurl}zsd/jsonList.action?zsdCustom.kcname=${kcname}',
		        valueField: 'name',
		        textField: 'name',
		        editable: true,
		        mode:'remote',
		    });

 			  $('#kcname').combobox('setValues', '${kcname}');

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

	//列表查询
	function xitmquery() {
 		//将form中的数据组成json
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
		var formdata = $("#xitmdeleteForm").serializeJson();
		//取消所有datagrid中的选择
		//$('#gysypmllist').datagrid('unselectAll');
		//json是datagrid需要格式数据，向服务器发送的是key/value
		$('#xitmlist').datagrid('load', formdata);
		

	}
</script>
<style type="text/css">
.dateScope{
 width: 80px;
}
</style>
</HEAD>
<BODY>
<div id="xitmquery_div">
    <form id="xitmdeleteForm" name="xitmdeleteForm" action="${baseurl}sjxitm/add.action" method="post">
			<input type="hidden" name="indexs" id="indexs" />
			<input type="hidden" name="scores" id="scores"/> 
			<input type="hidden" id="sjuuid" name="sjCustom.uuid" value="${sjuuid }" />
			<input type="hidden" id="score" name="sjCustom.score" value="${score }" />
			<input type="hidden"  name="sjCustom.kcname" value="${kcname }" />
			<input type="hidden"  name="sjTmCustom.kcname" value="${kcname }" />			
			<input type="hidden" name="sjTmCustom.sjid" value="${sjuuid}">
			<TABLE  class="table_search">
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
						<td><INPUT type="text" class="easyui-textbox" name="sjTmCustom.content" class="four"/></TD>
					</TR>
					<TR>
						<TD class="left">题目创建用户：</td>
						<td ><INPUT type="text" class="easyui-textbox" name="sjTmCustom.teachername" class="one"/></TD>
						<!-- 自行添加 -->
						<TD class="left">知识点名称：</td>
						<td>
							<input  name="sjTmCustom.zsdname" id="zsdname">
						</TD>
				  		<td>
							<a id="btn" href="#" onclick="xitmquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				  		</td>
					</tr>
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY align="center">
				<TR>
					<TD>
						<table id="xitmlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>
</div>


</BODY>
</HTML>

