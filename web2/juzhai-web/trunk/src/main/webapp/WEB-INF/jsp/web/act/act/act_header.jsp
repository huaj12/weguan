<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="project_show guoqi"><!--project_show begin-->
	<div class="btn_area">
		<a href="javascript:void(0);" class="wantgo" <c:if test="${hasAct}">style="display:none;"</c:if> actid="${act.id}">我想去</a>
		<div class="cancel_add" <c:if test="${!hasAct}">style="display:none;"</c:if>><p>已添加</p><a href="javascript:void(0);" class="delete" actid="${act.id}" actname="${act.name}"></a></div>
	</div>
	<c:if test="${act.endTime != null&&jzu:dateAfter(act.endTime)}">
		<div class="gq"></div>
	</c:if>
	<div></div>
	<div class="photo"><img src="${jzr:actLogo(act.id,act.logo,180)}" /></div>
	<div class="project_infor"><!--project_infor begin-->
		<h2><a href="/act/${act.id}"><c:out value="${act.name}" /></a></h2>
		<p>${act.intro}</p>
		<c:if test="${act.startTime != null || act.endTime != null}">
			<h3 class="time">
				<strong>时间:</strong><c:choose>
					<c:when test="${act.startTime==null}"><fmt:formatDate value="${act.endTime}" pattern="yyyy.MM.dd"/>(截止)</c:when>
					<c:when test="${act.endTime==null}"><fmt:formatDate value="${act.startTime}" pattern="yyyy.MM.dd"/>(开始)</c:when>
					<c:otherwise><fmt:formatDate value="${act.startTime}" pattern="yyyy.MM.dd"/>--<fmt:formatDate value="${act.endTime}" pattern="yyyy.MM.dd"/></c:otherwise>
				</c:choose>
			</h3>
		</c:if>
		<c:if test="${act.address != null && act.address != ''}">
			<h3 class="place"><strong>地点:</strong>${jzd:cityName(act.city)}${jzd:townName(act.town)}${act.address}</h3>
		</c:if>
		<c:if test="${act.minCharge > 0 || act.maxCharge > 0}">
			<h3 class="cost"><strong>费用:</strong>人均<c:choose><c:when test="${act.minCharge==act.maxCharge}">${act.minCharge}</c:when><c:otherwise>${act.minCharge}--${act.maxCharge}</c:otherwise></c:choose>元</h3>
		</c:if>
	</div><!--project_infor end-->
</div><!--project_show end-->
<div class="project_menu"><!--project_menu begin-->
	<div class="dw">
		<a href="/act/${act.id}/detail" <c:if test="${menuType=='detail'}">class="active"</c:if>>详细介绍</a>
		<a href="/act/${act.id}/users" <c:if test="${menuType=='users'}">class="active"</c:if>>想去的人(${userActCount})</a>
	</div>
</div><!--project_menu end-->