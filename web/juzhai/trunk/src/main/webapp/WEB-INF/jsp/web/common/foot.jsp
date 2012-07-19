<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${footType == 'fixed'}"><c:set var="footClass" value="bottom_welcome" /></c:when>
	<c:when test="${footType == 'invite'}"><c:set var="footClass" value="bottom_welcome" /></c:when>
	<c:when test="${footType == 'welcome'}"><c:set var="footClass" value="bottom_welcome_wel" /></c:when>
	<c:otherwise><c:set var="footClass" value="bottom" /></c:otherwise>
</c:choose>
<c:if test="${footType != 'welcome'}">
	<div class="clear"></div>
</c:if>
<div class="${footClass}"><!--bottom_welcome begin-->
	<div class="bottom_area">
		<c:choose>
			<c:when test="${footType == 'welcome'}"><p>拒宅网©2012&nbsp;<c:if test="${empty isQplus || !isQplus}"><a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11031778号</a><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a><a href="/about/us" target="_blank">关于我们</a><a href="/searchusers" target="_blank">找伴儿</a><a href="/showideas" target="_blank">出去玩</a><b>|</b><em>友情链接：</em><a href="http://www.douban.com/" target="_blank">豆瓣</a><a href="http://www.xuejineng.cn/" target="_blank">学技能</a><a href="http://www.gzhong.cn/" target="_blank">工众网</a><a href="http://www.party021.com" target="_blank">上海派对网</a></c:if></p></c:when>
			<c:when test="${footType == 'invite'}"><p>拒宅网©2011 沪ICP备11031778号</p></c:when>
			<c:otherwise><div class="copy"><p>拒宅网©2012&nbsp;<a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11031778号</a></p></div><div class="other_link"><a href="/about/us">关于我们</a><a href="/about/join">加入我们</a><a href="javascript:void(0);" target-uid="2" target-nickname="拒宅网" class="feed-back">意见反馈</a><a href="http://weibo.com/51juzhai" target="_blank">官方微博</a></div></c:otherwise>
		</c:choose>
	</div>
</div><!--bottom_welcome end-->
<jsp:include page="count.jsp"/>