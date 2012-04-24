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
														<div class="img"><a href="${jzr:ideaPic(idea.id, idea.pic, 0)}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>><img src="${jzr:ideaPic(idea.id, idea.pic,450)}"/></a></div>
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
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
							<div class="content_box w285"><!--content begin-->
								<div class="t"></div>
								<div class="m">
										<div class="right_title"><h2>邀请好友同去</h2></div>
										<div class="share_icons"><!--share_icons begin-->
											<!-- Baidu Button BEGIN -->
										    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'谁想去:${idea.content}<c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if><c:if test='${not empty idea.place}'> 地点:${idea.place}</c:if> (拒宅网:助你脱宅)','wbuid':'2294103501','url':'http://www.51juzhai.com/idea/${idea.id}','pic':'${jzr:ideaPic(idea.id,idea.pic, 200)}'}">
										        <a class="bds_tsina"></a>
										        <a class="bds_tqq"></a>
										        <a class="bds_douban"></a>
										    </div>
										    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'谁想去:${idea.content}<c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if><c:if test='${not empty idea.place}'> 地点:${idea.place}</c:if> (拒宅网:助你脱宅)','url':'http://www.51juzhai.com/idea/${idea.id}'}">
										         <a class="bds_renren"></a>
										    </div>
										    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'加入拒宅找伴儿出去玩!','url':'http://www.51juzhai.com/idea/${idea.id}','comment':'谁想去:${idea.content}<c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if><c:if test='${not empty idea.place}'> 地点:${idea.place}</c:if> (拒宅网:助你脱宅)','pic':'${jzr:ideaPic(idea.id,idea.pic, 200)}'}">
										       	<a class="bds_qzone"></a>
										       	<a class="bds_kaixin001"></a>
										    </div>
											<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=593065" ></script>
											<script type="text/javascript" id="bdshell_js"></script>
											<script type="text/javascript">
											var bds_config = {'snsKey':{'tsina':'3631414437','qzone':'100249114','douban':'00fb7fece2b96fd202f27fc6a82c4f76'}};
												document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
											</script>
											<!-- Baidu Button END -->
									  	</div><!--share_icons end-->
								</div>
								<div class="t"></div>
							</div><!--content end-->
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
