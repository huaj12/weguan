<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>举报管理</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
	<script>
	
	function unShieldReport(uid){
		if(confirm("是否为该用户解除锁定")){
		jQuery.ajax({
			url : "/cms/report/unshield",
			type : "post",
			data : {
				"uid":uid
			},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					location.reload();
				} else {
					alert("处理失败");
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
	<h2>
		已屏蔽列表
	</h2>
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">uid</td>
				<td width="200">昵称</td>
				<td width="250">操作</td>
				<c:forEach items="${views }" var="view">
				<tr>
					<td>${view.profile.uid}</td>
					<td><a href="/home/${view.profile.uid}" target="_blank">${view.profile.nickname}</a></td>
					<td>
							<a onclick="unShieldReport('${view.profile.uid}')" href="#">解禁</a>
					</td>
				</tr>
				</c:forEach>
			</tr>
			<tr>
			<td colspan="3">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/lock/user?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
		</table>
</body>
</html>