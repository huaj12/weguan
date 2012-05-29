<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="拒宅 ,找伴,出去玩 ,约会,交友" />
		<meta name="description" content="不想宅在家找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
		<title>找伴儿 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<jsp:include page="/WEB-INF/jsp/web/common/youke_login.jsp" />
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<h2>搜索小宅</h2>
										<div class="w100"><!--w70 begin-->
											<%-- <div id="city-select" class="l_select_menu" name="city"><!--l_select_menu begin-->
												<p><a href="javascript:void(0);" hidefocus city-id="${cityId}"><c:choose><c:when test="${cityId == 0}">全国</c:when><c:otherwise>${jzd:cityName(cityId)}</c:otherwise></c:choose></a></p>
												<div></div>
												<div class="l_select_menu_box"><!--city_list begin-->
													<div class="l_select_menu_box_t"></div>
													<div class="l_select_menu_box_m"><!--l_select_menu_box_m begin-->
														<div class="vip_city"><!--vip_city begin-->
															<h3>主要城市:</h3>
															<div class="list city-list"><!--list begin-->
																<c:forEach var="specialCity" items="${jzd:specialCityList()}">
																	<a href="javascript:void(0);" value="${specialCity.id}" <c:if test="${cityId==specialCity.id}">class="act"</c:if>>${specialCity.name}</a>
																</c:forEach>
																<a href="javascript:void(0);" value="0" <c:if test="${cityId == 0}">class="act"</c:if>>全国</a>
															</div><!--list end-->
														</div><!--vip_city end-->
														<div class="all_city"><!--all_city begin-->
															<h3>所有城市:</h3>
															<div class="list"><!--list begin-->
																<div class="sheng">
																</div>
																<div class="shi city-list" style="display: none;"><!--city_area begin-->
																</div><!--city_area end-->
															</div><!--list end-->
														</div><!--all_city end-->
													</div><!--l_menu_box_m end-->
													<div class="l_select_menu_box_b"></div>
												</div><!--l_select_menu_box end-->
											</div><!--l_select_menu end--> --%>
											<div id="town-select" class="select_menu" name="townId"><!--select_menu begin-->
												<p><a href="javascript:void(0);"></a></p>
												<div></div>
												<div class="select_box"><!--select_box begin-->
													<span>
														<a href="javascript:void(0);" value="0"   <c:if test="${townId == 0}">class="selected"</c:if>><c:choose><c:when test="${cityId > 0}">全${jzd:cityName(cityId)}</c:when><c:otherwise>全国</c:otherwise></c:choose></a>
														<c:forEach var="town" items="${jzd:townList(cityId)}">
															<a href="javascript:void(0);" value="${town.id}"   <c:if test="${townId==town.id}">class="selected"</c:if>>${town.name}</a>
														</c:forEach>
													</span>
													<div class="ch_area"><a href="/profile/index">我要搬家</a></div>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w70 end-->
										<div class="w100"><!--w100 begin-->
											<div class="select_menu" name="sex"><!--select_menu begin-->
												<p><a href="javascript:void(0);"></a></p>
												<div></div>
												<div class="select_box"><!--select_box begin-->
													<span>
														<a href="javascript:void(0);" value="all" <c:if test="${empty sex||sex=='all'}">class="selected"</c:if>>所有小宅</a>
														<a href="javascript:void(0);" value="male" <c:if test="${sex=='male'}">class="selected"</c:if>>宅男</a>
														<a href="javascript:void(0);" value="female" <c:if test="${sex=='female'}">class="selected"</c:if>>宅女</a>
													</span>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w100 end-->
										<div class="input"><!--input begin-->
											<p class="l"></p><span class="width70"><input name="minAge" type="text" value="" init-data="${minAge}" /></span><p class="r"></p>
										</div><!--input end-->
										<div class="txt">到</div>
										<div class="input"><!--input begin-->
											<p class="l"></p><span class="width70"><input name="maxAge" type="text" value="" init-data="${maxAge}" /></span><p class="r"></p>
										</div><!--input end-->
										<div class="txt">岁</div>
										<div class="btn"><a href="javascript:void(0);" class="query-btn">搜索</a></div>
									</div><!--search_title end-->
									<div class="clear"></div>
									<div class="search_result"><!--search_result begin-->
										<c:choose>
											<c:when test="${not empty userViews}">
												<c:forEach items="${userViews}" var="view">
													<div class="pub_box mouseHover <c:choose><c:when test='${view.profile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--pub_box begin-->
														<div class="pub_box_t"></div>
														<div class="pub_box_m"><!--pub_box_m begin-->
															<p><a href="/home/${view.profile.uid}"><img src="${jzr:userLogo(view.profile.uid,view.profile.logoPic,120)}" width="120" height="120" /></a></p>
															<h2><a href="/home/${view.profile.uid}"><c:out value="${view.profile.nickname}" /></a></h2>
															<c:set var="cityName" value="${jzd:cityName(view.profile.city)}" />
															<c:set var="townName" value="${jzd:townName(view.profile.town)}" />
															<c:set var="age" value="${jzu:age(view.profile.birthYear,view.profile.birthSecret)}" />
															<c:set var="constellationName" value="${jzd:constellationName(view.profile.constellationId)}" />
															<em><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${not empty cityName}">${cityName}<c:if test="${not empty townName}">${townName}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty view.profile.profession}">${view.profile.profession}</c:if></em>
															<div class="zbq"><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import></font><a href="/post/${view.post.id}"><c:out value="${jzu:truncate(view.post.content,50,'...')}"></c:out></a></div>
															<c:choose>
																<c:when test="${!empty view.online && view.online}"><b class="online">当前在线</b></c:when>
																<c:otherwise>
																	<b class="offline">
																		<c:if test="${view.profile.lastWebLoginTime != null}">
																			<c:set var="date" value="${view.profile.lastWebLoginTime}" scope="request"/><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />来访
																		</c:if>
																	</b>
																</c:otherwise>
															</c:choose>
															<c:if test="${context.uid != view.profile.uid}">
																<span><a href="javascript:void(0);" class="message_u1 send-message" target-uid="${view.profile.uid}" target-nickname="<c:out value='${view.profile.nickname}' />">私信</a></span>
																<div class="keep user-remove-interest remove-interest-${view.profile.uid}" <c:if test="${!view.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${view.profile.uid}" title="点击取消收藏">已收藏</a></div>
																<div class="keep user-add-interest interest-${view.profile.uid}" <c:if test="${view.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${view.profile.uid}" title="点击收藏">收藏ta</a></div>
																<div class="date"><a href="javascript:void(0);" target-uid="${view.profile.uid}" target-nickname="<c:out value='${view.profile.nickname}' />">约ta</a></div>
															</c:if>
														</div><!--pub_box_m end-->
														<div class="clear"></div>
														<div class="pub_box_b"></div>
													</div><!--pub_box end-->
												</c:forEach>
												<%-- <c:if test="${pager.totalResults <= 5}">
													<div class="more_fx"><a href="/queryusers/0___/1">目前这个城市人还比较少，先切换到全国看看吧</a></div>
												</c:if> --%>
											</c:when>
											<c:otherwise>
												<div class="none">暂时没有合适的人<%-- ,<a href="/queryusers/0___/1">先来看看其他地区的吧</a> --%>！</div>
											</c:otherwise>
										</c:choose>
									</div><!--search_result end-->
								</div><!--jz_list end-->
								<c:if test="${pager.totalResults > 0}">
									<div class="clear"></div>
									<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
										<c:param name="pager" value="${pager}"/>
										<c:param name="url" value="/queryusers/${townId}_${sex}_${minAge}_${maxAge}" />
									</c:import>
								</c:if>
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<c:if test="${empty isQplus||!isQplus}">
							<jsp:include page="/WEB-INF/jsp/web/home/index/share_widget.jsp" />
						</c:if>
						<jsp:include page="/WEB-INF/jsp/web/index/zbe/recommend_users_widget.jsp" />
						<jsp:include page="/WEB-INF/jsp/web/home/index/new_user_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/query_user.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
