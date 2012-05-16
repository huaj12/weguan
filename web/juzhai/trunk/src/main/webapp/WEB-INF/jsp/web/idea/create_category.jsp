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
		<title>好主意-发布-选择类别_拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>

					<div class="warp"><!--warp begin-->
					<div class="main"><!--main begin-->
					<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
						<div class="huodong_area"><!--huodong_area begin-->
							<div class="area_t"></div>
							<div class="area_m"><!--area_m begin-->
								<div class="hd_form"><!--hd_form begin-->
									<div class="title"><h2>请选择你要发布哪类拒宅信息</h2></div>
									<div class="hr_line"></div>
									<div class="hd_ca"><!--hd_ca begin-->
										<ul>
											<li><a href="/idea/create/3" class="jh_01">聚会活动</a></li>
											<li><a href="/idea/create/2" class="yw_02">结伴游玩</a></li>
											<li><a href="/idea/create/6" class="show_03">演出展览</a></li>
											<li><a href="/idea/create/5" class="eat_04">好吃好喝</a></li>
											<li><a href="/idea/create/1" class="happy_05">休闲娱乐</a></li>
											<li><a href="/idea/create/8" class="idea_06">拒宅灵感</a></li>
											<li><a href="/idea/create/7" class="sport_07">体育运动</a></li>
											<li><a href="/idea/create/4" class="movie_08">看场电影</a></li>
										</ul>
									</div><!--hd_ca end-->
								</div><!--hd_form end-->
							</div><!--area_m end-->
						<div class="clear"></div>
						<div class="area_b"></div>
						</div><!--huodong_area end-->
					</div><!--main end-->
					<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
					<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
				</div><!--warp end-->

</body>

</html>
