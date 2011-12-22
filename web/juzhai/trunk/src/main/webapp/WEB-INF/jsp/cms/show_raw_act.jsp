<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>审核推荐项目列表</title>
<script type="text/javascript" src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
	function removeRawAct(id){
		jQuery.ajax({
			url: "/cms/ajax/delRawAct",
			type: "post",
			data: {"id":id},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					$("#op_"+actId).text("已删除");
				}else{
					alert(result.errorInfo);
				}
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			}
		});
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>审核推荐项目列表</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">ID</td>
			<td width="100">项目名称</td>
			<td width="100">创建时间</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach var="raw" items="${rawActs}" varStatus="status">
			<tr>
				<td>${raw.id}</td>
				<td><c:out value="${raw.name}" /></td>
				<td width="100">创建时间</td>
				<td id="op_${raw.id}"><a href="javascript:;" onclick="javascript:removeRawAct('${raw.id}');">删除</a>
				<a href="/cms/showManagerRawAct?id=${raw.id}" >详细</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/showRawActs?pageId=${pageId}">${pageId}</a>
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