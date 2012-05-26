<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><c:out value="${profile.nickname}"/>的拒宅<title>  拒宅网-助你找伴儿出去玩(51juzhai.com)</title></title>
		<meta name="keywords" content="拒宅,找伴,出去玩,约会,交友" />
		<meta name="description" content="${profile.nickname}的拒宅,不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
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
						<jsp:include page="common/home_head.jsp" />
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="my_index"><!--my_index begin-->
									<div class="title"><!--title begin-->
										<div class="category"><!--category begin-->
											<span><p></p><a href="javascript:void(0);">ta的拒宅</a><p></p></span>
										</div><!--category end-->
									</div><!--title end-->
									<div class="my_jz pbm20"><!--my_jz begin-->
										<c:choose>
											<c:when test="${empty postViewList}">
												<div class="none">ta没有发布过拒宅哦</div>
											</c:when>
											<c:otherwise>
												<jsp:include page="post/post_view.jsp" />
												<c:if test="${showMore}">
													<div class="more"><a href="/home/${profile.uid}/posts">查看全部</a></div>
												</c:if>
											</c:otherwise>
										</c:choose>
									</div><!--my_jz end-->
								</div><!--my_index end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
						<div class="content_box w660"><!--content begin-->
							<div class="t"></div>
								<div class="m">
									<div class="title2"><h2>ta的拒宅偏好</h2><a href="#" class="done"></a></div>
									<jsp:include page="./preference/preference_list.jsp" />
								</div>
							<div class="t"></div>
							<div class="clear"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="common/home_info_right.jsp" />
						<c:if test="${tpUser != null && tpUser.tpName != 'qq'}">
							<div class="content_box w285"><!--content begin-->
								<div class="t"></div>
								<div class="m">
								<div class="right_title"><h2>ta的<c:choose><c:when test="${tpUser.tpName == 'weibo'}">最新微博</c:when><c:when test="${tpUser.tpName == 'douban'}">豆瓣广播</c:when></c:choose></h2><a href="#"></a></div>
								<div class="weibo"><!--weibo begin-->
									<ul>
										<c:choose>
											<c:when test="${empty userStatusList}">
												<div class="none">ta还没有原创的微博</div>
											</c:when>
											<c:otherwise>
												<c:forEach var="userStatus" items="${userStatusList}">
													<li>
														<p>"<c:out value='${userStatus.content}'/>"</p>
														<span>发布于 <fmt:formatDate value="${userStatus.time}" pattern="yyyy.MM.dd"/></span>
													</li>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</ul>
								</div><!--weibo end-->
								</div>
								<div class="t"></div>
							</div><!--content end-->
						</c:if>
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/home_posts.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>