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
		<title>好主意-发布-选择类别 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
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
									<div class="title"><h2>请选择你要发布哪类拒宅信息</h2>
										<div class="hd_share">
											<p>分享你想去的:</p>
											<a href="javascript:void(0);" class="hd">活动</a>
											<a href="javascript:void(0);" class="qc">好去处</a>
											<a href="javascript:void(0);" class="tg">团购</a>
										</div>
									</div>
									<div class="hr_line"></div>
									<div class="hd_ca"><!--hd_ca begin-->
										<ul>
											<c:forEach var="cat" items="${categoryList}">
												<li><a href="/idea/create/${cat.id }" class="${cat.icon }">${cat.name}</a></li>
											</c:forEach>
										</ul>
									</div><!--hd_ca end-->
								</div><!--hd_form end-->
							</div><!--area_m end-->
						<div class="clear"></div>
						<div class="area_b"></div>
						</div><!--huodong_area end-->
					</div><!--main end-->
					<jsp:include page="/WEB-INF/jsp/web/home/dialog/share_box.jsp" />
					<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
					<script type="text/javascript" src="${jzr:static('/js/web/select_category.js')}"></script>
					<c:set var="footType" value="fixed" scope="request"/>
					<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
				</div><!--warp end-->

</body>

</html>
