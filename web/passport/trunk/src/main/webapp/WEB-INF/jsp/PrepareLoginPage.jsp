<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>
<link href="/passport/style/public.css" rel="stylesheet" type="text/css" />
<link href="/passport/style/head.css" rel="stylesheet" type="text/css" />
<link href="/passport/style/homepage.css" rel="stylesheet" type="text/css" />
<jsp:include page="Script.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="/passport/script/login.js"></script>
</head>
<body>
<div class="Bg_head"></div>
<jsp:include page="IndexHeader.jsp" />
<table width="600" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td><img src="/passport/images/homepage/Bg_head.gif" width="600"
			height="58" /></td>
	</tr>
	<tr>
		<td height="100%" align="center" class="Bg_body">
		<form method="post" id="loginForm"
			onsubmit="javascript:return doLogin(this);"><input
			type="hidden" name="ru" value="${returnLink}" />
		<table width="80%" border="0" cellpadding="0" cellspacing="8">
			<tr>
				<td align="left" valign="top"><label><img
					src="/passport/images/login.png" width="71" height="32" /> <span
					class="Bottom_text">或</span> <a href="${registerLink}">注册</a></label></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="left"><span class="text">用户名</span><br />
				<input name="loginName" type="text" id="inputLoginName"
					class="reg_input_name" /><span class="weguan-text">.weguan.com</span></td>
			</tr>
			<tr>
				<td align="left"><span class="text">密码</span><br />
				<input name="loginPassword" type="password" class="reg_input_name" /><input
					name="rememberMe" type="checkbox" id="RM" value="true"/><label for="RM">&nbsp;记住我的密码</label>
				</td>
			</tr>
			<tr>
				<td align="left"><br />
				<button class="login_button" type="submit"></button>
				<a href="${getPasswordLink}" class="link">忘记密码？</a>
				<div id="message"></div>
				</td>
			</tr>


		</table>
		</form>
		</td>
	</tr>
	<tr>
		<td align="center" class="Bg_body"><img
			src="/passport/images/homepage/Bg_bottom.gif" width="600" height="58" /></td>
	</tr>
</table>
<jsp:include page="Footer.jsp" flush="true"></jsp:include>
<jsp:include page="GoogleAnalytics.jsp" flush="true"></jsp:include>
</body>
</html>
