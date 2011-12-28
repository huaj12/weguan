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
<title>审核推荐项目</title>
<script type="text/javascript"
	src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
<style type="text/css">
</style>
</head>
<body>
	<h2>审核推荐项目列表</h2>
	<table border="0">
		<tr>
			<td>项目名</td>
			<td><input id="name" type="text" value="${rawAct.name}" />
			</td>
		</tr>
		<tr>
			<td>详细信息</td>
			<td><textarea id="detail" name="detail"
					style="width: 700px; height: 200px; visibility: hidden;" cols=""
					rows="">${rawAct.detail}</textarea>
			</td>
		</tr>
		<tr>
			<td>上传图片：</td>
			<td><form id="uploadImgForm" method="post"
					enctype="multipart/form-data">
					<input type="file" onchange="uploadImage();" name="profileLogo" />
				</form>
				<img id="logo_path" width="120" height="120"
				src="${jzr:actTempLogo(rawAct.logo)}" />
			<div id="logo_tip"></div> <input id="logo" type="hidden"
				value="${rawAct.logo}" /></td>
		</tr>
		<tr>
			<td>分类</td>
			<td><select name="category_ids" id="category_ids">
					<c:forEach var="cats" items="${categoryList}">
						<option value="${cats.id}"
							<c:if test="${rawAct.categoryIds==cats.id}" >selected="selected" </c:if>>${cats.name
							}</option>
					</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>地点</td>
			<td><span> <select id="province"
					onchange="selectCity(this)">
						<c:forEach var="pro" items="${provinces}">
							<option
								<c:if test="${rawAct.province==pro.id}">selected="selected"</c:if>
								value="${pro.id}">${pro.name}</option>
						</c:forEach>
				</select> </span> <span id="citys"> <select id="city">
						<c:forEach var="city" items="${citys}">
							<c:if test="${rawAct.province==city.provinceId}">
								<option
									<c:if test="${rawAct.city==city.id}">selected="selected"</c:if>
									value="${city.id}">${city.name}</option>
							</c:if>
						</c:forEach>
				</select> </span> <span id="towns"
				<c:if test="${rawAct.town=='-1'}"> style="display: none" </c:if>><select
					name="town" id="town">
						<c:forEach var="town" items="${towns}">
							<c:if test="${rawAct.town==town.cityId}">
								<option
									<c:if test="${rawAct.town==town.id}">selected="selected"</c:if>
									value="${town.id}">${town.name}</option>
							</c:if>
						</c:forEach>
				</select> </span> <input name="address" id="address" type="text"
				value="${rawAct.address}" />
			</td>
		</tr>
		<tr>
			<td>时间</td>
			<td><input name="" readonly="readonly"
				value="<fmt:formatDate value="${rawAct.startTime}"
						pattern="yyyy-MM-dd" />"
				id="startTime" onclick="WdatePicker()" type="text" />到 <input
				id="endTime" readonly="readonly" onclick="WdatePicker()" name=""
				value="<fmt:formatDate value="${rawAct.endTime}"
						pattern="yyyy-MM-dd" />"
				type="text" /> <input id="createUid" value="${rawAct.createUid}"
				type="hidden" /></td>
		</tr>
		<tr>
			<input id="id" type="hidden" value="${rawAct.id}" />
			<td><input type="button" onclick="addRawAct();" value="通过" /></td>
		</tr>
	</table>
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
	<script type="text/javascript"
		src="${jzr:static('/js/core/validation.js')}"></script>
	<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
	<script type="text/javascript"
		src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
	<script type="text/javascript">
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="detail"]', {
				resizeType : 1,
				uploadJson : '/cms/kindEditor/upload',
				allowPreviewEmoticons : false,
				allowImageUpload : true,
				items : [ 'fontname', 'fontsize', '|', 'forecolor',
						'hilitecolor', 'bold', 'italic', 'underline',

						'removeformat', '|', 'justifyleft', 'justifycenter',
						'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image',
						'link' ]
			});
		});
		function uploadImage() {
			var options = {
				url : "/cms/logo/upload",
				type : "POST",
				dataType : "json",
				success : function(result) {
					if (result.success) {
						$("#logo_tip").html("上传成功");
						$("#logo_path").attr("src", result.result[0]);
						$("#logo").val(result.result[1]);
					} else {
						$("#logo_tip").text(result.errorInfo).show();
					}

				},
				error : function(data) {
					$("#logo_tip").html("上传失败");
				}
			};
			$("#uploadImgForm").ajaxSubmit(options);
			return false;
		}
		function selectCity(obj) {
			jQuery.get('/base/selectCity', {
				proId : obj.value,
				random : Math.random()
			}, function(result) {
				$("#citys").html(result);
				if ($("#c_id")[0]) {
					selectTown($("#c_id").val());
					$("#towns").show();
				} else {
					$("#towns").hide();
				}
			});
		}
		function selectTown(id) {
			jQuery.get('/base/selectTown', {
				cityId : id,
				random : Math.random()
			}, function(result) {
				$("#towns").html(result);
			});
		}
		function addRawAct() {
			var rawActId = $("#id").val();
			var name = $("#name").val();
			var detail = editor.html();
			var logo = $("#logo").val();
			var category_ids = $("#category_ids").val();
			var address = $("#address").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var province = $("#province").val();
			var city = $("#city").val();
			var createUid = $("#createUid").val();
			var town = $("#town").val();
			//判断是否有town
			if ($("#towns").css("display") == "none") {
				town = "-1";
			}
			if (!checkValLength(name, 2, 20)) {
				alert("项目名控制在1－10个中文内！");
				return;
			}
			var detailCount = editor.text();
			var detail_length = getByteLen(detailCount);
			if (detail_length > 8000) {
				alert("详细信息不能超过4000个中文当前" + (detail_length / 2) + "字");
				return;
			}
			if (logo == null || logo == "") {
				alert("项目图片不能为空");
				return;
			}
			if (!checkValLength(address, 0, 60)) {
				alert("详细地址必须少于30个字！");
				return;
			}
			jQuery.ajax({
				url : "/cms/ajax/agreeRawAct",
				type : "post",
				data : {
					"rawActId" : rawActId,
					"name" : name,
					"detail" : detail,
					"logo" : logo,
					"categoryId" : category_ids,
					"address" : address,
					"startTime" : startTime,
					"endTime" : endTime,
					"town" : town,
					"city" : city,
					"province" : province,
					"createUid" : createUid
				},
				dataType : "json",
				success : function(result) {
					if (result.success) {
						location.href = "/cms/showRawActs";
					} else {
						alert(result.errorInfo);
					}
				},
				statusCode : {
					401 : function() {
						alert("请先登陆");
					}
				}
			});
		}
	</script>
</body>
</html>