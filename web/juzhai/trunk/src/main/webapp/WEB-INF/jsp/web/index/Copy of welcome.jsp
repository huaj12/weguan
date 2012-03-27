<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找伴,出去玩,上海约会地点,北京约会地点,深圳约会地点-拒宅网(51juzhai.com)-帮你找伴出去玩提供有创意的约会地点</title>
<meta name="keywords"
	content="拒宅,找伴,出去玩,上海约会地点,北京约会地点,深圳约会地点,创意约会地点,约会地点,约会" />
<meta name="description"
	content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海约会地点,北京约会地点,深圳约会地点,创意约会地点和同兴趣的朋友,促成约会" />
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<div class="fix_top">
				<!--fix_top begin-->
				<div class="beta"></div>
				<div class="top">
					<!--top begin-->
					<h2></h2>
					<div class="welcome_login">
						<iframe width="100%" height="24" frameborder="0" allowtransparency="true" marginwidth="0" marginheight="0" scrolling="no" border="0" src="http://widget.weibo.com/relationship/followbutton.php?language=zh_cn&width=100%&height=24&uid=2294103501&style=3&btn=red&dpc=1"></iframe>
					</div>
				</div>
				<!--top end-->
			</div>
			<!--fix_top end-->
			<div class="main_part">
				<!--main_part begin-->
				<div class="main_left">
					<!--main_left begin-->

					<div class="huanying">
						<!--huanying begin-->

						<p>加入我们，寻找你的阳光周末：</p>

						<span> <a href="/web/login/6?turnTo=${turnTo}"><img src="${jzr:static('/images/hy_btn_wb.gif')}" />
						</a>   <a href="/web/login/7?turnTo=${turnTo}"><img src="${jzr:static('/images/hy_btn_db.gif')}" />
						</a>   <a href="/web/login/8?turnTo=${turnTo}" ><img src="${jzr:static('/images/hy_btn_qq.gif')}" />
						</a> </span>

					</div>
					<!--huanying end-->


					<div class="content_box w660 800">
						<!--content begin-->

						<div class="t"></div>

						<div class="m">

							<div class="jz_list">
								<!--jz_list begin-->

								<div class="title">
									<!--title begin-->

									<h2>大家的拒宅</h2>

								</div>
								<!--title end-->

								<div class="jz_main">
									<!--jz_main begin-->


									<c:forEach items="${postWindowViews}" var="view">
									<div class="jz_item hover girl">
										<!--jz_item begin-->
										<div class="face_infor">
											<!--face_infor begin-->

											<p>
												<a href="#"><img src="${jzr:userLogo(view.profileCache.uid, view.profileCache.logoPic, 120)}" />
												</a>
											</p>
															<c:set var="cityName" value="${jzd:cityName(view.profileCache.city)}" />
															<c:set var="townName" value="${jzd:townName(view.profileCache.town)}" />
															<c:set var="age" value="${jzu:age(view.profileCache.birthYear,view.profileCache.birthSecret)}" />
															<c:set var="constellationName" value="${jzd:constellationName(view.profileCache.constellationId)}" />
											<a href="/home/${view.profileCache.uid}">${view.profileCache.nickname}</a> <span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${not empty cityName}">${cityName}<c:if test="${not empty townName}">${townName}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty view.profileCache.profession}">${view.profileCache.profession}</c:if></span>

										</div>
										<!--face_infor end-->



										<div class="wtg">
											<!--wtg begin-->

											<div class="w_t"></div>

											<div class="w_m">
												<!--w_m begin-->

												<div class="arrow"></div>

												<p>
													<font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.postWindow.purposeType}"/></c:import></font><a href="/post/${view.postWindow.postId}"><c:out value="${jzu:truncate(view.postWindow.content, 74, '...')}" /></a>
												</p>
												<div class="infor">
													<!--infor begin-->

													<span>12秒前更新</span> <span class="adress">张江故园餐厅</span> <span
														class="time">01.21 周一</span> <span class="tag">约会</span>

												</div>
												<!--infor end-->



											</div>
											<!--w_m end-->

											<div class="clear"></div>

											<div class="w_b"></div>

											<div class="btn">
												<!--btn begin-->
												<div class="like">
													<a href="#" class="xy">响应</a>
													<div class="xy_num">
														<p class="l"></p>
														<a href="#">214</a>
														<p class="r"></p>
													</div>
												</div>

												<div class="message_s2">
													<a href="#">留言(123)</a>
												</div>
											</div>
											<!--btn end-->
											<div class="clear"></div>

										</div>
										<!--wtg end-->

									</div>
									<!--jz_item end-->
							</c:forEach>

								</div>
								<!--jz_main end-->


							</div>
							<!--jz_list end-->

							<div class="clear"></div>

							<div class="line_s1"></div>

						</div>

						<div class="t"></div>

					</div>
					<!--content end-->

				</div>
				<!--main_left end-->

				<div class="main_right">
					<!--main_right begin-->

					<div class="content_box w285">
						<!--content begin-->

						<div class="t"></div>
						<div class="m">

							<div class="right_title">
								<h2>拒宅好主意</h2>
								<a href="/showideas">更多</a>
							</div>
							<div class="idea">
								<!--idea begin-->
								<ul>
								<c:forEach items="${ideaWindowViews }" var="view">
									<li class="bn">
										<p>
											<a href="#">${view.idea.content}</a><c:if test="${!empty view.profileCache}"> <a href="/home/${view.profileCache.uid}" class="from">来自${view.profileCache.nickname}的好主意</a></c:if>
										</p>
										<c:if test="${not empty jzr:ideaPic(view.idea.id,view.idea.pic, 200)}">
										<div class="img">
											<a href="/idea/${view.idea.id}"><img src="${jzr:ideaPic(view.idea.id,view.idea.pic, 200)}" /></a>
										</div> 
										</c:if>
										<span><a href="/idea/${view.idea.id}">共${view.idea.useCount}人想去</a>
									</span>
										<div class="send">
											<a href="#">发布拒宅</a>
										</div></li>
								</c:forEach>
								</ul>
							</div>
							<!--idea end-->
						</div>

						<div class="t"></div>

					</div>
					<!--content end-->

				</div>
				<!--main_right end-->


			</div>
			<!--main_part end-->

		</div>
		<!--main end-->
		<c:set var="footType" value="welcome" scope="request" />
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<script type="text/javascript" src="${jzr:static('/js/web/welcome.js')}"></script>
</body>
</html>
