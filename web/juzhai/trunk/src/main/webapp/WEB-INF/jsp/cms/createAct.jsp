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
			proId:obj.value,
		    random : Math.random()
		}, function(result) {
			$("#citys").html(result);
		});
	}
	function showAddress(){
		document.getElementById("myaddress").style.display="";
	}
	function hiedAddress(){
		document.getElementById("myaddress").style.display="none";
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>添加项目</h2>
	<form action="/cms/createAct" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>简称</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>全称（选填）：</td>
				<td><input type="text" name="fullName" /></td>
			</tr>
			<tr>
				<td>简介（选填）：</td>
				<td><textarea rows="5" cols="40" name="intro"></textarea>
				</td>
			</tr>
			<tr>
				<td>分类：</td>
				<td><select name="catId">
						<c:forEach var="cats" items="${categoryList}">
							<option value="${cats.id}">${cats.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>地点（选填）：</td>
				<td><input type="radio" name="checkAddress" onclick="hiedAddress()" checked="checked"
					value="true" />不限 <input type="radio" name="checkAddress"
					value="false" onclick="showAddress()" />有限</td>
			</tr>
			<tr id="myaddress" style="display: none">
				<td></td>
				<td>省:<select name="province" onchange="selectCity(this)">
						<c:forEach var="pro" items="${provinces}" varStatus="status">
							<c:if test="${status.index==0}">
								<c:set var="s" value="${pro.id}"></c:set>							
							</c:if>
							<option value="${pro.id}">${pro.name}</option>
						</c:forEach>
				</select>市:<span id="citys"><select name="city">
							<c:forEach var="city" items="${citys}">
							<c:if test="${s==city.provinceId}">
								<option value="${city.id}">${city.name}</option>
							</c:if>
							</c:forEach>
					</select> </span> 详细地址:<input type="text" name="address" /></td>
			</tr>
			<tr>
				<td>适合人群</td>
				<td>年龄 <c:forEach var="suitAge" items="${suitAges}">
						<input type="radio" name="suiAge" value="${suitAge}" />${suitAge.type}
			</c:forEach></td>
			</tr>
			<tr>
				<td></td>
				<td>性别 <c:forEach var="suitGender" items="${suitGenders}">
						<input type="radio" name="suitGender" value="${suitGender}" />${suitGender.type}
			</c:forEach></td>
			</tr>
			<tr>
				<td></td>
				<td>适合人群 <c:forEach var="suitStatu" items="${suitStatus}">
						<input type="radio" name="suitStatu" value="${suitStatu}" />${suitStatu.type}
			</c:forEach></td>
			</tr>
			<tr>
				<td>适合人数（选填）</td>
				<td><input type="text" name="minRoleNum" value="1" />至 <input
					type="text" name="maxRoleNum" /></td>
			</tr>
			<tr>
				<td>起始时间（选填）</td>
				<td><input type="text" name="startTime" /></td>
			</tr>
			<tr>
				<td>截止时间（选填）</td>
				<td><input type="text" name="endTime" /></td>
			</tr>
			<tr>
				<td>消费区间（选填）</td>
				<td><input type="text" name=minCharge />至<input type="text"
					name=maxCharge /></td>
			</tr>
			<tr>
				<td>logo</td>
				<td><input type="file" name="imgFile" /></td>
			</tr>
			<tr>
				<td  colspan="2" align="center"><input type="submit" value="提交"/> </td>
			</tr>
		</table>
	</form>
</body>
</html>