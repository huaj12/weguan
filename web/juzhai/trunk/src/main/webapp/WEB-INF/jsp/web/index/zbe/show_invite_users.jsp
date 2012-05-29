<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="拒宅 ,找伴,出去玩,约会 ,交友" />
		<meta name="description" content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
		<title>找伴儿 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<c:if test="${setFreeDate!=null||setFreeDate}">
			<jsp:include page="/WEB-INF/jsp/web/common/set_free_date.jsp" />
		</c:if>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<c:if test="${inviteUserList!=null}">
							<div class="zbr_yq"><!--zbr_yq begin-->
								<div class="title"><h2>邀请好友加入</h2><a href="/home">返回首页</a></div>
								<div class="friend_list" currentpage="1" totalpage="${totalPage}"><!--friend_list begin-->
									<c:choose>
										<c:when test="${empty inviteUserList}"><div class="none">抱歉！未发现该帐号下的好友！</div></c:when>
										<c:otherwise>
											<ul>
												<jsp:include page="invite_user_list.jsp" />
											</ul>
										</c:otherwise>
									</c:choose>
								</div><!--friend_list end-->
								<c:if test="${not empty inviteUserList}">
									<div class="btn_area"><!--btn_area begin-->
										<c:if test="${totalPage > 1}">
											<p><a href="javascript:void(0);" id="showMoreInvite">查看更多</a></p>
											<p id="showMoreInviteLoading" style="display: none;">正在加载中...</p>
										</c:if>
										<span><a id="inviteBtn" href="javascript:void(0);">邀请他们</a></span>
										<em id="inviteError" style="display:none;">每次邀请不要超过10个人哦</em>
									</div><!--btn_area end-->
								</c:if>
							</div><!--zbr_yq end-->
						</c:if>
						<!-- <div class="zbr_yq">zbr_yq begin
							<div class="title"><h2>邀请好友加入</h2><a href="/home">返回首页</a></div>
							<div class="friend_list">friend_list begin
								<ul>
									<li class="girl" title="点击添加"><p><img src="images/web2/face_girl.png"  width="50" height="50"/></p><span><a href="#">骑着上帝去流浪</a></span><em>上海</em></li>
								</ul>
							</div>friend_list end
							<div class="btn_area">btn_area begin
								<p style="display:none;"><a href="#">查看更多</a></p>
								<p>正在加载中...</p>	
								<span><a href="#">邀请他们</a></span>
								<em>每次邀请不要超过10个人哦</em>
							</div>btn_area end
						</div>zbr_yq end -->
					</div><!--m end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/show_invite_users.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/script/invite_plug.jsp" />
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
