<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="reg_right"><!--reg_right begin-->
	<div class="login_area"><!--login_area begin-->
		<c:choose>
			<c:when test="${isLogin}"><p>还没有账号？请先<a href="/passport/register">注册</a></p></c:when>
			<c:otherwise><p>已有账号？请直接<a href="/login">登录</a></p></c:otherwise>
		</c:choose>
		<span>懒人可用下面合作网站的账号直接登录</span>
		<div class="btns">
			<a href="/web/login/6?turnTo=${loginForm.turnTo}" class="wb"></a>
			<a href="/web/login/7?turnTo=${loginForm.turnTo}" class="db"></a>
			<c:choose>
				<c:when test="${not empty isQplus&&isQplus}">
					<a href="/qplus/loginDialog/9?turnTo=${loginForm.turnTo}" class="qq"></a>	
				</c:when>
				<c:otherwise>
					<a href="/web/login/8?turnTo=${loginForm.turnTo}" class="qq"></a>
				</c:otherwise>
			</c:choose>
		</div>
	</div><!--login_area end-->
</div><!--reg_right end-->