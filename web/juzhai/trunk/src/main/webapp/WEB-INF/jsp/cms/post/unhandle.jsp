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

function handle(){
	var ids="";
	$('input[name=postIds]').each(function(){
		 ids=ids+this.value+",";
	});
	jQuery.ajax({
		url : "/cms/post/handle",
		type : "get",
		data : {
			"postIds" : ids
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
			} else {
				alert("标记错误请重试");
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
	<h2>未处理内容</h2><a href="javascript:;" onclick="handle();">将本页标记为已处理</a>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
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
				<td><c:param name="purposeType" value="${view.post.purposeType}"/>:${view.post.content}</td>
				<td>${view.username}</td>
				<td>${view.post.place}</td>
				<td>${jzr:postPic(view.post.id,view.post.pic)}</td>
				<td><fmt:formatDate value="${view.post.dateTime}"
						pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td><fmt:formatDate value="${view.post.createTime}"
						pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td>
				<a href="javascript:;" onclick="del('${view.post.id}')">删除</a></br>
				<a href="javascript:;" onclick="shield('${view.post.id}')">屏蔽</a></br>
				<a href="/cms/show/idea/add?postId=${view.post.id}&content=${view.purposeName}:${view.post.content}&date=<fmt:formatDate value="${view.post.dateTime}" pattern="yyyy-MM-dd" />&pic=${view.post.pic}&place=${view.post.place}&createUid=${view.post.createUid}" >好注意</a>
				<input type="hidden" value="${view.post.id}" name="postIds"/>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/post/unhandle?pageId=${pageId}">${pageId}</a>
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