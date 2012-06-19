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
				url : "/cms/qplug/update/content",
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
</script>
</head>
<body>
<h2>q+ 消息推送 <font color="red">${msg}</font></h2>
<form  method="post"  enctype="multipart/form-data" action="/cms/qplug/import">
<select name="type">
		<option value="add">添加</option>
		<option value="new">新建</option>
	</select>
<input type="file" name="config"/>
<input type="submit" value="上传"/>
</form>
<h3><a href="/cms/qplug/state" target="_blank" >查看发送状态</a></h3>
<br/>
<form id="pushForm">
	<select name="type">
		<option value="new">新用户</option>
		<option value="old">老用户</option>
	</select>
	<input type="text" name="text"/>
	<a href="javascript:void(0);" onclick="update();">更新内容</a>
</form>
</body>
</html>