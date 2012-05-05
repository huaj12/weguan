<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>审核推荐项目列表</title>
<script type="text/javascript"
	src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript">
	function removeRawAct() {
		var id=$("#r_id").val();
		var reason_msg=$("#reason_msg").val();
		var receive=$("#r_receive").val();
		jQuery.ajax({
			url : "/cms/ajax/delRawAct",
			type : "post",
			data : {
				"id" : id,
				"reasonMsg":reason_msg,
				"receive":receive,
			},
			dataType : "json",
			success : function(result) {
				if (result && result.success) {
					closeAllDiv();
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
	function show_remove_message(id,receive){
		$("#r_id").val(id);
		$("#r_receive").val(receive);
		$.dialog({
		    lock: true,
		    content: $("#reason")[0],
		    top:"50%"
		});
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>审核推荐项目列表</h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">操作</td>
				<td width="200">项目名称</td>
			<td width="100">提交人</td>
			<td width="100">提交时间</td>
		</tr>
		<c:forEach var="raw" items="${rawActs}" varStatus="status">
			<tr>
				<td id="op_${raw.rawAct.id}"><a href="javascript:;"
					onclick="javascript:show_remove_message('${raw.rawAct.id}','${raw.rawAct.createUid}');">拒绝</a> </td>
				<td><c:out value="${raw.rawAct.name}" /><a
					href="/cms/showManagerRawAct?id=${raw.rawAct.id}">详细</a>
				</td>
				<td>${raw.username}</td>
				<td width="100"><fmt:formatDate value="${raw.rawAct.createTime}"
						pattern="yyyy-MM-dd" /></td>

			</tr>
		</c:forEach>
		<tr>
			<td colspan="4"><c:forEach var="pageId"
					items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/showRawActs?pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
	</table>
	<div id="reason" style="display: none">
		原因:
		<textarea rows="3" id="reason_msg" cols="20"></textarea>
		<input type="hidden" id="r_id"/>
		<input type="hidden" id="r_receive"/>
		<input type="button" onclick="removeRawAct();" value="确定" />
		<input type="button" onclick="closeAllDiv();" value="取消" />
	</div>
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
</body>
</html>