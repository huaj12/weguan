<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看消息</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
</head>
<body>
	<h2>
	查看消息
	</h2>
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">发送者</td>
				<td width="200">举报内容</td>
				<td width="250">接收者</td>
				<td width="250">发送时间</td>
			</tr>
			<c:forEach items="${dialogContentViewList}" var="view">
				<tr>
					<td>${view.profile.nickname }</td>
					<td>${view.dialogContent.content }</td>
					<td>${view.receiverProfile.nickname }</td>
					<td><fmt:formatDate value="${view.dialogContent.createTime}"
						pattern="yyyy.MM.dd HH:mm" /></td>
				</tr>
			</c:forEach>
			<td colspan="3">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/report/message/${tagerUid}/${uid}?pageId=${pageId}">${pageId}</a>
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