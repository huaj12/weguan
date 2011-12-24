<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${friendViewList.size() <= 0}"><div class="item_none">你的好友还没有加入拒宅器哦</div></c:when>
	<c:otherwise>
		<ul>
			<c:forEach var="friendView" items="${friendViewList}">
				<li onmouseover="javascript:mouseHover(this, true);" onmouseout="javascript:mouseHover(this, false);">
					<p class="l"></p><p class="r"></p>
					<a href="javascript:void(0);" onclick="showAbout('${friendView.profile.nickname}','','','${friendView.profile.uid}');" class="msgh">给ta留言</a>
					<div class="photo1"><a href="/app/${friendView.profile.uid}"><img src="${jzr:userLogo(friendView.profile.uid,friendView.profile.logoPic,80)}" width="80" height="80"/></a></div>
					<h2><a href="/app/${friendView.profile.uid}" class="u"><c:out value="${friendView.profile.nickname}" /></a>最近想去:</h2>
					<div class="xq"><c:forEach var="act" items="${friendView.actList}"><a href="/app/showAct/${act.id}"><c:out value="${act.name}" /></a></c:forEach><c:if test="${friendView.actCnt > 0}"><b>等<a href="/app/${friendView.profile.uid}"></a>${friendView.actCnt}个项目</b></c:if></div>
					<em>ta在<c:choose><c:when test="${friendView.profile.city > 0}">${jz:cityName(friendView.profile.city)}</c:when><c:otherwise>地球</c:otherwise></c:choose>&nbsp;&nbsp;更新于<fmt:formatDate value="${friendView.profile.lastUpdateTime}" pattern="yyyy.MM.dd"/></em>
				</li>
			</c:forEach>
		</ul>
		<div class="page">
			<a href="javascript:;" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageFriend(1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
			<a href="javascript:;" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageFriend(${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
			<c:forEach var="pageId" items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">	
						<a href="javascript:;" class="link" onclick="javascript:pageFriend(${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<a href="javascript:;" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageFriend(${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
			<a href="javascript:;" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageFriend(${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
		</div>
	</c:otherwise>
</c:choose>