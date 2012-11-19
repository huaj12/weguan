<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="delim" value=","/>
<c:set var="educationValue" value="初中及以下,中专,高中,大专,本科,硕士,博士及以上" />
<c:set var="education" value="${fn:split(educationValue, delim)}"/>
<c:set var="houseValue" value="自有住房,与家人同住,独自租房,与人合租" />
<c:set var="house" value="${fn:split(houseValue, delim)}"/>
<c:set var="carValue" value="已购车,未购车,有自行车" />
<c:set var="car" value="${fn:split(carValue, delim)}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的个人资料 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<c:set var="page" value="index" scope="request" />
			<c:set var="messageHide" value="true" scope="request" />
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
									<p><img src="${jzr:userLogo(profile.uid,profile.newLogoPic,180)}" width="180" height="180" /><c:choose><c:when test="${profile.logoVerifyState == 1||profile.logoVerifyState == -1}"><b>审核中...</b></c:when><c:when test="${profile.logoVerifyState == 3}"><b>未通过审核</b></c:when></c:choose></p>
									<a href="/profile/index/face">重新上传</a>
								</div>
								<!--face end-->
								<div class="infor">
									<!--infor begin-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>名&nbsp;&nbsp;&nbsp;&nbsp;字：</h3>
										<div class="user" id="user_nickname">
											<p id="new_nickname"><c:out value="${profile.nickname}" /></p>
											<%-- <c:if test="${!profile.hasModifyNickname}"> --%>
												<span id="nickname_xg"><a href="javascript:void(0);">修改</a>
												</span>
											<%-- </c:if> --%>
										</div>
										<div class="input" id="input_nickname" style="display: none;">
											<!--input begin-->
											<p class="l"></p>
											<span class="w90"><input name="" id="nickname"
												type="text" /> </span>
											<p class="r"></p>
											<a href="javascript:void(0);" class="ok">确定</a>
											<a href="javascript:void(0);" class="cancel">取消</a>
										</div>
										<!--input end-->
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
												<span id="gender_xg"><a href="javascript:void(0);">修改</a>
												</span>
											</c:if>
										</div>
										<div class="select" id="input_gender" style="display: none">
											<!--select begin-->
											<span>
												<select id="gender">
													<option value="1" selected="selected">男</option>
													<option value="0">女</option>
												</select>
											</span>
											<a href="javascript:void(0);" class="ok">确定</a>
											<a href="javascript:void(0);" class="cancel">取消</a>
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
												onchange="selectProvince(this)">
													<option value="0" <c:if test="${empty profile.province||profile.province==0}">selected="selected" </c:if> >请选择</option>
													<c:forEach var="pro" items="${provinces}">
														<option
															<c:if test="${profile.province==pro.id}">selected="selected"</c:if>
															value="${pro.id}">${pro.name}</option>
													</c:forEach>
											</select>
											</span> <span id="citys"> <select id="city" onchange="selectCity(this)">
													<option value="0" <c:if test="${empty profile.city||profile.city==0}">selected="selected" </c:if> >请选择</option>
													<c:forEach var="city" items="${citys}">
														<c:if test="${profile.province==city.provinceId}">
															<option
																<c:if test="${profile.city==city.id}">selected="selected"</c:if>
																value="${city.id}">${city.name}</option>
														</c:if>
													</c:forEach>
											</select>
											</span><span id="towns"
												<c:if test="${profile.town=='-1'}"> style="display: none" </c:if>><select
												name="town" id="town">
													<option value="-1" <c:if test="${empty profile.town||profile.town==0}">selected="selected" </c:if> >请选择</option>
													<c:forEach var="town" items="${towns}">
														<c:if test="${profile.city==town.cityId}">
															<option
																<c:if test="${profile.town==town.id}">selected="selected"</c:if>
																value="${town.id}">${town.name}</option>
														</c:if>
													</c:forEach>
													<option <c:if test="${profile.town==0}">selected="selected"</c:if>
																value="0">其他</option>
											</select>
											</span>
										</div>
										<!--select end-->
										<div id="location_tip" style="margin: 0" class="error"></div>
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>生&nbsp;&nbsp;&nbsp;&nbsp;日：</h3>
										<div class="select">
											<!--select begin-->
											<span><select id="birthYear">
													<option value="0" <c:if test="${0==profile.birthYear}"> selected="selected" </c:if>>请选择</option>
													<c:forEach begin="0" end="63" var="i">
														<c:set var="year" value="${2012-i}"/>
														<option value="${year}"
															<c:if test="${year==profile.birthYear}"> selected="selected" </c:if>>${year
															}年</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthMonth">
													<option value="0" <c:if test="${0==profile.birthMonth}"> selected="selected" </c:if> >请选择</option>
													<c:forEach begin="1" end="12" var="month">
														<option value="${month}"
															<c:if test="${month==profile.birthMonth}"> selected="selected" </c:if>>${month}月</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthDay">
													<option value="0" <c:if test="${0==profile.birthDay}"> selected="selected" </c:if>>请选择</option>
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
										<div class="error" style="margin: 0" id="birth_tp"></div>
									</div>
									<!--infor_x end-->
									<div class="infor_x">
										<!--infor_x begin-->
										<h3>职&nbsp;&nbsp;&nbsp;&nbsp;业：</h3>
										<div class="select">
											<!--select begin-->
											<span><select id="professionId"
												onchange="profession(this)">
													<option value="-1" <c:if test="${empty profile.professionId||profile.professionId==-1}">selected="selected" </c:if> >请选择</option>
													<c:forEach items="${professions}" var="profession">
														<option value="${profession.id}"
															<c:if test="${profile.professionId==profession.id }"> selected="selected" </c:if>>
															${profession.name}
														</option>
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
											<span class="w120"> <input id="profession" type="text"
												value="<c:if test="${profile.professionId==0}">${profile.profession}</c:if>"
												onfocus="if(this.value=='10个字以内描述')this.value=''"
												onblur="if(this.value=='')this.value='10个字以内描述'" /> </span>
											<p class="r"></p>
										</div>
										<!--input end-->
										<div class="error" style="margin: 0" id="profession_tip" ></div>
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
									<div class="infor_x"><!--infor_x begin-->
										<h3 style="margin-top:5px;">个人主页：</h3>
										<div class="input"><!--input begin-->
										<div class="http">http://</div>
										<p class="l"></p><span class="w180"><input  type="text" id="blog" value="${profile.blog}"  /></span><p class="r"></p>
										</div><!--input end-->
										<div class="error" id="blog_tp"></div>
									</div><!--infor_x end-->
									<div class="infor_x"><!--infor_x begin-->
										<h3>身&nbsp;&nbsp;&nbsp;&nbsp;高：</h3>
										<div class="select"><!--select begin-->
										<span>
										<select  id="height">
										<option value="0" <c:if test="${empty profile.height||profile.height==0}">selected="selected"</c:if>  >可不填</option>
										<c:forEach begin="140" end="210" var="h">
										<option value="${h}" <c:if test="${profile.height==h}">selected="selected"</c:if> >${h}</option>
										</c:forEach>
										</select>&nbsp;cm
										</span>
										</div><!--select end-->
									</div><!--infor_x end-->
									
									<div class="infor_x"><!--infor_x begin-->
										<h3>血&nbsp;&nbsp;&nbsp;&nbsp;型：</h3>
										<div class="select"><!--select begin-->
										<span>
										<select  id="bloodType">
											<option value="" <c:if test="${empty profile.bloodType}">selected="selected"</c:if>>可不填</option>
											<option value="O" <c:if test="${profile.bloodType == 'O'}">selected="selected"</c:if>>O型</option>
											<option value="A" <c:if test="${profile.bloodType == 'A'}">selected="selected"</c:if>>A型</option>
											<option value="B" <c:if test="${profile.bloodType == 'B'}">selected="selected"</c:if>>B型</option>
											<option value="AB" <c:if test="${profile.bloodType == 'AB'}">selected="selected"</c:if>>AB型</option>
										</select>
										</span>
										</div><!--select end-->
									</div><!--infor_x end-->
									
									<div class="infor_x"><!--infor_x begin-->
										<h3>学&nbsp;&nbsp;&nbsp;&nbsp;历：</h3>
										<div class="select"><!--select begin-->
										<span>
										<select id="education">
										<option value=""  <c:if test="${empty profile.education}">selected="selected"</c:if>>可不填</option>
										<c:forEach items="${education}" var="ed" >
											<option value="${ed}" <c:if test="${profile.education==ed}">selected="selected"</c:if> >${ed}</option>
										</c:forEach>
										</select>
										</span>
										</div><!--select end-->
									</div><!--infor_x end-->
									
									<div class="infor_x"><!--infor_x begin-->
										<h3>月&nbsp;&nbsp;收&nbsp;&nbsp;入：</h3>
										<div class="select"><!--select begin-->
										<span>
										<select id="monthlyIncome">
										<option value="0-0"  <c:if test="${(empty profile.minMonthlyIncome||profile.minMonthlyIncome<=0)&&(empty profile.maxMonthlyIncome||profile.maxMonthlyIncome<=0)}">selected="selected"</c:if>>可不填</option>
										<option value="0-2000"  <c:if test="${profile.minMonthlyIncome==0&&profile.maxMonthlyIncome==2000}">selected="selected"</c:if>>&lt;2000</option>
										<option value="2000-5000"  <c:if test="${profile.minMonthlyIncome==2000}">selected="selected"</c:if>>&gt;2000</option>
										<option value="5000-10000"  <c:if test="${profile.minMonthlyIncome==5000}">selected="selected"</c:if>>&gt;5000</option>
										<option value="10000-20000"  <c:if test="${profile.minMonthlyIncome==10000}">selected="selected"</c:if>>&gt;10000</option>
										<option value="20000-30000"  <c:if test="${profile.minMonthlyIncome==20000}">selected="selected"</c:if>>&gt;20000</option>
										<option value="30000-50000"  <c:if test="${profile.minMonthlyIncome==30000}">selected="selected"</c:if>>&gt;30000</option>
										<option value="50000-0"  <c:if test="${profile.minMonthlyIncome==50000}">selected="selected"</c:if>>&gt;50000</option>
										</select>&nbsp;元
										</span>
										</div><!--select end-->
									</div><!--infor_x end-->
									
									<div class="infor_x"><!--infor_x begin-->
										<h3>居&nbsp;&nbsp;&nbsp;&nbsp;所：</h3>
										<div class="select"><!--select begin-->
										<span>
										<select  id="house">
										<option value="" <c:if test="${empty profile.house}">selected="selected"</c:if>>可不填</option>
										<c:forEach items="${house}" var="h" >
											<option value="${h}" <c:if test="${profile.house==h}">selected="selected"</c:if> >${h}</option>
										</c:forEach>
										</select>
										</span>
										</div><!--select end-->
									</div><!--infor_x end-->
									
									<div class="infor_x"><!--infor_x begin-->
										<h3>购&nbsp;&nbsp;&nbsp;&nbsp;车：</h3>
										<div class="select"><!--select begin-->
										<span>
										<select  id="car">
										<option value=""  <c:if test="${empty profile.house}">selected="selected"</c:if> >可不填</option>
										<c:forEach items="${car}" var="c" >
											<option value="${c}" <c:if test="${profile.car==c}">selected="selected"</c:if> >${c}</option>
										</c:forEach>
										</select>
										</span>
										</div><!--select end-->
									</div><!--infor_x end-->
									
									<div class="infor_x"><!--infor_x begin-->
										<h3>家&nbsp;&nbsp;&nbsp;&nbsp;乡：</h3>
										<div class="input"><!--input begin-->
										<div class="http"></div>
										<p class="l"></p><span class="w180"><input  type="text" id="home" value="${profile.home}"  /></span><p class="r"></p>
										</div><!--input end-->
										<div class="error" id="home_tp"></div>
									</div><!--infor_x end-->
									
									<div class="save_btn">
										<!--save_btn begin-->
										<a href="javascript:void(0);">保存</a>
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
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/web/profile.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
</body>
</html>
