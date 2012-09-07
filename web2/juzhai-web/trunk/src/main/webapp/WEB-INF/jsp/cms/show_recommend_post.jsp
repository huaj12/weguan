<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>更新首页推荐内容</title>
</head>
<body>
	<h2>更新欢迎页拒宅推荐内容 <a href="/cms//update/recommend/post">更新</a></h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">用户头像</td>
			<td width="300">我想去</td>
			<td width="100">发起人</td>
			<td width="100">地点</td>
			<td width="100">图片</td>
			<td width="100">拒宅时间</td>
			<td width="100">发布时间</td>
		</tr>
		<c:forEach var="view" items="${postView}" >
			<tr>
			<td><a href="/home/${view.profileCache.uid}" target="_blank"><img src="${jzr:userLogo(view.profileCache.uid,view.profileCache.logoPic,120)}" width="80" height="80"/></a></td>
				<td><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>:<c:out value="${view.post.content}"></c:out></td>
				<td><c:out value="${view.profileCache.nickname}" /></td>
				<td><c:out value="${view.post.place}"></c:out></td>
				<td><img src="${jzr:postPic(view.post.id, view.post.ideaId, view.post.pic, 200)}"  /> </td>
				<td><fmt:formatDate value="${view.post.dateTime}"
						pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${view.post.createTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>