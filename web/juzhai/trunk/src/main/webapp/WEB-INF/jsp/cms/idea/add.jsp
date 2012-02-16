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
								<option value="2"   >上海</option>
								<option value="1"   >北京</option>
								<option value="181" >广州</option>
								<option value="183" >深圳</option>
								<option value="343" >杭州</option>
								<option value="157" >南京</option>
								<option value="4"   >重庆</option>
								<option value="241" >成都</option>
								<option value="108" >武汉</option>
			</select>
			性别<select name="gender">
				<option value="" >不限</option>
				<option value="1">男</option>
				<option value="0">女</option>
			</select>
			推荐到随即库:<select name="random">
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
			类别：<select name="categoryId">
				<option value="0">不限</option>
				<c:forEach items="${categoryList}" var="cat">
					<option value="${cat.id}">${cat.name}</option>
				</c:forEach>
			</select>
			</td>
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
			<c:when test="${!empty jzr:postPic(addIdeaForm.postId,0, addIdeaForm.pic,200)}">
			<img src="${jzr:postPic(addIdeaForm.postId,0, addIdeaForm.pic,200)}"/>
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