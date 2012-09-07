<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${empty searchActViewList}">
		<div class="search_none"><!--search_none begin-->
			<div class="t"></div>
			<div class="m"><!--m begin-->
				<p></p>
				<span>抱歉，没有找到与“<b><c:out value="${searchWords}" /></b>”相关的拒宅项目</span>
				<a href="/act/showAddRawAct?name=${searchWords}">添加&nbsp;<c:out value="${searchWords}" />&nbsp;到拒宅库&gt;&gt;</a>
			</div><!--m end-->
			<div class="t"></div>
		</div><!--search_none end-->
	</c:when>
	<c:otherwise>
		<div class="cqw_item_list"><!--cqw_item_list begin-->
			<c:forEach var="searchActView" items="${searchActViewList}">
				<div class="cqw_item mouseHover"><!--cqw_item begin-->
					<div class="more"><a href="/act/${searchActView.act.id}" title="点击进入"></a></div>
					<div class="photo">
						<a href="/act/${searchActView.act.id}"><img data-original="${jzr:actLogo(searchActView.act.id,searchActView.act.logo,180)}" src="${jzr:static('/images/web/1px.gif')}" width="180" height="180" /></a>
					</div>
					<div class="dh_infor"><!--dh_infor begin-->
						<h2>
							<a href="/act/${searchActView.act.id}"><c:out value="${searchActView.act.name}" /></a>
						</h2>
						<p>
							${jzu:truncate(searchActView.act.intro,60,'...')}<a href="/act/${searchActView.act.id}">详情</a>
						</p>
						<c:if test="${searchActView.act.startTime != null || searchActView.act.endTime != null}">
							<h3 class="time">
								<strong>时间:</strong>
								<c:choose>
									<c:when test="${searchActView.act.startTime==null}"><fmt:formatDate value="${searchActView.act.endTime}" pattern="yyyy.MM.dd"/>(截止)</c:when>
									<c:when test="${searchActView.act.endTime==null}"><fmt:formatDate value="${searchActView.act.startTime}" pattern="yyyy.MM.dd"/>(开始)</c:when>
									<c:otherwise><fmt:formatDate value="${searchActView.act.startTime}" pattern="yyyy.MM.dd"/>--<fmt:formatDate value="${searchActView.act.endTime}" pattern="yyyy.MM.dd"/></c:otherwise>
								</c:choose>
							</h3>
						</c:if>
						<c:if test="${not empty searchActView.act.address}">
							<h3 class="place"><strong>地点:</strong>${jzd:cityName(searchActView.act.city)}${jzd:townName(searchActView.act.town)}${searchActView.act.address}</h3>
						</c:if>
						<c:if test="${searchActView.act.minCharge > 0 || searchActView.act.maxCharge > 0}">
							<h3 class="cost"><strong>费用:</strong>人均<c:choose><c:when test="${searchActView.act.minCharge==searchActView.act.maxCharge}">${searchActView.act.minCharge}</c:when><c:otherwise>${searchActView.act.minCharge}--${searchActView.act.maxCharge}</c:otherwise></c:choose>元</h3>
						</c:if>
						<div></div>
						<span>共<a href="/act/${searchActView.act.id}">${searchActView.act.popularity}</a>人想去</span>
						<div class="clear"></div>
						<div id="want${searchActView.act.id}" class="btn_area" <c:if test="${searchActView.hasUsed}">style="display:none;"</c:if>>
							<a href="javascript:void(0);" actid="${searchActView.act.id}">我想去</a>
						</div>
						<div id="dwant${searchActView.act.id}" class="delete_add" <c:if test="${!searchActView.hasUsed}">style="display:none;"</c:if>>
							<strong>已添加</strong><a href="javascript:void(0);" actid="${searchActView.act.id}" actname="${searchActView.act.name}"></a>
						</div>
					</div><!--dh_infor end-->
				</div><!--cqw_item end-->
			</c:forEach>
		</div><!--cqw_item_list end-->
		<div class="clear"></div>
		<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
			<c:param name="pager" value="${pager}"/>
			<c:param name="url" value="/searchActs" />
			<c:param name="queryParams" value="?searchWords=${searchWords}" />
		</c:import>
	</c:otherwise>
</c:choose>