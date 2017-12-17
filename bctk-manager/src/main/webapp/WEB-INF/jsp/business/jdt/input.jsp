<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/jquery-easyui-1.4.1/styles/default.css">

<%@ include file="/WEB-INF/jsp/base/common_css2.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js2.jsp"%>
<title>添加题目</title>
<script type="text/javascript">
//回登录页面
function toLogin(){
	
	if(parent.parent.parent){
		parent.parent.parent.location='${baseurl}login.action';
	}else if(parent.parent){
		parent.parent.location='${baseurl}login.action';
	}else if(parent){
		parent.location='${baseurl}login.action';
	}else{
		window.location='${baseurl}login.action';
	}  
	//window.location='${baseurl}login.action';
}
  function jdtsave(){
	  //准备使用jquery 提供的ajax Form提交方式
	  //将form的id传入，方法自动将form中的数据组成成key/value数据，通过ajax提交，提交方法类型为form中定义的method，
	  //使用ajax form提交时，不用指定url，url就是form中定义的action
	  //此种方式和原始的post方式差不多，只不过使用了ajax方式
	  
	  //第一个参数：form的id
	  //第二个参数：sysusersave_callback是回调函数，sysusersave_callback当成一个方法的指针
	  //第三个参数：传入的参数， 可以为空
	  //第四个参数：dataType预期服务器返回的数据类型,这里action返回json
	  //根据form的id找到该form的action地址
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
	  jquerySubByFId('jdtform',jdtsave_callback,toLogin,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function jdtsave_callback(data,add_callback){
 	  message_alert(data);
 		 
 	  if(data.resultInfo.type=='1'){
 		  setTimeout("parent.closemodalwindow()", 1500);
  		 //setTimeout("parent.window.location.reload()", 2000);
  		 var val=$.trim($("#kcname").combobox("getValue"));
  		parent.querydxtByKc(val);
 	  } 	  

	 // alert(data.message);
	  /* if(data.type=='0'){
		  $.messager.alert('提示信息',data.message,'success');
	  }else{
		  $.messager.alert('提示信息',data.message,'error');
	  } */
	 
	  //action返回的是json数据
	  //如果是成功显示一个对号
	  
	  //如果是错误信息
	  
	//提交结果类型
	//data中存放的是action返回Resultinfo，有一个type
/* 		var type=data.resultInfo.type;
		//结果提示信息
		var message=data.resultInfo.message;
		//alert(message);
		if(type==0){
			//如果type等于0表示失败，调用 jquery easyui的信息提示组件
			$.messager.alert('提示信息',message,'error');
		}else if(type==1){
			$.messager.alert('提示信息',message,'success');
		}else if(type==2){
			$.messager.alert('提示信息',message,'warning');
		}else if(type==3){
			$.messager.alert('提示信息',message,'info');
		} */
  }
  $(function(){
	    
	    
	    $('#kcname').combobox({
	    	onSelect: function(data){
	    		var kcname=data.name;
	    		//根据课程名称查询对应章节信息
	    	    $('#zsd').combobox('reload','${baseurl}zsd/jsonList.action?zsdCustom.kcname='+kcname);
	    	}
	    });

	    $('#zsd').combobox({
            url: '',//对应的ashx页面的数据源
            valueField: 'uuid',//绑定字段ID
            textField: 'name',//绑定字段Name
            panelHeight: 'auto',//自适应
            editable:false,
            required:true,
            multiple: true,
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
            },

            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onSelect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });	    
});  
</script>
</head>
<body>


<form id="jdtform" action="${baseurl}jdt/add.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

   <TBODY>
   		<TR>
			<TD background=images/r_0.gif width="100%">
				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;单选题目信息</TD>
							<TD align=right>&nbsp;</TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
			
		<TR>
			<TD>
				<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4 align=center>
					<TBODY>
						<TR>
							<TD height=30 width="15%" align=right >课程名称：</TD>
							<TD class=category width="35%">
								<div>
									<input id="kcname" class="easyui-combobox" data-options="required:true,editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action?kcCustom.sysuseruuid=${sysuseruuid }',valueField:'name',textField:'name'" name="jdtCustom.kcname" >
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="jdt_kcnameTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >难度类型：</TD>
							<TD class=category width="35%">
								<div>
									<input id="ndtype" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}dxt/ndTypeJsonList.action',valueField:'dictcode',textField:'info'" name="jdtCustom.ndtype" >
								</div>
								<div id="jdt_ndtypeTip"></div>
							</TD>							
						</TR>
							
						<tr>
							<TD height=30 width="15%" align=right >题目内容：</TD>
							<TD class=category width="100%" colspan="3">
								<div>
									<input class="easyui-textbox" id="jdtcontent" name="jdtCustom.content" data-options="required:true,multiline:true,validType:'length[0,150]'" style="height:60px;width: 280px;"></input>
								</div>
								<div id="jdt_ndtypeTip"></div>
							</TD>
						</tr>
						
						<tr>
							<TD height=30 width="15%" align=right >答案：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" id="tkt_answer" class="easyui-textbox" data-options="required:true" name="jdtCustom.answer" />
								</div>
								<div id="jdt_answerTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >知识点：</TD>
							<td>
     						<input id="zsd" class="easyui-combobox" name="zsdList" />
							</td>
						</tr>	
						
						<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="jdtsave()">提交</a>
								<a id="closebtn"  class="easyui-linkbutton" iconCls="icon-cancel" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
						</tr>
						
					</TBODY>
				</TABLE>
			</TD>
		</TR>
   </TBODY>
</TABLE>
</form>
</body>
</html>