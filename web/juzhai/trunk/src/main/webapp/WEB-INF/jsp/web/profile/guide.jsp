<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息确认</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<div class="fix_top"><!--fix_top begin-->
					<div class="top"><!--top begin-->
						<h2><a href="#"></a></h2>
						<div class="exit"><a href="/logout">退出</a></div>
					</div><!--top end-->
				</div><!--fix_top end-->
				<div class="qrxx"><!--qrxx begin-->
					<div class="qrxx_t"></div>
					<div class="qrxx_m">
						<div class="num_title">81,322人正在找伴儿出去玩</div>
						<div class="m_box"><!--m_box begin-->
							<div class="title"><h2>请先确认个人信息，我们会为您安排合适的拒宅项目）</h2></div>
							<form id="guide-form" action="/home/guide/next" method="post">
								<div class="infor"><!--infor begin-->
									<div class="infor_x"><!--infor_x begin-->
										<h3>性&nbsp;&nbsp;&nbsp;&nbsp;别：</h3>
										<div class="select"><!--select begin-->
											<span>
												<select name="gender" id="gender-select">
													<option value="" <c:if test="${empty settingForm.gender}">selected="selected"</c:if>>请选择</option>
													<option value="1" <c:if test="${settingForm.gender == 1}">selected="selected"</c:if>>男</option>
													<option value="0" <c:if test="${settingForm.gender == 0}">selected="selected"</c:if>>女</option>
												</select>
											</span>
										</div><!--select end-->
										<div id="gender-error" class="error_warning" style="display: none;"><em></em><b></b></div>
									</div><!--infor_x end-->
									<div class="infor_x"><!--infor_x begin-->
										<h3>所&nbsp;在&nbsp;地：</h3>
										<div class="select"><!--select begin-->
											<c:import url="/WEB-INF/jsp/web/common/widget/location.jsp">
												<c:param name="provinceId" value="${settingForm.province}"/>
												<c:param name="cityId" value="${settingForm.city}"/>
												<c:param name="townId" value="${settingForm.town}"/>
											</c:import>
										</div><!--select end-->
										<div id="location-error" class="error_warning" style="display: none;"><em></em><b></b></div>
									</div><!--infor_x end-->
									<div class="infor_x"><!--infor_x begin-->
										<h3>生&nbsp;&nbsp;&nbsp;&nbsp;日：</h3>
										<div class="select"><!--select begin-->
											<span><select id="birthYear" name="birthYear">
													<option value="0">年</option>
													<c:forEach begin="0" end="63" var="i">
														<c:set var="year" value="${2012-i}"/>
														<option value="${year}" <c:if test="${year==settingForm.birthYear}"> selected="selected" </c:if>>${year}年</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthMonth" name="birthMonth">
													<option value="0">月</option>
													<c:forEach begin="1" end="12" var="month">
														<option value="${month}" <c:if test="${month==settingForm.birthMonth}"> selected="selected" </c:if>>${month}月</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthDay" name="birthDay">
													<option value="0">日</option>
													<c:forEach begin="1" end="31" var="day">
														<option value="${day}" <c:if test="${day==settingForm.birthDay}"> selected="selected" </c:if>>${day}日</option>
													</c:forEach>
											</select></span>
										</div><!--select end-->
										<div id="birth-error" class="error_warning" style="display: none;"><em></em><b></b></div>
									</div><!--infor_x end-->
									<div class="infor_x"><!--infor_x begin-->
										<h3>职&nbsp;&nbsp;&nbsp;&nbsp;业：</h3>
										<div class="select"><!--select begin-->
											<span>
												<select name="professionId" id="professionId">
													<option value="-1">请选择</option>
													<c:forEach items="${professions}" var="profession">
														<option value="${profession.id}" <c:if test="${settingForm.professionId==profession.id }"> selected="selected" </c:if>>${profession.name}</option>
													</c:forEach>
													<option value="0" <c:if test="${settingForm.professionId==0}"> selected="selected" </c:if>>其他（自己填写）</option>
												</select>
											</span>
										</div><!--select end-->
										<div id="profession-input" class="input" <c:if test="${settingForm.professionId!=0}">style="display: none"</c:if>><!--input begin-->
											<p class="l"></p><span class="w120"><input name="profession" type="text" value="${settingForm.profession}" /></span><p class="r"></p>
											
											<div class="clear"></div>
										</div><!--input end-->
										<div id="profession-error" class="error_warning" style="display: none;"><em></em><b></b></div>
									</div><!--infor_x end-->
								</div><!--infor end-->
							</form>
							<div class="save_btn"><a href="javascript:void(0);">确认&gt;&gt;</a><div id="general-error" class="error"><c:if test="${not empty errorInfo}">${errorInfo}</c:if></div></div>
						</div><!--m_box end-->
						<div class="clear"></div>
					</div>
					<div class="qrxx_b"></div>
				</div><!--qrxx end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/guide.js')}"></script>
			<c:set var="footType" value="fixed" scope="request"/>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
