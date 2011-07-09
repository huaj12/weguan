<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="${staticService}/style/public.css" rel="stylesheet" type="text/css" />
<link href="${staticService}/style/homepage.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<c:if test="${uid>0}">
	<meta http-equiv="Refresh" content="0;URL=/passport" />
</c:if>
<title>用户注册</title>
</head>
<body>
<div class="Bg_head"></div>
<jsp:include page="../IndexHeader.jsp" />
<form action="${httpService}/register" method="post">
	<table width="600" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td><img src="${staticService}/images/homepage/Bg_head.gif" width="600"
				height="58" /></td>
		</tr>
		<tr>
			<td height="100%" align="center" class="Bg_body">
			<table width="80%" border="0" cellpadding="0" cellspacing="8">
				<tr>
					<td align="left" valign="top"><label><img
						src="${staticService}/images/regist.png" width="72" height="33" /> <span
						class="Bottom_text">或</span> <a class="link" href="${loginLink}">登录</a></label></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="left"><span class="text">用户名</span><br />
					<input type="text" name="loginName" class="input_name" size="15" maxlength="16" value="${registerForm.loginName}"/>
					<span class="weguan-text">.weguan.com</span></td>
				</tr>
				<c:if test="${errorCode=='20002'}">
					<tr>
						<td align="left">${error}</td>
					</tr>
				</c:if>
				<tr>
					<td align="left"><span class="text">密码</span><br />
					<input type="password" class="input" size="35" name="password"
						maxlength="18" /></td>
				</tr>
				<c:if test="${errorCode=='20004'}">
					<tr>
						<td align="left">${error}</td>
					</tr>
				</c:if>
				<tr>
					<td align="left"><span class="text">邮箱</span><br />
					<input type="text" name="emailAddress" class="input" size="35" value="${registerForm.emailAddress}" /></td>
				</tr>
				<c:if test="${errorCode=='20003'||errorCode=='20001'}">
					<tr>
						<td align="left">${error}</td>
					</tr>
				</c:if>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center">
					<button class="register_button" type="submit"></button>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center" class="Bg_body"><img
				src="${staticService}/images/homepage/Bg_bottom.gif" width="600" height="58" /></td>
		</tr>
	</table>
</form>
<jsp:include page="../Footer.jsp" flush="true"></jsp:include>
<jsp:include page="../GoogleAnalytics.jsp" flush="true"></jsp:include>
</body>
</html>