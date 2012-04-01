<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="jz_ph" style="margin-top:5px;"><!--jz_ph begin-->
			<c:choose>
				<c:when test="${empty preferenceListviews}">
						<div class="none">还没有填写拒宅偏好哦</div>
				</c:when>
				<c:otherwise>
						<ul>
							<c:forEach items="${preferenceListviews}" var="view">
								<c:if test="${!view.userPreference.open||not empty isMe}">
									<li>
										<p><c:out value="${view.preference.name}"/></p>
										<c:choose>
										<c:when test="${view.input.inputType==1||view.input.inputType==0}">
											<c:forEach items="${view.input.options}" var="option">
													<c:forEach items="${view.answer}" var="box"><c:if test="${box==option.value}">
														<span><c:out value="${option.name}"/></span>
													</c:if></c:forEach> 	
											</c:forEach>
										</c:when>
										<c:when test="${view.input.inputType==2}">
											<span><c:out value="${view.answer[0]}"/></span>
											<span><c:out value="${view.answer[1]}"/></span>
										</c:when>
										<c:when test="${view.input.inputType==3}">
											<span><c:out value="${view.answer[0]}"/></span>
										</c:when>
										</c:choose>
											<c:if test="${view.preference.openDescription && not empty view.userPreference.description && view.userPreference.description!=''}" >
													<em>补充说明：<c:out value="${view.userPreference.description}"/></em>
											</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
				</c:otherwise>
			</c:choose>
			<c:if test="${not empty isMe}">
				<div class="bjph"><a href="/profile/preference">编辑我的拒宅偏好</a></div>
			</c:if>
		</div>