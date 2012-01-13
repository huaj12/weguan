<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的个人资料-拒宅网</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<c:set var="page" value="index" scope="request" />
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
			<div class="content">
				<!--content begin-->
				<div class="t"></div>
				<div class="m">
					<!--m begin-->
					<div class="set">
						<!--set begin-->
						<c:set var="page" value="index" scope="request" />
						<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" /><!--set_left end-->
						<div class="set_right">
							<!--set_right begin-->
							<div class="title">
								<h2>设置我的个人资料</h2>
							</div>
							<div class="my_infor">
								<!--my_infor begin-->
								<div class="face">
									<!--face begin-->
									<p>
										<img src="${jzr:userLogo(profile.uid,profile.logoPic,180)}" />
									</p>
									<a href="/profile/index/face">重新上传</a>
								</div>
								<!--face end-->
								<div class="infor">
									<!--infor begin-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>名&nbsp;&nbsp;&nbsp;&nbsp;字：</h3>
										<div class="user" id="user_nickname">
											<p id="new_nickname">${profile.nickname}</p>
											<c:if test="${!profile.hasModifyNickname}">
												<span id="nickname_xg"><a href="javascript:void(0);"
													onclick="show_updateDiv('nickname');">修改</a>
												</span>
											</c:if>
										</div>
										<div class="input" id="input_nickname" style="display: none;">
											<!--input begin-->
											<p class="l"></p>
											<span class="w90"><input name="" id="nickname"
												type="text" /> </span>
											<p class="r"></p>
											<a href="#" onclick="setNickname()" class="ok">确定</a> <a
												href="javascript:void(0);"
												onclick="cancel_updateDiv('nickname');" class="cancel">取消</a>
										</div>
										<!--input end-->
										<div class="ts" id="ts_nickname" style="display: none;">只能修改一次,谨慎操作!</div>
										<div class="error" id="error_nickname"></div>
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>性&nbsp;&nbsp;&nbsp;&nbsp;别：</h3>
										<div class="user" id="user_gender">
											<p id="new_gender">
												<c:choose>
													<c:when test='${profile.gender==1}'>男</c:when>
													<c:otherwise>女</c:otherwise>
												</c:choose>
											</p>
											<c:if test="${!profile.hasModifyGender}">
												<span id="gender_xg"><a href="javascript:void(0);"
													onclick="show_updateDiv('gender');">修改</a>
												</span>
											</c:if>
										</div>
										<div class="select" id="input_gender" style="display: none">
											<!--select begin-->
											<span><select id="gender">
													<option value="1" selected="selected">男</option>
													<option value="0">女</option>
											</select>
											</span> <a href="#" onclick="setGender();" class="ok">确定</a> <a
												href="javascript:void(0);"
												onclick="cancel_updateDiv('gender');" class="cancel">取消</a>
										</div>
										<!--select end-->
										<div class="ts" id="ts_gender" style="display: none">只能修改一次,谨慎操作!</div>
										<div class="error" id="error_gender"></div>
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>所&nbsp;在&nbsp;地：</h3>
										<div class="select">
											<!--select begin-->
											<span><select id="province"
												onchange="selectCity(this)">
													<c:forEach var="pro" items="${provinces}">
														<option
															<c:if test="${profile.province==pro.id}">selected="selected"</c:if>
															value="${pro.id}">${pro.name}</option>
													</c:forEach>
											</select>
											</span> <span id="citys"> <select id="city" onchange="selectTown(this.value)">
													<c:forEach var="city" items="${citys}">
														<c:if test="${profile.province==city.provinceId}">
															<option
																<c:if test="${profile.city==city.id}">selected="selected"</c:if>
																value="${city.id}">${city.name}</option>
														</c:if>
													</c:forEach>
											</select>
											</span><span id="towns"
												<c:if test="${profile.town=='-1'||profile.town=='0'}"> style="display: none" </c:if>><select
												name="town" id="town">
													<c:forEach var="town" items="${towns}">
														<c:if test="${profile.city==town.cityId}">
															<option
																<c:if test="${profile.town==town.id}">selected="selected"</c:if>
																value="${town.id}">${town.name}</option>
														</c:if>
													</c:forEach>
											</select>
											</span>
										</div>
										<!--select end-->
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>生&nbsp;&nbsp;&nbsp;&nbsp;日：</h3>
										<div class="select">
											<!--select begin-->
											<span><select id="birthYear">
													<c:forEach begin="1900" end="2012" var="year">
														<option value="${year}"
															<c:if test="${year==profile.birthYear}"> selected="selected" </c:if>>${year
															}年</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthMonth">
													<c:forEach begin="1" end="12" var="month">
														<option value="${month}"
															<c:if test="${month==profile.birthMonth}"> selected="selected" </c:if>>${month}月</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthDay">
													<c:forEach begin="1" end="31" var="day">
														<option value="${day}"
															<c:if test="${day==profile.birthDay}"> selected="selected" </c:if>>${day}日</option>
													</c:forEach>
											</select>
											</span>
											<div class="dis_age">
												<b><input type="checkbox"
													<c:if test="${profile.birthSecret}"> checked="checked"</c:if>
													id="birthSecret" value="1" /> </b>
												<p>隐藏年龄</p>
											</div>
										</div>
										<!--select end-->
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>职&nbsp;&nbsp;&nbsp;&nbsp;业：</h3>
										<div class="select">
											<!--select begin-->
											<span><select id="professionId"
												onchange="profession(this)">
													<c:forEach items="${professions}" var="profession">
														<option value="${profession.id}"
															<c:if test="${profile.professionId==profession.id }"> selected="selected" </c:if>>${profession.name
															}</option>
													</c:forEach>
													<option value="0"
														<c:if test="${profile.professionId==0}"> selected="selected" </c:if>>其他（自己填写）</option>
											</select>
											</span>
										</div>
										<!--select end-->
										<div class="input" id="profession_input"
											<c:if test="${profile.professionId!=0}">style="display: none"</c:if>>
											<!--input begin-->
											<p class="l"></p>
											<span class="w190"> <input id="profession" type="text"
												value="<c:if test="${profile.professionId==0}">${profile.profession}</c:if>"
												onfocus="if(this.value=='10个字以内描述')this.value=''"
												onblur="if(this.value=='')this.value='10个字以内描述'" /> </span>
											<p class="r"></p>
											<div class="error" id="profession_tip" ></div>
										</div>
										<!--input end-->
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>自我评价：</h3>
										<div class="pj_box">
											<!--pj_box begin-->
											<textarea rows="4" id="feature" cols="40" name="">${profile.feature}</textarea>
										</div>
										<!--pj_box end-->
										<div class="pj_error" id="feature_tp" >
											<!--pj_error begin-->
											
										</div>
										<!--pj_error end-->
									</div>
									<div class="save_btn">
										<!--save_btn begin-->
										<a href="javascript:void(0);" onclick="setting()">保存</a>
									</div>
									<!--save_btn end-->
									<div class="clear"></div>
								</div>
								<!--infor end-->
							</div>
							<!--my_infor end-->
						</div>
						<!--set_right end-->
					</div>
					<!--set end-->
				</div>
				<!--m end-->
				<div class="b"></div>
			</div>
			<!--content end-->
		</div>
		<!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<script type="text/javascript"
		src="${jzr:static('/js/web/profile.js')}"></script>
</body>
</html>
