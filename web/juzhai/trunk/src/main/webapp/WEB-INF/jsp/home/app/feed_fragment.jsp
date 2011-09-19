<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="top"></div>
<div class="mid"><!--mid begin-->
	<div class="jz_box"><!--jz_box begin-->
		<c:choose>
			<c:when test="${feed.feedType.name=='SPECIFIC'}">
				<div class="photo fl"><img src="${feed.profileCache.logoPic}" /></div>
				<div class="infor fl" data="{'friendId':feed.${profileCache.uid},'actId':${feed.act.id},'times':${times}}"><!--infor begin-->
					<h2><span class="u"><c:out value="${feed.profileCache.nickname}" /></span><span class="w">最近想去</span><span class="v"><c:out value="${feed.act.name}" /></span></h2>
					<p><fmt:formatDate value="${feed.date}" pattern="YYYY.MM.DD"/>&nbsp;&nbsp;&nbsp;&nbsp;${feed.profileCache.province}&nbsp;${feed.profileCache.city}</p>
					<a href="#" class="want" onclick="javascript:response(this, 1);">我也想去</a><a href="#" class="dwant" onclick="javascript:response(this, 2);">没兴趣</a>
				</div><!--infor end-->
			</c:when>
			<c:when test="${feed.feedType.name=='GRADE'}">
				<div class="photo fl"><img src="images/pic.png" /></div>
				<div class="df fl"><!--infor begin-->
					<h2><span class="w">你觉得</span><span class="u"><c:out value="${feed.tpFriend.name}" /></span><span class="w">有多宅？</span></h2>
					<div class="star"><!--star begin-->
						<span class="link" onmouseover="javascript:holdStar(1);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(1, '${feed.tpFriend.userId}', ${times});" title="有点宅"></span>
						<span class="link" onmouseover="javascript:holdStar(2);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(2, '${feed.tpFriend.userId}', ${times});" title="比较宅"></span>
						<span class="link" onmouseover="javascript:holdStar(3);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(3, '${feed.tpFriend.userId}', ${times});" title="比我宅"></span>
						<span class="link" onmouseover="javascript:holdStar(4);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(4, '${feed.tpFriend.userId}', ${times});" title="很宅"></span>
						<span class="link" onmouseover="javascript:holdStar(5);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(5, '${feed.tpFriend.userId}', ${times});" title="相当宅"></span>
						<p class="zai"></p>
					</div><!--star end-->
				</div><!--infor end-->
			</c:when>
			<c:otherwise>
			<!-- 提示页面 -->
			</c:otherwise>
		</c:choose>
	</div><!--jz_box end-->
</div><!--mid end-->
<div class="bot"></div>
<c:if test="${feed!=null&&feed.feedType.name=='GRADE'}">
	<div class="next_btn"><a href="#">跳  过</a></div>
</c:if>