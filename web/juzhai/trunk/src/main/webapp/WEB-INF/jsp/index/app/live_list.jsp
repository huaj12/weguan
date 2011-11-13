<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<ul>
	<c:forEach var="actLiveView" items="${actLiveViewList}">
		<li onmouseover="javascript:mouseHover(this, true);" onmouseout="javascript:mouseHover(this, false);">
			<p class="l"></p><p class="r"></p>
			<a href="#" class="iwg">❤ 我想去</a>
			<a href="#" onclick="showAbout('${actLiveView.profileCache.nickname}','${actLiveView.act.id}','${actLiveView.act.name}','${actLiveView.profileCache.uid}');" class="whg">给ta留言</a>
			<div class="photo1"><a href="/app/${actLiveView.profileCache.uid}"><img src="${actLiveView.profileCache.logoPic}"  width="80" height="80"/></a></div>
			<div class="photo2"><a href="/app/showAct/${actLiveView.act.id}"><img src="${jz:actLogo(actLiveView.act.id,actLiveView.act.logo,80)}"  width="80" height="80"/></a></div>
			<h2><a href="/app/${actLiveView.profileCache.uid}" class="u"><c:out value="${actLiveView.profileCache.nickname}" /></a>最近想去<a href="/app/showAct/${actLiveView.act.id}" class="v"><c:out value="${actLiveView.act.name}" /></a></h2>
			<span>${jz:truncate(feed.act.intro,54,'...')}</span>
			<strong>
				<c:import url="/WEB-INF/jsp/common/fragment/user_city.jsp">
					<c:param name="cityId" value="${actLiveView.profileCache.city}"/>
				</c:import>&nbsp;&nbsp;<c:set var="date" value="${actLiveView.time}" scope="request"/><c:import url="/WEB-INF/jsp/common/fragment/show_time.jsp" />
			</strong>
		</li>
	</c:forEach>
</ul>
<div class="page">
	<a href="javascript:void(0);" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageLive(1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
	<a href="javascript:void(0);" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageLive(${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
	<c:forEach var="pageId" items="${pager.showPages}">
		<c:choose>
			<c:when test="${pageId!=pager.currentPage}">	
				<a href="javascript:void(0);" class="link" onclick="javascript:pageLive(${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
			</c:when>
			<c:otherwise>
				<a href="javascript:void(0);" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<a href="javascript:void(0);" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageLive(${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
	<a href="javascript:void(0);" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageLive(${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
</div>