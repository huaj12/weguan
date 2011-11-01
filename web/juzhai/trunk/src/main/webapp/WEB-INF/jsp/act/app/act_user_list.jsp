<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
<c:when test="${actUserViewList.size() > 0}">
	<div class="item_list"><!--item_list begin-->
		<c:forEach var="actUserView" items="${actUserViewList}">
			<div class="item_w link" onmouseover="javascript:actUserHover(this,true);" onmouseout="javascript:actUserHover(this,false);">
				<em class="l"></em>
				<em class="r"></em>
				<p><img src="${actUserView.profileCache.logoPic}" width="50" height="50"/></p>
				<h3><c:out value="${actUserView.profileCache.nickname}" /></h3>
				<c:if test="${actUserView.friend}">
					<a href="javascript:inviteHer(this, ${actUserView.profileCache.uid});">约ta</a>
				</c:if>
				<span>ta在<c:choose><c:when test="${actUserView.profileCache.cityName != ''}">${actUserView.profileCache.cityName}</c:when><c:otherwise>地球</c:otherwise></c:choose>&nbsp;发布于<font><fmt:formatDate value="${actUserView.createTime}" pattern="yyyy.MM.dd"/></font></span>
			</div>
		</c:forEach>
	</div><!--item_list end-->
	<div class="page_dw">
		<div class="page">
			<a href="javascript:;" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageUser(${pageFriend}, 1);;"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
			<a href="javascript:;" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageUser(${pageFriend}, ${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
			<c:forEach var="pageId" items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">	
						<a href="javascript:;" class="link" onclick="javascript:pageUser(${pageFriend}, ${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<a href="javascript:;" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageUser(${pageFriend}, ${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
			<a href="javascript:;" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageUser(${pageFriend}, ${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
		</div>
	</div>
</c:when>
<c:otherwise>
	<div class="item_none"><!--item_none begin-->
	目前没人
	</div><!--item_none end-->
</c:otherwise>
</c:choose>
