<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>拒宅兴趣</title>
<script type="text/javascript" src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
	function cancelHotAct(actId){
		jQuery.ajax({
			url: "/cms/cancelHotAct",
			type: "post",
			data: {"actId":actId},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					$("#op_"+actId).text("已取消");
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
	function deleteHotAct(actId){
		jQuery.ajax({
			url: "/cms/deleteHotAct",
			type: "post",
			data: {"actId":actId},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					$("#op_"+actId).text("已删除");
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
	function addHotAct(){
		var actName = $("#actName").val();
		if(actName==""){
			alert("请输入要推荐的项目名称");
			return;
		}
		jQuery.ajax({
			url: "/cms/addHotAct",
			type: "post",
			data: {"actName":actName},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					$("#actName").val("");
					alert("添加成功")
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
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>推荐项目列表</h2>
	<table>
		<tr>
			<td><input type="text" name="actName" id="actName" /></td>
			<td><input type="button" value="添加" onclick="addHotAct();"></input></td>
		</tr>
	</table>
	<form action="/cms/showHotAct" method="get">
		<table>
			<tr>
				<td>推荐状态<input type="radio" name="active" value="true" <c:if test="${active}">checked="checked"</c:if> />&nbsp;&nbsp;不推荐状态<input type="radio" name="active" value="false" <c:if test="${!active}">checked="checked"</c:if> /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="查询" /></td>
			</tr>
		</table>
	</form>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">ID</td>
			<td width="100">名称</td>
			<td width="100">推荐状态</td>
			<td width="100">是否过期</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach var="hotAct" items="${hotActList}" varStatus="status">
			<tr>
				<td>${hotAct.id}</td>
				<td><c:out value="${hotAct.name}" /></td>
				<td><c:choose><c:when test="${active}">推荐</c:when><c:otherwise>不推荐</c:otherwise></c:choose></td>
				<td><c:choose><c:when test="${hotAct.endTime!=null&&jz:dateAfter(hotAct.endTime)}"><font color="red">已过期</font></c:when><c:otherwise>未过期</c:otherwise></c:choose></td>
				<td id="op_${hotAct.id}"><c:if test="${active}"><a href="javascript:;" onclick="javascript:cancelHotAct(${hotAct.id});">取消推荐</a>&nbsp;&nbsp;</c:if><a href="javascript:deleteHotAct(${hotAct.id});">删除</a></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/showHotAct?active=${active}&pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
	</table>
</body>
</html>