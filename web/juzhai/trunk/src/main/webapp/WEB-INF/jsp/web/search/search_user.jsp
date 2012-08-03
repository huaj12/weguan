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
	<meta name="keywords" content="拒宅 ,找伴,出去玩 ,约会" />
		<meta name="description" content="不想宅在家找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
		<title>${jzd:cityName(cityId)}找伴儿 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
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
										<h2>查找小宅</h2>
										<div class="w100"><!--w100 begin-->
											<div class="select_menu" name="town"><!--select_menu begin-->
												<p><a href="javascript:void(0);"></a></p>
												<div></div>
												<div class="select_box"><!--select_box begin-->
													<span>
													<a href="javascript:void(0);" <c:if test="${townId==0||cityId==0}">class="selected"</c:if> value="0"><c:choose><c:when test="${cityId == 0}">全国</c:when><c:otherwise>${jzd:cityName(cityId)}</c:otherwise></c:choose></a>
													<c:forEach var="town" items="${jzd:townList(cityId)}">
														<a href="javascript:void(0);" value="${town.id}"   <c:if test="${townId==town.id}">class="selected"</c:if>>${town.name}</a>
													</c:forEach>
													</span>
													<div class="ch_area"><a href="/profile/index">我要搬家</a></div>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w100 end-->
										
										
										<div class="w100"><!--w100 begin-->
											<div class="select_menu" name="sex"><!--select_menu begin-->
												<p><a href="javascript:void(0);">所有小宅</a></p>
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
										
										<div class="txt">年龄:</div>
										<div class="input"><!--input begin-->
											<p class="l"></p><span class="width70"><input name="minStringAge" type="text" value="${minStringAge}" /></span><p class="r"></p>
										</div><!--input end-->
										<div class="txt">到</div>
										<div class="input"><!--input begin-->
											<p class="l"></p><span class="width70"><input name="maxStringAge"  value="${maxStringAge}" type="text" /></span><p class="r"></p>
										</div><!--input end-->
										<div class="txt">岁</div>
										
										<div class="w100" id="constellation-select" style="margin-left:10px;display: none"><!--w70 begin-->
												<div class="xz_menu" name="constellation" load-lazy="true"><!--select_menu begin-->
												<p><a href="javascript:void(0);" value="0" initName="请选星座"></a></p>
												<div></div>
													<div class="select_box"><!--select_box begin-->
													<div class="check_list"><!--check_list begin-->
														<ul>
															<c:forEach items="${constellations}" var="constellation">
																<li value="${constellation.id}" <c:forEach items="${constellationIds}" var="cid"><c:if test="${cid==constellation.id}"> class="act"</c:if></c:forEach>>${constellation.name}</li>
															</c:forEach>
														</ul>
													</div><!--check_list end-->
													<div class="clear"></div>
													<em></em>
													</div><!--select_box end-->
												</div><!--select_menu end-->
											</div><!--w70 end-->
										
										
										
										<div class="btn" id="simple"><a href='javascript:void(0);' class='query-btn'>搜索</a></div>
										<div class="more_search"><a href="javascript:void(0);">更多条件</a></div>
										<div class="search_more_area" style="display: none"><!--search_more_area begin-->
										<div class="w100"><!--w100 begin-->
											<div class="select_menu" name="educations" load-lazy="true"><!--select_menu begin-->
											<p><a href="javascript:void(0);"></a></p>
											<div></div>
												<div class="select_box"><!--select_box begin-->
													<span>
																<a href="javascript:void(0);" value="0" <c:if test="${educationId==''||educationId==0}"> class="selected" </c:if>>学历不限</a>
														<c:forEach items="${educations}" var="ed" >
																<a href="javascript:void(0);" value="${ed.key}" <c:if test="${educationId==ed.key}">class="selected"</c:if> >${fn:substring(ed.value, 0, 2)}以上</a>
														</c:forEach>
													</span>
													<em></em>
												</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w100 end-->
										
										
										<div class="w100"><!--w100 begin-->
											<div class="select_menu" name="monthlyIncome" load-lazy="true"><!--select_menu begin-->
												<p><a href="javascript:void(0);"></a></p>
												<div></div>
													<div class="select_box"><!--select_box begin-->
														<span>
															<a href="javascript:void(0);" value="0" <c:if test="${minMonthlyIncome=='0'}">class="selected"</c:if>>收入不限</a>
															<a href="javascript:void(0);" value="2000"   <c:if test="${minMonthlyIncome=='2000'}">class="selected"</c:if>		>&gt;2000</a>
															<a href="javascript:void(0);" value="5000"  <c:if test="${minMonthlyIncome=='5000'}">class="selected"</c:if> 	>&gt;5000</a>
															<a href="javascript:void(0);" value="10000" <c:if test="${minMonthlyIncome=='10000'}">class="selected"</c:if>  	>&gt;10000</a>
															<a href="javascript:void(0);" value="20000" <c:if test="${minMonthlyIncome=='20000'}">class="selected"</c:if> 	>&gt;20000</a>
															<a href="javascript:void(0);" value="30000" <c:if test="${minMonthlyIncome=='30000'}">class="selected"</c:if>	>&gt;30000</a>
															<a href="javascript:void(0);" value="50000"  	 <c:if test="${minMonthlyIncome=='50000'}">class="selected"</c:if>	>&gt;50000</a>
														</span>
														<em></em>
													</div><!--select_box end-->
											</div><!--select_menu end-->
										</div><!--w100 end-->
										
										<div class="txt">身高:</div>
											<div class="input"><!--input begin-->
											<p class="l"></p><span class="width80"><input name="minStringHeight" value="${minStringHeight}" type="text" /></span><p class="r"></p>
											</div><!--input end-->
										<div class="txt">到</div>
											<div class="input"><!--input begin-->
											<p class="l"></p><span class="width80"><input name="maxStringHeight" value="${maxStringHeight}" type="text" /></span><p class="r"></p>
											</div><!--input end-->
										<div class="txt">CM</div>
										<div class="btn" id="more"><a href='javascript:void(0);' class='query-btn'>搜索</a></div>
										</div><!--search_more_area end-->
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
															<b><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${not empty cityName}">${cityName}<c:if test="${not empty townName}">${townName}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty view.profile.profession}">${view.profile.profession}</c:if></b>
															<div class="zbq"><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import></font><a href="/post/${view.post.id}"><c:out value="${jzu:truncate(view.post.content,50,'...')}"></c:out></a></div>
															<c:choose>
																<c:when test="${!empty view.online && view.online}"><em class="on">当前在线</em></c:when>
																<c:otherwise>
																		<c:if test="${view.lastWebLoginTime != null}">
																			<c:set var="date" value="${view.lastWebLoginTime}" scope="request"/><c:import url="/WEB-INF/jsp/web/common/fragment/show_login_time.jsp" />
																		</c:if>
																</c:otherwise>
															</c:choose>
															<c:if test="${context.uid != view.profile.uid}">
																<span><a href="javascript:void(0);" class="message_u1 send-message" target-uid="${view.profile.uid}" target-nickname="<c:out value='${view.profile.nickname}' />">私信</a></span>
																<div class="keep"><a href="javascript:void(0);" target-uid="${view.profile.uid}" target-nickname="<c:out value='${view.profile.nickname}' />">约ta</a></div>
																<div class="date user-remove-interest remove-interest-${view.profile.uid} done" <c:if test="${!view.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${view.profile.uid}" title="点击取消关注">已关注</a></div>
																<div class="date user-add-interest interest-${view.profile.uid}" <c:if test="${view.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${view.profile.uid}" title="点击关注">+关注</a></div>
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
								<c:if test="${pager.totalResults > 0}">
									<div class="clear"></div>
									<c:if test="${minStringAge==''}"><c:set var="minStringAge" value="0" /></c:if>
									<c:if test="${maxStringAge==''}"><c:set var="maxStringAge" value="0" /></c:if>
									<c:if test="${minStringHeight==''}"><c:set var="minStringHeight" value="0" /></c:if>
									<c:if test="${maxStringHeight==''}"><c:set var="maxStringHeight" value="0" /></c:if>
									<c:if test="${strConstellationIds==''}"><c:set var="strConstellationIds" value="0" /></c:if>
									<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
										<c:param name="pager" value="${pager}"/>
										<c:param name="url" value="/searchusers/${townId}_${sex}_${minStringAge}_${maxStringAge}_${minStringHeight}_${maxStringHeight}_${strConstellationIds}_${educationId}_${minMonthlyIncome}" />
									</c:import>
								</c:if>
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="/WEB-INF/jsp/web/search/common/search_post_input.jsp" />
						<jsp:include page="/WEB-INF/jsp/web/index/zbe/recommend_users_widget.jsp" />
						<c:if test="${empty isQplus||!isQplus}">
							<jsp:include page="/WEB-INF/jsp/web/home/index/share_widget.jsp" />
						</c:if>
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/login/login_tips.jsp" />
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/search_user.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
