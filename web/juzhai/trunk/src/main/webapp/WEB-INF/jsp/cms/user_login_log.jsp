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
<title>查询用户登陆日志</title>
</head>
<body>
		<h2>查询用户登陆记录</h2>
		<form action="/cms/find/userLoginLog" method="get">
			用户uid：<input type="text" name="uid" value="${profile.uid }" />
				<input type="submit" value="查看"/>
		</form>	
		<br/>
		<c:if test="${not empty profile }">
		<font color="red"><c:out value="${profile.nickname}"></c:out></font>的登陆日志
		</c:if>
		<br/>
		<c:choose>
			<c:when test="${empty loginLogList &&not empty profile.uid }">
				<div class="none">用户没有登陆记录</div>
			</c:when>
			<c:otherwise>
						<table border="0" cellspacing="4">
							<tr style="background-color: #CCCCCC;">
								<td width="100">ip</td>
								<td width="200">登陆时间</td>
							</tr>
							<c:forEach var="log" items="${loginLogList}">
							<tr>
								<td>${log.ip }</td>
								<td><fmt:formatDate value="${log.loginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							</c:forEach>
							<tr>
								<td colspan="2">
									<c:forEach var="pageId" items="${pager.showPages}">
										<c:choose>
											<c:when test="${pageId!=pager.currentPage}">
												<a href="/cms/find/userLoginLog?pageId=${pageId}&uid=${profile.uid}">${pageId}</a>
											</c:when>
											<c:otherwise>
												<strong>${pageId}</strong>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</td>
							</tr>	
						</table>
			</c:otherwise>
		</c:choose>
</body>
</html>