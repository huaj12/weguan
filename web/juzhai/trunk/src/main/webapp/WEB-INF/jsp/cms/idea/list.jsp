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
<title>已发布的好主意</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function del(id){
	if(confirm("是否删除该条好主意")){
			jQuery.ajax({
				url : "/cms/idea/del",
				type : "post",
				data : {
					"ideaId" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						location.reload();
					} else {
						alert("删除失败");
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
function operateIdeaRandom(id,random){
	jQuery.ajax({
		url : "/cms/operate/idea/random",
		type : "post",
		data : {
			"ideaId" : id,
			"random":random
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
			} else {
				alert("操作失败刷新后重试");
			}
		},
		statusCode : {
			401 : function() {
				alert("请先登陆");
			}
		}
	});
}

function operateIdeaWindow(id,window){
	jQuery.ajax({
		url : "/cms/operate/idea/window",
		type : "post",
		data : {
			"ideaId" : id,
			"window":window
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
			} else {
				alert("操作失败刷新后重试");
			}
		},
		statusCode : {
			401 : function() {
				alert("请先登陆");
			}
		}
	});
}
function updateIdeaWindow(){
	jQuery.ajax({
		url : "/cms/operate/idea/update/window",
		type : "post",
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				alert('刷新成功');
			} else {
				alert("操作失败刷新后重试");
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
	<h2>已发布的好主意----<a href="/cms/show/idea/add">添加好主意</a><input type="button" onclick="updateIdeaWindow();" value="更新欢迎页内容"/></h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="40">是否推荐到橱窗</td>
			<td width="200">我想去</td>
			<td width="100">发起人</td>
			<td width="100">地点</td>
			<td width="40">性别</td>
			<td width="100">类别</td>
			<td width="100">图片</td>
			<td width="100">城市</td>
			<td width="60">是否推荐到随机库</td>
			<td width="100">拒宅时间</td>
			<td width="100">发布时间</td>
			<td width="150">操作</td>
		</tr>
		<c:forEach var="view" items="${ideaViews}" >
			<tr>
				<td>
					<c:choose>
						<c:when test="${view.idea.window}">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<td><c:out value="${view.idea.content}"></c:out></td>
				<td><c:out value="${view.userName}"></c:out></td>
				<td>${view.idea.place}</td>
					<td>
					<c:choose>
						<c:when test="${view.idea.gender==0}">
							女
						</c:when>
						<c:when test="${view.idea.gender==1}">
							男
						</c:when>
						<c:otherwise>
							不限
						</c:otherwise>
					</c:choose> </td>
				<td>
				<c:choose>
					<c:when test="${empty view.categoryName}">
						不限
					</c:when>
					<c:otherwise>
					${view.categoryName}
					</c:otherwise>
				</c:choose>
				</td>
				<td><img width="70" height="70" src="${jzr:ideaPic(view.idea.id,view.idea.pic, 200)}"/></td>
				<td>
				<c:choose>
					<c:when test="${empty jzd:cityName(view.idea.city)}">全国</c:when>
					<c:otherwise>${jzd:cityName(view.idea.city)}</c:otherwise>
				</c:choose></td>
				<td>
					<c:choose>
						<c:when test="${view.idea.random}">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatDate value="${view.idea.date}"
						pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${view.idea.createTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>
				<a href="javascript:;" onclick="del('${view.idea.id}')">取消好主意</a><br />
				<a href="/cms/show/idea/update?id=${view.idea.id}">修改好主意</a><br />
				<c:choose>
						<c:when test="${view.idea.random}"><a href="javascript:void(0);" onclick="operateIdeaRandom('${view.idea.id}',0)">移出随即库</a></c:when>
						<c:otherwise><a href="javascript:void(0);" onclick="operateIdeaRandom('${view.idea.id}',1)">进入随即库</a></c:otherwise>
				</c:choose>
				<br />
				<c:choose>
						<c:when test="${view.idea.window}"><a href="javascript:void(0);" onclick="operateIdeaWindow('${view.idea.id}',0)">移出橱窗</a></c:when>
						<c:otherwise><a href="javascript:void(0);" onclick="operateIdeaWindow('${view.idea.id}',1)">进入橱窗</a></c:otherwise>
				</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/idea?pageId=${pageId}">${pageId}</a>
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