<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>拒宅统计</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
</head>
<body>
	<h2>拒宅统计</h2>
	<form action="/cms/showStats" method="get">
	<table>
		<tr>
			<td><input type="text" name="beginDate" readonly="readonly" onclick="WdatePicker();" value="${beginDate}" /></td>
			<td><input type="text" name="endDate" readonly="readonly" onclick="WdatePicker();" value="${endDate}" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="查询" /></td>
		</tr>
	</table>
	</form>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<c:forEach var="object" items="${CounterStats}">
    		<td>${object.key}:<c:out value="${object.value}"></c:out></td>
    	</c:forEach>
		</tr>
	</table>
</body>
</html>