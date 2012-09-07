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
<title>机器人管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function del(uid){
	var cityId=$("select[name=cityId]").val();
	if(confirm("是否将该用户移出机器人库")){
		jQuery.ajax({
			url : "/cms/robot/del",
			type : "post",
			data : {
				"cityId" : cityId,
				"uid" : uid
			},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#robot-" + uid).removeAttr("onclick").text("已删除");
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
}
function add(){
	var cityId=$("select[name=cityId]").val();
	var uid=$("#uid").val();
	jQuery.ajax({
		url : "/cms/robot/add",
		type : "post",
		data : {
			"cityId" : cityId,
			"uid" : uid
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
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
</head>
<body>
<h2>机器人管理<font color="red">${msg}</font></h2>
<form  method="post"  enctype="multipart/form-data" action="/cms/robot/import">
<input type="file" name="robotConfig"/>
<input type="submit" value="上传"/>
</form>
<br/>
<form action="/cms/robot/show" method="get">查询：
				<c:import url="/WEB-INF/jsp/web/common/widget/location.jsp">
				<c:param name="provinceId" value="${provinceId}"/>
				<c:param name="cityId" value="${cityId}"/>
				<c:param name="townId" value=""/>
				</c:import>
下的机器人<input type="submit" value="查询" /></form>
<br/>
<input type="text" id="uid"  /><a href="javascript:void(0);" onclick="add();">添加机器人</a>
<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">邮箱</td>
			<td width="100">昵称</td>
			<td width="100">地点</td>
			<td width="40">性别</td>
			<td width="100">logo</td>
			<td width="100">操作</td>
		</tr>
		<c:forEach items="${profileList}" var="profile">
			<tr>
				<td width="100"><c:out value="${profile.email}" /></td>
				<td width="100"><a href="/home/${profile.uid}" target="_blank"><c:out value="${profile.nickname}" /></a></td>
				<td width="100"><c:out value="${jzd:cityName(profile.city)}" /></td>
				<td width="40"><c:choose><c:when test="${profile.gender == 1}">男</c:when><c:otherwise>女</c:otherwise></c:choose></td>
				<td width="100"><img src="${jzr:userLogo(profile.uid, profile.newLogoPic, 80)}" width="50" height="50"/></td>
				<td width="100"><a href="javascript:void(0);" id="robot-${profile.uid}" onclick="del('${profile.uid}');">删除</a></td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />			
	<script>new LocationWidget();</script>
</body>
</html>