<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="title"><!--title begin-->
	<h2 class="<c:choose><c:when test='${loginUser.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>">
		<span><c:choose><c:when test="${loginUser!=null}"><c:out value="${loginUser.nickname}" /></c:when><c:otherwise>小宅</c:otherwise></c:choose></span>，最近想找个伴儿么？
	</h2>
	<c:if test="${inviteUserList==null}">
		<div class="select" id="genderSelect" requesturi="${userType}"><!--select begin-->
			<span>
				<select id="language">
					<option value="all" <c:if test="${genderType=='all'}">selected="selected"</c:if>>所有</option>
					<option value="male" <c:if test="${genderType=='male'}">selected="selected"</c:if>>男性</option>
					<option value="female" <c:if test="${genderType=='female'}">selected="selected"</c:if>>女性</option>
				</select>
			</span>
		</div><!--select end-->
	</c:if>
	<div class="title_right"><!--title_right begin-->
		<a href="/showusers" <c:if test="${userType=='showusers'}">class="active"</c:if>>有缘的人</a>
		<a href="/showFollows" <c:if test="${userType=='showFollows'}">class="active"</c:if>>你认识的人</a>
	</div><!--title_right end-->
</div><!--title end-->