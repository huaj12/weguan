<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h3>ta想去的（${pager.totalResults}）</h3>
<div class="clear"></div>
<c:choose>
	<c:when test="${userActViewList.size() <= 0}"><div class="item_none2">ta还没有添加拒宅兴趣</div></c:when>
	<c:otherwise>
		<ul>
			<c:forEach var="userActView" items="${userActViewList}">
				<li>
					<p><a href="/app/showAct/${userActView.act.id}"><img src="${jz:actLogo(userActView.act.id,userActView.act.logo,80)}"  width="80" height="80"/></a></p>
					<h2><a href="/app/showAct/${userActView.act.id}"><c:out value="${userActView.act.name}"/></a></h2>
				</li>
			</c:forEach>
		</ul>
		<div class="page">
			<a href="javascript:void(0);" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageUserAct(${uid}, 1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
			<a href="javascript:void(0);" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageUserAct(${uid}, ${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
			<c:forEach var="pageId" items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">	
						<a href="javascript:void(0);" class="link" onclick="javascript:pageUserAct(${uid}, ${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<a href="javascript:void(0);" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageUserAct(${uid}, ${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
			<a href="javascript:void(0);" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageUserAct(${uid}, ${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
		</div>
	</c:otherwise>
</c:choose>