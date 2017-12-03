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
	}, {
		field : 'timeView',//对应json中的key
		title : '时长',
		width : 100
	},{
		field : 'kcname',//对应json中的key
		title : '课程',
		width : 100
	}, {
		field : 'statusname',//对应json中的key
		title : '状态',
		width : 50
	},{
		field : 'option1',//对应json中的key
		title : '操作1',
		width : 65,
		formatter : function(value, row, index) {
			return '<a href=javascript:ks("'+row.uuid+'","'+row.sjuuid+'")>开始考试</a>';
		}
	} ] ];

	//加载datagrid
	$(function() {
		var sysuseruuid="${sysuseruuid}";
		var query={
			"ksglCustom.sysuseruuid":sysuseruuid,
			"ksglCustom.status":3	
		};
		
		$('#ksgllist').datagrid({
			title : '考试查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}ksgl/query2.action',//加载数据的连接，引连接请求过来是json数据
			queryParams:query,
			idField : 'uuid',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList:[15,30,50],
			onClickRow : function(index, field, value) {
				$('#ksgllist').datagrid('unselectRow', index);
			},
			//将加载成功后执行：清除选中的行
			onLoadSuccess:function(){
				$('#ksgllist').datagrid('clearSelections');
			}
		});
	});
	
	//查询方法
/* 	function queryksgl(){
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
		var formdata = $("#ksglqueryForm").serializeJson();
		$('#ksgllist').datagrid('load',formdata);
	} */
	
	function kssj_callback(data){
		var type=data.resultInfo.type;
		var messageCode = data.resultInfo.messageCode;
		if(type==1){
			if(messageCode==1){
				var ksgluuid=$("#ksgl_id").val();
				var sjuuid=$("#sj_id").val();
				createmodalwindow("请输入考试密码", 450, 220, '${baseurl}ksglKsPwdList.action?ksgluuid='+ksgluuid+'&sjid='+sjuuid);
			}else if(messageCode==-1){
				var ksgluuid=$("#ksgl_id").val();
				var sjuuid=$("#sj_id").val();
				createmodalwindow("请输入监考密码", 450, 220, '${baseurl}ksglJkPwdList.action?ksgluuid='+ksgluuid+'&sjid='+sjuuid);
			}else{
				var sjuuid=$("#sj_id").val();
				var ksgluuid=$("#ksgl_id").val();
				var sendUrl = "${baseurl}ksglKsSj.action";
				//parent.opentabwindow('试卷',sendUrl);//打开一个新标签

				$.ajax({
					url : sendUrl,
					type : 'POST',
					data: "sjTmCustom.sjid="+sjuuid+"&ksgluuid="+ksgluuid,
					dataType : 'json',
					success : function(data) {
						var _menus = data;
						parent.closemenu("考试管理");
						parent.refreshmenu(_menus);//解析json数据，将菜单生成

					},
					error : function(msg) {
						alert('菜单加载异常!');
					}
				});
			}
			

		}
		else {
			message_alert(data);
		}

	}
	
	//修改系
	function ks(uuid,sjuuid){
		$("#ksgl_id").val(uuid);
		$("#sj_id").val(sjuuid);
		jquerySubByFId('kssjPreform',kssj_callback,null,"json");
		
	}
</script>

</head>
<body>

	<!-- html的静态布局 -->
  <form id="ksglqueryForm">

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
<form id="kssjPreform" action="${baseurl}ksglKsSjPre.action" method="post">
  <input type="hidden" id="ksgl_id" name="ksgluuid" />
  <input type="hidden" id="sj_id" name="sjuuid" />
</form>
</body>
</html>