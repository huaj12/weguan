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
<title>审核好主意</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function del(obj){
	var id=$(obj).attr("raw-idea-id");
	jQuery.ajax({
		url : "/cms/del/rawIdea",
		type : "post",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				$(obj).removeAttr("onclick").text("已拒绝");
			} else {
				alert("操作失败刷新后重试");
			}
		},
		statusCode : {
			401 : function() {
				alert("请先登陆");
			}
		}
	});
}
</script>
</head>
<body>
			<h2>审核好主意</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">创建者</td>
			<td width="300">标题</td>
			<td width="100">发布时间</td>
			<td width="150">操作</td>
		</tr>
		<c:forEach var="view" items="${rawIdeaViews}" >
			<tr>
				<td><c:out value="${view.createUser.nickname }"></c:out></td>
				<td><c:out value="${view.rawIdea.content}"></c:out></td>
				<td><fmt:formatDate value="${view.rawIdea.createTime}" pattern="yyyy-MM-dd" /></td>
				<td> <a href="javascript:vioid(0);" raw-idea-id=${view.rawIdea.id} onclick="del(this)">拒绝</a> <a href="/cms/show/rawIdea/update?rawIdeaId=${view.rawIdea.id}">查看详情</a></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/list/rawIdea?pageId=${pageId}">${pageId}</a>
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