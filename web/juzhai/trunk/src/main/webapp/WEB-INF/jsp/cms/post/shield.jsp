<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>拒宅内容管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function unshield(id){
	if(confirm("是否取消屏蔽")){
		jQuery.ajax({
			url : "/cms/post/unshield",
			type : "get",
			data : {
				"postId" : id
			},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					location.reload();
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
	<h2>屏蔽内容</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">用户头像</td>
			<td width="100">我想去</td>
			<td width="100">发起人</td>
			<td width="100">地点</td>
			<td width="100">图片</td>
			<td width="100">拒宅时间</td>
			<td width="100">发布时间</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach var="view" items="${postView}" >
			<tr>
				<td><img src="${view.userLogo}" width="80" height="80"/></td>
				<td><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>:${view.post.content}</td>
				<td>${view.username}</td>
				<td>${view.post.place}</td>
				<td>${jzr:postPic(view.post.id, view.post.ideaId, view.post.pic, 200)}</td>
				<td><fmt:formatDate value="${view.post.dateTime}"
						pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${view.post.createTime}"
						pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td>
				<a href="javascript:;" onclick="unshield('${view.post.id}')">取消屏蔽</a></br>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/post/shield?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
	</table>
	<div id="synonym"></div>
</body>
</html>