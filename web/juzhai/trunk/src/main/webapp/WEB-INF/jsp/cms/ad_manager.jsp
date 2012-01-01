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
<title>优惠信息管理</title>
<style type="text/css">
</style>
</head>
<body>
	<h2>审核推荐项目列表</h2>
	<c:if test="${!empty msg}"><h3>成功导入${msg }个优惠信息</h3></c:if>
	城市：<select>
	<c:forEach var="city" items="${citys}">
		<option value="${city.id }">${city.name }</option>
	</c:forEach>
	</select>
	<table border="0" cellspacing="4">
	<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/ad/manager?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
		<tr style="background-color: #CCCCCC;">
			<td width="100">操作</td>
			<td width="100">图片</td>
			<td width="180">名称</td>
			<td width="50">价格</td>
			<td width="50">原价</td>
			<td width="50">折扣</td>
			<td width="100">来源</td>
			<td width="50">城市</td>
			<td width="100">地段</td>
			<td width="100">有效期</td>
		</tr>
		<c:forEach var="ad" items="${ads}">
		<tr>
			<td>发布</td>
			<td><a href="${ad.targetUrl}" target="_blank"><img src="${ad.img}" width="100" height="100"/></a></td>
			<td><a href="${ad.targetUrl}" target="_blank">${ad.title}</a></td>
			<td>${ad.price}</td>
			<td>${ad.original}</td>
			<td>${ad.discount}</td>
			<td>${ad.source}</td>
			<td>${cityMap[ad.city].name}</td>
			<td>${ad.address}</td>
			<td>
			<fmt:formatDate value="${ad.startDate}"
						pattern="yyyy-MM-dd" />
			至<fmt:formatDate value="${ad.endDate}"
						pattern="yyyy-MM-dd" /></td>
		</tr>	
		</c:forEach>
		<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/ad/manager?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
	</table>
</body>
</html>