<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="top"></div>
<div class="mid"><!--mid begin-->
	<c:choose>
		<c:when test="${feed.feedType=='SPECIFIC'}">
			<div class="jz_box"><!--jz_box begin-->
				<div class="photo fl"><a href="${feed.tpHomeUrl}" class="user" target="_blank"><img src="${feed.profileCache.logoPic}" /></a></div>
				<div class="infor fl data" data="{'friendId':${feed.profileCache.uid},'actId':${feed.act.id}}"><!--infor begin-->
					<h2><span class="u"><a href="${feed.tpHomeUrl}" class="user" target="_blank"><c:out value="${feed.profileCache.nickname}" /></a></span><span class="w">想找伴去</span><span class="v"><a href="/app/showAct/${feed.act.id}"><c:out value="${feed.act.name}" /></a></span></h2>
					<h5>ta在<c:choose><c:when test="${feed.profileCache.cityName != ''}">${feed.profileCache.cityName}</c:when><c:otherwise>地球</c:otherwise></c:choose>，发布于<fmt:formatDate value="${feed.date}" pattern="yyyy.MM.dd"/></h5>
					<h6><c:if test="${allUserCnt!=null&&allUserCnt>0}">共<font color="#4E90DB">${allUserCnt}</font>人想去<c:if test="${friendUserCnt!=null&&friendUserCnt>0}">，其中<font color="#4E90DB">${friendUserCnt}</font>个是你的好友！</c:if></c:if></h6>
					<a href="javascript:;" class="want btn" onclick="javascript:respSpecific(1);" tip="将 {0} 加为我的兴趣" onmouseover="javascript:showTip(this, true, '${feed.act.name}');" onmouseout="javascript:showTip(this, false);" title="点击告诉ta"></a>
					<a href="javascript:;" class="dwant btn" onclick="javascript:respSpecific(2);" tip="切换到下一张，什么都不做" onmouseover="javascript:showTip(this, true);" onmouseout="javascript:showTip(this, false);" title="看下一张"></a>
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
									<span class="link" onmouseover="javascript:holdStar(${status.count});" onmouseout="javascript:holdStar(-1);" onclick="javascript:respQuestion(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}');" title="点击评价" tip="${answer}"></span>
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
									<a href="javascript:;" class="${answer_class}" onclick="javascript:respQuestion(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}');" <c:if test="${answer_class=='yes'}">title="点击评价"</c:if> >&nbsp;&nbsp;${answer}</a>
								</c:forEach>
							</div>
						</c:when>
					</c:choose>
					<!-- <strong class="henzai"></strong> -->
				</div><!--infor end-->
			</div>
		</c:when>
		<c:when test="${feed.feedType=='RECOMMEND'}">
			<div class="jz_box"><!--jz_box begin-->
				<div class="photo fl"><a href="/app/showAct/${feed.act.id}"><img src="${jz:actLogo(feed.act.id,feed.act.logo,120)}" /></a></div>
				<div class="infor fl"><!--infor begin-->
					<h2><span class="x">想和朋友去</span><span class="v"><a href="/app/showAct/${feed.act.id}"><c:out value="${feed.act.name}" /></a></span><span class="w">么？</span></h2>
					<h5>${jz:truncate(feed.act.intro,50,'...')}<!-- <a href="#">详细</a> --></h5>
					<h6><c:if test="${allUserCnt!=null&&allUserCnt>0}">共<a href="/app/showAct/${feed.act.id}?allUser=1">${allUserCnt}</a>人想去<c:if test="${friendUserCnt!=null&&friendUserCnt>0}">，其中<a href="/app/showAct/${feed.act.id}">${friendUserCnt}</a>个是你的好友！</c:if></c:if></h6>
					<a href="javascript:;" class="want btn" onclick="javascript:respRecommend(${feed.act.id}, 1);" tip="将 {0} 加为我的兴趣" onmouseover="javascript:showTip(this, true, '${feed.act.name}');" onmouseout="javascript:showTip(this, false);" title="点击添加"></a>
					<a href="javascript:;" class="dwant btn" onclick="javascript:respRecommend(${feed.act.id}, 2);" title="不再显示"></a>
				</div><!--infor end-->
			</div><!--jz_box end-->
		</c:when>
		<c:otherwise>
			<div class="none_message"><!--none_message begin-->
				<h2></h2><p></p><span>试试<a href="javascript:;" onclick="javascript:request();">邀请更多好友加入</a>！</span>
			</div><!--none_message end-->
		</c:otherwise>
	</c:choose>
</div><!--mid end-->
<div class="bot"></div>
<c:choose>
	<c:when test="${feed.feedType=='SPECIFIC'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:respSpecific(2);" tip="切换到下一张，什么都不做" onmouseover="javascript:showTip(this, true);" onmouseout="javascript:showTip(this, false);">换一换</a></div>
	</c:when>
	<c:when test="${feed.feedType=='QUESTION'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:respQuestion(0, 0, '${feed.tpFriend.userId}');">跳  过</a></div>
	</c:when>
	<c:when test="${feed.feedType=='RECOMMEND'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:respRecommend(${feed.act.id}, 2);">换一换</a></div>
	</c:when>
</c:choose>