<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的首页 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,约会,交友" />
		<meta  name="description"   content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<c:if test="${hasNotAccount || hasNotActive}">
					<div class="float_box" style="display: none;bottom: -100px;"><!--float_box begin-->
						<div class="width960"><!--width960 begin-->
							<p>
								<c:choose>
									<c:when test="${hasNotAccount}">拒宅提示：你还没有设置拒宅邮箱，为了方便以后找回密码，建议你现在就去<a href="/passport/account">设置邮箱</a></c:when>
									<c:when test="${hasNotActive}">拒宅提示：你还没有验证拒宅邮箱，为了方便以后找回密码，建议你现在就去<a href="/passport/account">激活邮箱</a></c:when>
								</c:choose>
							</p>
							<div class="close"><a href="javascript:void(0);"></a></div>
						</div><!--width960 end-->
					</div><!--float_box end-->
				</c:if>
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 z900"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<c:choose>
									<c:when test="${not empty loginUser.logoPic || loginUser.logoVerifyState == 1}">
										<div class="tips" style="display: none;"><!--tips begin-->
											<p>没想好去哪玩？试试这个</p>
											<a href="javascript:void(0);"></a>
										</div><!--tips end-->
									</c:when>
									<c:otherwise>
										<div class="lock"><!--lock begin-->
											<a href="/profile/index/face">
											<img src="${jzr:static('/images/web2/bunengfabu.jpg')}" />
											</a>
										</div><!--lock end-->
									</c:otherwise>
								</c:choose>
								<div class="send_box"><!--send_box begin-->
									<jsp:include page="send_post.jsp" />
								</div>
							</div>
							<div class="t"></div>
						</div><!--content end-->
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<jsp:include page="post_list.jsp" />
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="/WEB-INF/jsp/web/home/index/home_logo.jsp" />
						<jsp:include page="/WEB-INF/jsp/web/home/index/idea_widget.jsp" />
						<jsp:include page="/WEB-INF/jsp/web/home/common/visitor_widget.jsp" />
						<jsp:include page="/WEB-INF/jsp/web/search/common/search_post_input.jsp" />
						<jsp:include page="/WEB-INF/jsp/web/home/index/new_user_widget.jsp" />
						<c:if test="${empty isQplus||!isQplus}">
							<jsp:include page="/WEB-INF/jsp/web/home/index/share_widget.jsp" />
						</c:if>
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/home/dialog/share_box.jsp" />
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/home.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
