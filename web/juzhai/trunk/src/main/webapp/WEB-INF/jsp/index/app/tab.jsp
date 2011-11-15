<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<div class="title">
	<h2>
		<span <c:if test="${tab == 'live'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a <c:choose><c:when test="${tab == 'live'}">href="javascript:void(0);"</c:when><c:otherwise>href="/app/showLive"</c:otherwise></c:choose>>拒宅直播</a></span>
		<span <c:if test="${tab == 'rank'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a <c:choose><c:when test="${tab == 'rank'}">href="javascript:void(0);"</c:when><c:otherwise>href="/app/showRank"</c:otherwise></c:choose>>近期热门</a></span>
		<span <c:if test="${tab == 'category'}">class="hover"</c:if>><p class="l"></p><p class="r"></p><a <c:choose><c:when test="${tab == 'category'}">href="javascript:void(0);"</c:when><c:otherwise>href="/app/showCategoryActs"</c:otherwise></c:choose>>拒宅项目库</a></span>
	</h2>
</div>