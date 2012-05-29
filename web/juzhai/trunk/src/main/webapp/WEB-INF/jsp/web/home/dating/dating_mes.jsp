<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>约我的人 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="index_page  <c:choose><c:when test='${profile.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--index_page begin-->
							<jsp:include page="/WEB-INF/jsp/web/home/common/home_info.jsp" />
							<c:set var="tabType" value="datingMes" scope="request" />
							<jsp:include page="/WEB-INF/jsp/web/home/common/tab.jsp" />
							<c:choose>
								<c:when test="${empty datingViewList}">
									<div class="item_none">目前还没有人约你哦<a href="#">去找伴儿》</a></div>
								</c:when>
								<c:otherwise>
									<jsp:include page="datings_list.jsp" />
								</c:otherwise>
							</c:choose>
						</div><!--index_page end-->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/home_header.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/home_dating_mes.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
