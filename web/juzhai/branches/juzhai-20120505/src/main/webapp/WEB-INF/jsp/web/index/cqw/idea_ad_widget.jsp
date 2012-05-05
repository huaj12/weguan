<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty ads}">
	<div class="content_box w285"><!--content begin-->
		<div class="t"></div>
		<div class="m">
			<div class="right_title"><h2>优惠推荐</h2></div>
			<div class="preferential"><!--preferential begin-->
				<c:forEach items="${ads}" var="ad">
					<div class="item"><!--item begin-->
						<p><a href="${ad.link }" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>><img src="${ad.picUrl }" width="250" height="170" /></a></p>
						<span><a href="${ad.link }" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>${jzu:truncate(ad.name,70,'...')}</a></span>
						<b>团购价：${ad.price}元</b>
						<strong>${ad.district}</strong>
					</div><!--item end-->
				</c:forEach>
			</div><!--preferential end-->
		</div>
		<div class="t"></div>
	</div><!--content end-->
</c:if>