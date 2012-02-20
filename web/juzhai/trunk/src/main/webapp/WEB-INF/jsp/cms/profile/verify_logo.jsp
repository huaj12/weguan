<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>头像管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	function passLogo(uid){
		jQuery.ajax({
			url : "/cms/profile/passLogo",
			type : "post",
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#pass-logo-" + uid).removeAttr("onclick").text("已通过");
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
	}
	function denyLogo(uid){
		jQuery.ajax({
			url : "/cms/profile/denyLogo",
			type : "post",
			data : {"uid" : uid},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#deny-logo-" + uid).removeAttr("onclick").text("已拒绝");
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					alert("请先登陆");
				}
			}
		});
	}
</script>
</head>
<body>
	<h2>
		<c:choose>
			<c:when test="${type == 'listVerifyingLogo'}">待审核</c:when>
			<c:when test="${type == 'listVerifiedLogo'}">已通过</c:when>
			<c:when test="${type == 'listUnVerifiedLogo'}">未通过</c:when>
		</c:choose>头像列表
	</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="200">用户昵称</td>
			<td width="80">用户头像</td>
			<td width="200">处理</td>
		</tr>
		<c:forEach var="profile" items="${profileList}" >
			<tr>
				<td><c:out value="${profile.nickname}" /></td>
				<td><img src="${jzr:userLogo(profile.uid, profile.newLogoPic, 80)}" width="80" height="80"/></td>
				<td>
					<c:if test="${type != 'listVerifiedLogo'}"><a href="javascript:;" onclick="passLogo(${profile.uid})" id="pass-logo-${profile.uid}">通过</a><br /></c:if>
					<c:if test="${type == 'listVerifyingLogo'}"><a href="javascript:;" onclick="denyLogo(${profile.uid})" id="deny-logo-${profile.uid}">拒绝</a></c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/profile/${type}?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
	</table>
</body>
</html>