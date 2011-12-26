<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="cqw_item_list"><!--cqw_item_list begin-->
	<c:forEach var="categoryActView" items="${categoryActViewList}">
		<div class="cqw_item mouseHover"><!--cqw_item begin-->
			<div class="photo">
				<a href="/act/${categoryActView.act.id}"><img src="${jzr:actLogo(categoryActView.act.id,categoryActView.act.logo,180)}" /></a>
			</div>
			<div class="dh_infor"><!--dh_infor begin-->
				<h2>
					<a href="/act/${categoryActView.act.id}"><c:out value="${categoryActView.act.name}" /></a>
				</h2>
				<p>
					${jzu:truncate(categoryActView.act.intro,60,'...')}<a href="/act/${categoryActView.act.id}">详情</a>
				</p>
				<c:if test="${categoryActView.act.startTime != null || categoryActView.act.endTime != null}">
					<h3 class="time">
						<strong>时间:</strong>
						<c:choose>
							<c:when test="${categoryActView.act.startTime==null}"><fmt:formatDate value="${categoryActView.act.endTime}" pattern="yyyy.MM.dd"/>(截止)</c:when>
							<c:when test="${categoryActView.act.endTime==null}"><fmt:formatDate value="${categoryActView.act.startTime}" pattern="yyyy.MM.dd"/>(开始)</c:when>
							<c:otherwise><fmt:formatDate value="${categoryActView.act.startTime}" pattern="yyyy.MM.dd"/>--<fmt:formatDate value="${categoryActView.act.endTime}" pattern="yyyy.MM.dd"/></c:otherwise>
						</c:choose>
					</h3>
				</c:if>
				<c:if test="${categoryActView.act.address != null && categoryActView.act.address != ''}">
					<h3 class="place"><strong>地点:</strong>${categoryActView.act.address}</h3>
				</c:if>
				<c:if test="${categoryActView.act.minCharge > 0 || categoryActView.act.maxCharge > 0}">
					<h3 class="cost"><strong>费用:</strong>人均<c:choose><c:when test="${categoryActView.act.minCharge==categoryActView.act.maxCharge}">${categoryActView.act.minCharge}</c:when><c:otherwise>${categoryActView.act.minCharge}--${categoryActView.act.maxCharge}</c:otherwise></c:choose>元</h3>
				</c:if>
				<div></div>
				<span>共<a href="/act/${categoryActView.act.id}">${categoryActView.act.popularity}</a>人想去</span>
				<div class="clear"></div>
				<c:if test="${context.uid > 0}">
					<div id="want${categoryActView.act.id}" class="btn_area" <c:if test="${categoryActView.hasUsed}">style="display:none;"</c:if>>
						<a href="javascript:void(0);" actid="${categoryActView.act.id}">我想去</a>
					</div>
					<div id="dwant${categoryActView.act.id}" class="delete_add" <c:if test="${!categoryActView.hasUsed}">style="display:none;"</c:if>>
						<strong>已添加</strong><a href="javascript:void(0);" actid="${categoryActView.act.id}" actname="${categoryActView.act.name}"></a>
					</div>
				</c:if>
			</div><!--dh_infor end-->
		</div><!--cqw_item end-->
	</c:forEach>
</div><!--cqw_item_list end-->
<div class="clear"></div>
<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
	<c:param name="pager" value="${pager}"/>
	<c:param name="url" value="/showActs/${orderType}/${categoryId}" />
</c:import>