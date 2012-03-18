<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类别管理</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	function delete_preference(id){
		if(confirm("是否删除该条拒宅")){
			jQuery.ajax({
				url : "/cms/delete/preference",
				type : "get",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						location.reload();
					} else {
						alert("屏蔽失败");
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
	<h2>偏好设置排序</h2>
	<form action="/cms/cmsUpdateCategory" method="get">
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">题目名称</td>
				<td width="200">排序</td>
				<td width="250">删除</td>
			</tr>
			<c:forEach items="${prefernces}" var="p">
			<tr>
				<td>${p.name}</td>
				<td>${p.sequence}</td>
				<td>
				<a href="#" onclick="delete_preference('${p.id}')">屏蔽</a>
				<a href="/cms/show/update/preference?id=${p.id}" >修改</a>
				</td>
			</tr>	
			</c:forEach>
		</table>
		<!--  >input type="submit" value="保存" /-->
	</form>
</body>
</html>