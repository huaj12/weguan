<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="top"></div>
<div class="mid"><!--mid begin-->
	<c:choose>
		<c:when test="${feed.feedType=='QUESTION'}">
			<div class="jz_box"><!--jz_box begin-->
				<div class="photo fl"><a href="${jz:tpHomeUrl(feed.tpFriend.userId,context.tpId)}" target="_blank"><img src="${feed.tpFriend.logoUrl}" width="120"/></a></div>
				<div class="df fl"><!--infor begin-->
					<h2>
						<span class="w">${feed.questionNamePrefix}</span>
						<span class="u"><a href="${jz:tpHomeUrl(feed.tpFriend.userId,context.tpId)}" target="_blank" class="user"><c:out value="${feed.tpFriend.name}" /></a></span>
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
									<a href="javascript:void(0);" class="${answer_class}" onclick="javascript:respQuestion(${feed.question.id}, ${status.count}, '${feed.tpFriend.userId}');" <c:if test="${answer_class=='yes'}">title="点击评价"</c:if> >&nbsp;&nbsp;${answer}</a>
								</c:forEach>
							</div>
						</c:when>
					</c:choose>
				</div><!--infor end-->
			</div><!--jz_box end-->
		</c:when>
		<c:otherwise>
			<div class="none_message"><!--none_message begin-->
				<h2></h2><p>这里没有信息了。</p><%-- <a href="javascript:void(0);" onclick="javascript:request();">邀请更多好友加入</a> --%>
			</div><!--none_message end-->
		</c:otherwise>
	</c:choose>
</div><!--mid end-->
<div class="bot"></div>
<c:choose>
	<c:when test="${feed.feedType=='QUESTION'}">
		<div class="next_btn1"><a href="javascript:;" onclick="javascript:respQuestion(0, 0, '${feed.tpFriend.userId}');">跳  过</a></div>
	</c:when>
</c:choose>