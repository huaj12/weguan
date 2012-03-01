<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="fix_top">
	<div class="beta"></div>
	<div class="top"><!--top begin-->
		<h1><a href="http://www.51juzhai.com"></a></h1>
		<div class="menu"><!--menu begin-->
			<a href="/home" title="首页" <c:if test="${pageType=='home'}">class="selected"</c:if>>首页</a>
			<a href="/showusers" title="找伴儿" <c:if test="${pageType=='zbe'}">class="selected"</c:if>>找伴儿</a>
			<a href="/showideas" title="出去玩" <c:if test="${pageType=='cqw'}">class="selected"</c:if>>出去玩</a>
		</div><!--menu end-->
		<c:if test="${context.uid>0}">
			<div class="acc"><!--acc begin-->
				<p class=""><a href="javascript:void(0);">帐号</a></p>
				<div class="acc_list" style="display:none;"><!--acc_list begin-->
					<a href="/profile/index">我的资料</a>
					<a href="/profile/index/face">个人形象</a>
					<!-- <a href="/profile/email">订阅设置</a> -->
					<a href="/logout">退出</a>
				</div><!--acc_list end-->
			</div><!--acc end-->
			<div class="my_message" ><!--my_message begin-->
				<a href="/home/dialog/1"><p>消息</p><span style="display: none;" id="notice5">1</span></a>
			</div><!--my_message end-->
		</c:if>
		<c:if test="${context.uid<=0}">
			<div class="welcome_login"><p>登录:</p>
				<a href="javascript:void(0);" class="wb" title="使用微博账号登录" go-uri="/web/login/6"></a>
				<a href="javascript:void(0);" class="db"  title="使用豆瓣账号登录" go-uri="/web/login/7"></a>
				<a href="javascript:void(0);" class="qq"  title="使用QQ账号登录" go-uri="/web/login/8"></a>
			</div>
		</c:if>
		<div class="user_area"><!--user_area begin-->
			<c:if test="${context.uid > 0}">
				<div class="login"><!--login begin-->
					<div class="user_box"><!--user_box begin-->
						<p>
							<c:choose>
								<c:when test="${context.tpName == 'weibo'}"><img src="${jzr:static('/images/web2/sina_icon.png')}" /></c:when>
								<c:when test="${context.tpName == 'douban'}"><img src="${jzr:static('/images/web2/db16.png')}" /></c:when>
								<c:when test="${context.tpName == 'qq'}"><img src="${jzr:static('/images/web2/qq16.png')}" /></c:when>
								<c:otherwise><img src="${jzr:userLogo(loginUser.uid,loginUser.logoPic,80)}" height="20" width="20" /></c:otherwise>
							</c:choose>
						</p>
						<a href="/home/${loginUser.uid}" class="user <c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><c:out value="${loginUser.nickname}" /></a>
					</div><!--user_box end-->
				</div><!--login end-->
			</c:if>
		</div><!--user_area end-->
		<%-- <form id="searchActsForm" action="/searchActs" method="get">
			<div class="search"><!--search begin-->
				<div class="s_l"></div>
				<div class="s_m"><!--s_m begin-->
					<input name="searchWords" type="text" value="${searchWords}" init-msg="输入拒宅项目,如:打台球"/>
				</div><!--s_m end-->
				<div class="s_r"><a href="javascript:void(0);"></a></div>
			</div><!--search end-->
		</form> --%>
		<div class="search" >
			<iframe width="150" height="24" frameborder="0" allowtransparency="true" marginwidth="0" marginheight="0" scrolling="no" border="0" src="http://widget.weibo.com/relationship/followbutton.php?language=zh_cn&width=136&height=24&uid=2294103501&style=2&dpc=1"></iframe>
		</div>
	</div><!--top end-->
</div>
<jsp:include page="/WEB-INF/jsp/web/common/back_top.jsp" />