<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
																<c:if test="${not empty ideaView.idea.place}">
																	<span class="adress"><c:out value="${jzu:truncate(ideaView.idea.place,40,'...')}"></c:out></span>
																</c:if>
																<c:if test="${ideaView.idea.startTime != null || ideaView.idea.endTime != null}">
																	<span class="time"><c:if test="${ideaView.idea.startTime != null}"><fmt:formatDate value="${ideaView.idea.startTime}" pattern="yyyy.MM.dd HH:mm"/>-</c:if><fmt:formatDate value="${ideaView.idea.endTime}" pattern="yyyy.MM.dd HH:mm"/></span>
																</c:if>
															<span class="jj_height"></span>
															<div class="fb_area"><!--fb_area begin-->
																<c:choose>
																	<c:when test="${ideaView.hasUsed}">
																		<div class="done"><a href="javascript:void(0);">已想去</a></div>
																	</c:when>
																	<c:otherwise>
																		<div class="date_btn idea-btn idea-btn-${ideaView.idea.id}"><a href="javascript:void(0);" idea-id="${ideaView.idea.id}">我想去</a></div>															
																	</c:otherwise>
																</c:choose>
																<div class="xq"><c:if test="${ideaView.idea.useCount>0}"><a href="/idea/${ideaView.idea.id}/#ideaList">${ideaView.idea.useCount}人想去</a></c:if></div>
																<c:choose>
																	<c:when test="${ideaView.hasInterest}">
																		<div class="add_fav fav_done"><a href="javascript:void(0);">已收藏</a></div>
																	</c:when>
																	<c:otherwise>
																		<div class="add_fav idea-interest" idea-id="${ideaView.idea.id}"><a href="javascript:void(0);">收藏</a></div>
																	</c:otherwise>
																</c:choose>
															</div><!--fb_area end-->
														</div><!--pub_box_m end-->
														<div class="clear"></div>
														<div class="idea_box_b"></div>
													</div><!--pub_box end-->
												</c:forEach>