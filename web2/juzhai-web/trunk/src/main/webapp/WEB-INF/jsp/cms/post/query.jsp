<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>拒宅查询</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function del(id){
	if(confirm("是否删除该条拒宅")){
			jQuery.ajax({
				url : "/cms/post/unhandle/del",
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
function shield(id){
	if(confirm("是否屏蔽该条拒宅")){
		jQuery.ajax({
			url : "/cms/post/shield",
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
function handleById(id){
	jQuery.ajax({
		url : "/cms/post/handle",
		type : "post",
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
</script>
</head>
<body>
	<h2>拒宅查询</h2>
	<form action="/cms/post/query" method="get">
	<input type="radio" name="type" value="1" checked="checked" />人 <input type="radio" name="type" value="2" />拒宅<input name="id" type="text" />
	<input type="submit" value="查询"/>
	</form> 
	<c:if test="${not empty views}">
		<table border="0" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">用户头像</td>
				<td width="300">我想去</td>
				<td width="100">发起人</td>
				<td width="100">地点</td>
				<td width="100">图片</td>
				<td width="100">拒宅时间</td>
				<td width="100">发布时间</td>
				<td width="100">操作</td>
			</tr>
			<c:forEach items="${views}" var="view">
				<tr>
				<td><a href="/home/${view.profileCache.uid}" target="_blank"><img src="${jzr:userLogo(view.profileCache.uid,view.profileCache.logoPic,120)}" width="80" height="80"/></a></td>
					<td><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>:<c:out value="${view.post.content}"></c:out></td>
						<td><c:out value="${view.profileCache.nickname}"></c:out></td>
					<td><c:out value="${view.post.place}"></c:out></td>
					<td><img src="${jzr:postPic(view.post.id, view.post.ideaId, view.post.pic, 200)}" /> </td>
					<td><fmt:formatDate value="${view.post.dateTime}"
							pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${view.post.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
					
					<c:if test="${view.post.verifyType==0}"><a href="javascript:;" onclick="handleById('${view.post.id}')">通过</a></br></c:if>
					<c:if test="${view.post.verifyType!=2}"><a href="javascript:;" onclick="shield('${view.post.id}')">屏蔽</a></br></c:if>
					<a href="javascript:;" onclick="del('${view.post.id}')">删除</a></br>
					<c:if test="${view.post.ideaId==0}"><a href="/cms/show/idea/add?postId=${view.post.id}" >好注意</a></c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>