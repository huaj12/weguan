<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 z900"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="detail"><!--detail begin-->
									<div class="pub_box"><!--pub_box begin-->
										<div class="pub_box_t"></div>
										<div class="pub_box_m"><!--pub_box_m begin-->
											<div class="arrow"></div>
											<div></div>
											<div class="con"><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${post.purposeType}"/></c:import>:</font><a href="/post/detail/${post.id}"><c:out value="${post.content}" /></a></div>
											<div class="infor"><!--infor begin-->
												<c:if test="${not empty post.pic}">
													<div class="img"><img src="${jzr:postPic(post.id, post.ideaId, post.pic)}" /></div>
												</c:if>
												<span><c:set var="date" value="${post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
												<c:if test="${not empty post.place}">
													<span class="adress"><c:out value="${post.place}" /></span>
												</c:if>
												<c:if test="${post.dateTime != null}">
													<span class="time"><fmt:formatDate value="${post.dateTime}" pattern="yyyy.MM.dd"/></span>
												</c:if>
												<c:if test="${not empty post.link}">
													<span class="link"><a href="${post.link}" target="_blank">查看相关链接</a></span>
												</c:if>
												<!-- <div class="img"><img src="images/web2/pic2.jpg"  width="450" height="300"/></div>
												<span>12秒前更新</span>
												<span class="adress">张江故园餐厅</span>
												<span class="time">01.21 周一</span>
												<span class="link"><a href="#">查看相关链接</a></span> -->
											</div><!--infor end-->
										</div><!--pub_box_m end-->
										<div class="clear"></div>
										<div class="pub_box_b"></div>
									</div><!--pub_box end-->
									<div class="btn"><!--btn begin-->
										<c:choose>
											<c:when test="${context.uid != postProfile.uid}">
												<div class="message_s1"><a href="#">私信</a></div>
												<div class="like"><span class="l"></span><a href="#">已感兴趣  23</a><span class="r"></span></div>
											</c:when>
											<c:otherwise>
												<div class="own_btn">
													<a href="#" class="edit">编辑</a>
													<a href="#" class="delete">删除</a>
												</div>
											</c:otherwise>
										</c:choose>
									</div><!--btn end--> 
									<div class="zfa"><a href="#">转发</a></div>
								</div><!--detail end-->
								<div class="response"><!--response begin-->
									<jsp:include page="response_user_list.jsp" />
								</div><!--response end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<div class="content_box w285"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="person <c:choose><c:when test="${responseUserView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--person begin-->
									<p><a href="/home/${postProfile.uid}"><img src="${jzr:userLogo(postProfile.uid, postProfile.logoPic, 80)}"  width="80" height="80"/></a></p>
									<div class="personname"><a href="/home/${postProfile.uid}"><c:out value="${postProfile.nickname}" /></a></div>
									<c:set var="age" value="${jzu:age(postProfile.birthYear, postProfile.birthSecret)}" />
									<c:set var="constellationName" value="${jzd:constellationName(postProfile.constellationId)}" />
									<span><c:if test="${age >= 0}">${age}岁&nbsp;</c:if><c:if test="${postProfile.city != null && postProfile.city > 0}">${jzd:cityName(postProfile.city)}<c:if test="${postProfile.town != null && postProfile.town > 0}">${jzd:townName(postProfile.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty postProfile.profession}">${postProfile.profession}</c:if></span>
									<c:if test="${context.uid != postProfile.uid}">
										<div class="keep user-remove-interest remove-interest-${postProfile.uid}" <c:if test="${!hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${postProfile.uid}" title="点击取消收藏">已收藏</a></div>
										<div class="keep user-add-interest interest-${postProfile.uid}" <c:if test="${hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${postProfile.uid}" title="点击收藏">收藏ta</a></div>
									</c:if>
								</div><!--person end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
						<jsp:include page="/WEB-INF/jsp/web/home/index/idea_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/web/post_detail.js')}"></script>
	</body>
</html>
