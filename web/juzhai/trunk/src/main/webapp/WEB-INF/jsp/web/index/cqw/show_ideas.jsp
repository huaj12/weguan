<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
			
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${jzd:cityName(cityId)}出去玩,${jzd:cityName(cityId)}约会地点,${jzd:cityName(cityId)}约会,${jzd:cityName(cityId)}创意约会 -拒宅网(51juzhai.com)</title>
		<meta name="keywords" content="${jzd:cityName(cityId)}拒宅，${jzd:cityName(cityId)}出去玩,${jzd:cityName(cityId)}约会地点,${jzd:cityName(cityId)}约会,${jzd:cityName(cityId)}创意约会 ,${jzd:cityName(cityId)}交友" />
		<meta name="description" content="周末不想宅在家拒宅网为你提供${jzd:cityName(cityId)}创意约会,${jzd:cityName(cityId)}约会地点,发现同兴趣的朋友,促成约会" />
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
										<h2>拒宅好主意</h2>
										<div class="w70"><!--w70 begin-->
											<div class="select_menu"><!--select_menu begin-->
												<p><a href="javascript:void(0);">全国</a></p>
												<div></div>
												<div class="select_box"><!--select_box begin-->
													<span id="city-options">
														<a href="javascript:void(0);" value="0"   <c:if test="${cityId==null || cityId==0}">class="selected"</c:if>>全国</a>
														<c:forEach var="specialCity" items="${jzd:specialCityList()}">
															<a href="javascript:void(0);" value="${specialCity.id}"   <c:if test="${cityId==specialCity.id}">class="selected"</c:if>>${specialCity.name}</a>
														</c:forEach>
													</span>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w70 end-->
										<div class="category" order-type="${orderType}"><!--category begin-->
											<span <c:if test="${empty orderType||'time'==orderType}"> class="act"</c:if>><p></p><a href="/showIdeas/time_${cityId}/1">最新</a><p></p></span>
											<span <c:if test="${'pop'==orderType}"> class="act"</c:if>><p></p><a href="/showIdeas/pop_${cityId}/1" >最热</a><p></p></span>
										</div><!--category end-->
									</div><!--search_title end-->
									<div class="good_idea"><!--good_idea begin-->
										<c:choose>
											<c:when test="${not empty ideaViewList}">
												<c:forEach items="${ideaViewList}" var="ideaView">
													<div class="pub_box mouseHover"><!--pub_box begin-->
														<div class="pub_box_t"></div>
														<div class="pub_box_m"><!--pub_box_m begin-->
															<p><a href="/idea/${ideaView.idea.id}"><c:out value="${ideaView.idea.content}" /></a><c:if test="${ideaView.profileCache != null}"><a href="/home/${ideaView.profileCache.uid}" class="from">来自&nbsp;<c:out value="${ideaView.profileCache.nickname}" /></a></c:if></p>
															<div class="infor"><!--infor begin-->
																<c:if test="${not empty ideaView.idea.pic}">
																	<div class="img"><a href="/idea/${ideaView.idea.id}"><img src="${jzr:static('/images/web/1px.gif')}" data-original="${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}" /></a></div>
																</c:if>
																<!-- <div class="clear"></div> -->
																<span><c:set var="date" value="${ideaView.idea.lastModifyTime}" scope="request"/><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
																<c:if test="${ideaView.idea.categoryId > 0}">
																	<span class="tag">${jzd:categoryName(ideaView.idea.categoryId)}</span>
																</c:if>
																<c:if test="${ideaView.idea.date != null}">
																	<span class="time"><fmt:formatDate value="${ideaView.idea.date}" pattern="yyyy-MM-dd" /></span>
																</c:if>
																<c:if test="${not empty ideaView.idea.place}">
																	<span class="adress">${ideaView.idea.place}</span>
																</c:if>
																<c:if test="${!empty ideaView.idea.link }">
																	<span class="link"><a href="${ideaView.idea.link}">查看相关链接</a></span>
																</c:if>
															</div><!--infor end-->
															<div class="fb_area"><!--fb_area begin-->
																<c:choose>
																	<c:when test="${empty ideaView.ideaUserViews}">
																		<div class="fb_members_none">点击右侧发布拒宅按钮，抢沙发</div>
																	</c:when>
																	<c:otherwise>
																		<div class="fb_members">
																			<c:forEach items="${ideaView.ideaUserViews}"  var="ideaUser">
																				<em><a href="/home/${ideaUser.profileCache.uid}"><img src="${jzr:userLogo(ideaUser.profileCache.uid,ideaUser.profileCache.logoPic,80)}" width="40" height="40" alt="${ideaUser.profileCache.nickname}" title="${ideaUser.profileCache.nickname}"/></a></em>
																			</c:forEach>
																			<b><a href="/idea/${ideaView.idea.id}">共${ideaView.idea.useCount}人想去</a></b>
																		</div>
																	</c:otherwise>
																</c:choose>
																<c:choose>
																	<c:when test="${ideaView.hasUsed}">
																		<div class="send_done"><a href="javascript:void(0);">已发布</a></div>
																	</c:when>
																	<c:otherwise>
																		<div class="sending" style="display: none;"><a href="javascript:void(0);">发布中</a></div>
																		<div class="fb_btn"><a href="javascript:void(0);" idea-id="${ideaView.idea.id}">发布拒宅</a></div>															
																	</c:otherwise>
																</c:choose>
															</div><!--fb_area end-->
														</div><!--pub_box_m end-->
														<div class="clear"></div>
														<div class="pub_box_b"></div>
													</div><!--pub_box end-->
												</c:forEach>
											</c:when>
											<c:otherwise>
												<div class="none">这里还没有内容</div>
											</c:otherwise>
										</c:choose>
									</div><!--good_idea end-->
								</div><!--jz_list end-->
								<div class="clear"></div>
								<c:if test="${not empty ideaViewList}">
									<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
										<c:param name="pager" value="${pager}"/>
										<c:param name="url" value="/showIdeas/${orderType}_${cityId}" />
									</c:import>
								</c:if>
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="idea_ad_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/show_ideas.js')}"></script>
	</body>
</html>

