<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content_box w285"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="right_title"><h2><c:choose><c:when test="${cityId > 0}">${jzd:cityName(cityId)}</c:when><c:otherwise>全国</c:otherwise></c:choose>拒宅好主意</h2></div>
		<div class="jz_goodidea"><!--jz_goodidea begin-->
			<ul>
				<c:choose>
					<c:when test="${not empty orderType}">
						<c:set value="/showideas/0/${orderType}/1" var="catAllUrl"></c:set>
					</c:when>
					<c:otherwise>
						<c:set value="/showrecideas/0/1" var="catAllUrl"></c:set>
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty totalCount&&totalCount>0}"><li <c:if test="${categoryId <= 0}">class="act"</c:if>><p class="ca_all"></p><a href="${catAllUrl}" class="ca">全部</a><a href="${catAllUrl}" class="all_num">${totalCount}</a></li></c:if>
				<c:forEach var="categoryView" items="${categoryViewList}">
					<c:choose>
						<c:when test="${not empty orderType}">
							<c:set value="/showideas/${categoryView.category.id}/${orderType}/1" var="categoryUrl"></c:set>
						</c:when>
						<c:otherwise>
							<c:set value="/showrecideas/${categoryView.category.id}/1" var="categoryUrl"></c:set>
						</c:otherwise>
					</c:choose>
					<li <c:if test="${categoryId == categoryView.category.id}">class="act"</c:if>><p class="${categoryView.category.icon}"></p><a href="${categoryUrl}" class="ca">${categoryView.category.name}</a><a href="${categoryUrl}" class="all_num">${categoryView.ideaCount}</a></li>
				</c:forEach>
			</ul>
		</div><!--jz_goodidea end-->
	</div>
	<div class="t"></div>
</div><!--content end-->