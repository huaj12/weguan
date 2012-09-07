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
<title>搜索热词</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
	function addSearchHot(){
		var city=$("#city").val();
		var name=$("#hotName").val();
		jQuery.ajax({
			url: "/cms/add/searchHot",
			type: "post",
			data: {"city":city,"name":name},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					location.reload();
				}else{
					alert(result.errorInfo);
				}
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			}
		});
	}
	function del(id){
		if(confirm("是否删除该条热门搜索")){
				jQuery.ajax({
					url : "/cms/del/searchHot",
					type : "post",
					data : {
						"id" : id
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
	
	function updateWordHot(){
		if(confirm("是否更新热词热度?请在人少的时候操作。谨慎操作！")){
				jQuery.ajax({
					url : "/cms/update/searchWordHot",
					type : "post",
					dataType : "json",
					success : function(result) {
						if (result.success!=null&&result.success) {
							alert("更新成功");
							location.reload();
						} else {
							alert("更新失败");
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
<style type="text/css">
</style>
</head>
<body>
	<h2>搜索热词<a href="javascript:updateWordHot();return false;">更新热词热度</a></h2>
	<select id="city" onchange="javascript:location.href='/cms/show/searchHot?city='+this.value+''">
				<option value="0">全国</option>
				<c:forEach var="specialCity" items="${jzd:specialCityList()}">
					<option value="${specialCity.id}" <c:if test="${city==specialCity.id}">selected="selected"</c:if>>${specialCity.name}</option>
		</c:forEach>
	</select>
	<input type="text" id="hotName"/><input type="button" onclick="addSearchHot();" value="添加"/>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">name</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach items="${hots }" var="hot">
		<tr>
			<td>${hot.name}(${hot.hot })</td>
			<td><a href="javascript:void(0);" onclick="del('${hot.id}')">删除</a></td>
		</tr>
		</c:forEach>
		<td colspan="2">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/searchHot?pageId=${pageId}&city=${city}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</table>
</body>
</html>