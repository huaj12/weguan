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
				<li onmouseover="javascript:mouseHover(this, true);" onmouseout="javascript:mouseHover(this, false);">
					<p class="l"></p><p class="r"></p>
					<a href="#" class="iwg">❤ 我想去</a>
					<!-- <a href="#" class="ytj">已添加</a> -->
					<a href="#" class="whg">留 言</a>
					<div class="photo1"><a href="/app/showAct/${userActView.act.id}"><img src="${jz:actLogo(userActView.act.id,userActView.act.logo,80)}"  width="80" height="80"/></a></div>
					<h2><a href="${jz:tpHomeUrl(userActView.profileCache.tpIdentity,context.tpId)}" class="u" target="_blank"><c:out value="${userActView.profileCache.nickname}" /></a>最近想去玩<a href="/app/showAct/${userActView.act.id}" class="v"><c:out value="${userActView.act.name}"/></a></h2>
					<span>ta在<c:choose><c:when test="${userActView.profileCache.city > 0}">${jz:cityName(userActView.profileCache.city)}</c:when><c:otherwise>地球</c:otherwise></c:choose>&nbsp;&nbsp;更新于<fmt:formatDate value="${userActView.userAct.createTime}" pattern="yyyy.MM.dd"/></span>
					<em>${jz:truncate(userActView.act.intro,50,'...')}</em>
				</li>
			</c:forEach>
		</ul>
		<div class="page mr30">
			<a href="javascript:;" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageUserAct(${uid}, 1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
			<a href="javascript:;" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageUserAct(${uid}, ${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
			<c:forEach var="pageId" items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">	
						<a href="javascript:;" class="link" onclick="javascript:pageUserAct(${uid}, ${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<a href="javascript:;" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageUserAct(${uid}, ${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
			<a href="javascript:;" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageUserAct(${uid}, ${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
		</div>
	</c:otherwise>
</c:choose>