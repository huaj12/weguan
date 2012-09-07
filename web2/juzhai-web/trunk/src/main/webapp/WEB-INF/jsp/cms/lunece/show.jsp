<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>lunece 索引维护</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
function createPost(){
	var postId=$("#postId").val();
	jQuery.ajax({
		url: "/cms/lunece/post/add",
		type: "post",
		data: {"postId":postId},
		dataType: "json",
		success: function(result){
			alert("操作成功");
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}
function removePost(){
	var postId=$("#postId").val();
	jQuery.ajax({
		url: "/cms/lunece/post/del",
		type: "post",
		data: {"postId":postId},
		dataType: "json",
		success: function(result){
			alert("操作成功");
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}
function removeProfile(){
	var uid=$("#uid").val();
	jQuery.ajax({
		url: "/cms/lunece/profile/del",
		type: "post",
		data: {"uid":uid},
		dataType: "json",
		success: function(result){
			alert("操作成功");
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}
function createProfile(){
	var uid=$("#uid").val();
	jQuery.ajax({
		url: "/cms/lunece/profile/add",
		type: "post",
		data: {"uid":uid},
		dataType: "json",
		success: function(result){
			alert("操作成功");
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}
</script>
</head>
<body>
	<h2>lunece 索引维护</h2>
	post:<input id="postId" type="text"/><a href="javascript:void(0);" onclick="createPost();">创建索引</a>|<a href="javascript:void(0);" onclick="removePost();">删除索引</a>
	<br/>
	<br/>
	profile:<input id="uid" type="text"/><a href="javascript:void(0);" onclick="createProfile();">创建索引</a>|<a href="javascript:void(0);" onclick="removeProfile();">删除索引</a>
</body>
</html>