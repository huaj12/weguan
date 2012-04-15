<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的拒宅偏好-拒宅网</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
<!--warp begin-->
	<div class="warp">
		<div class="main"><!--main begin-->
			<c:set var="page" value="shield" scope="request" />
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
				<div class="t"></div>
					<div class="m"><!--m begin-->
							<div class="set"><!--set begin-->
								<!--set begin-->
								<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" />
								<!--set_left end-->
								<div class="set_right"><!--set_right begin-->
								<div class="title"><h2>被我屏蔽的人<font>被你屏蔽的人将不能再私信，响应，约你</font></h2></div>
									<div class="my_infor"><!--my_infor begin-->
									<div class="pub_box_t"></div>
										<div class="my_pb"><!--my_pb begin-->
										<c:choose>
											<c:when test="${not empty profileView}">
														<c:forEach items="${profileView}" var="profile">
															<div class="pub_box <c:choose><c:when test='${profile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
																<div class="pub_box_t"></div>
																<div class="pub_box_m"><!--pub_box_m begin-->
																<p><a href="/home/${profile.uid}"  target="_blank"><img src="${jzr:userLogo(profile.uid,profile.logoPic,80)}" width="80" height="80" /></a></p>
																<h2><a href="/home/${profile.uid}"  target="_blank"><c:out value="${profile.nickname }"/> </a></h2>
																<c:set var="cityName" value="${jzd:cityName(profile.city)}" />
																<c:set var="townName" value="${jzd:townName(profile.town)}" />
																<c:set var="age" value="${jzu:age(profile.birthYear,profile.birthSecret)}" />
																<c:set var="constellationName" value="${jzd:constellationName(profile.constellationId)}" />
																<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${not empty cityName}">${cityName}<c:if test="${not empty townName}">${townName}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty profile.profession}">${profile.profession}&nbsp;</c:if></em>
																<span><a href="javascript:void(0);" target-name="<c:out value="${profile.nickname }"/>" target-uid="${profile.uid}" title="取消屏蔽后，ta可以私信，响应，约你">取消屏蔽</a></span>
																</div><!--pub_box_m end-->
																<div class="clear"></div>
																<div class="pub_box_b"></div>
															</div><!--pub_box end-->
														</c:forEach>
											</c:when>
										</c:choose>
										</div><!--my_pb end-->
									<div class="clear"></div>
									<c:if test="${not empty profileView}">
										<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
											<c:param name="pager" value="${pager}"/>
											<c:param name="url" value="/home/blacklist" />
										</c:import>
									</c:if>
									</div><!--my_infor end-->
								</div><!--set_right end-->
							</div><!--set end-->
					</div><!--m end-->
				<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/blacklist.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
</body>
</html>
