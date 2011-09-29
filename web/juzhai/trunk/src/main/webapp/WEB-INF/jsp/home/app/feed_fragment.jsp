<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="top"></div>
<div class="mid"><!--mid begin-->
	<div class="jz_box"><!--jz_box begin-->
		<c:choose>
			<c:when test="${feed.feedType=='SPECIFIC'}">
				<div class="photo fl"><img src="${feed.profileCache.logoPic}" /></div>
				<div class="infor fl" data="{'friendId':${feed.profileCache.uid},'actId':${feed.act.id},'times':${times}}"><!--infor begin-->
					<h2><span class="u"><c:out value="${feed.profileCache.nickname}" /></span><span class="w">最近想去</span><span class="v"><c:out value="${feed.act.name}" /></span></h2>
					<p><fmt:formatDate value="${feed.date}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;&nbsp;&nbsp;${feed.profileCache.provinceName}&nbsp;${feed.profileCache.cityName}</p>
					<a href="#" class="want" onclick="javascript:response(this, 1);" tip="点击“我也想去”，告知所有有同样兴趣的好友" onmouseover="javascript:tip(this, true);" onmouseout="javascript:tip(this, false);"></a>
					<a href="#" class="dwant" onclick="javascript:response(this, 2);" tip="切换到下一张，什么都不做" onmouseover="javascript:tip(this, true);" onmouseout="javascript:tip(this, false);"></a>
				</div><!--infor end-->
			</c:when>
			<c:when test="${feed.feedType=='GRADE'}">
				<div class="photo fl"><img src="${feed.tpFriend.logoUrl}" /></div>
				<div class="df fl"><!--infor begin-->
					<h2><span class="w">你觉得</span><span class="u"><c:out value="${feed.tpFriend.name}" /></span><span class="w">有多宅？</span></h2>
					<div class="star"><!--star begin-->
						<span class="link" onmouseover="javascript:holdStar(1);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(1, '${feed.tpFriend.userId}', ${times});" title="有点宅"></span>
						<span class="link" onmouseover="javascript:holdStar(2);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(2, '${feed.tpFriend.userId}', ${times});" title="比较宅"></span>
						<span class="link" onmouseover="javascript:holdStar(3);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(3, '${feed.tpFriend.userId}', ${times});" title="比我宅"></span>
						<span class="link" onmouseover="javascript:holdStar(4);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(4, '${feed.tpFriend.userId}', ${times});" title="很宅"></span>
						<span class="link" onmouseover="javascript:holdStar(5);" onmouseout="javascript:holdStar(-1);" onclick="javascript:grade(5, '${feed.tpFriend.userId}', ${times});" title="相当宅"></span>
					</div><!--star end-->
					<strong class="henzai"></strong>
				</div><!--infor end-->
			</c:when>
			<c:otherwise>
				<div class="none_message"><!--none_message begin-->
					<span>试试<a href="#" onclick="javascript:kaixinRequest();">邀请更多好友加入</a>！</span>
				</div><!--none_message end-->
			</c:otherwise>
		</c:choose>
	</div><!--jz_box end-->
</div><!--mid end-->
<div class="bot"></div>
<c:if test="${feed!=null&&feed.feedType=='GRADE'}">
	<div class="next_btn"><a href="#" onclick="javascript:grade(0, '${feed.tpFriend.userId}', ${times});">跳  过</a></div>
</c:if>