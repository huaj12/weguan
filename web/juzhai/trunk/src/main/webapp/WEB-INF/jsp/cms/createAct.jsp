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
<script type="text/javascript"
	src="${jz:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<script>
	function selectCity(obj) {
		$.get('/base/selectCity', {
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
	<h2>添加项目${msg}</h2>
	<form action="/cms/createAct" onsubmit="return checkData();"
		method="post" enctype="multipart/form-data">
		<input value="${addUid}" type="hidden" name="addUid"/>
		<table>
			<tr>
				<td>简称</td>
				<td><input type="text" id="name" value="${actName}" name="name" />
				</td>
			</tr>
			<tr>
				<td>全称（选填）：</td>
				<td><input type="text" id="fullName" name="fullName" />
				</td>
			</tr>
			<tr>
				<td>简介（选填）：</td>
				<td><textarea rows="5" id="intro" cols="40" name="intro"></textarea>
				</td>
			</tr>
			<tr>
			<td>详情（选填）：</td>
				<td><textarea id="detail"  name="detail"></textarea>
				</td>
			
			</tr>
			<tr>
				<td>分类：</td>
				<td><c:forEach var="cats" items="${categoryList}"
						varStatus="step">
					${cats.name}:<input type="checkbox" name="catIds"
							value="${cats.id}" />
							 <c:if test="${step.count % 4==0}">
							<br />
						</c:if>
					</c:forEach></td>
			</tr>
			<tr>
				<td>地点（选填）：</td>
				<td><input type="radio" name="checkAddress"
					onclick="hiedAddress()" checked="checked" value="true" />不限 <input
					type="radio" name="checkAddress" value="false"
					onclick="showAddress()" />有限</td>
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
					</select> </span> 详细地址:<input type="text" name="address" />
				</td>
			</tr>
			<tr>
				<td>适合人群</td>
				<td>年龄 <c:forEach var="suitAge" items="${suitAges}">

						<input <c:if test="${suitAge=='ALL'}">checked="checked"</c:if>
							type="radio" name="suiAge" value="${suitAge}" />${suitAge.type}
			</c:forEach>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>性别 <c:forEach var="suitGender" items="${suitGenders}">
						<input type="radio"
							<c:if test="${suitGender=='ALL'}">checked="checked"</c:if>
							name="suitGender" value="${suitGender}" />${suitGender.type}
			</c:forEach>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>适合人群 <c:forEach var="suitStatu" items="${suitStatus}">
						<input type="radio" name="suitStatu"
							<c:if test="${suitStatu=='ALL'}">checked="checked"</c:if>
							value="${suitStatu}" />${suitStatu.type}
			</c:forEach>
				</td>
			</tr>
			<tr>
				<td>适合人数（选填）</td>
				<td><input type="text" name="minRoleNum" value="1" />至 <input
					type="text" name="maxRoleNum" />
				</td>
			</tr>
			<tr>
				<td>起始时间（选填）</td>
				<td><input type="text" onClick="WdatePicker()" name="startTime" />
				</td>
			</tr>
			<tr>
				<td>截止时间（选填）</td>
				<td><input type="text" onClick="WdatePicker()" name="endTime" />
				</td>
			</tr>
			<tr>
				<td>消费区间（选填）</td>
				<td><input type="text" name=minCharge />至<input type="text"
					name=maxCharge />
				</td>
			</tr>
			<tr>
				<td>logo</td>
				<td><input type="file" name="imgFile" />
				</td>
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