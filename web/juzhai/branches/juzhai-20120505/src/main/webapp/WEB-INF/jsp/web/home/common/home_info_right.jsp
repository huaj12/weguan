<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content_box w285"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="right_title">
			<c:choose>
				<c:when test="${context.uid != profile.uid}">
					<h2>ta的档案</h2>
					<a id="inviteEditProfile" uid="${profile.uid}" href="javascript:void(0);">邀请ta完善档案</a>
				</c:when>
				<c:otherwise>
					<h2>我的档案</h2>
					<a href="/profile/index">编辑</a>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="about_me"><!--about_me begin-->
			<c:set var="age" value="${jzu:age(profile.birthYear,profile.birthSecret)}" />
			<p>年龄：<c:choose><c:when test="${age > 0}"><span>${age}岁</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<c:set var="constellationName" value="${jzd:constellationName(profile.constellationId)}" />
			<p>星座：<c:choose><c:when test="${not empty constellationName}"><span>${constellationName}</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>职业：<c:choose><c:when test="${not empty profile.profession}"><span>${profile.profession}</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>身高：<c:choose><c:when test="${profile.height!=null&&profile.height>0}"><span>${profile.height}cm</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>血型：<c:choose><c:when test="${not empty profile.bloodType}"><span>${profile.bloodType}型</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>学历：<c:choose><c:when test="${not empty profile.education}"><span>${profile.education}</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<c:if test="${profile.minMonthlyIncome>0||profile.maxMonthlyIncome>0}">
				<c:choose>
					<c:when test="${profile.minMonthlyIncome==0}"><c:set var="income" value="小于${profile.maxMonthlyIncome}" /></c:when>
					<c:when test="${profile.maxMonthlyIncome==0}"><c:set var="income" value="大于${profile.minMonthlyIncome}" /></c:when>
					<c:otherwise><c:set var="income" value="${profile.minMonthlyIncome}-${profile.maxMonthlyIncome}" /></c:otherwise>
				</c:choose>
			</c:if>
			<p>收入：<c:choose><c:when test="${not empty income}"><span>${income}元/月</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>住所：<c:choose><c:when test="${not empty profile.house}"><span>${profile.house}</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>购车：<c:choose><c:when test="${not empty profile.car}"><span>${profile.car}</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
			<p>家乡：<c:choose><c:when test="${not empty profile.home}"><span>${profile.home}</span></c:when><c:otherwise><span class="unknow">未填</span></c:otherwise></c:choose></p>
		</div><!--about_me end-->
	</div>
	<div class="t"></div>
</div><!--content end-->