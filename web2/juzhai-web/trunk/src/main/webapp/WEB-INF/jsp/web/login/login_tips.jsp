<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<c:if test="${context.uid<=0}">
	<div class="yk_login" style="display: none;bottom: -100px;">
		<div class="pos_re"><!---->
			<div class="yk_area">
			<h2>拒宅网，助你找伴出去玩</h2>
				<span>
					<a href="javascript:void(0);" class="login-btn" title="使用微博账号登录" go-uri="/web/login/6"><img src="${jzr:static('/images/web2/login_btn.png')}" /></a>
					<a href="javascript:void(0);" class="login-btn" title="使用豆瓣账号登录" go-uri="/web/login/7"><img src="${jzr:static('/images/web2/db_login_btn.png')}" /></a>
					<c:choose>
						<c:when test="${not empty isQplus&&isQplus}">
							<a href="javascript:void(0);" title="使用QQ账号登录" go-uri="/qplus/loginDialog/9" class="login-btn"><img src="${jzr:static('/images/web2/qq_login_btn.png')}" /></a>	
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0);" title="使用QQ账号登录" go-uri="/web/login/8" class="login-btn"><img src="${jzr:static('/images/web2/qq_login_btn.png')}" /></a>
						</c:otherwise>
					</c:choose>
				</span>
			<p><a href="javascript:void(0);"></a></p>
			<em><a href="/passport/register">注册账号</a></em>
			</div>
			<div class="black"></div>
		</div><!---->
	</div>
</c:if>