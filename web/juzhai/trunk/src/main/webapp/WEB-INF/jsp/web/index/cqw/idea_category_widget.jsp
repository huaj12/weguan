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
				<li <c:if test="${categoryId <= 0}">class="act"</c:if>><p class="ca_all"></p><a href="/showideas/0/${orderType}/1" class="ca">全部</a><a href="/showideas/0/${orderType}/1" class="all_num">${totalCount}</a></li>
				<c:forEach var="categoryView" items="${categoryViewList}">
					<li <c:if test="${categoryId == categoryView.category.id}">class="act"</c:if>><p class="${categoryView.category.icon}"></p><a href="/showideas/${categoryView.category.id}/${orderType}/1" class="ca">${categoryView.category.name}</a><a href="/showideas/${categoryView.category.id}/${orderType}/1" class="all_num">${categoryView.ideaCount}</a></li>
				</c:forEach>
			</ul>
		</div><!--jz_goodidea end-->
	</div>
	<div class="t"></div>
</div><!--content end-->