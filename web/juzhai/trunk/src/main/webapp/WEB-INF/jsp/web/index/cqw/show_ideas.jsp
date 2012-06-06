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
		<title>${jzd:cityName(cityId)}拒宅好主意 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
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
					
					<div class="idea_right"><!--main_right begin-->
						<div class="content_box w660 800"><!--content begin-->
						<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<div class="idea_category" order-type="${orderType}"><!--category begin-->
											<span <c:if test="${empty orderType||'window'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/window/1">推荐</a><p></p></span>
											<span <c:if test="${'time'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/time/1">最新</a><p></p></span>
											<span <c:if test="${'pop'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/pop/1" >最热</a><p></p></span>
										</div><!--category end-->
										<c:if test="${not empty ideaViewList}">
											<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
												<c:param name="pager" value="${pager}"/>
												<c:param name="url" value="/showideas/${categoryId}/${orderType}" />
											</c:import>
										</c:if>
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
																<p><a href="/idea/${ideaView.idea.id}"><c:out value="${jzu:truncate(ideaView.idea.content,64,'...')}" /></a><c:if test="${ideaView.profileCache != null}"><a href="/home/${ideaView.profileCache.uid}" class="from">来自&nbsp;<c:out value="${ideaView.profileCache.nickname}" /></a></c:if></p>
																<c:if test="${ideaView.idea.categoryId > 0}">
																	<span class="tag">${jzd:categoryName(ideaView.idea.categoryId)}</span>
																</c:if>
																<c:if test="${ideaView.idea.startTime != null || ideaView.idea.endTime != null}">
																	<span class="time"><c:if test="${ideaView.idea.startTime != null}"><fmt:formatDate value="${ideaView.idea.startTime}" pattern="yyyy.MM.dd HH:mm"/>-</c:if><fmt:formatDate value="${ideaView.idea.endTime}" pattern="yyyy.MM.dd HH:mm"/></span>
																</c:if>
																<c:if test="${not empty ideaView.idea.place}">
																	<span class="adress"><c:out value="${jzu:truncate(ideaView.idea.place,40,'...')}"></c:out></span>
																</c:if>
															<span class="jj_height"></span>
															<div class="fb_area"><!--fb_area begin-->
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
																		<div class="share_icon"><a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${ideaView.idea.content}<%--<c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if>--%> <c:if test='${not empty ideaView.idea.place}'> 地点:${jzu:truncate(ideaView.idea.place,40,'...')}</c:if>','','${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}','','拒宅网');return false;" title="分享">分享</a></div>
																	</c:when>
																	<c:otherwise>
																		<div class="zj_friend"><!--zj_friend begin-->
																		<a href="javascript:void(0);" idea-id="${ideaView.idea.id}" class="zj" >邀朋友</a>
																		<div class="show_icons" id="show_icons_${ideaView.idea.id}" style="display: none">
																			<!-- Baidu Button BEGIN -->
																			    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'text':'谁想去:${ideaView.idea.content}<%-- <c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty ideaView.idea.place}'> 地点:${jzu:truncate(ideaView.idea.place,40,'...')}</c:if> (拒宅网:助你脱宅)','wbuid':'2294103501','url':'http://www.51juzhai.com/idea/${ideaView.idea.id}','pic':'${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}'}">
																			        <a class="bds_tsina"></a>
																			        <a class="bds_tqq"></a>
																			        <a class="bds_douban"></a>
																			    </div>
																			    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'text':'谁想去:${ideaView.idea.content}<%-- <c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty ideaView.idea.place}'> 地点:${jzu:truncate(ideaView.idea.place,40,'...')}</c:if> (拒宅网:助你脱宅)','url':'http://www.51juzhai.com/idea/${ideaView.idea.id}'}">
																			         <a class="bds_renren"></a>
																			    </div>
																			    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'text':'加入拒宅找伴儿出去玩!','url':'http://www.51juzhai.com/idea/${ideaView.idea.id}','comment':'谁想去:${ideaView.idea.content}<%-- <c:if test='${ideaView.idea.date != null}'> 时间:<fmt:formatDate value='${ideaView.idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty ideaView.idea.place}'> 地点:${jzu:truncate(ideaView.idea.place,40,'...')}</c:if> (拒宅网:助你脱宅)','pic':'${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic, 200)}'}">
																			       	<a class="bds_qzone"></a>
																			       	<a class="bds_kaixin001"></a>
																			    </div>
																				<!-- Baidu Button END -->
																				<div class="txt">分享给朋友</div>
																			</div>
																		</div><!--zj_friend end-->
																	</c:otherwise>
																</c:choose>
																<div class="fb_members">
																	<c:if test="${ideaView.idea.useCount>0}"><a href="/idea/${ideaView.idea.id}">共${ideaView.idea.useCount}人想去</a></c:if>
																</div>
																
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
					</div><!--main_right end-->
					
					<div class="idea_left"><!--main_right begin-->
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

