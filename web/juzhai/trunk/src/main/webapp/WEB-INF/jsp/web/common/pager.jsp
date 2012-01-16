<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageUrl" value="${param.url}" />
<c:set var="queryParams" value="${param.queryParams}" />
<div class="page"><!--page begin-->
	<c:choose>
		<c:when test="${pager.currentPage != 1}"><a href="${pageUrl}/1${queryParams}" class="link pre"><p class="l"></p><strong class="c">首页</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link pre"><p class="l"></p><strong class="c">首页</strong><p class="r"></p></a></c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pager.currentPage > 1}"><a href="${pageUrl}/${pager.currentPage-1}${queryParams}" class="link pre"><p class="l"></p><strong class="c">上一页</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link pre"><p class="l"></p><strong class="c">上一页</strong><p class="r"></p></a></c:otherwise>
	</c:choose>
	<c:forEach var="pageId" items="${pager.showPages}">
		<c:choose>
			<c:when test="${pageId!=pager.currentPage}"><a href="${pageUrl}/${pageId}${queryParams}" class="link" onmouseover="javascript:$(this).removeClass('link').addClass('hover');" onmouseout="javascript:$(this).removeClass('hover').addClass('link');"><p class="l"></p><strong class="c">${pageId}</strong><p class="r"></p></a></c:when>
			<c:otherwise><a href="javascript:void(0);" class="active"><p class="l"></p><strong class="c">${pageId}</strong><p class="r"></p></a></c:otherwise>
		</c:choose>
	</c:forEach>
	<c:choose>
		<c:when test="${pager.hasNext}"><a href="${pageUrl}/${pager.currentPage+1}${queryParams}" class="link nex"><p class="l"></p><strong class="c">下一页</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link nex"><p class="l"></p><strong class="c">下一页</strong><p class="r"></p></a></c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pager.currentPage != pager.totalPage}"><a href="${pageUrl}/${pager.totalPage}${queryParams}" class="link nex"><p class="l"></p><strong class="c">末页</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link nex"><p class="l"></p><strong class="c">末页</strong><p class="r"></p></a></c:otherwise>
	</c:choose>
</div><!--page end-->