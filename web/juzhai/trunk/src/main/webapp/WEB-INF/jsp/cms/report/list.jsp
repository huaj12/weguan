<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>举报管理</title>
<script type="text/javascript"
	src="${jz:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
	<script>
	function deleteReport(id){
		if(confirm("是否删除该条举报")){
			jQuery.ajax({
				url : "/cms/report/delete",
				type : "post",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						location.reload();
					} else {
						alert("删除失败");
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
	function ignoreReport(id){
		if(confirm("是否忽略该举报")){
			jQuery.ajax({
				url : "/cms/report/ignore",
				type : "post",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						location.reload();
					} else {
						alert("处理失败");
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
	
	function shieldReport(id,uid,level){
		if(confirm("是否确定屏蔽该用户")){
		jQuery.ajax({
			url : "/cms/report/shield",
			type : "post",
			data : {
				"id" : id,
				"uid":uid,
				"level":level
			},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					location.reload();
				} else {
					alert("处理失败");
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
	
	
	</script>
</head>
<body>
	<h2>
	<c:choose><c:when test="${type!=0}"><a href="/cms/report/show?type=0">待审核</a></c:when><c:otherwise>待审核</c:otherwise>	</c:choose><br/>
	<c:choose><c:when test="${type!=1}"><a href="/cms/report/show?type=1">已忽略</a></c:when><c:otherwise>已忽略</c:otherwise>	</c:choose><br/>
	<c:choose><c:when test="${type!=2}"><a href="/cms/report/show?type=2">已处理</a></c:when><c:otherwise>已处理</c:otherwise>	</c:choose><br/>
	<c:choose><c:when test="${type!=3}"><a href="/cms/report/show?type=3">自动处理</a></c:when><c:otherwise>自动处理</c:otherwise>	</c:choose><br/>
	<c:choose><c:when test="${type!=4}"><a href="/cms/report/show?type=4">防广告自动处理</a></c:when><c:otherwise>防广告自动处理</c:otherwise>	</c:choose><br/>
	</h2>
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">被举报人</td>
				<td width="200">举报内容</td>
				<td width="400">用户描述</td>
				<td width="250">举报类型</td>
				<td width="250">操作</td>
				<td width="250">举报时间</td>
				<td width="250">举报者</td>
				<c:forEach items="${views }" var="view">
				<tr>
					<td><a href="/home/${view.reportProfile.uid}" target="_blank"><c:out value="${view.reportProfile.nickname}"></c:out></a></td>
					<td><a href="${view.report.contentUrl}" target="_blank">点击查看内容</a></td>
					<td><c:out value="${view.report.description}"></c:out> </td>
					<td><c:import url="/WEB-INF/jsp/web/common/fragment/report_type.jsp">
					<c:param name="reportType" value="${view.report.reportType}"/>
				</c:import></td>
					<td>
						<c:choose>
							<c:when test="${type==0}">
							<a href="#" onclick="shieldReport('${view.report.id}','${view.report.reportUid}','1')">屏蔽7天</a>
							<a href="#" onclick="shieldReport('${view.report.id}','${view.report.reportUid}','2')">屏蔽30天</a>
							<a href="#" onclick="shieldReport('${view.report.id}','${view.report.reportUid}','3')">永久屏蔽</a>
							<a href="#" onclick="ignoreReport('${view.report.id}')">忽略</a>
							</c:when>
							<c:when test="${type==1 }">
							<a href="#" onclick="shieldReport('${view.report.id}','${view.report.reportUid}','1')">屏蔽7天</a>
							<a href="#" onclick="shieldReport('${view.report.id}','${view.report.reportUid}','2')">屏蔽30天</a>
							<a href="#" onclick="shieldReport('${view.report.id}','${view.report.reportUid}','3')">永久屏蔽</a>
							</c:when>
						</c:choose>
						<a onclick="deleteReport('${view.report.id}')" href="#">删除</a>
					</td>
					<td><fmt:formatDate value="${view.report.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><a href="/home/${view.createProfile.uid}" target="_blank">${view.createProfile.nickname}</a></td>
				</tr>
				</c:forEach>
			</tr>
			<tr>
			<td colspan="6">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/report/show?type=${type}&pageId=${pageId}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
		</table>
</body>
</html>