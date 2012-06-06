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
<script type="text/javascript"
	src="${jz:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<script type="text/javascript">
	function showSynonym(actId){
		jQuery.ajax({
			url: "/cms/showSynonym",
			type: "get",
			data: {"actId": actId},
			dataType: "html",
			success: function(result){
				$("#synonym").html(result);
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			}
		});
	}
	
	function useMaybeSynonym(actName){
		$("#addInput").val(actName);
	}
	
	function removeSynonym(removeActId, actId){
		jQuery.ajax({
			url: "/cms/removeSynonym",
			type: "post",
			data: {"actId":actId, "removeActId":removeActId},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					showSynonym(actId);
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
	
	function addSynonym(actId){
		var actName = $("#addInput").val();
		if(!actName||actName==""){
			alert("请输入内容");
			return false;
		}
		jQuery.ajax({
			url: "/cms/addSynonym",
			type: "post",
			data: {"actId":actId, "synonymActName":actName},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					showSynonym(actId);
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
	
	function removeShield(actId){
		jQuery.ajax({
			url: "/cms/removeShield",
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
	
	function addShield(actId){
		jQuery.ajax({
			url: "/cms/addShield",
			type: "post",
			data: {"actId":actId},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					$("#op_"+actId).text("已添加");
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
	<h2>拒宅兴趣查询</h2>
	<form action="/cms/searchActs" method="get">
		<table>
			<tr>
				<td><input type="text" name="startDate" onClick="WdatePicker()" value="${searchActForm.startDate}" />&nbsp;到&nbsp;<input type="text" name="endDate" onClick="WdatePicker()" value="${searchActForm.endDate}" /></td>
				<td>热度<input type="radio" name="order" value="0" <c:if test="${searchActForm.order == 0}">checked="checked"</c:if> />&nbsp;&nbsp;时间<input type="radio" name="order" value="1" <c:if test="${searchActForm.order == 1}">checked="checked"</c:if> /></td>
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
			<td width="100">同义词</td>
			<td width="100">想用人次</td>
			<td width="100">添加时间</td>
			<td width="100">状态</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach var="actView" items="${cmsActViewList}" varStatus="status">
			<tr>
				<td>${actView.act.id}</td>
				<td><c:out value="${actView.act.name}" /></td>
				<td>${actView.synonymActSize}个&nbsp;<a href="javascript:;" onclick="javascript:showSynonym('${actView.act.id}');">查看</a></td>
				<td>${actView.act.popularity}</td>
				<td><fmt:formatDate value="${actView.act.createTime}" pattern="yyyy.MM.dd HH:mm" /></td>
				<td>${actView.act.active}</td>
				<td id="op_${actView.act.id}">
					<c:choose>
						<c:when test="${actView.shield}"><a href="javascript:;" onclick="javascript:removeShield('${actView.act.id}');">删除屏蔽</a></c:when>
						<c:otherwise><a href="javascript:;" onclick="javascript:addShield('${actView.act.id}');">添加屏蔽</a></c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/searchActs?startDate=${searchActForm.startDate}&endDate=${searchActForm.endDate}&order=${searchActForm.order}&pageId=${pageId}">${pageId}</a>
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