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
function initPushUser(){
		jQuery.ajax({
			url : "/cms/qplug/initPushUser",
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					alert("初始化成功");
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
	function stop(){
		var type=$("select[name=type]").val();
		var text="";
		if(type=="new"){
			text="是否确定停止给新用户发消息";
		}else{
			text="是否确定停止给老用户发消息";
		}
		if(confirm(text)){
			jQuery.ajax({
				url : "/cms/qplug/stop",
				type : "post",
				data : {
					"type" : type
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("操作成功");
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
	function send(){
		var type=$("select[name=type]").val();
		var text="";
		if(type=="new"){
			text="是否确定给新用户发消息";
		}else{
			text="是否确定给老用户发消息";
		}
		if(confirm(text)){
			jQuery.ajax({
				url : "/cms/qplug/push",
				type : "post",
				data :$("#pushForm").serialize(),
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("开始发送");
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
</script>
</head>
<body>
<h2>q+ 消息推送 <a href="javascript:void(0);" onclick="initPushUser();" >初始化发送列表</a></h2>
<h3><a href="/cms/qplug/state" target="_blank" >查看发送状态</a></h3>
<br/>
<form id="pushForm">
	<select name="type">
		<option value="new">新用户</option>
		<option value="old">老用户</option>
	</select>
	<input type="text" name="text"/>
	<a href="javascript:void(0);" onclick="send();">发送</a>
</form>
<a href="javascript:void(0);" onclick="stop();">停止发送（请先选择停止发送用户类型）</a>
</body>
</html>