<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
						<jsp:include page="common/home_head.jsp" />
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="my_index"><!--my_index begin-->
									<%-- <c:set var="tabType" value="posts" scope="request"/>
									<jsp:include page="common/new_tab.jsp" /> --%>
									<div class="my_jz"><!--my_jz begin-->
										<jsp:include page="post/post_view.jsp" />
										<div class="more"><a href="/home/${profile.uid}/posts">查看全部</a></div>
									</div><!--my_jz end-->
								</div><!--my_index end-->
								<div class="weibo"><!--weibo begin-->
									<h2>ta的最近微薄</h2>
									<c:forEach var="userStatus" items="${userStatusList}">
										<ul>
											<li>
												<p>"${userStatus.content}"</p>
												<span>发布于 <fmt:formatDate value="${userStatus.time}" pattern="yyyy.MM.dd"/></span>
											</li>
										</ul>
									</c:forEach>
									<div class="wb_more"><a href="#">更多</a></div>
								</div><!--weibo end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="common/home_info_right.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/web/home_posts.js')}"></script>
	</body>
</html>