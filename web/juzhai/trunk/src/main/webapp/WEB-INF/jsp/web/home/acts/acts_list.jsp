<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="item_box"><!--item_box begin-->
	<div class="iwantgo"><!--iwantgo begin-->
		<c:forEach var="userActView" items="${userActViewList}">
			<div class="item mouseHover"><!--item begin-->
				<%-- <div class="show_box">
					<h2>您已有3个很想去的项目</h2>
					<p>请先取消1个。</p>
					<div class="btn"><a href="#" class="ok">知道了</a></div>
				</div>
				<div class="show_box">
					<h2>确定不再想去么？</h2>
					<p>你将不再收到相关邀请。</p>
					<div class="btn"><a href="#" class="ok">确定</a><a href="#" class="cancel">取消</a></div>
				</div> --%>
				<div class="close"><a href="#"></a></div>
				<%-- <div class="add_fav"><div class="click"><a href="#">很想去</a></div></div> --%>
				<div class="photo"><img src="${jzr:actLogo(userActView.act.id,userActView.act.logo,180)}" /></div>
				<div class="dh_infor"><!--dh_infor begin-->
					<h2><c:out value="${userActView.act.name}" /></h2>
					<p>${jzu:truncate(userActView.act.intro,60,'...')}<a href="#">详情</a></p>
					<c:if test="${userActView.act.startTime != null || userActView.act.endTime != null}">
						<h3 class="time">
							<strong>时间:</strong>
							<c:choose>
								<c:when test="${userActView.act.startTime==null}"><fmt:formatDate value="${userActView.act.endTime}" pattern="yyyy.MM.dd"/>(截止)</c:when>
								<c:when test="${userActView.act.endTime==null}"><fmt:formatDate value="${userActView.act.startTime}" pattern="yyyy.MM.dd"/>(开始)</c:when>
								<c:otherwise><fmt:formatDate value="${userActView.act.startTime}" pattern="yyyy.MM.dd"/>--<fmt:formatDate value="${userActView.act.endTime}" pattern="yyyy.MM.dd"/></c:otherwise>
							</c:choose>
						</h3>
					</c:if>
					<c:if test="${userActView.act.address != null && userActView.act.address != ''}">
						<h3 class="place"><strong>地点:</strong>${userActView.act.address}</h3>
					</c:if>
					<c:if test="${userActView.act.minCharge > 0 || userActView.act.maxCharge > 0}">
						<h3 class="cost"><strong>费用:</strong>人均<c:choose><c:when test="${userActView.act.minCharge==userActView.act.maxCharge}">${userActView.act.minCharge}</c:when><c:otherwise>${userActView.act.minCharge}--${userActView.act.maxCharge}</c:otherwise></c:choose>元</h3>
					</c:if>
					<span>共<b>${userActView.act.popularity}</b>人想去</span>
					<div class="clear"></div>
				</div><!--dh_infor end-->
			</div><!--item end-->
		</c:forEach>
	</div><!--iwantgo end-->
</div><!--item_box end-->
<div class="clear"></div>
<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
	<c:param name="pager" value="${pager}"/>
	<c:param name="url" value="/home/myActs" />
</c:import>