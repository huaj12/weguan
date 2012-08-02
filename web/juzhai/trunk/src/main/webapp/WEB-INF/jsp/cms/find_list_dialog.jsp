<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>调查对话</title>
</head>
<body>
		<h2>调查对话</h2>
		<br/>
						<table border="0" cellspacing="4">
							<tr style="background-color: #CCCCCC;">
								<td width="100">发送者</td>
								<td width="100">头像</td>
								<td width="100">接受者</td>
								<td width="100">头像</td>
								<td width="100">对话数</td>
								<td width="100">操作</td>
							</tr>
							<c:forEach var="dialogView" items="${dialogViewList}">
							<tr>
							<td><c:out value='${dialogView.userProfile.nickname}'></c:out></td>
								<td><a href="/home/${dialogView.userProfile.uid}"><img src="${jzr:userLogo(dialogView.userProfile.uid,dialogView.userProfile.logoPic,80)}"  width="80" height="80"/></a></td>
								<td><c:out value='${dialogView.targetProfile.nickname}'></c:out></td>
								<td><a href="/home/${dialogView.targetProfile.uid}"><img src="${jzr:userLogo(dialogView.targetProfile.uid,dialogView.targetProfile.logoPic,80)}"  width="80" height="80"/></a></td>
								<td>${dialogView.dialogContentCnt }</td>
								<td><a href="/cms/report/message/${dialogView.userProfile.uid}/${dialogView.targetProfile.uid}" target="_blank">点击查看内容</a></td>
							</tr>
							</c:forEach>
							<tr>
								<td colspan="2">
									<c:forEach var="pageId" items="${pager.showPages}">
										<c:choose>
											<c:when test="${pageId!=pager.currentPage}">
												<a href="/cms/find/list/dialog?pageId=${pageId}">${pageId}</a>
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