<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="p_right"><!--p_right begin-->
	<div class="project_box"><!--project_box begin-->
		<h2>召集好友同去</h2>
		<div class="share"><!--share begin-->
			<p>分享到：</p>
			<a href="#"><img src="${jzr:static('/images/web/wb_s.jpg')}" /></a>
			<a href="#"><img src="${jzr:static('/images/web/rr_s.jpg')}" /></a>
			<a href="#"><img src="${jzr:static('/images/web/kx_s.jpg')}" /></a>
		</div><!--share end-->
	</div><!--project_box end-->
	<div class="project_box"><!--project_box begin-->
		<h2>相关链接</h2>
		<div class="link"><!--link begin-->
			<c:forEach var="actLink" items="${actLinkList}">
				<p><a href="${actLink.link}">${actLink.name}》</a>来自${actLink.source}</p>
			</c:forEach>
		</div><!--link end-->
	</div><!--project_box end-->
	<div class="project_box" style="background:none;"><!--project_box begin-->
		<h2>相关优惠</h2>
		<div class="tuangou"><!--tuangou begin-->
			<c:forEach var="actAd" items="${actAdList}">
				<div class="item_tg"><!--item_tg begin-->
					<div class="tg_pic"><a href="${actAd.link}"><img src="${actAd.picUrl}" /></a></div>
					<a href="${actAd.link}">${actAd.name}</a>
					<h3><p>团购价：<b>${actAd.price}元</b></p><em>${actAd.address}</em></h3>
				</div><!--item_tg end-->
			</c:forEach>
		</div><!--tuangou end-->
	</div><!--project_box end-->
</div><!--p_right end-->