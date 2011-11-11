<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<div class="i_w_g"><!--我想去的 begin-->
	<c:choose>
		<c:when test="${userActViewList.size() <= 0}"><div class="item_none">你还没有添加任何项目. <a href="/app/showCategoryActs">去添加>></a></div></c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="userActView" items="${userActViewList}" varStatus="status">
					<li <c:if test="${status.count%2==0}">class="mr0"</c:if> onmouseover="javascript:actHover(this, true)" onmouseout="javascript:actHover(this, false)">
						<p class="l"></p>
						<p class="r"></p>
						<a href="/app/showAct/${userActView.act.id}" class="btn">找伴</a>
						<a href="javascript:void(0);" onclick="javascript:removeAct(this);" actid="${userActView.act.id}" actname="${userActView.act.name}" class="close"></a>
						<div></div>
						<div class="photo"><a href="/app/showAct/${userActView.act.id}"><img src="${jz:actLogo(userActView.act.id,userActView.act.logo,80)}"  width="80" height="80"/></a></div>
						<h2><a href="/app/showAct/${userActView.act.id}"><c:out value="${userActView.act.name}"/></a></h2>
						<span>
							<c:choose>
								<c:when test="${userActView.act.popularity > 0}">
									<c:choose>
										<c:when test="${userActView.friendList.size() > 0}">
											<c:forEach var="friend" items="${userActView.friendList}" varStatus="status">
												<a href="/app/${friend.uid}" class="user">${friend.nickname}</a><c:if test="${!status.last}">、</c:if>
											</c:forEach>
											&nbsp;等<a href="/app/showAct/${userActView.act.id}?allUser=1" class="num">${userActView.act.popularity}</a>人想去
										</c:when>
										<c:otherwise>共<a href="/app/showAct/${userActView.act.id}?allUser=1" class="num">${userActView.act.popularity}</a>人想去</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									还没有人想去哦
								</c:otherwise>
							</c:choose>
						</span>
					</li>
				</c:forEach>
			</ul>
			<div class="page">
				<a href="javascript:;" class="link"  <c:if test="${pager.currentPage != 1}">onclick="javascript:pageMyAct(1);"</c:if>><p class="l"></p><strong class="m">首页</strong><p class="r"></p></a>
				<a href="javascript:;" class="link"  <c:if test="${pager.currentPage > 1}">onclick="javascript:pageMyAct(${pager.currentPage-1});"</c:if>><p class="l"></p><strong class="m">上一页</strong><p class="r"></p></a>
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">	
							<a href="javascript:;" class="link" onclick="javascript:pageMyAct(${pageId});"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
						</c:when>
						<c:otherwise>
							<a href="javascript:;" class="active"><p class="l"></p><strong class="m">${pageId}</strong><p class="r"></p></a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<a href="javascript:;" class="link" <c:if test="${pager.hasNext}">onclick="javascript:pageMyAct(${pager.currentPage+1});"</c:if>><p class="l"></p><strong class="m">下一页</strong><p class="r"></p></a>
				<a href="javascript:;" class="link" <c:if test="${pager.currentPage != pager.totalPage}">onclick="javascript:pageMyAct(${pager.totalPage});"</c:if>><p class="l"></p><strong class="m">尾页</strong><p class="r"></p></a>
			</div>
		</c:otherwise>
	</c:choose>
</div><!--我想去的 end-->