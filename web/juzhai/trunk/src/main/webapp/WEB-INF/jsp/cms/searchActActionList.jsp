<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找伴儿记录</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
	<jsp:include page="/WEB-INF/jsp/cms/common/cmsAutoMatch.jsp" />
<script>
	function deleteSearchAct(id) {
		if (id != null) {
			$.get('/cms/deleteSearchActAction', {
				id : id,
				random : Math.random()
			}, function(result) {
			});
		}
	}
	
	function  cmsSynonymAct(id,name){
		var actName=$("#actName_"+id).val();
		if(actName==null||actName==""){
			alert("请输入现存词");
			return ;
		}
		$.get('/cms/cmsSynonymAct', {
			actName : actName,
			name:name,
			random : Math.random()
		}, function(result) {
			if(result&&result.success){
			deleteSearchAct(id);
			alert("指向成功");
			location.reload();
			}else{
				if(result.errorCode=='-1'){
					alert("现存词不存在");
				}else{
					alert("未知错误刷新页面后重试");
				}
			}
		});
		
		
	}
</script>
</head>
<body>
	<h2>找伴儿记录</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">新内容</td>
			<td width="100">搜索人</td>
			<td width="180">添加时间</td>
			<td width="100">操作</td>
			<td width="100">指向</td>
		</tr>
		<c:forEach var="list" items="${lists}">
			<tr>
				<td>${list.name }</td>
				<td>${list.userName }</td>
				<td><fmt:formatDate value="${list.createTime}"
						pattern="yyyy.MM.dd HH:mm" /></td>
				<td><a onclick="deleteSearchAct('${list.id}')" href="/cms/showCreateAct?actName=${list.name}"
					target="_blank">添加</a></td>
				<td>输入现存在词<input id="actName_${list.id}"  /><input type="button" onclick="cmsSynonymAct('${list.id}','${list.name }')" value="确定" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/showSearchActAction?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
	</table>
		<c:forEach var="list" items="${lists}">
	<script type="text/javascript">
	$(document).ready(function() {
		var addActInput = new AddActInput($("#actName_${list.id}"));
		addActInput.bindAutocomplete();
	});
	</script>
	</c:forEach>
</body>
</html>