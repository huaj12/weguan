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
		<title>${jzd:cityName(idea.city )}拒宅好主意_<c:out value="${jzu:truncate(idea.content, 60, '...')}" />_拒宅网(51juzhai.com)</title>
		<meta name="keywords" content="${jzd:cityName(idea.city )}拒宅 ,${jzd:cityName(idea.city)}好主意,${jzd:cityName(idea.city )}出去玩,${jzd:cityName(idea.city )}约会地点,${jzd:cityName(idea.city )}约会,${jzd:cityName(idea.city )}交友" />
		<meta name="description" content="<c:if test="${!empty jzd:cityName(idea.city )}">在jzd:cityName(idea.city )}</c:if>周末不想宅在家拒宅网帮你出好主意,<c:out value="${jzu:truncate(idea.content, 120, '...')}" />_" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 z800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<h2>拒宅好主意</h2>
										<div class="back"><a href="/showIdeas">查看全部</a></div>
									</div><!--search_title end-->
									<div class="good_idea_detail"><!--good_idea_detail begin-->
										<div class="pub_box"><!--pub_box begin-->
											<div class="pub_box_t"></div>
											<div class="pub_box_m"><!--pub_box_m begin-->
												<p><c:out value="${idea.content}" /><c:if test="${ideaCreateUser != null}"><a href="/home/${ideaCreateUser.uid}" class="from">来自&nbsp;<c:out value="${ideaCreateUser.nickname}" /></a></c:if></p>
												<div class="infor"><!--infor begin-->
													<div class="clear"></div>
													<c:if test="${not empty idea.pic}">
														<div class="img"><a href="${jzr:ideaPic(idea.id, idea.pic, 0)}" target="_blank"><img src="${jzr:ideaPic(idea.id, idea.pic,450)}"/></a></div>
													</c:if>
													<span><c:set var="date" value="${idea.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
													<c:if test="${idea.categoryId > 0}">
														<span class="tag">${jzd:categoryName(idea.categoryId)}</span>
													</c:if>
													<c:if test="${idea.date != null}">
														<span class="time"><fmt:formatDate value="${idea.date}" pattern="yyyy.MM.dd"/></span>
													</c:if>
													<c:if test="${not empty idea.place}">
														<span class="adress"><c:out value="${idea.place}" /></span>
													</c:if>
													<c:if test="${not empty idea.link}">
														<span class="link"><a href="${idea.link}" target="_blank">查看相关链接</a></span>
													</c:if>
												</div><!--infor end-->
												<div class="fb_area"><!--fb_area begin-->
													<c:if test="${pager.totalResults <= 0}">
														<div class="fb_ts">点击右侧发布拒宅按钮，抢沙发</div>
													</c:if>
													<c:choose>
														<c:when test="${hasUsed}">
															<div class="send_done"><a href="javascript:void(0);">已发布</a></div>
														</c:when>
														<c:otherwise>
															<div class="sending" style="display: none;"><a href="javascript:void(0);">发布中</a></div>
															<div class="fb_btn"><a href="javascript:void(0);" idea-id="${idea.id}">发布拒宅</a></div>															
														</c:otherwise>
													</c:choose>
												</div><!--fb_area end-->
											</div><!--pub_box_m end-->
											<div class="clear"></div>
											<div class="pub_box_b"></div>
										</div><!--pub_box end-->
									</div><!--good_idea_detail end-->
									<div class="response"><!--response begin-->
										<jsp:include page="idea_user_list.jsp" />
									</div><!--response end-->
								</div><!--jz_list end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="/WEB-INF/jsp/web/index/cqw/idea_ad_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/web/idea_detail.js')}"></script>
	</body>
</html>
