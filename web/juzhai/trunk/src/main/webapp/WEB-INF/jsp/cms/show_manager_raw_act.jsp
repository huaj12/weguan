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
<script type="text/javascript">
	function removeRawAct(id){
		jQuery.ajax({
			url: "/cms/ajax/delRawAct",
			type: "post",
			data: {"id":id},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					location.href="/cms/showRawActs";
				}else{
					alert(result.errorInfo);
				}
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			}
		});
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<h2>审核推荐项目列表</h2>
	<table border="0">
		<tr>
			<td>项目名</td>
			<td><input id="name" type="text"  value="${rawAct.name}"/></td>
		</tr>
		<tr>
			<td>详细信息</td>
			<td><textarea id="detail" name="detail" 
					style="width: 700px; height: 200px; visibility: hidden;" cols=""
					rows="">${rawAct.detail}</textarea></td>
		</tr>
		<tr>
			<td>
				上传图片：
			</td>
			<td><form id="uploadImgForm" method="post"
					enctype="multipart/form-data">
					<input type="file" onchange="uploadImage();" name="fileupload" />
				</form><img id="logo" src="${rawAct.logo}" /><div id="logo_tip"></div></td>
		</tr>
		<tr>
			<td>分类</td>
			<td><select name="category_ids" id="category_ids">
					<c:forEach var="cats" items="${categoryList}">
						
						<option value="${cats.id}" <c:if test="${rawAct.categoryIds==cats.id}" >selected="selected" </c:if> >${cats.name }</option>
					</c:forEach>
			</select></td>
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
											</select> </span> <span id="citys" > <select  id="city">
													<c:forEach var="city" items="${citys}">
														<c:if test="${rawAct.province==city.provinceId}">
															<option
																<c:if test="${rawAct.city==city.id}">selected="selected"</c:if>
																value="${city.id}">${city.name}</option>
														</c:if>
													</c:forEach>
											</select> </span> 
											
											<span id="towns" <c:if test="${rawAct.town=='-1'}"> style="display: none" </c:if> ><select name="town" id="town">
													<c:forEach var="town" items="${towns}">
														<c:if test="${rawAct.town==town.cityId}">
															<option
																<c:if test="${rawAct.town==town.id}">selected="selected"</c:if>
																value="${town.id}">${town.name}</option>
														</c:if>
													</c:forEach>
											</select> </span> <input name="address" id="address" type="text" value="${rawAct.address}"  /></td>
		</tr>
		<tr>
			<td>时间</td>
			<td><input name="" readonly="readonly" value="<fmt:formatDate value="${rawAct.startTime}"
						pattern="yyyy-MM-dd" />" id="startTime"
				onclick="WdatePicker()" type="text" />到 <input id="endTime"
				readonly="readonly" onclick="WdatePicker()" name="" value="<fmt:formatDate value="${rawAct.endTime}"
						pattern="yyyy-MM-dd" />" type="text" />
						<input id="createUid" value="${rawAct.createUid}" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td><input type="button" onclick="addRawAct();" value="通过"/> </td>
			<td><input type="button" onclick="removeRawAct('${rawAct.id}')" value="删除"/> </td>
		</tr>
	</table>
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
	<script type="text/javascript"
		src="${jzr:static('/js/core/validation.js')}"></script>
	<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
	<script type="text/javascript"
		src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
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
		function uploadImage() {
			$(document).ready(
					function() {
						var options = {
							url : "/act/ajax/temp/addActImage",
							type : "POST",
							dataType : "json",
							success : function(data) {
								if (data.error==0) {
									$("#logo_tip").html("上传成功");
									$("#logo").attr("src",data.url);
								} else {
									$("#logo_tip").html(data.message);
								}
							},
							error:function(data){
								$("#logo_tip").html("上传失败");
							}
						};
						$("#uploadImgForm").ajaxSubmit(options);
						return false;
					});
		}
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
		function addRawAct(){
			var name=$("#name").val();
			var detail=editor.text();
			var logo=$("#logo").attr("src");
			var category_ids=$("#category_ids").val();
			var address=$("#address").val();
			var startTime=$("#startTime").val();
			var endTime=$("#endTime").val();
			var province=$("#province").val();
			var city=$("#city").val();
			var createUid=$("#createUid").val();
			createUid
			var town="";
			//判断是否有town
			if($("#c_id")[0]){
				town=$("#town").val();
			}
			if(!checkValLength(name, 2, 20)){
				alert("项目名控制在1－10个中文内！");
				return ;
			}
			var detail_length = getByteLen(detail);
			if(detail_length>4000){
				alert("详细信息不能超过2000个中文当前"+(detail_length/2)+"字");
				return ;
			}
			if(logo==null||logo==""){
				alert("项目图片不能为空");
				return ;
			}
			if(!checkValLength(address, 0, 60)){
				alert("详细地址必须少于30个字！");
			}
			$.post('/cms/ajax/AgreeRawAct', {
				name : name,
				detail:detail,
				logo:logo,
				categoryIds:category_ids,
				address:address,
				startTime:startTime,
				endTime:endTime,
				town:town,
				city:city,
				province:province,
				createUid:createUid,
				random : Math.random()
			}, function(result) {
				if(result.success){
					location.href="/cms/showRawActs";
				}else{
					alert(result.errorInfo);
				}
			});
		}
		</script>
</body>
</html>