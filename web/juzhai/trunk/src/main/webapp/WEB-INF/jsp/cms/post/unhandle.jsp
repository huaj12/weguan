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
<title>拒宅内容管理</title>
<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
<script>
function del(id){
	if(confirm("是否删除该条拒宅")){
			jQuery.ajax({
				url : "/cms/post/unhandle/del",
				type : "get",
				data : {
					"postId" : id
				},
				dataType : "json",
				success : function(result) {
					if (result.success!=null&&result.success) {
						$("#post-del-"+id).text("已删除").removeAttr("onclick");
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
function shield(id){
	if(confirm("是否屏蔽该条拒宅")){
		jQuery.ajax({
			url : "/cms/post/shield",
			type : "get",
			data : {
				"postId" : id
			},
			dataType : "json",
			success : function(result) {
				if (result.success!=null&&result.success) {
					$("#post-shield-"+id).text("已屏蔽").removeAttr("onclick");
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

function handle(){
	var ids="";
	$('input[name=postIds]').each(function(){
		 ids=ids+this.value+",";
	});
	jQuery.ajax({
		url : "/cms/post/handle",
		type : "get",
		data : {
			"postIds" : ids
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				location.reload();
			} else {
				alert("标记错误请重试");
			}
		},
		statusCode : {
			401 : function() {
				alert("请先登陆");
			}
		}
	});
}

function handleById(id){
	jQuery.ajax({
		url : "/cms/post/handle",
		type : "get",
		data : {
			"postIds" : id
		},
		dataType : "json",
		success : function(result) {
			if (result.success!=null&&result.success) {
				$("#post-handle-"+id).text("已处理").removeAttr("onclick");
			} else {
				alert("标记错误请重试");
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
	<h2>未处理内容</h2><a href="javascript:;" onclick="handle();">将本页标记为已处理</a>
	<form action="/cms/show/post/unhandle" method="get">
	<select name="city">
				<option value="0">全国</option>
				<c:forEach var="specialCity" items="${jzd:specialCityList()}">
					<option value="${specialCity.id}" <c:if test="${city==specialCity.id}">selected="selected"</c:if>>${specialCity.name}</option>
		</c:forEach>
	</select>
	<select name="isIdea">
		<option <c:if test="${empty isIdea||isIdea==null}">selected="selected"</c:if> value="">不限</option>
		<option <c:if test="${not empty isIdea&&isIdea}">selected="selected"</c:if> value="true">好主意</option>
		<option <c:if test="${not empty isIdea&&!isIdea}">selected="selected"</c:if> value="false">拒宅</option>
	</select>
	<input type="submit" value="查询" />
	</form>
	<table border="0" cellspacing="4">
		<tr style="background-color: #CCCCCC;">
			<td width="100">用户头像</td>
			<td width="300">我想去</td>
			<td width="100">所在城市</td>
			<td width="100">互动</td>
			<td width="100">操作</td>
			<td width="100">发起人</td>
			<td width="100">地点</td>
			<td width="100">图片</td>
			<td width="100">拒宅时间</td>
			<td width="100">发布时间</td>
			
		</tr>
		<c:forEach var="view" items="${postView}" >
			<tr>
			<td><a href="/home/${view.profileCache.uid}" target="_blank"><img src="${jzr:userLogo(view.profileCache.uid,view.profileCache.logoPic,120)}" width="80" height="80"/></a></td>
				<td><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>: <c:out value="${view.post.content}"></c:out> </td>
				<td>${jzd:cityName(view.profileCache.city)}</td>
				<td>
				<a href="javascript:void(0);" id="robot-response-${view.post.id}" class="robot-response"  city-id="${view.profileCache.city}" post-id="${view.post.id}">响应</a><br/>
				<a href="javascript:void(0);" id="robot-comment-${view.post.id}" class="robot-comment"   city-id="${view.profileCache.city}" post-id="${view.post.id}" >留言</a><br/>
				<a href="javascript:void(0);" id="robot-interest-${view.post.id}" class="robot-interest"  city-id="${view.profileCache.city}" post-id="${view.post.id}" target-uid="${view.profileCache.uid}">关注</a><br/>
				</td>
				<td>
				<a href="javascript:;" id="post-handle-${view.post.id}" onclick="handleById('${view.post.id}')">通过</a></br>
				<a href="javascript:;" id="post-del-${view.post.id}" onclick="del('${view.post.id}')">删除</a></br>
				<a href="javascript:;" id="post-shield-${view.post.id}" onclick="shield('${view.post.id}')">屏蔽</a></br>
				<c:if test="${view.post.ideaId==0}"><a href="/cms/show/idea/add?postId=${view.post.id}" >好注意</a></c:if>
				<input type="hidden" value="${view.post.id}" name="postIds"/>
				</td>
				<td><c:out value="${view.profileCache.nickname}"></c:out></td>
				<td><c:out value="${view.post.place}"></c:out></td>
				<td><img src="${jzr:postPic(view.post.id, view.post.ideaId, view.post.pic, 200)}"  /> </td>
				<td><fmt:formatDate value="${view.post.dateTime}"
						pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${view.post.createTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:forEach var="pageId" items="${pager.showPages}">
					<c:choose>
						<c:when test="${pageId!=pager.currentPage}">
							<a href="/cms/show/post/unhandle?pageId=${pageId}&city=${city}">${pageId}</a>
						</c:when>
						<c:otherwise>
							<strong>${pageId}</strong>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="${jzr:static('/js/cms/common/base.js')}"></script>
</body>
</html>