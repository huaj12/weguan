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
			<td colspan="2">添加好主意到:
				<c:import url="/WEB-INF/jsp/web/common/widget/location.jsp">
				<c:param name="provinceId" value="${ideaForm.province}"/>
				<c:param name="cityId" value="${ideaForm.city}"/>
				<c:param name="townId" value="${ideaForm.town}"/>
				</c:import>
				<c:if test="${not empty rawIdea.city&&not empty rawIdea.town &&(rawIdea.town!=ideaForm.town||ideaForm.city!=rawIdea.city) }">
				<font color="red">纠错:</font>
				${jzd:cityName(rawIdea.city)}-${jzd:townName(rawIdea.town)}
				</c:if>
			性别:<select name="gender">
				<option <c:if test="${empty ideaForm.gender}"> selected="selected"</c:if>value="">不限</option>
				<option <c:if test="${ideaForm.gender==1}"> selected="selected"</c:if> value="1">男</option>
				<option <c:if test="${ideaForm.gender==0}"> selected="selected"</c:if> value="0">女</option>
			</select>
			推荐到随即库:<select name="random">
				<option value="0" <c:if test="${!ideaForm.random}"> selected="selected"</c:if> >否</option>
				<option value="1"<c:if test="${ideaForm.random}"> selected="selected"</c:if>>是</option>
			</select>
			类别:<select name="categoryId">
				<option <c:if test="${ideaForm.categoryId==0}"> selected="selected"</c:if> value="0">不限</option>
				<c:forEach items="${categoryList}" var="cat">
					<option <c:if test="${cat.id==ideaForm.categoryId}"> selected="selected"</c:if> value="${cat.id}">${cat.name}</option>
				</c:forEach>
			</select>
			<c:if test="${not empty rawIdea.categoryId&&rawIdea.categoryId!=ideaForm.categoryId}">
			<font color="red">纠错:</font>
			${jzd:categoryName(rawIdea.categoryId)}
			</c:if>
			</td>
		</tr>
		<tr>
			<td>
			内容：
			</td>
			<td>
				<input name="content" value="${ideaForm.content}"/>
			</td>
			<c:if test="${not empty rawIdea.content &&rawIdea.content!=ideaForm.content }">
			<td><font color="red">纠错</font></td>
			<td>${rawIdea.content}</td>
			</c:if>
		</tr>
		<tr>	
			<td>
			日期:
			</td>
			<td>
			<input type="text" name="startDateString" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" value="${ideaForm.startDateString}" />-
			<input type="text" name="endDateString" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" value="${ideaForm.endDateString}" />
			</td>
			<c:if test="${not empty startDate && not empty endDate&&(startDate!=ideaForm.startDateString||endDate!=ideaForm.endDateString) }">
			<td><font color="red">纠错</font></td>
			<td>${startDate}-${endDate }</td>
			</c:if>
		</tr>
		<tr>	
		<td>
			地点:
		</td>
			<td><input type="text" name="place"  value="${ideaForm.place}" /></td>
			<c:if test="${not empty rawIdea.place &&rawIdea.place!=ideaForm.place  }">
			<td><font color="red">纠错</font></td>
			<td>${rawIdea.place}</td>
			</c:if>
		</tr>
		<tr>	
		<td>
			图片:
		</td>
			<td><input type="file" name="newpic"/>
			<c:set value="${jzr:ideaPic(ideaForm.ideaId,ideaForm.pic,200) }" var="picPath"></c:set>
			<c:choose>
			<c:when test="${!empty picPath}">
			<img src="${picPath }" width="100" height="100"/>
			<input name="pic" type="hidden" value="${ideaForm.pic}"/>
			</c:when>
			<c:otherwise>
				无图片
			</c:otherwise>
			</c:choose>
			</td>
			<c:if test="${not empty rawIdea.pic&&ideaForm.pic!=rawIdea.pic }">
			<td><font color="red">纠错</font></td>
			<td>${jzr:ideaTempLogo(rawIdea.pic)}</td>
			</c:if>
		</tr>
		<tr>
			<td>
				详情链接
			</td>
			<td>
				<input type="text" name="link" value="${ideaForm.link}" />
			</td>
			<c:if test="${not empty rawIdea.link &&rawIdea.link!=ideaForm.link }">
			<td><font color="red">纠错</font></td>
			<td>${rawIdea.link}</td>
			</c:if>
		</tr>
		<tr>
			<td>
				费用
			</td>
			<td>
				<input type="text" name="charge" value="${ideaForm.charge}" />
			</td>
			<c:if test="${not empty rawIdea.charge &&rawIdea.charge!=ideaForm.charge  }">
			<td><font color="red">纠错</font></td>
			<td>${rawIdea.charge}</td>
			</c:if>
		</tr>
		<tr>
		<td>详情</td>
			<td><textarea id="detail" style="width: 700px; height: 200px; visibility: hidden;" name="detail">${detail.detail}</textarea></td>
			<c:if test="${not empty rawIdea.detail &&rawIdea.detail!=detail.detail}">
			<td><font color="red">纠错</font></td>
			<td>${rawIdea.detail}</td>
			</c:if>
		</tr>
		<tr>
			<td></td>
			<td>
			<input name="ideaId" type="hidden" value="${ideaForm.ideaId}" />
			<input type="submit" value="修改" /> </td>
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