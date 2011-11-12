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
				<p><a href="/app/${actUserView.profileCache.uid}"><img src="${actUserView.profileCache.logoPic}" width="50" height="50"/></a></p>
				<h3><a href="/app/${actUserView.profileCache.uid}" class="user"><c:out value="${actUserView.profileCache.nickname}" /></a></h3>
				<a href="javascript:void(0);" class="btn" onclick="javascript:inviteHer(this, ${actUserView.profileCache.uid});">留言</a>
				<span>ta在<c:choose><c:when test="${actUserView.profileCache.cityName != ''}">${actUserView.profileCache.cityName}</c:when><c:otherwise>地球</c:otherwise></c:choose>&nbsp;发布于<font><fmt:formatDate value="${actUserView.createTime}" pattern="yyyy.MM.dd"/></font></span>
			</div>
		</c:forEach>
	</div><!--item_list end-->
	<div class="page">
		<a href="javascript:;" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageUser(${pageFriend}, 1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
		<a href="javascript:;" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageUser(${pageFriend}, ${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
		<c:forEach var="pageId" items="${pager.showPages}">
			<c:choose>
				<c:when test="${pageId!=pager.currentPage}">	
					<a href="javascript:;" class="link" onclick="javascript:javascript:pageUser(${pageFriend}, ${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<a href="javascript:;" class="link" <c:if test="${pager.hasNext}">onclick="javascript:javascript:pageUser(${pageFriend}, ${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
		<a href="javascript:;" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageUser(${pageFriend}, ${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
	</div>
</c:when>
<c:otherwise>
	<div class="item_none">这里没有人<br /><a href="javascript:void(0);" onclick="javascript:request();">邀请好友加入</a></div>
</c:otherwise>
</c:choose>