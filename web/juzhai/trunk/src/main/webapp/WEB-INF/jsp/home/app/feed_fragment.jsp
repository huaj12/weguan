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
				<div class="infor fl data" data="{'friendId':${feed.profileCache.uid},'actId':${feed.act.id},'times':${times}}"><!--infor begin-->
					<h2><span class="u"><c:out value="${feed.profileCache.nickname}" /></span><span class="w">最近想去</span><span class="v"><c:out value="${feed.act.name}" /></span></h2>
					<p><fmt:formatDate value="${feed.date}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;&nbsp;&nbsp;${feed.profileCache.provinceName}&nbsp;${feed.profileCache.cityName}</p>
					<a href="#" class="want" onclick="javascript:response(1);" tip="点击“我也想去”，告知所有有同样兴趣的好友" onmouseover="javascript:tip(this, true);" onmouseout="javascript:tip(this, false);"></a>
					<a href="#" class="dwant" onclick="javascript:response(2);" tip="切换到下一张，什么都不做" onmouseover="javascript:tip(this, true);" onmouseout="javascript:tip(this, false);"></a>
				</div><!--infor end-->
			</c:when>
			<c:when test="${feed.feedType=='QUESTION'}">
				<div class="photo fl"><img src="${feed.tpFriend.logoUrl}" /></div>
				<div class="df fl"><!--infor begin-->
					<h2><span class="w">你觉得</span><span class="u"><c:out value="${feed.tpFriend.name}" /></span><span class="w">有多宅？</span></h2>
					<p>${feed.tpFriend.city}</p>
					<c:choose>
						<c:when test="${feed.question.type == 0}">
							<div class="star"><!--star begin-->
								<c:forEach var="answer" items="${feed.answers}" varStatus="status">
									<span class="link" onmouseover="javascript:holdStar(${status.count});" onmouseout="javascript:holdStar(-1);" onclick="javascript:answer(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}', ${times});" title="${answer}"></span>
								</c:forEach>
								<em class="zai" style="display:none;"></em>
							</div><!--star end-->
						</c:when>
						<c:when test="${feed.question.type == 1}">
							<div class="df_btn">
								<c:forEach var="answer" items="${feed.answers}" varStatus="status">
									<c:choose>
										<c:when test="${status.count == 1}">
											<c:set var="answer_class" value="yes" />
										</c:when>
										<c:when test="${status.count == 2}">
											<c:set var="answer_class" value="no" />
										</c:when>
									</c:choose>
									<a href="#" class="${answer_class}" onclick="javascript:answer(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}', ${times});">&nbsp;&nbsp;${answer}</a>
								</c:forEach>
							</div>
						</c:when>
					</c:choose>
					<!-- <strong class="henzai"></strong> -->
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
<c:choose>
	<c:when test="${feed.feedType=='SPECIFIC'}">
		<div class="next_btn"><a href="#" onclick="javascript:response(2);">跳  过</a></div>
	</c:when>
	<c:when test="${feed.feedType=='QUESTION'}">
		<div class="next_btn"><a href="#" onclick="javascript:answer(0, 0, '${feed.tpFriend.userId}', ${times});">跳  过</a></div>
	</c:when>
</c:choose>