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
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
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
									<div class="good_idea_detail"><!--good_idea_detail begin-->
										<div class="pub_box"><!--pub_box begin-->
											<div class="pub_box_t"></div>
											<div class="pub_box_m"><!--pub_box_m begin-->
												<div class="idea_show"><!--idea_show begin-->
													<c:if test="${not empty idea.pic}">
														<div class="idea_pic"><a href="#"><img src="${jzr:ideaPic(idea.id, idea.pic,450)}" width="250" /></a></div>
													</c:if>
													<div class="idea_infor"><!--idea_infor begin-->
														<h2><c:out value="${idea.content}" /></h2>
														<c:if test="${not empty idea.link}">
															<p>链接:</p><span><a href="${idea.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关内容</a></span>
														</c:if>
														<c:if test="${idea.startTime != null || idea.endTime != null}">
															<p>时间:</p><span><c:if test="${idea.startTime != null}"><fmt:formatDate value="${idea.startTime}" pattern="yyyy.MM.dd hh:mm"/>-</c:if><fmt:formatDate value="${idea.endTime}" pattern="yyyy.MM.dd hh:mm"/></span>
														</c:if>
														<c:if test="${not empty idea.place}">
															<p>地点:</p><span>${jzd:cityName(idea.city)}${jzd:townName(idea.town)}&nbsp;<c:out value="${idea.place}"/></span>
														</c:if>
														<c:if test="${not empty idea.charge && idea.charge > 0}">
															<p>费用:</p><span>${idea.charge}元</span>
														</c:if>
														<c:if test="${idea.categoryId > 0}">
															<p>类型:</p><span><a href="/showideas/${idea.categoryId}/time/1">${jzd:categoryName(idea.categoryId)}</a></span>
														</c:if>
														<c:if test="${ideaCreateUser != null}">
															<p>来自: <a href="/home/${ideaCreateUser.uid}" class="user"><c:out value="${ideaCreateUser.nickname}" /></a></p>
														</c:if>
														<div class="clear"></div>
													</div><!--idea_infor end-->
													<div class="edit_error"><a href="/idea/update/${idea.id}">修改/报错</a></div>
													<div class="idea_btns"><!--idea_btns begin-->
														<c:choose>
															<c:when test="${hasUsed}">
																<div class="done"><a href="javascript:void(0);">已想去</a></div>
															</c:when>
															<c:otherwise>
																<div class="fb_btn idea-btn idea-btn-${idea.id}"><a href="javascript:void(0);" idea-id="${idea.id}">我想去</a></div>															
															</c:otherwise>
														</c:choose>
														<c:if test="${not empty isQplus && isQplus}">
															<div class="share_icon">
																<a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${idea.content}<%--<c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if>--%><c:if test='${not empty idea.place}'> 地点:${idea.place}</c:if>','','${jzr:ideaPic(idea.id,idea.pic, 200)}','','拒宅网');return false;" title="分享">分享</a>
															</div>
														</c:if>
													</div><!--idea_btns end-->
												</div><!--idea_show end-->
											</div><!--pub_box_m end-->
											<div class="clear"></div>
											<div class="pub_box_b"></div>
										</div><!--pub_box end-->
										<c:if test="${ideaDetail != null}">
											<div class="pub_box"><!--pub_box begin-->
												<div class="pub_box_t"></div>
												<div class="pub_box_m"><!--pub_box_m begin-->
													<div class="idea_js h200"><!--idea_js begin-->
														<h2>简介</h2>
														<p>${ideaDetail.detail}</p>
													</div><!--idea_js end-->
													<a href="javascript:void(0);" class="show_more">展开更多&gt;&gt;</a>
												</div><!--pub_box_m end-->
												<div class="clear"></div>
												<div class="pub_box_b"></div>
											</div><!--pub_box end-->
										</c:if>
										<div class="pub_box"><!--pub_box begin-->
											<div class="pub_box_t"></div>
											<div class="pub_box_m"><!--pub_box_m begin-->
												<jsp:include page="idea_user_list.jsp" />
											</div><!--pub_box_m end-->
											<div class="clear"></div>
											<div class="pub_box_b"></div>
										</div><!--pub_box end-->
									</div><!--good_idea_detail end-->
								</div><!--jz_list end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					
					
					<%-- <div class="main_left"><!--main_left begin-->
						<div class="content_box w660 z800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<h2>拒宅好主意</h2>
										<div class="back"><a href="/showideas">查看全部</a></div>
									</div><!--search_title end-->
									<div class="good_idea_detail"><!--good_idea_detail begin-->
										<div class="pub_box"><!--pub_box begin-->
											<div class="pub_box_t"></div>
											<div class="pub_box_m"><!--pub_box_m begin-->
												<p><c:out value="${idea.content}" /><c:if test="${ideaCreateUser != null}"><a href="/home/${ideaCreateUser.uid}" class="from">来自&nbsp;<c:out value="${ideaCreateUser.nickname}" /></a></c:if></p>
												<div class="infor"><!--infor begin-->
													<div class="clear"></div>
													<c:if test="${not empty idea.pic}">
														<div class="img"><img src="${jzr:ideaPic(idea.id, idea.pic,450)}"/></div>
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
														<span class="link"><a href="${idea.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关链接</a></span>
													</c:if>
												</div><!--infor end-->
												<div class="fb_area"><!--fb_area begin-->
													<c:if test="${pager.totalResults <= 0}">
														<div class="fb_ts">点击右侧发布拒宅按钮，抢沙发</div>
													</c:if>
													<c:choose>
														<c:when test="${hasUsed}">
															<div class="done"><a href="javascript:void(0);">已想去</a></div>
														</c:when>
														<c:otherwise>
															<div class="fb_btn idea-btn idea-btn-${idea.id}"><a href="javascript:void(0);" idea-id="${idea.id}">我想去</a></div>															
														</c:otherwise>
													</c:choose>
													<c:if test="${not empty isQplus && isQplus}">
														<div class="share_icon">
															<a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${idea.content}<c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if><c:if test='${not empty idea.place}'> 地点:${idea.place}</c:if>','','${jzr:ideaPic(idea.id,idea.pic, 200)}','','拒宅网');return false;" title="分享">分享</a>
														</div>
													</c:if>		
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
					</div><!--main_left end--> --%>
					<div class="main_right"><!--main_right begin-->
						<c:if test="${empty isQplus || !isQplus}">
							<jsp:include page="/WEB-INF/jsp/web/idea/common/share_idea_widget.jsp" />
						</c:if>			
							<jsp:include page="/WEB-INF/jsp/web/index/cqw/recent_idea_widget.jsp" />
							<jsp:include page="/WEB-INF/jsp/web/index/cqw/idea_ad_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/idea_detail.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
