<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageUrl" value="${param.url}" />
<c:set var="queryParams" value="${param.queryParams}" />
<c:set var="urlRewrite" value="${param.urlRewrite}"></c:set>
<div class="page index_s1"><!--page begin-->
	<%-- <c:choose>
		<c:when test="${pager.currentPage != 1}"><a href="${pageUrl}/1${queryParams}" class="link pre"><p class="l"></p><strong class="c">首页</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link pre"><p class="l"></p><strong class="c">首页</strong><p class="r"></p></a></c:otherwise>
	</c:choose> --%>
	<c:choose>
		<c:when test="${pager.currentPage > 1}"><a href="<c:choose><c:when test="${not empty urlRewrite&&!urlRewrite}">${pageUrl}&pageId=${pager.currentPage-1}</c:when><c:otherwise>${pageUrl}/${pager.currentPage+-1}${queryParams}</c:otherwise></c:choose>" class="link pre"><p class="l"></p><strong class="c">&lt;</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link pre"><p class="l"></p><strong class="c">&lt;</strong><p class="r"></p></a></c:otherwise>
	</c:choose>
	<c:forEach var="pageId" items="${pager.showPages}">
		<c:choose>
			<c:when test="${pageId!=pager.currentPage}"><a href="<c:choose><c:when test="${not empty urlRewrite&&!urlRewrite}">${pageUrl}&pageId=${pageId}</c:when><c:otherwise>${pageUrl}/${pageId}${queryParams}</c:otherwise></c:choose>" class="link" onmouseover="javascript:$(this).removeClass('link').addClass('hover');" onmouseout="javascript:$(this).removeClass('hover').addClass('link');"><p class="l"></p><strong class="c">${pageId}</strong><p class="r"></p></a></c:when>
			<c:otherwise><a href="javascript:void(0);" class="active"><p class="l"></p><strong class="c">${pageId}</strong><p class="r"></p></a></c:otherwise>
		</c:choose>
	</c:forEach>
	<c:choose>
		<c:when test="${pager.hasNext}"><a href="<c:choose><c:when test="${not empty urlRewrite&&!urlRewrite}">${pageUrl}&pageId=${pager.currentPage+1}</c:when><c:otherwise>${pageUrl}/${pager.currentPage+1}${queryParams}</c:otherwise></c:choose>" class="link nex"><p class="l"></p><strong class="c">&gt;</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link nex"><p class="l"></p><strong class="c">&gt;</strong><p class="r"></p></a></c:otherwise>
	</c:choose>
	<%-- <c:choose>
		<c:when test="${pager.currentPage != pager.totalPage}"><a href="${pageUrl}/${pager.totalPage}${queryParams}" class="link nex"><p class="l"></p><strong class="c">末页</strong><p class="r"></p></a></c:when>
		<c:otherwise><a href="javascript:void(0);" class="link nex"><p class="l"></p><strong class="c">末页</strong><p class="r"></p></a></c:otherwise>
	</c:choose> --%>
</div><!--page end-->