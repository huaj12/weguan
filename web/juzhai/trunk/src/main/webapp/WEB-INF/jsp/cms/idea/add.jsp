<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加好主意</title>
<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
</head>
<body>
	<h2><a href="/cms/show/idea">已发布的好主意</a>----添加好主意</h2>
	<h3><font color="red">${msg}</font></h3>
	<form action="/cms/add/idea" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<td>添加好主意到:
			<div>
			<c:import url="/WEB-INF/jsp/web/common/widget/location.jsp">
			<c:param name="provinceId" value="${ideaForm.province}"/>
			<c:param name="cityId" value="${ideaForm.city}"/>
			<c:param name="townId" value="${ideaForm.town}"/>
			</c:import>
			</div>
			性别<select name="gender">
				<option value="" <c:if test="${empty ideaForm.gender}">selected="selected"</c:if>>不限</option>
				<option value="1" <c:if test="${1==ideaForm.gender}">selected="selected"</c:if> >男</option>
				<option value="0" <c:if test="${0==ideaForm.gender}">selected="selected"</c:if> >女</option>
			</select>
			推荐到随即库:<select name="random">
				<option value="0" <c:if test="${!ideaForm.random}">selected="selected"</c:if> >否</option>
				<option value="1" <c:if test="${ideaForm.random}">selected="selected"</c:if>>是</option>
			</select>
			类别：<select name="categoryId">
				<option value="0">不限</option>
				<c:forEach items="${categoryList}" var="cat">
					<option value="${cat.id}"  <c:if test="${cat.id==ideaForm.categoryId}">selected="selected"</c:if>>${cat.name}</option>
				</c:forEach>
				
			</select>
			</td>
		</tr>
		<tr>
			<td>
			内容：
			</td>
			<td>
				<textarea rows="10" name="content" cols="20">${ideaForm.content}</textarea>
			</td>
		</tr>
		<tr>	
			<td>
			日期:
			</td>
			<td>
			<input type="text" name="startDateString" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" value="${ideaForm.startDateString}" />-
			<input type="text" name="endDateString" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" value="${ideaForm.endDateString}" />
			</td>
		</tr>
		<tr>	
		<td>
			地点:
		</td>
			<td><input type="text" name="place"  value="${ideaForm.place}" /></td>
		</tr>
		<tr>	
		<td>
			图片:
		</td>
			<td><input type="file" name="newpic"/>
			<c:choose>
				<c:when test="${!empty jzr:postPic(ideaForm.postId,0, ideaForm.pic,200)}">
					<img src="${jzr:postPic(ideaForm.postId,0, ideaForm.pic,200)}"/>
					<input name="pic" type="hidden" value="${ideaForm.pic}"/>
				</c:when>
				<c:when test="${not empty picWeb}">
					<img src="${ideaForm.picWeb}" width="120" height="120"/>
					<input name="picWeb" type="hidden" value="${ideaForm.picWeb}"/>
					<input name="pic" type="hidden" value="${ideaForm.pic}"/>
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
				<input type="text" name="link" value="${ideaForm.link}" />
			</td>
		</tr>
		<tr>
			<td>
				费用
			</td>
			<td>
				<input type="text" name="charge" value="${ideaForm.charge}" />
			</td>
		</tr>
		<tr>
		<td>详情</td>
			<td><textarea id="detail" style="width: 700px; height: 200px; visibility: hidden;" name="detail">${ideaForm.detail }</textarea></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<input name="postId" type="hidden" value="${ideaForm.postId}" />
			<input name="createUid" type="hidden" value="${ideaForm.createUid }"></input>
			<input type="submit" value="添加" /> </td>
		</tr>
	</table>
	</form>
	<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
		<script>
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="detail"]', {
				resizeType : 1,
				uploadJson : '/idea/kindEditor/upload',
				allowPreviewEmoticons : false,
				allowImageUpload : true,
				items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
						'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter',
						'justifyright', 'insertorderedlist', 'insertunorderedlist',
						'|', 'emoticons', 'image', 'link' ]
			});
		});
		</script>
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script>new LocationWidget();</script>
</body>
</html>