<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加好主意</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
</head>
<body>
	<h2><a href="/cms/show/idea">已发布的好主意</a>----添加好主意</h2>
	<h3>${msg}</h3>
	<form action="/cms/add/idea" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<td>添加好主意到:<select name="city">
			
			<option value="0">全国</option>
			<c:forEach var="c" items="${citys}">
				<option value="${c.id }">${c.name}</option>
			</c:forEach>	
			</select></td>
		</tr>
		<tr>
			<td>
			内容：
			</td>
			<td>
				<textarea rows="10" name="content" cols="20">${addIdeaForm.content}</textarea>
			</td>
		</tr>
		<tr>	
			<td>
			日期:
			</td>
			<td><input type="text" name="dateString" readonly="readonly" onclick="WdatePicker();" value="${addIdeaForm.date}" /></td>
		</tr>
		<tr>	
		<td>
			地点:
		</td>
			<td><input type="text" name="place"  value="${addIdeaForm.place}" /></td>
		</tr>
		<tr>	
		<td>
			图片:
		</td>
			<td><input type="file" name="newpic"/>
			<c:choose>
			<c:when test="${!empty jzr:postPic(view.post.id,view.post.pic)}">
			<img src="${jzr:postPic(view.post.id,view.post.pic)}"/>
			<input name="pic" type="hidden" value="${addIdeaForm.pic}"/>
			</c:when>
			<c:otherwise>
				无图片
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td>
				详情链接
			</td>
			<td>
				<input type="text" name="link" value="" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<input name="postId" type="hidden" value="${addIdeaForm.postId}" />
			<input name="createUid" type="hidden" value="${addIdeaForm.createUid }"></input>
			<input type="submit" value="添加" /> </td>
		</tr>
	</table>
	</form>
</body>
</html>