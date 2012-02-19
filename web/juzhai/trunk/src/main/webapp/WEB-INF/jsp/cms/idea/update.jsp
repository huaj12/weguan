<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改好主意</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
</head>
<body>
	<h2><a href="/cms/show/idea">已发布的好主意</a>----添加好主意</h2>
	<h3><font color="red">${msg}</font></h3>
	<form action="/cms/update/idea" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<td>添加好主意到:<select name="city">
								<option value="0"  <c:if test="${idea.city==0}"> selected="selected"</c:if> >全国</option>
								<option value="2"   <c:if test="${idea.city==2}">selected="selected"</c:if>>上海</option>
								<option value="1"   <c:if test="${idea.city==1}">selected="selected"</c:if>>北京</option>
								<option value="181" <c:if test="${idea.city==181}">selected="selected"</c:if>>广州</option>
								<option value="183" <c:if test="${idea.city==183}">selected="selected"</c:if>>深圳</option>
								<option value="343" <c:if test="${idea.city==343}">selected="selected"</c:if>>杭州</option>
								<option value="157" <c:if test="${idea.city==157}">selected="selected"</c:if>>南京</option>
								<option value="4"   <c:if test="${idea.city==4}">selected="selected"</c:if>>重庆</option>
								<option value="241" <c:if test="${idea.city==241}">selected="selected"</c:if>>成都</option>
								<option value="108" <c:if test="${idea.city==108}">selected="selected"</c:if>>武汉</option>
			</select>
			性别:<select name="gender">
				<option <c:if test="${empty idea.gender}"> selected="selected"</c:if>value="">不限</option>
				<option <c:if test="${idea.gender==1}"> selected="selected"</c:if> value="1">男</option>
				<option <c:if test="${idea.gender==0}"> selected="selected"</c:if> value="0">女</option>
			</select>
			推荐到随即库:<select name="random">
				<option value="0" <c:if test="${!idea.random}"> selected="selected"</c:if> >否</option>
				<option value="1"<c:if test="${idea.random}"> selected="selected"</c:if>>是</option>
			</select>
			类别:<select name="categoryId">
				<option <c:if test="${idea.categoryId==0}"> selected="selected"</c:if> value="0">不限</option>
				<c:forEach items="${categoryList}" var="cat">
					<option <c:if test="${cat.id==idea.categoryId}"> selected="selected"</c:if> value="${cat.id}">${cat.name}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>
			内容：
			</td>
			<td>
				<textarea rows="10" name="content" cols="20">${idea.content}</textarea>
			</td>
		</tr>
		<tr>	
			<td>
			日期:
			</td>
			<td><input type="text" name="dateString" readonly="readonly" onclick="WdatePicker();" value="<fmt:formatDate value="${idea.date}"
						pattern="yyyy-MM-dd" />" /></td>
		</tr>
		<tr>	
		<td>
			地点:
		</td>
			<td><input type="text" name="place"  value="${idea.place}" /></td>
		</tr>
		<tr>	
		<td>
			图片:
		</td>
			<td><input type="file" name="newpic"/>
			<c:set value="${jzr:ideaPic(idea.id,idea.pic,200) }" var="picPath"></c:set>
			<c:choose>
			<c:when test="${!empty picPath}">
			<img src="${picPath }" width="100" height="100"/>
			<input name="pic" type="hidden" value="${idea.pic}"/>
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
				<input type="text" name="link" value="${idea.link}" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<input name="ideaId" type="hidden" value="${idea.id}" />
			<input type="submit" value="修改" /> </td>
		</tr>
	</table>
	</form>
</body>
</html>