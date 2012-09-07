<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指向词管理</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
	<jsp:include page="/WEB-INF/jsp/cms/common/cmsAutoMatch.jsp" />
<script>
	function deleteSyn(id) {
		if (id != null) {
			$.get('/cms/deleteSynonymAct', {
				id : id,
				random : Math.random()
			}, function(result) {
				if (result && result.success) {
					location.reload();
				} else {
					alert("删除失败");
				}
			});
		}
	}

	function cmsSynonymAct(id) {
		var actName = $("#actName_" + id).val();
		if (actName == null || actName == "") {
			alert("请输入现存词");
			return;
		}
		$.get('/cms/replaceSynonymAct', {
			actName : actName,
			id : id,
			random : Math.random()
		}, function(result) {
			if (result && result.success) {
				alert("修改指向成功");
				location.reload();
			} else {
				if (result.errorCode == '-1') {
					alert("现存词不存在");
				} else {
					alert("未知错误刷新页面后重试");
				}
			}
		});

	}
</script>
</head>
<body>
	<h2>指向词管理</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">name</td>
			<td width="100">指向词</td>
			<td width="100">更新时间</td>
			<td width="100">操作</td>
			<td width="100">重新指向</td>
		</tr>
		<c:forEach var="view" items="${synonymActViews}">
			<tr>
				<td>${view.synonymAct.name }</td>
				<td>${view.actName}</td>
				<td><fmt:formatDate value="${view.synonymAct.lastModifyTime}"
						pattern="yyyy.MM.dd HH:mm" />
				</td>
				<td><a onclick="deleteSyn('${view.synonymAct.id}')" href="#">删除</a>
				</td>
				<td>输入现存在词<input id="actName_${view.synonymAct.id}" /><input
					type="button" onclick="cmsSynonymAct('${view.synonymAct.id}')"
					value="确定" />
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/showSynonymActs?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
	</table>
	<c:forEach var="view" items="${synonymActViews}">
	<script type="text/javascript">
	$(document).ready(function() {
		var addActInput = new AddActInput($("#actName_${view.synonymAct.id}"));
		addActInput.bindAutocomplete();
	});
	</script>
	</c:forEach>
</body>
</html>