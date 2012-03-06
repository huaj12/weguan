<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<c:set var="arrayvalue" value="未发布,已发布,已过期" />
<c:set var="delim" value=","/> 
<c:set var="array" value="${fn:split(arrayvalue, delim)}"/>
<c:set var="categoryvalue" value="娱乐,美食,生活服务" /> 
<c:set var="categorys" value="${fn:split(categoryvalue, delim)}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>优惠信息管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
function seachRawAd(){
	  document.rawAdForm.submit();
}
function removeAd(rawAdId){
	jQuery.ajax({
		url : "/cms/remove/raw/ad",
		type : "post",
		data : {
			"rawAdId" : rawAdId
		},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				location.reload();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				alert("未登录");
			}
		}
	});
}
function addAd(rawAdId){
	jQuery.ajax({
		url : "/cms/publish/ad",
		type : "post",
		data : {
			"rawAdId":rawAdId
		},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				location.reload();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				alert("未登录");
			}
		}
	});
}

function delAllRawad(){
	jQuery.ajax({
		url : "/cms/remove/all/raw/ad",
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
	<h2>审核推荐项目列表</h2>
	<c:if test="${!empty msg}"><h3>成功导入${msg }个优惠信息</h3></c:if>
	<form action="/cms/show/ad/manager" name="rawAdForm" method="post">
	城市：<select name="cityId" onchange="seachRawAd();">
		<option value="0"  <c:if test="${cityId==0}"> selected="selected"</c:if> >全国</option>
		<c:forEach var="specialCity" items="${jzd:specialCityList()}">
			<option value="${specialCity.id}" <c:if test="${cityId==specialCity.id}">selected="selected"</c:if>>${specialCity.name}</option>
		</c:forEach>
	</select>
	<select name="source" onchange="seachRawAd();">
	<option <c:if test="${empty source }">selected="selected"</c:if> value="">所有来源</option>
		<c:forEach var="adSource"  items="${adSources}">
			<option value="${adSource.name }" <c:if test="${adSource.name ==source}">selected="selected"</c:if> >${adSource.name }</option>
		</c:forEach>
	</select>
	<select name="status" onchange="seachRawAd();">
		<option <c:if test="${empty status }">selected="selected"</c:if> value="">所有状态</option>
		<c:forEach var="value" items="${array}" varStatus="v">
			<option value="${v.index }" <c:if test="${v.index ==status}">selected="selected"</c:if> >${value}</option>
		</c:forEach>
	</select>
	<select  name="category" onchange="seachRawAd();">
			<option <c:if test="${empty category }">selected="selected"</c:if> value="">所有类别</option>
			<c:forEach items="${categorys }" var="cat">
			<option value="${cat }" <c:if test="${cat==category}">selected="selected"</c:if> >${cat}</option>
			</c:forEach>
	</select>
	</form>
	<input type="button"  onclick="delAllRawad();" value="删除所有过期的优惠信息" />
	<table border="0" cellspacing="4">
		<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/ad/manager?pageId=${pageId}&source=${source}&cityId=${cityId}&status=${status}">${pageId}</a>
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
				<input type="button" value="删除" onclick="removeAd('${ad.id}','false')"/>
				<c:choose>
					<c:when test="${ad.status==0}">
						<input type="button" value="发布" onclick="addAd('${ad.id}')"/>
					</c:when>
					<c:when test="${ad.status==1}">
						已发布
					</c:when>
				</c:choose>
			</td>
			<td><a href="${ad.targetUrl}" target="_blank"><img src="${ad.img}" width="210" height="120"/></a></td>
			<td><a href="${ad.targetUrl}" target="_blank">${ad.title}</a></td>
			<td>${ad.price}</td>
			<td>${ad.original}</td>
			<td>${ad.discount}</td>
			<td>${ad.source}</td>
			<td>${cityMap[ad.city].name}</td>
			<td>${ad.circle}</td>
			<td>${ad.address}</td>
			<td>
			<fmt:formatDate value="${ad.startDate}"
						pattern="yyyy-MM-dd" />
			至<fmt:formatDate value="${ad.endDate}"
						pattern="yyyy-MM-dd" /></td>
		</tr>	
		</c:forEach>
		<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/ad/manager?pageId=${pageId}&source=${source}&cityId=${cityId}&status=${status}">${pageId}</a>
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