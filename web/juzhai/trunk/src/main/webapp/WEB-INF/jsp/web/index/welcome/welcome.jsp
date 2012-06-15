<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,上海拒宅好主意,北京拒宅好主意,深圳拒宅好主意,创意拒宅好主意" />
		<meta  name="description"   content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海拒宅好主意,北京拒宅好主意,深圳拒宅好主意,创意拒宅好主意和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<c:if test="${empty isQplus||!isQplus}">
							<div class="beta"></div>
					</c:if>
					<div class="top"><!--top begin-->
						<h1></h1>
						<div class="menu"><!--menu begin-->
							<a href="/searchusers" title="找伴儿">找伴儿</a>
							<a href="/showideas" title="出去玩">出去玩</a>
						</div><!--menu end-->
						<div class="login_btn"><a href="/login" class="btn_log" title="登录">登录</a><a href="/passport/register" class="btn_res" title="注册">注册</a></div>
						<%-- <div class="welcome_login"><p>登录:</p>
							<a href="javascript:void(0);" go-uri="/web/login/6" class="wb" title="使用微博账号登录"></a>
							<a href="javascript:void(0);" go-uri="/web/login/7" class="db"  title="使用豆瓣账号登录"></a>
							<a href="javascript:void(0);" go-uri="/web/login/8" class="qq"  title="使用QQ账号登录"></a>
						</div>
						<div class="login_btns"><!--login_btns begin-->
							<p>加入拒宅：</p>
							<span>
								<a href="javascript:void(0);" class="wb login-btn" title="使用微博账号登录" go-uri="/web/login/6">登录</a>
								<a href="javascript:void(0);" class="db login-btn"  title="使用豆瓣账号登录" go-uri="/web/login/7">登录</a>
								<a href="javascript:void(0);" class="qq login-btn"  title="使用QQ账号登录" go-uri="/web/login/8">登录</a>
							</span>
						</div><!--login_btns end--> --%>
					</div><!--top end-->
				</div><!--fix_top end-->
					<div class="wel"><!--begin end-->
						<div class="wel_box w490 fl"><!--wel_box begin-->
							<div class="wel_t"></div>
							<div class="wel_m"><!--wel_m begin-->
								<h2><font>${ideaCount}</font>个拒宅好主意等你来发现</h2>
								<div class="wel_idea"><!--wel_idea begin-->
									<ul>
										<c:forEach items="${ideaViewList}" var="view">
											<li>
												<p><a href="/idea/${view.idea.id}"><img src="${jzr:ideaPic(view.idea.id,view.idea.pic, 200)}" /></a></p>
												<span><em><c:out value="${jzu:truncate(view.idea.content,46,'...')}"></c:out></em><a href="/idea/${view.idea.id}">${view.idea.useCount}人想去</a></span>
											</li>
										</c:forEach>
									</ul>
								</div><!--wel_idea begin-->
							</div><!--wel_m end-->
							<div class="wel_b"></div>
						</div><!--wel_box end-->
						<div class="wel_box w455 fr blue"><!--wel_box begin-->
							<div class="wel_t"></div>
							<div class="wel_m"><!--wel_m begin-->
								<h2>
								已有<font>${userCount}</font>人加入拒宅网,你周末还宅着么？<br />
								<em>快加入我们：寻找你的阳光周末</em>
								</h2>
								<div class="wel_login_area"><!--wel_login_area begin-->
									<a href="javascript:void(0);" class="wb login-btn" title="使用微博账号登录" go-uri="/web/login/6"></a>
									<a href="javascript:void(0);" class="db login-btn" title="使用豆瓣账号登录" go-uri="/web/login/7"></a>
									<c:choose>
										<c:when test="${not empty isQplus&&isQplus}">
										<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/qplus/loginDialog/9"></a>
										</c:when>
										<c:otherwise>
										<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/web/login/8"></a>	
										</c:otherwise>
									</c:choose>
								</div><!--wel_login_area end-->
							</div><!--wel_m end-->
							<div class="wel_b"></div>
						</div><!--wel_box end-->
						<div class="wel_box w455 fr"><!--wel_box begin-->
							<div class="wel_t"></div>
							<div class="wel_m"><!--wel_m begin-->
								<h2><font>${postCount }</font>人正在找伴儿出去玩</h2>
								<div class="wel_cqw"><!--wel_cqw begin-->
									<ul>
										<c:forEach items="${postView }" var="view">
											<li>
												<div class="photo"><a href="/home/${view.profileCache.uid}"><img src="${jzr:userLogo(view.profileCache.uid,view.profileCache.logoPic,80)}" width="60" height="60"/></a></div>
												<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>:</font><a href="/post/${view.post.id}"><c:out value="${jzu:truncate(view.post.content,90,'...')}"></c:out></a></p>
												<div class="xy_ly"><!--xy_ly begin-->
												<div class="message_s2"><a href="/post/${view.post.id}">留言<c:if test="${view.post.commentCnt > 0}">(${view.post.commentCnt})</c:if></a></div>
												<div class="like"><a href="/post/${view.post.id}" class="xy">好主意</a><div class="xy_num"><p class="l"></p><a href="/post/${view.post.id}">${view.post.responseCnt}</a><p class="r"></p></div></div>
												</div><!--xy_ly end-->
											</li>
										</c:forEach>
									</ul>
								</div><!--wel_cqw end-->
							</div><!--wel_m end-->
							<div class="wel_b"></div>
						</div><!--wel_box end-->
				</div><!--wel end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/welcome.js')}"></script>
			<c:set var="footType" value="welcome" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
