<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table border="0" cellspacing="4">
	<tr style="background-color: #CCCCCC;">
		<td width="100">id</td>
		<td width="100">logo</td>
		<td width="100">简称</td>
		<td width="100">全名</td>
		<td width="100">简介</td>
		<td width="100">分类</td>
		<td width="150">地点</td>
		<td width="150">适合人群</td>
		<td width="100">适合人数</td>
		<td width="100">起始时间</td>
		<td width="100">结束时间</td>
		<td width="100">消费区间</td>
	</tr>
	<c:forEach var="cmsActMagerView" items="${cmsActMagerViews}"
		varStatus="status">
		<tr>
			<td>${cmsActMagerView.act.id}<a
				href="/cms/showUpdate?actId=${cmsActMagerView.act.id}">修改</a>
			</td>
			<td><img src="${cmsActMagerView.logoWebPath}" width="50"
				height="50" />
			</td>
			<td>${cmsActMagerView.act.name}</td>
			<td>${cmsActMagerView.act.fullName}</td>
			<td>${cmsActMagerView.act.intro}</td>
			<td>${cmsActMagerView.category}</td>
			<td>省份：${cmsActMagerView.proName}<br />
				城市：${cmsActMagerView.cityName}<br /> 详细地址：${cmsActMagerView.address}<br />
			</td>
			<td>年龄：${cmsActMagerView.age}<br /> 性别：${cmsActMagerView.gender}<br />
				群体：${cmsActMagerView.status}</td>
			<td>${cmsActMagerView.act.minRoleNum}-${cmsActMagerView.act.maxRoleNum}人</td>
			<td><fmt:formatDate value="${cmsActMagerView.act.startTime}"
					pattern="yyyy.MM.dd" /></td>
			<td><fmt:formatDate value="${cmsActMagerView.act.endTime}"
					pattern="yyyy.MM.dd" /></td>
			<td>${cmsActMagerView.act.minCharge}-${cmsActMagerView.act.maxCharge}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="12"><c:forEach var="pageId"
				items="${pager.showPages}">
				<c:choose>
					<c:when test="${pageId!=pager.currentPage}">
						<a
							onclick="actManagerPage('${pageId}','${bDate}','${eDate}','${name}')"
							href="#">${pageId}</a>
					</c:when>
					<c:otherwise>
						<strong>${pageId}</strong>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</td>
	</tr>
</table>