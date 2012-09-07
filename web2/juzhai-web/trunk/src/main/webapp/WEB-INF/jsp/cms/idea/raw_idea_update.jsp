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
	<h2>审核好主意-<a></a></h2>
	<h3><font color="red">${msg}</font></h3>
	<form action="/cms/update/rawIdea" id="updateRawIdeaForm" method="post" >
		<input type="hidden" value="${rawIdeaForm.createUid }" name="createUid"/>
		<table>
			<tr>
				<td>
				所在地：
				</td>
				<td>
				<c:import url="/WEB-INF/jsp/web/common/widget/location.jsp">
				<c:param name="provinceId" value="${rawIdeaForm.province}"/>
				<c:param name="cityId" value="${rawIdeaForm.city}"/>
				<c:param name="townId" value="${rawIdeaForm.town}"/>
				</c:import>
				</td>
			</tr>
			<tr>
				<td>类别</td>
				<td>
				<select name="categoryId">
				<option <c:if test="${ideaForm.categoryId==0}"> selected="selected"</c:if> value="0">不限</option>
				<c:forEach items="${categoryList}" var="cat">
					<option <c:if test="${cat.id==rawIdeaForm.categoryId}"> selected="selected"</c:if> value="${cat.id}">${cat.name}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>
				内容：
				</td>
				<td>
					<input name="content" value="${rawIdeaForm.content}" />
				</td>
			</tr>
			<tr>
				<td>
				日期:
				</td>
				<td>
				<input type="text" name="startDateString" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" value="${rawIdeaForm.startDateString}" />-
				<input type="text" name="endDateString" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" value="${rawIdeaForm.endDateString}" />
				</td>
			</tr>
			<tr>
				<td>
				地点:
				</td>
				<td><input type="text" name="place"  value="${rawIdeaForm.place}" /></td>
			</tr>
			<tr>
				<td>
				详情链接
				</td>
				<td>
					<input type="text" name="link" value="${rawIdeaForm.link}" />
				</td>
			</tr>
			<tr>
			<td>
				费用
			</td>
			<td>
				<input type="text" name="charge" value="${rawIdeaForm.charge}" />
			</td>
			</tr>
			<tr>
			<td>详情</td>
			<td><textarea name="detail" style="width: 700px; height: 200px;" >${rawIdeaForm.detail}</textarea></td>
			</tr>
			<tr>
				<td>
				<input name="pic"  type="hidden" value="${rawIdeaForm.pic}" />
				<input name="id" type="hidden" value="${rawIdeaForm.id}" />
				</td>
			</tr>
		</table>
	</form>
	<table>
			<tr>
				<td>
					封面
				</td>
				<td>
				<img src="${jzr:ideaTempLogo(rawIdeaForm.pic)}" id="raw_idea_pic" width="180" height="180"></img>
				<form id="uploadImgForm" method="post" enctype="multipart/form-data">
												重新上传：<input  size=6 type="file" onchange="uploadImage();" name="rawIdeaLogo" />  
				</form>
				</td>
			</tr>
			<tr>
				<td><a onclick="pass();" href="javascript:void(0);">修改并通过</a></td><td><a href="javascript:void(0);" onclick="del('${rawIdeaForm.id}');">拒绝</a></td>
			</tr>
	</table>
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
		function pass(){
			$("textarea[name='detail']").val(editor.html());
			$("#updateRawIdeaForm").submit();
		}
		function uploadImage() {
			var options = {
				url : "/idea/logo/upload",
				type : "POST",
				dataType : "json",
				iframe : "true",
				success : function(result) {
					if (result.success) {
						$("#raw_idea_pic").attr("src", result.result[0]);
						$("input[name=pic]").val(result.result[1]);
					} else if (result.errorCode == "00003") {
						alert("请登录");
					} else {
						alert(result.errorInfo);
					}
				},
				error : function(data) {
					alert("上传失败");
				}
			};
			$("#uploadImgForm").ajaxSubmit(options);
			return false;
		}
		function del(id){
			jQuery.ajax({
				url : "/cms/del/rawIdea",
				type : "post",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						alert("删除成功");
						window.location.href = "/cms/list/rawIdea";
					} else {
						alert("操作失败刷新后重试");
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
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
		<script>new LocationWidget();</script>
</body>
</html>