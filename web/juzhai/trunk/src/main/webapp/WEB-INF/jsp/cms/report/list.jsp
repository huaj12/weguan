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
	function delete_report(id){
		if(confirm("是否删除该条举报")){
			jQuery.ajax({
				url : "/cmsreport/delete",
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
	function handle_report(id){
			jQuery.ajax({
				url : "/cms/report/handle",
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
	function un_shield_report(id,uid){
		jQuery.ajax({
			url : "/cms/report/unshield",
			type : "post",
			data : {
				"id" : id,
				"uid":uid
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
	
	function shield_report(id,uid,level){
		if(confirm("是否确定屏蔽该用户")){
		jQuery.ajax({
			url : "/cms/report/shield/",
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
	<c:choose><c:when test="${type!=1}"><a href="/cms/report/show?type=1">已处理</a></c:when><c:otherwise>已处理</c:otherwise>	</c:choose><br/>
		<c:choose><c:when test="${type!=2}"><a href="/cms/report/show?type=2">已屏蔽</a></c:when><c:otherwise>已屏蔽</c:otherwise>	</c:choose><br/>
	</h2>
		<table border="0" id="DynaTable" cellspacing="4">
			<tr style="background-color: #CCCCCC;">
				<td width="100">被举报人</td>
				<td width="200">举报内容</td>
				<td width="250">举报类型</td>
				<td width="250">操作</td>
				<td width="250">举报时间</td>
				<td width="250">举报者</td>
				<c:forEach items="${views }" var="view">
				<tr>
					<td>${view.reportProfile.nickname}</td>
					<td><a href="${view.report.contentUrl}" target="_blank">点击查看内容</a></td>
					<td><c:import url="/WEB-INF/jsp/web/common/fragment/report_type.jsp">
					<c:param name="reportType" value="${view.report.reportType}"/>
				</c:import></td>
					<td>
						<c:choose>
							<c:when test="${type==0}">
							<a href="#" onclick="shield_report('${view.report.id}','${view.report.reportUid}','1')">屏蔽1天</a>
							<a href="#" onclick="shield_report('${view.report.id}','${view.report.reportUid}','2')">屏蔽7天</a>
							<a href="#" onclick="shield_report('${view.report.id}','${view.report.reportUid}','3')">永久屏蔽</a>
							<a href="#" onclick="handle_report('${view.report.id}')">忽略</a>
							</c:when>
							<c:when test="${type==1 }">
							<a onclick="delete_report('${view.report.id}')" href="#">删除</a>
							<a href="#" onclick="shield_report('${view.report.id}','${view.report.reportUid}','1')">屏蔽1天</a>
							<a href="#" onclick="shield_report('${view.report.id}','${view.report.reportUid}','2')">屏蔽7天</a>
							<a href="#" onclick="shield_report('${view.report.id}','${view.report.reportUid}','3')">永久屏蔽</a>
							</c:when>
							<c:when test="${type==2 }">
							<a onclick="un_shield_report('${view.report.id}','${view.report.reportUid}')" href="#">解禁</a></c:when>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${view.report.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${view.createProfile.nickname}</td>
				</tr>
				</c:forEach>
			</tr>
			<tr>
			<td colspan="6">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/report?type=${type}&pageId=${pageId}">${pageId}</a>
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