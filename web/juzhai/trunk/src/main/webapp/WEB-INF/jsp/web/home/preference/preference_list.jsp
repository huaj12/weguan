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
										<p>${view.preference.name}</p>
										<c:forEach items="${view.input.options}" var="option">
												<c:forEach items="${view.answer}" var="box"><c:if test="${box==option.value}">
													<span>${option.name}</span>
												</c:if></c:forEach> 	
										</c:forEach>
											<c:if test="${view.preference.openDescription}" >
													<em>${view.userPreference.description}</em>
											</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
				</c:otherwise>
			</c:choose>
			<div class="bjph"><a href="/profile/preference">编辑我的拒宅偏好</a></div>
		</div>