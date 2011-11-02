<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加项目</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
	function selectCity(obj) {
		$.get('/cms/selectCity', {
			proId : obj.value,
			random : Math.random()
		}, function(result) {
			$("#citys").html(result);
		});
	}
	function showAddress() {
		document.getElementById("myaddress").style.display = "";
	}
	function hiedAddress() {
		document.getElementById("myaddress").style.display = "none";
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>添加项目</h2>
	<form action="/cms/updateAct" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="id" value="${act.id }" />
		<table>
			<tr>
				<td>简称</td>
				<td><input type="text" name="name" value="${act.name}" /></td>
			</tr>
			<tr>
				<td>全称（选填）：</td>
				<td><input type="text" name="fullName" value="${act.fullName}" />
				</td>
			</tr>
			<tr>
				<td>简介（选填）：</td>
				<td><textarea rows="5" cols="40" name="intro">
						<c:out value="${act.intro}"></c:out> </textarea>
				</td>
			</tr>
			<tr>
				<td>分类：</td>
				<td>
					<c:forEach var="cats" items="${categoryList}">
						<c:set var="checked" value="false" />
						<c:forEach var="cId" items="${act.categoryIds}">
							<c:if test="${cId==cats.id}"><c:set var="checked" value="true" /></c:if>
						</c:forEach>
						${cats.name}:<input checked="${checked}" type="checkbox" name="catIds" value="${cats.id}" />
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>地点（选填）：</td>
				<td><input type="radio" name="checkAddress"
					onclick="hiedAddress()"
					<c:if test="${act.province==0}">checked="checked"</c:if>
					value="true" />不限 <input type="radio" name="checkAddress"
					<c:if test="${act.province!=0}">checked="checked"</c:if>
					value="false" onclick="showAddress()" />有限</td>
			</tr>

			<tr id="myaddress"
				<c:if test="${act.province==0}">style="display: none"</c:if>>
				<td></td>
				<td>省:<select name="province" onchange="selectCity(this)">
						<c:forEach var="pro" items="${provinces}">
							<option
								<c:if test="${act.province==pro.id}">selected="selected"</c:if>
								value="${pro.id}">${pro.name}</option>
						</c:forEach>
				</select>市:<span id="citys"><select name="city">
							<c:forEach var="city" items="${citys}">
								<c:if test="${act.province==city.provinceId}">
									<option
										<c:if test="${act.city==city.id}">selected="selected"</c:if>
										value="${city.id}">${city.name}</option>
								</c:if>
							</c:forEach>
					</select> </span> 详细地址:<input type="text" name="address" value="${act.address}" />
				</td>
			</tr>
			<tr>
				<td>适合人群</td>
				<td>年龄 <c:forEach var="suitAge" items="${suitAges}">
						<input type="radio" name="suiAge"
							<c:if test="${age==suitAge}">checked="checked"</c:if>
							checked="checked" value="${suitAge}" />${suitAge.type}
			</c:forEach></td>
			</tr>
			<tr>
				<td></td>
				<td>性别 <c:forEach var="suitGender" items="${suitGenders}">
						<input type="radio" name="suitGender"
							<c:if test="${gender==suitGender}">checked="checked"</c:if>
							value="${suitGender}" />${suitGender.type}
			</c:forEach></td>
			</tr>
			<tr>
				<td></td>
				<td>适合人群 <c:forEach var="suitStatu" items="${suitStatus}">
						<input type="radio" name="suitStatu"
							<c:if test="${stauts==suitStatu}">checked="checked"</c:if>
							value="${suitStatu}" />${suitStatu.type}
			</c:forEach></td>
			</tr>
			<tr>
				<td>适合人数（选填）</td>
				<td><input type="text" name="minRoleNum"
					value="${act.minRoleNum}" />至 <input type="text" name="maxRoleNum"
					value="${act.maxRoleNum}" /></td>
			</tr>
			<tr>
				<td>起始时间（选填）</td>
				<td><input type="text" name="startTime"
					value="<fmt:formatDate value="${act.startTime}"
						pattern="yyyy.MM.dd" />" />
				</td>
			</tr>
			<tr>
				<td>截止时间（选填）</td>
				<td><input type="text" name="endTime"
					value="<fmt:formatDate value="${act.endTime}"
						pattern="yyyy.MM.dd" />" />
				</td>
			</tr>
			<tr>
				<td>消费区间（选填）</td>
				<td><input type="text" name=minCharge value="${act.minCharge }" />至<input
					type="text" name=maxCharge value="${act.maxCharge }" /></td>
			</tr>
			<tr>
				<td>logo</td>
				<td><img src="${logoWebPath}" width="50" height="50" />重新上传<input
					type="file" name="imgFile" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="提交" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>