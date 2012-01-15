<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>拒宅项目管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<script>
	function queryAct() {
		var bDate = $("#bDate").val();
		var eDate = $("#eDate").val();
		var name = $("#name").val();
		$.get('/cms/queryAct', {
			name : name,
			bDate : bDate,
			eDate : eDate,
			random : Math.random()
		}, function(result) {
			$("#act_list").html(result);
		});
	}
	function actManagerPage(pageId, bDate, eDate, name) {
		$.get('/cms/queryAct', {
			name : name,
			bDate : bDate,
			eDate : eDate,
			pageId : pageId,
			random : Math.random()
		}, function(result) {
			$("#act_list").html(result);
		});
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>拒宅项目管理</h2>
	<table>
		<tr>
			<td>添加时间:</td>
			<td><input id="bDate" type="text"  onClick="WdatePicker()"/>&nbsp;到&nbsp;<input
				id="eDate" type="text"  onClick="WdatePicker()" />
			</td>
		</tr>
		<!-- >tr>
			<td>分类:</td>
			<td><select id="catId">
					<c:forEach var="cat" items="${categoryList}">
						<option value="${cat.id }">${cat.name}</option>
					</c:forEach>
			</select>
			</td>
		</tr -->
		<tr>
			<td>简称:</td>
			<td><input type="text" id="name" /> <input type="button"
				value="查找" onclick="queryAct()" />
			</td>
		</tr>
	</table>
	<span id="act_list"> </span>
</body>
</html>