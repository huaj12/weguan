<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>头像管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	function removeHighQuality(uid, obj){
		var nickname = $(obj).attr("nickname");
		if(confirm("确认要移除 " + nickname + " 的优质资格吗？")){
			jQuery.ajax({
				url : "/cms/profile/removeHighQuality",
				type : "post",
				data : {"uid" : uid},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						$("#remove-high-quality-" + uid).removeAttr("onclick").text("已移除");
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
	}
</script>
</head>
<body>
	<h2>
		优质用户列表
	</h2>
	<table border="0" cellspacing="4">
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/profile/listHighQuality?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
		<tr style="background-color: #CCCCCC;">
			<td width="50">id</td>
			<td width="150">用户昵称</td>
			<td width="50">性别</td>
			<td width="100">城市</td>
			<td width="180">用户头像</td>
			<td width="200">处理</td>
		</tr>
		<c:forEach var="profile" items="${profileList}" >
			<tr>
				<td>${profile.uid}</td>
				<td><c:out value="${profile.nickname}" /></td>
				<td><c:choose><c:when test="${profile.gender == 1}">男</c:when><c:otherwise>女</c:otherwise></c:choose></td>
				<td><c:out value="${jzd:cityName(profile.city)}" /></td>
				<td><img src="${jzr:userLogo(profile.uid, profile.logoPic, 180)}" width="180" height="180"/></td>
				<td>
					<a href="javascript:void(0);" onclick="removeHighQuality(${profile.uid}, this)" nickname="${jzu:htmlOut(profile.nickname)}" id="remove-high-quality-${profile.uid}">移除优质用户</a><br />
					<a href="/home/${profile.uid}" target="_blank">发私信</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/profile/listHighQuality?pageId=${pageId}">${pageId}</a>
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