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
		<title><c:out value="${postProfile.nickname}"/>:<c:out value="${jzu:truncate(post.content, 30, '...')}" />_拒宅网</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,约会,交友" />
		<meta  name="description"   content="<c:out value='${postProfile.nickname}'/>:<c:out value="${jzu:truncate(post.content, 30, '...')}" />_不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
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
											<div class="con"><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${post.purposeType}"/></c:import>:</font><c:out value="${post.content}" /></div>
											<div class="infor"><!--infor begin-->
												<c:if test="${not empty post.pic}">
													<div class="img"><a href="${jzr:postPic(post.id, post.ideaId, post.pic, 0)}" target="_blank"><img src="${jzr:postPic(post.id, post.ideaId, post.pic, 450)}"/></a></div>
												</c:if>
												<span><c:set var="date" value="${post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
												<span class="tag">${jzd:categoryName(post.categoryId)}</span>
												<c:if test="${post.dateTime != null}">
													<span class="time"><fmt:formatDate value="${post.dateTime}" pattern="yyyy.MM.dd"/></span>
												</c:if>
												<c:if test="${not empty post.place}">
													<span class="adress"><c:out value="${post.place}" /></span>
												</c:if>
												<c:if test="${not empty post.link}">
													<span class="link"><a href="${post.link}" target="_blank">查看相关链接</a></span>
												</c:if>
											</div><!--infor end-->
										</div><!--pub_box_m end-->
										<div class="clear"></div>
										<div class="pub_box_b"></div>
									</div><!--pub_box end-->
									<div class="btn"><!--btn begin-->
										<c:choose>
											<c:when test="${context.uid != postProfile.uid}">
												<c:choose>
													<c:when test="${hasResponse}">
														<div class="like done"><a href="javascript:void(0);" class="xy">已响应</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${post.responseCnt}</a><p class="r"></p></div></div>
													</c:when>
													<c:otherwise>
														<div class="like post-response" id="response${post.id}" post-id="${post.id}"><a href="javascript:void(0);" class="xy">响应</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);"><font>${post.responseCnt}</font></a><p class="r"></p></div></div>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<div class="own_btn">
													<a href="javascript:void(0);" class="edit" post-id="${post.id}">编辑</a>
													<a href="javascript:void(0);" class="delete" post-id="${post.id}">删除</a>
												</div>
											</c:otherwise>
										</c:choose>
									</div><!--btn end--> 
									<c:if test="${context.uid != postProfile.uid}">
										<div class="zfa"><a href="javascript:void(0);" post-id="${post.id}">转发</a></div>
									</c:if>
								</div><!--detail end-->
								<div class="response"><!--response begin-->
									<div class="title"><!--title begin-->
										<div class="tab">
											<span <c:if test="${pageType == 'comment'}">class="act"</c:if>><p></p><a href="/post/${post.id}/comment">${commentTotalCnt}条留言</a><p></p></span>
											<span <c:if test="${pageType == 'response'}">class="act"</c:if>><p></p><a href="/post/${post.id}/respuser">${respTotalCnt}人响应</a><p></p></span>
										</div>
									</div><!--title end-->
									<c:choose>
										<c:when test="${pageType == 'comment'}"><c:set var="postId" value="${post.id}" scope="request"/><jsp:include page="comment_user_list.jsp" /></c:when>
										<c:when test="${pageType == 'response'}"><jsp:include page="response_user_list.jsp" /></c:when>
									</c:choose>
								</div><!--response end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<div class="content_box w285"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="person <c:choose><c:when test="${postProfile.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--person begin-->
									<p><a href="/home/${postProfile.uid}"><img src="${jzr:userLogo(postProfile.uid, postProfile.logoPic, 80)}"  width="80" height="80"/></a></p>
									<div class="personname"><a href="/home/${postProfile.uid}"><c:out value="${postProfile.nickname}" /></a></div>
									<c:set var="age" value="${jzu:age(postProfile.birthYear, postProfile.birthSecret)}" />
									<c:set var="constellationName" value="${jzd:constellationName(postProfile.constellationId)}" />
									<span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${postProfile.city != null && postProfile.city > 0}">${jzd:cityName(postProfile.city)}<c:if test="${postProfile.town != null && postProfile.town > 0}">${jzd:townName(postProfile.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty postProfile.profession}">${postProfile.profession}</c:if></span>
									<c:if test="${context.uid != postProfile.uid}">
										<div class="keep user-remove-interest remove-interest-${postProfile.uid}" <c:if test="${!hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${postProfile.uid}" title="点击取消收藏">已收藏</a></div>
										<div class="keep user-add-interest interest-${postProfile.uid}" <c:if test="${hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${postProfile.uid}" title="点击收藏">收藏ta</a></div>
										<div class="mail"><a class="send-message" href="javascript:void(0);" title="发私信给ta" target-uid="${postProfile.uid}" target-nickname="${postProfile.nickname}">私信</a></div>
									</c:if>
								</div><!--person end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
						<jsp:include page="/WEB-INF/jsp/web/home/index/idea_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/post_detail.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
