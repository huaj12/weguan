<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>更新首页推荐内容</title>
</head>
<body>
	<h2>更新欢迎页好主意推荐内容 <a href="/cms/update/recommend/idea">更新</a></h2>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="200">好主意图片</td>
			<td width="300">好注意内容</td>
		</tr>
		<c:forEach var="idea" items="${ideas}" >
			<tr>
				<td>
					<img src="${jzr:ideaPic(idea.id,idea.pic, 200)}" width="200"  height="200" /> 
				</td>
					<td>
					${idea.content}
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>