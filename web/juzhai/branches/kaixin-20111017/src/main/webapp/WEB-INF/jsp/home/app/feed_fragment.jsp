<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="top"></div>
<div class="mid"><!--mid begin-->
	<c:choose>
		<c:when test="${feed.feedType=='SPECIFIC'}">
			<div class="jz_box"><!--jz_box begin-->
				<div class="photo fl"><img src="${feed.profileCache.logoPic}" /></div>
				<div class="infor fl data" data="{'friendId':${feed.profileCache.uid},'actId':${feed.act.id},'times':${times}}"><!--infor begin-->
					<h2><span class="u"><a href="${feed.tpHomeUrl}" class="user" target="_blank"><c:out value="${feed.profileCache.nickname}" /></a></span><span class="w">最近想去</span><span class="v"><c:out value="${feed.act.name}" /></span></h2>
					<p>ta在<c:choose><c:when test="${feed.profileCache.cityName != ''}">${feed.profileCache.cityName}</c:when><c:otherwise>地球</c:otherwise></c:choose>，发布于<fmt:formatDate value="${feed.date}" pattern="yyyy.MM.dd"/></p>
					<a href="javascript:;" class="want btn" onclick="javascript:response(1);" tip="将{0}加为我的兴趣，并找到同好好友" onmouseover="javascript:showTip(this, true, '${feed.act.name}');" onmouseout="javascript:showTip(this, false);"></a>
					<a href="javascript:;" class="dwant btn" onclick="javascript:response(2);" tip="切换到下一张，什么都不做" onmouseover="javascript:showTip(this, true);" onmouseout="javascript:showTip(this, false);"></a>
				</div><!--infor end-->
			</div>
		</c:when>
		<c:when test="${feed.feedType=='QUESTION'}">
			<div class="jz_box"><!--jz_box begin-->
				<div class="photo fl"><img src="${feed.tpFriend.logoUrl}" /></div>
				<div class="df fl"><!--infor begin-->
					<h2>
						<span class="w">${feed.questionNamePrefix}</span>
						<span class="u"><a href="${feed.tpHomeUrl}" class="user" target="_blank"><c:out value="${feed.tpFriend.name}" /></a></span>
						<span class="w">${feed.questionNameSuffix}</span>
					</h2>
					<p>ta在<c:choose><c:when test="${feed.tpFriend.city != ''}">${feed.tpFriend.city}</c:when><c:otherwise>地球</c:otherwise></c:choose></p>
					<c:choose>
						<c:when test="${feed.question.type == 0}">
							<div class="star"><!--star begin-->
								<c:forEach var="answer" items="${feed.answers}" varStatus="status">
									<span class="link" onmouseover="javascript:holdStar(${status.count});" onmouseout="javascript:holdStar(-1);" onclick="javascript:answer(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}', ${times});" title="点击评价" tip="${answer}"></span>
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
									<a href="javascript:;" class="${answer_class}" onclick="javascript:answer(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}', ${times});" <c:if test="${answer_class=='yes'}">title="点击评价"</c:if> >&nbsp;&nbsp;${answer}</a>
								</c:forEach>
							</div>
						</c:when>
					</c:choose>
					<!-- <strong class="henzai"></strong> -->
				</div><!--infor end-->
			</div>
		</c:when>
		<c:when test="${feed.feedType=='INVITE'}">
			<div class="jz_box"><!--jz_box begin-->
				<div class="icon1 fl"></div>
				<div class="infor fl"><!--infor begin-->
					<h2><span class="u">很多好友还没有加入拒宅</span></h2>
					<p>拒宅器还不能充分发挥作用哦</p>
					<a href="javascript:;" class="zjl" onclick="javascript:invite();">发布拒宅召集令</a>
					<h3>立即获得20分拒宅积分</h3>
				</div><!--infor end-->
			</div>
		</c:when>
		<c:otherwise>
			<div class="none_message"><!--none_message begin-->
				<h2></h2><p></p><span>试试<a href="javascript:;" onclick="javascript:kaixinRequest();">邀请更多好友加入</a>！</span>
			</div><!--none_message end-->
		</c:otherwise>
	</c:choose>
</div><!--mid end-->
<div class="bot"></div>
<c:choose>
	<c:when test="${feed.feedType=='SPECIFIC'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:response(2);" tip="切换到下一张，什么都不做" onmouseover="javascript:showTip(this, true);" onmouseout="javascript:showTip(this, false);">跳  过</a></div>
	</c:when>
	<c:when test="${feed.feedType=='QUESTION'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:answer(0, 0, '${feed.tpFriend.userId}', ${times});">跳  过</a></div>
	</c:when>
	<c:when test="${feed.feedType=='INVITE'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:skipInvite();">跳  过</a></div>
	</c:when>
</c:choose>