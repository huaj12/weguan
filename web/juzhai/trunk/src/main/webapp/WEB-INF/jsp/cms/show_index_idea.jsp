<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>维护首页好主意封面</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
	function addIdea(){
		var ideaId=$("#ideaId").val();
		jQuery.ajax({
			url: "/cms/add/index/idea",
			type: "post",
			data: {"ideaId":ideaId},
			dataType: "json",
			success: function(result){
				location.reload();
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			}
		});
	}
	function delIdea(ideaId){
		jQuery.ajax({
			url: "/cms/del/index/idea",
			type: "post",
			data: {"ideaId":ideaId},
			dataType: "json",
			success: function(result){
				location.reload();
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
	<h2>添加首页好主意<input value="" id="ideaId"/><input type="button" value="添加" onclick="addIdea();"/></h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="200">好主意图片</td>
			<td width="300">好注意内容</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach var="idea" items="${ideas}" >
			<tr>
				<td>
					<img src="${jzr:ideaPic(idea.id,idea.pic, 200)}" width="200"  height="200" /> 
				</td>
					<td>
					${idea.content}
				</td>
				<td><a href="javascript:void(0);" onclick="delIdea('${idea.id}');">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>