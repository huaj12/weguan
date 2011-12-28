<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改项目</title>
<script type="text/javascript"
	src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script type="text/javascript"
	src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<script>
function selectCity(obj) {
	$.get('/base/selectCity', {
		proId : obj.value,
		random : Math.random()
	}, function(result) {
		$("#citys").html(result);
		if($("#c_id")[0]){
			selectTown($("#c_id").val());
			$("#towns").show();
		}else{
			$("#towns").hide();
		}
	});
}

function selectTown(id) {
	$.get('/base/selectTown', {
		cityId : id,
		random : Math.random()
	}, function(result) {
		$("#towns").html(result);
	});
}
	function showAddress() {
		document.getElementById("myaddress").style.display = "";
	}
	function hiedAddress() {
		document.getElementById("myaddress").style.display = "none";
	}
	function checkData(){
		var name=$("#name").val();
		var fullName=$("#fullName").val();
		var intro=$("#intro").val();
		if(trimStr(name).length==0){
			alert("简称不能为空");
			return false;
		}
		if(name.length>10){
			alert("简称不能超过10个字");
			return false;
		}
		if(fullName.length>30){
			alert("全称不能超过30个字");
			return false;
		}if(intro.length>200){
			alert("简介不能超过200个字");
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
	function trimStr(str)  
	{   
	    if ((typeof(str) != "string") || !str)  
	    {  
	        return "";   
	    }  
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>修改项目</h2>
	<form action="/cms/updateAct" onsubmit="return checkData();"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${act.id }" />
		<table>
			<tr>
				<td>简称</td>
				<td><input type="text" id="name" name="name"
					value="${act.name}" /></td>
			</tr>
			<tr>
				<td>全称（选填）：</td>
				<td><input type="text" id="fullName" name="fullName"
					value="${act.fullName}" /></td>
			</tr>
			<tr>
				<td>简介（选填）：</td>
				<td><textarea rows="5" cols="40" id="intro" name="intro">
						<c:out value="${act.intro}"></c:out>
					</textarea>
				</td>
			</tr>
			<tr>
				<td>详情（选填）：</td>
				<td><textarea rows="5" cols="40" id="detail" name="detail">${actDetail.detail}</textarea>
				</td>
			</tr>
			<tr>
				<td>分类：</td>
				<td><c:forEach var="cats" items="${categoryList}"
						varStatus="step">
						<c:set var="checked" value="false" />
						<c:forEach var="cId" items="${act.categoryIds}">
							<c:if test="${cId==cats.id}">
								<c:set var="checked" value="true" />
							</c:if>
						</c:forEach>
						${cats.name}:<input
							<c:if test="${checked}">checked="checked"</c:if> type="checkbox"
							name="catIds" value="${cats.id}" />&nbsp;&nbsp;
						 <c:if test="${step.count % 4==0}">
							<br />
						</c:if>
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
				<td><span><select name="province" id="province"
						onchange="selectCity(this)">
							<c:forEach var="pro" items="${provinces}">
								<option
									<c:if test="${act.province==pro.id}">selected="selected"</c:if>
									value="${pro.id}">${pro.name}</option>
							</c:forEach>
					</select> </span> <span id="citys"> <select name="city" id="city">
							<c:forEach var="city" items="${citys}">
								<c:if test="${act.province==city.provinceId}">
									<option
										<c:if test="${act.city==city.id}">selected="selected"</c:if>
										value="${city.id}">${city.name}</option>
								</c:if>
							</c:forEach>
					</select> </span> <span id="towns"
					<c:if test="${act.town=='-1'}"> style="display: none" </c:if>><select
						name="town" id="town">
							<c:forEach var="town" items="${towns}">
								<c:if test="${act.city==town.cityId}">
									<option
										<c:if test="${act.town==town.id}">selected="selected"</c:if>
										value="${town.id}">${town.name}</option>
								</c:if>
							</c:forEach>
					</select> </span> 详细地址:<input type="text" name="address" value="${act.address}" />
				</td>
			</tr>
			<tr>
				<td>适合人群</td>
				<td>年龄<c:forEach var="suitAge" items="${suitAges}">
						<input type="radio" name="suiAge"
							<c:if test="${age==suitAge}">checked="checked"</c:if>
							value="${suitAge}" />${suitAge.type}
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
				<td><input type="text" onClick="WdatePicker()" name="startTime"
					value="<fmt:formatDate value="${act.startTime}"
						pattern="yyyy-MM-dd" />" />
				</td>
			</tr>
			<tr>
				<td>截止时间（选填）</td>
				<td><input type="text" onClick="WdatePicker()" name="endTime"
					value="<fmt:formatDate value="${act.endTime}"
						pattern="yyyy-MM-dd" />" />
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