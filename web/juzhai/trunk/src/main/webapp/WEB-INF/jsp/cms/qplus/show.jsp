<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>q+ 消息推送</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	function update(){
		if(confirm("是否更新发送内容？")){
			jQuery.ajax({
				url : "/cms/qplus/update/content",
				type : "post",
				data :$("#pushForm").serialize(),
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("更新成功");
					} else {
						alert(result.errorInfo);
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
	}
	function stop(){
		var type=$("#push-user-type").val();
		var text="";
		if(type=="new"){
			text="是否确定停止给新用户发消息";
		}else{
			text="是否确定停止给老用户发消息";
		}
		if(confirm(text)){
			jQuery.ajax({
				url : "/cms/qplus/push/stop",
				type : "post",
				data : {
					"type" : type
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("停止发送");
					} else {
						alert("操作失败");
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
	}
	function start(){
		var type=$("#push-user-type").val();
		var text="";
		if(type=="new"){
			text="是否确定给新用户发消息";
		}else{
			text="是否确定给老用户发消息";
		}
		if(confirm(text)){
			jQuery.ajax({
				url : "/cms/qplus/push/start",
				type : "post",
				data :$("#pushForm").serialize(),
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("开始发送");
					} else {
						alert("操作失败");
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
	}
	function selectPushContent(obj){
		if(obj.value==0){
			$("#push-content").show();
		}else if(obj.value==1){
			$("#push-content").hide();
		}
	}	
</script>
</head>
<body>
<h2>q+ 消息推送 <font color="red">${msg}</font></h2>
<form  method="post"  enctype="multipart/form-data" action="/cms/qplus/import">
<select name="type">
		<option value="add">添加</option>
		<option value="new">新建</option>
	</select>
<input type="file" name="config"/>
<input type="submit" value="上传"/>
</form>
<br/>
<form id="pushForm">
	<select id="push-user-type" name="type">
		<option value="new">新用户</option>
		<option value="old">老用户</option>
	</select>
	发送内容：
	<select name="sendType" onchange="selectPushContent(this)">
		<option value="1">好主意模版</option>
		<option value="0">自定义</option>
	</select>
	<div id="push-content" style="display: none">
		<input type="text" name="text"/>
		<input type="text" name="link"/>
	</div>
	<a href="javascript:void(0);" onclick="update();">更新内容</a>
</form>
<h3><a href="/cms/qplus/state" target="_blank" >查看状态</a></h3>
<a href="javascript:void(0);" onclick="start();">开始发送</a>
<a href="javascript:void(0);" onclick="stop();">停止</a>
</body>
</html>