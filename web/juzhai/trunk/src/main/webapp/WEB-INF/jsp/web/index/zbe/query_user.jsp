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
		<title>找伴儿-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<h2>搜索小宅</h2>
										<div class="w70"><!--w70 begin-->
											<div class="select_menu" name="city"><!--select_menu begin-->
												<p><a href="javascript:void(0);">全国</a></p>
												<div></div>
												<div class="select_box"><!--select_box begin-->
													<span>
														<a href="javascript:void(0);" value="0"   <c:if test="${cityId==null || cityId==0}">class="selected"</c:if>>全国</a>
														<a href="javascript:void(0);" value="2"   <c:if test="${cityId==2}">class="selected"</c:if>>上海</a>
														<a href="javascript:void(0);" value="1"   <c:if test="${cityId==1}">class="selected"</c:if>>北京</a>
														<a href="javascript:void(0);" value="181" <c:if test="${cityId==181}">class="selected"</c:if>>广州</a>
														<a href="javascript:void(0);" value="183" <c:if test="${cityId==183}">class="selected"</c:if>>深圳</a>
														<a href="javascript:void(0);" value="343" <c:if test="${cityId==343}">class="selected"</c:if>>杭州</a>
														<a href="javascript:void(0);" value="157" <c:if test="${cityId==157}">class="selected"</c:if>>南京</a>
														<a href="javascript:void(0);" value="4"   <c:if test="${cityId==4}">class="selected"</c:if>>重庆</a>
														<a href="javascript:void(0);" value="241" <c:if test="${cityId==241}">class="selected"</c:if>>成都</a>
														<a href="javascript:void(0);" value="108" <c:if test="${cityId==108}">class="selected"</c:if>>武汉</a>
													</span>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w70 end-->
										<div class="w100"><!--w100 begin-->
											<div class="select_menu" name="sex"><!--select_menu begin-->
												<p><a href="javascript:void(0);">性别不限</a></p>
												<div></div>
												<div class="select_box"><!--select_box begin-->
													<span>
														<a href="javascript:void(0);" value="" <c:if test="${empty sex||sex=='all'}">class="selected"</c:if>>性别不限</a>
														<a href="javascript:void(0);" value="male" <c:if test="${sex=='male'}">class="selected"</c:if>>男生</a>
														<a href="javascript:void(0);" value="female" <c:if test="${sex=='female'}">class="selected"</c:if>>女生</a>
													</span>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w100 end-->
										<div class="input"><!--input begin-->
											<p class="l"></p><span class="width70"><input name="minStringAge" type="text" value="${minStringAge}" /></span><p class="r"></p><em>到</em>
										</div><!--input end-->
										<div class="input"><!--input begin-->
											<p class="l"></p><span class="width70"><input name="maxStringAge" value="${maxStringAge}" type="text" /></span><p class="r"></p><em>岁</em>
										</div><!--input end-->
										<div class="btn"><a href="javascript:void(0);" class="query-btn">搜索</a></div>
									</div><!--search_title end-->
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
															<div class="zbq"><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import></font><a href="/post/${view.post.id}">${jzu:truncate(view.post.content,50,'...')}</a></div>
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
																<span><a href="javascript:void(0);" class="message_u1" target-uid="${view.profile.uid}" target-nickname="${view.profile.nickname}">私信</a></span>
																<div class="keep user-remove-interest remove-interest-${view.profile.uid}" <c:if test="${!view.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${view.profile.uid}" title="点击取消收藏">已收藏</a></div>
																<div class="keep user-add-interest interest-${view.profile.uid}" <c:if test="${view.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${view.profile.uid}" title="点击收藏">收藏ta</a></div>
															</c:if>
														</div><!--pub_box_m end-->
														<div class="clear"></div>
														<div class="pub_box_b"></div>
													</div><!--pub_box end-->
												</c:forEach>
											</c:when>
											<c:otherwise>
												<div class="none">暂时没有合适的人！</div>
											</c:otherwise>
										</c:choose>
									</div><!--search_result end-->
								</div><!--jz_list end-->
								<c:if test="${not empty userViews}">
								<div class="clear"></div>
								<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
									<c:param name="pager" value="${pager}"/>
									<c:param name="url" value="/queryUsers/${cityId}_${sex}_${minStringAge}_${maxStringAge}" />
								</c:import>
								</c:if>
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="/WEB-INF/jsp/web/home/index/new_user_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/query_user.js')}"></script>
	</body>
</html>
