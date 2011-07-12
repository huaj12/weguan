<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="UserPanel"><a href="${httpService}/home"><img
	id="avatar" class="avatar" src="http://www.discaz.net/bbs/attachments/forumid_24/0912110847be2ab1da321ba6d2.jpg"
	alt="" width="50" height="50"/></a>
<div class="user_titles">
<h1><c:if test="${blog==null}">${userView.nickName}的微观！</c:if>${blog.title}</h1>
<h4>http://${userView.nickName}.weguan.com</h4>
<h2>${blog.about}</h2>
</div>
</div>