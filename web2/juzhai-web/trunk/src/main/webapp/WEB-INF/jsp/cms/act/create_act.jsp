<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加项目</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
<script type="text/javascript">
	function checkData(){
		var name=$("#name").val();
		var fullName=$("#fullName").val();
		var intro=$("#intro").val();
		if(!checkValLength($.trim(name), 2, 20)){
			alert("简称不能超过10个字");
			return false;
		}
		if(!checkValLength($.trim(fullName), 0, 50)){
			alert("全称不能超过50个字");
			return false;
		}
		if(!checkValLength($.trim(intro), 0, 150)){
			alert("简介不能超过150个字");
			return false;
		}
		var flag=true;
		$('input[name=catIds]').each(function(){
			 if(this.checked){
		   		flag=false;
		   		return false;
		   }
		})
		if(flag){
			alert("至少选择一个分类");
			return false;
		}
		
		return true;
	}    
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>添加项目&nbsp;<font color="red">${errorInfo}</font></h2>
	<form action="/cms/createAct" onsubmit="return checkData();" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${addActForm.id }" />
		<input value="${addActForm.addUid}" type="hidden" name="addUid" />
		<table>
			<tr>
				<td>简称</td>
				<td><input type="text" id="name" value="${addActForm.name}" name="name" /></td>
			</tr>
			<tr>
				<td>全称（选填）：</td>
				<td><input type="text" id="fullName" name="fullName" value="${addActForm.fullName}" /></td>
			</tr>
			<tr>
				<td>简介（选填）：</td>
				<td><textarea rows="5" id="intro" cols="40" name="intro">${addActForm.intro}</textarea></td>
			</tr>
			<tr>
				<td>详情（选填）：</td>
				<td><textarea id="detail" style="width: 700px; height: 200px; visibility: hidden;" name="detail">${addActForm.detail}</textarea></td>
			</tr>
			<tr>
				<td>分类：</td>
				<td>
					<c:forEach var="cats" items="${categoryList}" varStatus="step">
						<c:set var="checked" value="false" />
						<c:forEach var="cId" items="${addActForm.catIds}">
							<c:if test="${cId==cats.id}">
								<c:set var="checked" value="true" />
							</c:if>
						</c:forEach>
						${cats.name}:<input <c:if test="${checked}">checked="checked"</c:if> type="checkbox" name="catIds" value="${cats.id}" />&nbsp;&nbsp;
						 <c:if test="${step.count % 4==0}">
							<br />
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>地点（选填）：</td>
				<td>
					<c:import url="/WEB-INF/jsp/web/common/widget/location.jsp">
						<c:param name="provinceId" value="${addActForm.province}"/>
						<c:param name="cityId" value="${addActForm.city}"/>
						<c:param name="townId" value="${addActForm.town}"/>
					</c:import>详细地址:<input type="text" name="address" value="${addActForm.address}" />
				</td>
			</tr>
			<tr>
				<td>适合人群</td>
				<td>年龄
					<c:forEach var="suitAge" items="${suitAges}">
						<input <c:if test="${addActForm.suitAge==suitAge || (empty addActForm.suitAge && suitAge=='ALL')}">checked="checked"</c:if> type="radio" name="suitAge" value="${suitAge}" />${suitAge.type}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>性别
					<c:forEach var="suitGender" items="${suitGenders}">
						<input type="radio" <c:if test="${addActForm.suitGender==suitGender || (empty addActForm.suitGender && suitGender=='ALL')}">checked="checked"</c:if> name="suitGender" value="${suitGender}" />${suitGender.type}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>适合人群
					<c:forEach var="suitStatu" items="${suitStatus}">
						<input type="radio" <c:if test="${addActForm.suitStatus==suitStatu || (empty addActForm.suitStatus && suitStatu=='ALL')}">checked="checked"</c:if> name="suitStatus" value="${suitStatu}" />${suitStatu.type}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>适合人数（选填）</td>
				<td><input type="text" name="minRoleNum" value="${addActForm.minRoleNum}" />至 <input type="text" name="maxRoleNum" value="${addActForm.maxRoleNum}" /></td>
			</tr>
			<tr>
				<td>起始时间（选填）</td>
				<td><input type="text" onclick="WdatePicker()" name="startTime" value="${addActForm.startTime}"/></td>
			</tr>
			<tr>
				<td>截止时间（选填）</td>
				<td><input type="text" onclick="WdatePicker()" name="endTime" value="${addActForm.endTime}"/></td>
			</tr>
			<tr>
				<td>消费区间（选填）</td>
				<td><input type="text" name="minCharge" value="${addActForm.minCharge}" />至<input type="text" name="maxCharge" value="${addActForm.maxCharge}" /></td>
			</tr>
			<tr>
				<td>关键字（选填）</td>
				<td><input type="text" name="keyWords" value="${addActForm.keyWords}" /></td>
			</tr>
			<tr>
				<td>logo</td>
				<td><input type="file" name="imgFile" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="提交" /></td>
			</tr>
		</table>
	</form>
	<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
	<script>
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="detail"]', {
				resizeType : 1,
				uploadJson : '/act/kindEditor/upload',
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
</body>
</html>