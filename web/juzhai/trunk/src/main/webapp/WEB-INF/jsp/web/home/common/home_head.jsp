<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content_box w660 z900"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="my_head_area"><!--my_head_area begin-->
			<c:choose>
				<c:when test="${context.uid != profile.uid}"><div class="face"><a href="/home/${profile.uid}"><img src="${jzr:userLogo(profile.uid,profile.logoPic,180)}" width="180" height="180" /></a></div></c:when>
				<c:otherwise><div class="face"><a href="/home/${profile.uid}"><img src="${jzr:userLogo(profile.uid,profile.newLogoPic,180)}" width="180" height="180" /></a><c:choose><c:when test="${profile.logoVerifyState == 1}"><b>审核中...</b></c:when><c:when test="${profile.logoVerifyState == 3}"><b>未通过审核</b></c:when></c:choose></div></c:otherwise>
			</c:choose>
			<div class="my_infor <c:choose><c:when test='${profile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--my_infor begin-->
				<h2><a href="/home/${profile.uid}"><c:out value="${profile.nickname}" /></a></h2>
				<c:if test="${context.uid != profile.uid}">
					<c:choose>
						<c:when test="${online}">
							<em class="online">当前在线</em>
						</c:when>
						<c:otherwise>
							<em class="offline">
								<c:if test="${profile.lastWebLoginTime != null}">
									<c:set var="date" value="${profile.lastWebLoginTime}" scope="request"/><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />来访
								</c:if>
							</em>	
						</c:otherwise>
					</c:choose>
				</c:if>
				<p>${jzd:cityName(profile.city)}${jzd:townName(profile.town)}</p>
				<c:if test="${not empty profile.feature}">
					<span>自我评价：<font><c:out value="${profile.feature}" /></font></span>
				</c:if>
				<c:if test="${not empty profile.blog}">
					<span>个人主页：<a href="http://${profile.blog}" target="_blank">http://${profile.blog}</a></span>
				</c:if>
			</div><!--my_infor end-->
			<c:choose>
				<c:when test="${context.uid != profile.uid}">
					<c:if test="${profile.logoVerifyState == 3}"><div class="yq_load_face"><a href="javascript:void(0);" id="inviteUploadLogo" uid="${profile.uid}">邀请ta上传头像</a></div></c:if>
					<div class="keep user-remove-interest remove-interest-${profile.uid}" <c:if test="${!isInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${profile.uid}" title="点击取消收藏">已收藏</a></div>
					<div class="keep user-add-interest interest-${profile.uid}" <c:if test="${isInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${profile.uid}" title="点击收藏">收藏ta</a></div>
					<div class="message_icon"><a href="javascript:void(0);" target-uid="${profile.uid}" target-nickname="<c:out value='${profile.nickname}'/>">私信</a></div>
					<div class="ta_btn"><a href="javascript:void(0);" target-uid="${profile.uid}" target-nickname="<c:out value='${profile.nickname}'/>">约ta</a></div>
					<div class="jb"><a href="javascript:void(0);" target-uid="${profile.uid}" id="report-profile" >举报</a></div>
				</c:when>
				<c:otherwise>
					<div class="btn"><a href="/home">发布拒宅</a></div>
				</c:otherwise>
			</c:choose>
		</div><!--my_head_area end-->
	</div>
	<div class="t"></div>
</div><!--content end-->