<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>已发布优惠信息管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
function removeAd(id){
	jQuery.ajax({
		url : "/cms/remove/ad",
		type : "post",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				location.reload();
			} else {
				alert("操作失败请刷新页面后重试");
			}
		},
		statusCode : {
			401 : function() {
				alert("未登录");
			}
		}
	});
}
function showAdList(obj){
	location.href="/cms/list/ad?cityId="+obj.value;
}
function delAllad(){
	jQuery.ajax({
		url : "/cms/remove/all/ad",
		type : "get",
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				location.reload();
			} else {
				alert("操作失败请刷新页面后重试");
			}
		},
		statusCode : {
			401 : function() {
				alert("未登录");
			}
		}
	});
}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>已发布优惠信息列表</h2>
	城市：<select name="cityId" onchange="showAdList(this)">
	<option <c:if test="${empty cityId||cityId==0 }">selected="selected"</c:if> value="0">所有城市</option>
	<c:forEach var="city" items="${citys}">
		<option value="${city.id }" <c:if test="${city.id==cityId }">selected="selected"</c:if> >${city.name }</option>
	</c:forEach>
	</select>
	<input type="button"  onclick="delAllad();" value="删除所有过期的优惠信息" />
	<table border="0" cellspacing="4">
	<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/list/ad?pageId=${pageId}&cityId=${cityId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
		<tr style="background-color: #CCCCCC;">
			<td width="100">操作</td>
			<td width="220">图片</td>
			<td width="360">名称</td>
			<td width="50">价格</td>
			<td width="50">原价</td>
			<td width="50">折扣</td>
			<td width="100">来源</td>
			<td width="50">城市</td>
			<td width="100">地段</td>
			<td width="100">详细地址</td>
			<td width="100">有效期</td>
		</tr>
		<c:forEach var="ad" items="${ads}">
		<tr>
			<td>
				<input type="button" value="删除" onclick="removeAd('${ad.id}')"/>
			</td>
			<td><a href="${ad.link}" target="_blank"><img src="${ad.picUrl}" width="210" height="120"/></a></td>
			<td><a href="${ad.link}" target="_blank">${ad.name}</a></td>
			<td>${ad.price}</td>
			<td>${ad.primePrice}</td>
			<td>${ad.discount}</td>
			<td>${ad.source}</td>
			<td>${jzd:cityName(ad.city)}</td>
			<td>${ad.district}</td>
			<td>${ad.address}</td>
			<td>
			<fmt:formatDate value="${ad.startTime}"
						pattern="yyyy-MM-dd" />
			至<fmt:formatDate value="${ad.endTime}"
						pattern="yyyy-MM-dd" /></td>
		</tr>	
		</c:forEach>
		<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/list/ad?pageId=${pageId}&cityId=${cityId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
	</table>
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
</body>
</html>