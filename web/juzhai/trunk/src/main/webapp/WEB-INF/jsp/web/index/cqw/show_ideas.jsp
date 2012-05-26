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
		<title>${jzd:cityName(cityId)}出去玩,${jzd:cityName(cityId)}拒宅好主意 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta name="keywords" content="${jzd:cityName(cityId)}拒宅,${jzd:cityName(cityId)}出去玩" />
		<meta name="description" content="周末不想宅在家拒宅网为你提供${jzd:cityName(cityId)}拒宅好主意,发现同兴趣的朋友,帮你拒宅" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<jsp:include page="/WEB-INF/jsp/web/common/youke_login.jsp" />
					<c:if test="${topIdea != null}">
						<div class="main_tj"><!--main_tj begin-->
							<div class="big_pic"><!--big_pic begin-->
								<p><a href="/idea/${topIdea.id}"><img src="${jzr:ideaPic(topIdea.id, topIdea.pic,450)}" width="430" /></a></p>
								<span><em>${jzu:truncate(topIdea.content,46,'...')}</em><a href="/idea/${topIdea.id}">共${topIdea.useCount}人想去</a></span>
							</div><!--big_pic end-->
							<div class="small_pic"><!--small_pic begin-->
								<ul>
									<li><div class="tj"><a href="/idea/select/category"></a></div></li>
									<c:forEach var="topIdea" items="${topIdeaList}">
										<li class="mouseHover">
											<p><a href="/idea/${topIdea.id}"><img src="${jzr:ideaPic(topIdea.id, topIdea.pic,200)}" width="160"/></a></p>
											<span><em>${jzu:truncate(topIdea.content,48,'...')}</em></span>
										</li>
									</c:forEach>
								</ul>
							</div><!--small_pic end-->
					</div><!--main_tj end-->
					</c:if>
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<h2>拒宅好主意&nbsp;—&nbsp;<c:choose><c:when test="${categoryId > 0}">${jzd:categoryName(categoryId)}</c:when><c:otherwise>全部</c:otherwise></c:choose></h2>
										<%-- <div class="w70"><!--w70 begin-->
											<div id="city-select" class="l_select_menu"><!--l_select_menu begin-->
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
											</div><!--l_select_menu end-->
										</div><!--w70 end--> --%>
										<div class="category" order-type="${orderType}"><!--category begin-->
											<span <c:if test="${empty orderType||'time'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/time/1">最新</a><p></p></span>
											<span <c:if test="${'pop'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/pop/1" >最热</a><p></p></span>
										</div><!--category end-->
									</div><!--search_title end-->
									<div class="clear"></div>
									<div class="good_idea"><!--good_idea begin-->
										<c:choose>
											<c:when test="${not empty ideaViewList}">
												<c:forEach items="${ideaViewList}" var="ideaView">
													<div class="idea_box mouseHover"><!--pub_box begin-->
														<div class="idea_box_t"></div>
														<div class="idea_box_m"><!--pub_box_m begin-->
															<c:if test="${not empty ideaView.idea.pic}">
																<div class="img"><a href="/idea/${ideaView.idea.id}"><img src="${jzr:static('/images/web2/1px.gif')}" data-original="${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}" /></a></div>
															</c:if>
															<p><a href="/idea/${ideaView.idea.id}"><c:out value="${ideaView.idea.content}" /></a><c:if test="${ideaView.profileCache != null}"><a href="/home/${ideaView.profileCache.uid}" class="from">来自&nbsp;<c:out value="${ideaView.profileCache.nickname}" /></a></c:if></p>
															<c:if test="${ideaView.idea.categoryId > 0}">
																<span class="tag">${jzd:categoryName(ideaView.idea.categoryId)}</span>
															</c:if>
															<c:if test="${ideaView.idea.startTime != null || ideaView.idea.endTime != null}">
																<span class="time"><c:if test="${ideaView.idea.startTime != null}"><fmt:formatDate value="${ideaView.idea.startTime}" pattern="yyyy.MM.dd hh:mm"/>-</c:if><fmt:formatDate value="${ideaView.idea.endTime}" pattern="yyyy.MM.dd hh:mm"/></span>
															</c:if>
															<c:if test="${not empty ideaView.idea.place}">
																<span class="adress"><c:out value="${ideaView.idea.place}"></c:out></span>
															</c:if>
															<c:if test="${!empty ideaView.idea.link }">
																<span class="link"><a href="${ideaView.idea.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关链接</a></span>
															</c:if>
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
																		<div class="done"><a href="javascript:void(0);">已想去</a></div>
																	</c:when>
																	<c:otherwise>
																		<div class="fb_btn idea-btn idea-btn-${ideaView.idea.id}"><a href="javascript:void(0);" idea-id="${ideaView.idea.id}">我想去</a></div>															
																	</c:otherwise>
																</c:choose>
																<c:choose>
																	<c:when test="${not empty isQplus && isQplus}">	
																		<div class="share_icon"><a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${ideaView.idea.content}<%--<c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if>--%> <c:if test='${not empty ideaView.idea.place}'> 地点:${ideaView.idea.place}</c:if>','','${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}','','拒宅网');return false;" title="分享">分享</a></div>
																	</c:when>
																	<c:otherwise>
																		<div class="zj_friend"><!--zj_friend begin-->
																		<a href="javascript:void(0);" idea-id="${ideaView.idea.id}" class="zj" >邀朋友</a>
																		<div class="show_icons" id="show_icons_${ideaView.idea.id}" style="display: none">
																			<!-- Baidu Button BEGIN -->
																			    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'text':'谁想去:${ideaView.idea.content}<%-- <c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty ideaView.idea.place}'> 地点:${ideaView.idea.place}</c:if> (拒宅网:助你脱宅)','wbuid':'2294103501','url':'http://www.51juzhai.com/idea/${ideaView.idea.id}','pic':'${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}'}">
																			        <a class="bds_tsina"></a>
																			        <a class="bds_tqq"></a>
																			        <a class="bds_douban"></a>
																			    </div>
																			    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'text':'谁想去:${ideaView.idea.content}<%-- <c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty ideaView.idea.place}'> 地点:${ideaView.idea.place}</c:if> (拒宅网:助你脱宅)','url':'http://www.51juzhai.com/idea/${ideaView.idea.id}'}">
																			         <a class="bds_renren"></a>
																			    </div>
																			    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'text':'加入拒宅找伴儿出去玩!','url':'http://www.51juzhai.com/idea/${ideaView.idea.id}','comment':'谁想去:${ideaView.idea.content}<%-- <c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty ideaView.idea.place}'> 地点:${ideaView.idea.place}</c:if> (拒宅网:助你脱宅)','pic':'${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}'}">
																			       	<a class="bds_qzone"></a>
																			       	<a class="bds_kaixin001"></a>
																			    </div>
																				<!-- Baidu Button END -->
																				<div class="txt">分享给朋友</div>
																			</div>
																		</div><!--zj_friend end-->
																	</c:otherwise>
																</c:choose>
															</div><!--fb_area end-->
														</div><!--pub_box_m end-->
														<div class="clear"></div>
														<div class="idea_box_b"></div>
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
										<c:param name="url" value="/showideas/${categoryId}/${orderType}" />
									</c:import>
								</c:if>
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="idea_category_widget.jsp" />
						<jsp:include page="recent_idea_widget.jsp" />
						<jsp:include page="idea_ad_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/show_ideas.js')}"></script>
			<script type="text/javascript" id="bdshare_js" data="mini=1&uid=593065" ></script>
			<script type="text/javascript" id="bdshell_js"></script>
			<script type="text/javascript">
				var bds_config = {'snsKey':{'tsina':'3631414437','qzone':'100249114','douban':'00fb7fece2b96fd202f27fc6a82c4f76'}};
				document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
			</script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>

