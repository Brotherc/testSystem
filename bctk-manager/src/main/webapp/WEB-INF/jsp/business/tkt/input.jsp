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

//取出所有空格
function removeAllSpace(str){
	return str.replace(/\s+/g,"");
}

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
  function tktsave(){
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
		//判断是否是程序填空题
		if($("#isprogram").combobox('getValue')==2){
			$("input[name^='tktCustom.answer']").each(function(i){
				var val=removeAllSpace($(this).val());
				$(this).val(val);
			});
		}
 
	  jquerySubByFId('tktform',tktsave_callback,toLogin,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function tktsave_callback(data,add_callback){
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
	    	    $('#zsd').combobox('reload','${baseurl}zsd/jsonList.action?zsdCustom.kcname='+kcname);
	    	}
	    });

	    $('#zsd').combobox({
            url: '',//对应的ashx页面的数据源
            valueField: 'uuid',//绑定字段ID
            textField: 'name',//绑定字段Name
            panelHeight: 'auto',//自适应
            editable:false,
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
	    
	    $('#answernum').combobox({
	    	onSelect: function(data){
	    		var length=$(".del").length;
	    		
	    		var num=parseInt(data.value);
	    		var delNum=num-1;
	    		if(length>num){
 		    		for(var i=0;i<length-num;i++){
		    			$("#content").prev().remove();
		    		} 
	    		}else {
		    		var html='';
		    		for(var i=length+1;i<=num;i++){
		    			html+='<tr class="del">';
		    			var td1_content='<a  class="answer" data="'+i+'"  href="#" >添加答案</a>';
		    			var td1='<TD height=30 width="15%" align=right >'+td1_content+'</TD>';
		    			html+=td1;
		    			var td2='<td class="category" colspan="3"></td>';
		    			html+=td2;	    			
		    			html+='</tr>';
		    		}
		    		$("#content").before(html);
		    		$(".answer").linkbutton();
		    		$(".answer").off("click");
		    		$(".answer").on("click", function(){
		    	    	var name="tktCustom.answer"+$(this).attr('data');
		    	    	//var name="tktCustom.answerMap['"+$(this).attr('data')+"'].answer";
		    	    	var input=$('<input type="text" name="'+name+'"/>');
		    	    	var button=$('<a  href="#" >删除</a>');
		    	    	$(this).parent().next().append(input);
		    	    	$(this).parent().next().append(button);
		    	    	button.click(function(){
		    	    		$(this).prev().remove();
		    	    		$(this).remove();
		    	    	});
		    	    	button.linkbutton();
		    	    	input.textbox();
		    		});
	    		}
	    		
	    		//根据课程名称查询对应章节信息

	    	}
	    });
	    
	    
});
	   
</script>
</head>
<body>


<form id="tktform" action="${baseurl}tkt/add.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>

   <TBODY>
   		<TR>
			<TD background=images/r_0.gif width="100%">
				<TABLE cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD>&nbsp;填空题题目信息</TD>
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
									<input id="kcname" class="easyui-combobox" data-options="required:true,editable:true,mode:'remote',url:'${baseurl}kc/jsonList.action?kcCustom.sysuseruuid=${sysuseruuid }',valueField:'name',textField:'name'" name="tktCustom.kcname" >
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="tkt_kcnameTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >难度类型：</TD>
							<TD class=category width="35%">
								<div>
									<input id="ndtype" class="easyui-combobox" data-options="required:true,editable:false,url:'${baseurl}dxt/ndTypeJsonList.action',valueField:'dictcode',textField:'info'" name="tktCustom.ndtype" >
								</div>
								<div id="tkt_ndtypeTip"></div>
							</TD>							
						</TR>
							
						<tr>
							<TD height=30 width="15%" align=right >题目内容：</TD>
							<TD class=category width="100%" colspan="3">
								<div>
									<input class="easyui-textbox" id="tktcontent" name="tktCustom.content" data-options="required:true,multiline:true,validType:'length[0,150]'" style="height:60px;width: 280px;"></input>
								</div>
								<div id="tkt_ndtypeTip"></div>
							</TD>
						</tr>
						<tr>
							<TD height=30 width="15%" align=right >答案处理方案：</TD>
							<TD class=category width="35%">
								<div>
								<input id="isprogram" class="easyui-combobox" 
								data-options="editable:false,data: [{
										key: '1',
										value: '去除前后空格',
										selected:'true'
									},{
										key: '2',
										value: '去除所有空格'
									},],valueField:'key',textField:'value'" 
								name="tktCustom.isprogram" >
								</div>
								<div id="tkt_isprogramTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >填空题空格格式：</TD>
							<TD class=category width="100%" colspan="3">
								___
							</TD>
						</tr>

						
						<tr >
							<TD height=30 width="15%" align=right >空格数：</TD>
							<TD class=category width="35%">
								<div>
								<input id="answernum" class="easyui-combobox" 
								data-options="editable:false,data: [{
										key: '',
										value: '请选择',
										selected:'true'
									},{
										key: '1',
										value: '1'
									},{
										key: '2',
										value: '2'
									},{
										key: '3',
										value: '3'
									},{
										key: '4',
										value: '4'
									},{
										key: '5',
										value: '5'
									}],valueField:'key',textField:'value'" 
								name="tktCustom.answernum" >
								</div>
								<div id="tkt_answerTip"></div>
							</TD>
							<TD height=30 width="15%" align=right >知识点：</TD>
							<td>
     						<input id="zsd" class="easyui-combobox" name="zsdList" />
							</td>
						</tr>	
						<tr>
							<TD height=30 width="15%" align=right >答案：</TD>
							<TD class=category width="35%">
								<div>
									<input type="text" id="tkt_answer" class="easyui-textbox" data-options="required:true" name="tktCustom.answer" />
								</div>
								<div id="tkt_answerTip"></div>
							</TD>
						</tr>					
						<tr id="content">
							  <td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="tktsave()">提交</a>
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