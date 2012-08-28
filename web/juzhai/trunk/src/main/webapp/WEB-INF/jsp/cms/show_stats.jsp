<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="delim" value=","/>
<c:set var="values" value="发布拒宅数|发布好主意数|通过审核拒宅数|感兴趣数,本地注册数|注册数|q+注册数|通过审核头像数|完成引导数,点击解救小宅数|响应拒宅数|私密约数|打开好主意弹框数|私信总数|点击解救女宅数|解救女宅框数|收藏用户数|解救小宅弹框数,登陆数|本地登录数|打开邮件数|通过邮件回访数|宅男自救器开启人数|宅男自救器关闭人数" />
<c:set var="value" value="${fn:split(values, delim)}"/>
<c:set var="names" value="行为统计,互动统计,转化率统计,其他统计" />
<c:set var="name" value="${fn:split(names, delim)}"/>
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
		<c:forEach items="${value }"  varStatus="index">
		<tr><td>${name[index.index]}</td></tr>
		<tr style="background-color: #CCCCCC;">
			<c:forEach var="object" items="${CounterStats}">
				<c:if test="${fn:indexOf(value[index.index],object.key)!=-1}">
	    			<td>${object.key}:<c:out value="${object.value}"></c:out></td>
	    		</c:if>
    		</c:forEach>
		</tr>
		<tr style="height: 50px"><td></td></tr>
		</c:forEach>
	</table>
</body>
</html>