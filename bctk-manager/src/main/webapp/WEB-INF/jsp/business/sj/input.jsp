<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html> 
<head>
<title>自由组卷</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

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

<script type="text/javascript">

function resetSjmb(){
/* 	  $('#dxtcount').combobox('setValues', $("#dxtcount").combobox('getText')); */
}

//试卷保存
function sjsave(){
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
	jquerySubByFId('sjsaveForm', sjsave_callback, null);
}

function sjsave_callback(data) {
	//由服务端统一返回submitResultInfo的json数据，所以客户端统一使用_alert统一解析方法
	_alert(data.resultInfo); 
	
 	var type=data.resultInfo.type;
 	if(type==1){
 		//window.location.reload();
 		setTimeout('parent.refreshtabwindow("组卷","${baseurl}sjInput.action")', 2000);
	}
	 
}

function xitmdel_callback(data) {
	var result = getCallbackData(data);
	_alert(result);//显示失败明细的
	var type=data.resultInfo.type;
 	if(type==1){
		//成功结果
 		xitmquery();
	}

}

function sjcheck_callback(data){
	
	var type=data.resultInfo.type;
 	if(type==1){

		var dxtcount=$("#dxtcount").val();
 		var tktcount=$("#tktcount").val();     
 		var pdtcount=$("#pdtcount").val();     
 		
 		var dxtscore=$("#dxtscore").val();   
 		var tktscore=$("#tktscore").val();      
 		var pdtscore=$("#pdtscore").val();
 		
 		var kcname=$("#kcname").combobox('getValue');
 		
 		var sendUrl = "${baseurl}sjXitmList.action?kcname="+kcname+"&sjid="+$("#sjid").val()
 				+"&dxtcount="+dxtcount
 				+"&tktcount="+tktcount
 				+"&pdtcount="+pdtcount
 				+"&dxtscore="+dxtscore
 				+"&tktscore="+tktscore
 				+"&pdtscore="+pdtscore;
 		createmodalwindow("试卷题目添加", 1150, 500, sendUrl);
	}
 	else{
 		var result = getCallbackData(data);
 		_alert(result);
 	}

}

var sjtmadd_xtk = function (){
		var dxtcount=$("#dxtcount").val();  
 		var tktcount=$("#tktcount").val();     
 		var pdtcount=$("#pdtcount").val();     
 		
 		var dxtscore=$("#dxtscore").val();   
 		var tktscore=$("#tktscore").val();      
 		var pdtscore=$("#pdtscore").val();
 	
 		
	var kcname=$.trim($("#kcname").combobox("getText"));
		$("#kcname").combobox("setValue",kcname);
	$("#kcnamePre").val(kcname);
	$("#dxtcountPre").val(dxtcount);
	$("#tktcountPre").val(tktcount);
	$("#pdtcountPre").val(pdtcount);
	$("#dxtscorePre").val(dxtscore);
	$("#tktscorePre").val(tktscore);
	$("#pdtscorePre").val(pdtscore);
	jquerySubByFId('sjXitmListPreForm', sjcheck_callback, null);
};



function tminfo(uuid,type){
	var sendUrl = "${baseurl}tm/detail.action?uuid="+uuid+"&&type="+type;
	createmodalwindow("题目信息查看", 900, 500, sendUrl);
}

function updateSjxitmScore(uuid){
	//打开修改窗口
	createmodalwindow("修改试卷题目分数", 450, 220, '${baseurl}sjxitmEditScore.action?uuid='+uuid);
}
function updateSjtmid(uuid){
	//打开修改窗口
	createmodalwindow("修改试卷题目编号", 450, 220, '${baseurl}sjxitmEditSjtmid.action?uuid='+uuid);
}

function sjxitmdel(){
	
	_confirm('您确定要执行删除选中的题目吗?',null,
			  function(){
				var indexs = [];//定义一个数组准备存放删除记录的序号
				//通过jquery easyui的datagrid的getSelections函数，得到当前所有选中的行(对象数组)
				var rows = $('#sjxitmlist').datagrid('getSelections');
				alert(rows.length);
				//循环遍历选中行
				for(var i=0;i<rows.length;i++){

					//通过jquery easyui的datagrid的getRowIndex方法得行的序号
					var index=$('#sjxitmlist').datagrid('getRowIndex',rows[i]);
					//将选中行的序号放入indexs数组
					indexs.push(index);
				}
				//如果存在选中的行
				if(rows.length>0){
					//将选中的行通过indexs.join(',')，将选中行的序号中间以逗号分隔组成一个字符串，调用$("#indexs").val方法，将这个字符串放入indexs对象
					$("#indexs").val(indexs.join(','));
					//执行ajax的form提交
					jquerySubByFId('sj_xitmForm', xitmdel_callback, null);
				}else{
					alert_warn("请选择要删除的题目");
				}
				
			  }
			)
}

/* function ypxxedit_callback(redata){
	$('#ypxxquery_div').css("display","none");
	$("#ypxxedit_div").css("display","block");
	$("#ypxxedit_div").html(redata);
} */






	//datagrid列定义
	var columns_v = [ [{
		field : 'ck',
		checkbox:true //显示一个复选框
	},{
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
		width : 500
	},{
		field : 'ndtypename',
		title : '难度',
		width : 40
	},{
		field : 'score',
		title : '分数',
		width : 40
	},{
		field : 'statusname',
		title : '状态',
		width : 40
	},{
		field : 'detail',
		title : '详细',
		width : 40,
		formatter:function(value, row, index){
			return '<a href=javascript:tminfo("'+row.tuuid+'","'+row.type+'")>查看</a>';
		}
	},{
		field : 'operate2',
		title : '操作',
		width : 80,
		formatter:function(value, row, index){
			return '<a href=javascript:updateSjxitmScore("'+row.uuid+'")>修改分数</a>';
		}
	},{
		field : 'operate3',
		title : '操作',
		width : 80,
		formatter:function(value, row, index){
			return '<a href=javascript:updateSjtmid("'+row.uuid+'")>修改编号</a>';
		}
	}]];
//工具栏
	var toolbar_v = [{
		id : 'sjtmadd_xtk',
		text : '从课程题库中添加试卷题目',
		iconCls : 'icon-add',
		handler : sjtmadd_xtk
		} ,{
			id : 'sjtm_delete',
			text : '删除试卷题目',
			iconCls : 'icon-remove',
			handler : sjxitmdel
		}];



//加载datagrid
	$(function() {
		var sjuuid="${sjid}";
		var query={
			"sjTmCustom.sjid":sjuuid	
		};
		$('#sjxitmlist').datagrid({
			title : '试卷题目',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}sjxitm/list.action',//加载数据的连接，引连接请求过来是json数据
			queryParams:query,
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			toolbar : toolbar_v,
			onClickRow : function(index, field, value) {
				$('#sjxitmlist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#sjxitmlist').datagrid('clearSelections');
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
	        url: '${baseurl}zsd/jsonList.action?zsdCustom.sysuseruuid=${sysuseruuid}',
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
	
	    $("input[type='checkbox']").click(function(){
	    	$(this).parent().next().show();
	    });
	});


	//列表查询
	function xitmquery() {
 		//将form中的数据组成json
		var formdata = $("#sj_xitmForm").serializeJson();
		//alert(formdata);
		//取消所有datagrid中的选择
		//$('#gysypmllist').datagrid('unselectAll');
		//json是datagrid需要格式数据，向服务器发送的是key/value
		$('#sjxitmlist').datagrid('load', formdata);
		

	}


function addXitmCallBack(){
	xitmquery();
}


</script>
</HEAD>
<BODY>

<form id="sjsaveForm" name="sjsaveForm" method="post" action="${baseurl}sj/add.action">
<input type="hidden" id="sjid" name="sjCustom.uuid" value="${sjid }"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="70%" bgColor=#c4d8ed align=center>
		
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;试卷</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							
							<tr>
								<TD height=30 width="15%" align=right >试卷名称：</TD>
								<TD class=category width="35%">
									<div>
										<input type="text" id="sj_name" class="easyui-textbox" data-options="required:true" name="sjCustom.name"/>
									</div>
								</TD>
							</tr>	
						<TR>
							<TD height=30 width="15%" align=right >课程名称：</TD>	
							<td class=category>
								<input id="kcname" class="easyui-combobox" data-options="required:true,editable:true,url:'${baseurl}kc/jsonList.action?kcCustom.sysuseruuid=${sysuseruuid}',mode:'remote',valueField:'name',textField:'name'" name="sjCustom.kcname" >
							</TD>						
						</TR>	
						
						<TR>
							<TD height=30 width="15%" align=right >难度类型：</TD>	
							<td class="one" class=category>
								<input class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}sj/ndTypeJsonList.action',valueField:'dictcode',textField:'info'" name="sjCustom.ndtype" >
							</TD>						
						</TR>
						<TR>
							<TD height=30 width="15%" align=right ><input type="checkbox" />单项选择题：</TD>
							<TD class=category width="35%" style="display:none">
								题量
<input type="text" id="dxtcount" data-options="required:true" class="easyui-textbox" name="sjmbCustom.dxtcount" value="0"/>
								分值
<input type="text" id="dxtscore" data-options="required:true" class="easyui-textbox" name="sjmbCustom.dxtscore" value="0"/>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right ><input type="checkbox" />填空题：</TD>
							<TD class=category width="35%" style="display:none">
								题量
<input type="text" id="tktcount" data-options="required:true" class="easyui-textbox" name="sjmbCustom.tktcount" value="0" />
								分值
<input type="text" id="tktscore" data-options="required:true" class="easyui-textbox" name="sjmbCustom.tktscore" value="0"/>
							</TD>							
						</TR>
						<TR>
							<TD height=30 width="15%" align=right ><input type="checkbox" />判断题：</TD>
							<TD class=category width="35%" style="display:none">
								题量
<input type="text" id="pdtcount" data-options="required:true" class="easyui-textbox" name="sjmbCustom.pdtcount" value="0"/>
								分值
<input type="text" id="pdtscore" data-options="required:true" class="easyui-textbox" name="sjmbCustom.pdtscore" value="0"/>
							</TD>							
						</TR>
						
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="sjsave()" class="easyui-linkbutton" iconCls='icon-save'>保存</a>
							  </td>
							</tr>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>




	<!-- html的静态布局 -->
  <form id="sj_xitmForm" action="${baseurl}sjxitm/delete.action" method="post">
  <input type="hidden" value=${sjid } name="sjTmCustom.sjid"/>
  <input type="hidden" name="indexs" id="indexs" />
	<!-- 查询条件 -->
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
						<td><INPUT type="text" class="easyui-textbox" name="sjTmCustom.content"/></TD>
					</TR>
					<TR>
						
						<TD class="left">分数：</TD>
						<td><INPUT type="text" class="easyui-textbox" name="sjTmCustom.score"/></TD>
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

	<!-- 查询列表 -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY  align="center">
			<TR>
				<TD>
					<table id="sjxitmlist"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>
<form action="${baseurl}sjXitmListPre.action" method="post" id="sjXitmListPreForm">
	<input type="hidden" name="sjCustom.kcname"  id="kcnamePre"/>
		<input type="hidden" name="sjmbCustom.dxtcount" id="dxtcountPre" />
	<input type="hidden" name="sjmbCustom.dxtscore"  id="dxtscorePre"/>
		<input type="hidden" name="sjmbCustom.tktcount" id="tktcountPre" />
	<input type="hidden" name="sjmbCustom.tktscore"  id="tktscorePre"/>
		<input type="hidden" name="sjmbCustom.pdtcount" id="pdtcountPre" />
	<input type="hidden" name="sjmbCustom.pdtscore"  id="pdtscorePre"/>
</form>

</BODY>
</HTML>

