<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>解救小宅</title>
		<meta name="keywords"   content="拒宅,找伴,出去玩,上海约会地点,北京约会地点,深圳约会地点,创意约会地点,约会地点,约会" />
		<meta  name="description"   content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海约会地点,北京约会地点,深圳约会地点,创意约会地点和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="jjxz"><!--jjxz begin-->
						<div class="title"><!--title begin-->
							<c:set var="gapWeekendHours" value="${jzu:gapWeekendHours()}" />
							<c:set var="gapDays"><fmt:parseNumber value="${gapWeekendHours / 24}" integerOnly="true"/></c:set>
							<c:set var="gapHours" value="${gapWeekendHours%24}" />
							<div class="jj_time">
								<p></p>
								<c:choose>
									<c:when test="${gapWeekendHours > 0}">
										<span>离周末还有<c:if test="${gapDays > 0}">${gapDays}天</c:if><c:if test="${gapHours > 0}">${gapHours}小时</c:if></span>
									</c:when>
									<c:otherwise><span>今天是周末，你还没脱宅么</span></c:otherwise>
								</c:choose>
							</div>
							<div class="jj_title">解救小宅</div>
							<div class="view_btn"><a href="javascript:void(0);" class="kw">调整口味</a><a href="javascript:void(0);" class="jj">我要被解救</a></div>
							<div class="kw_show" style="display:none;"><p>我们会根据你的拒宅偏好，为您推荐合适的人</p><a href="/profile/preference">去设置偏好</a></div>
							<div class="jj_show" style="display:none;"><p>发布一条拒宅即可被更新到被解救的队列中哦</p><a href="/home">去发布拒宅</a></div>
						</div><!--title end-->
						<div class="clear"></div>
						<div class="jjxz_con"><!--jjxz_con begin-->
							<div class="jj_top"></div>
							<div class="jj_mid"><!--jj_mid begin-->
								<div>
									<jsp:include page="card.jsp" />
								</div>
							</div><!--jj_mid end-->
							<div class="jj_bot"></div>
						</div><!--jjxz_con end-->
					</div><!--jjxz end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/rescue.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
