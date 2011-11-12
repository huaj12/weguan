<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<ul>
	<c:forEach var="categoryActView" items="${categoryActViewList}" varStatus="status">
		<li <c:if test="${status.count%2==0}">class="mr0"</c:if> onmouseover="javascript:actHover(this, true)" onmouseout="javascript:actHover(this, false)">
			<p class="l"></p><p class="r"></p>
			<c:choose>
				<c:when test="${categoryActView.hasUsed}"><a class="wg unclick" href="javascript:void(0);">已添加</a></c:when>
				<c:otherwise><a href="javascript:void(0);" class="wg" onclick="javascript:addCategoryAct(this);" actid="${categoryActView.act.id}">我想去</a></c:otherwise>
			</c:choose>
			<div></div>
			<div class="photo"><a href="/app/showAct/${categoryActView.act.id}"><img src="${jz:actLogo(categoryActView.act.id,categoryActView.act.logo,80)}"  width="80" height="80"/></a></div>
			<h2><a href="/app/showAct/${categoryActView.act.id}"><c:out value="${categoryActView.act.name}" /></a></h2>
			<span>${jz:truncate(categoryActView.act.intro,68,'...')}</span>
			<h5><a href="/app/showAct/${categoryActView.act.id}?allUser=1">${categoryActView.act.popularity}</a>人想去</h5>
		</li>
	</c:forEach>
</ul>
<div class="page">
		<a href="javascript:;" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageCategoryAct(${categoryId}, 1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
		<a href="javascript:;" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageCategoryAct(${categoryId}, ${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
		<c:forEach var="pageId" items="${pager.showPages}">
			<c:choose>
				<c:when test="${pageId!=pager.currentPage}">	
					<a href="javascript:;" class="link" onclick="javascript:pageCategoryAct(${categoryId}, ${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<a href="javascript:;" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageCategoryAct(${categoryId}, ${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
		<a href="javascript:;" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageCategoryAct(${categoryId}, ${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
	</div>