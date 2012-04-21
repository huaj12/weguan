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
				<c:if test="${not empty isQplus && isQplus}">
				<div class="welcome"><!--welcome begin-->
					<div class="welcome_t"></div>
					<div class="welcome_m" style="height:430px;">
						<h2 style="padding-top:10px;"><b>一个小清新的脱宅社区</b><div class="ljjr"><a href="javascript:void(0);" class="wb go-login">立即加入</a></div></h2>
						<div class="clear"></div>
						<div style="height: 290px;position: relative;overflow: hidden;margin: 0px 100px;">
							<div id="window-box" style="position: absolute;height: 250px;width: 7500px;left: 5px;">
								<ul style="padding-top:10px;" window-count="10">
									<li>
										<p><a href="javascript:void(0);" class="go-login"><img src="http://static.51juzhai.com/upload/user/0/1/164/120/415e0274-f95d-4dfe-827d-e773e31bb381.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">这周六去欢乐谷吗？如果不下雨。我要坐5次跳楼机。</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/7/1986/120/d81ea3a1-a785-4332-a22c-319e0356a46d.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">周日找个公园，各自带着好吃的，一堆人出来玩。然后照一些2B的照片~</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/1/131/120/14f09147-a345-4bdf-af7f-cfa76399ddbf.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">尝遍上海美食 各地旅行 DIY蛋糕 陪我完成绘本 看电影 有没有好友邻愿意陪我</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://tp2.sinaimg.cn/2537648421/180/5619153408/1" width="120" height="120" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">来我的饭店尝尝我研发的新菜，一共3道菜，全部免单。感兴趣的私信我吧</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/2/640/120/b19809f2-6281-4ad9-b9f2-872d44848221.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">一起去教堂。从来没有去过，只想去向牧师忏悔下心中的罪孽。</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/0/79/120/9c959471-6b02-47fa-9f2a-92996dec3586.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">电玩城，玩投篮机，跳舞机，求高手pk。</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/4/974/120/3e431b8a-86f6-41c1-a316-1d006604d25c.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">找家小书店，迎着店老板能够回光返照般的白眼，光看不买，安静奢侈地打发掉...</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/5/475/120/3a85c508-d48f-4e04-9806-46156b94e34b.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">找个没有城管的地方摆摊。把家里的闲置（书、衣服和日用品）摆摊换点零花钱...</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/4/1424/120/0c37a1ea-8e8a-4dae-939e-9346095222bd.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">猫的天空之城概念书店。环境“很赞”，满墙“琳琅”的明信片，像是“在诉说...</a></em>
									</li>
									<li>
										<p><a href="javascript:void(0);" class="go-login" ><img src="http://static.51juzhai.com/upload/user/0/0/17/120/4c804f0f-a787-423a-932b-9938ba683ba6.jpg" /></a></p>
										<span>我想找伴儿去: </span>
										<em><a href="javascript:void(0);" class="go-login">朋友家看英超，买点啤酒，鸭脖，支持阿森纳的举手</a></em>
									</li>
								</ul>
							</div>
						</div>
						<div class="arrow_left" style="margin-top:10px;"><a href="javascript:void(0);"></a></div>
						<div class="arrow_right" style="margin-top:10px;"><a href="javascript:void(0);"></a></div>
					</div>
					<div class="welcome_b"></div>
				</div><!--welcome end-->
				</c:if>
				<div class="qrxx" <c:if test="${not empty isQplus && isQplus}">style="display: none;"</c:if>><!--qrxx begin-->
					<div class="qrxx_t"></div>
					<div class="qrxx_m">
						<div class="m_box"><!--m_box begin-->
							<div class="title"><h2>hi&nbsp;&nbsp;<c:out value="${loginUser.nickname}" />，请确认您的个人信息</h2></div>
							<form id="guide-form" action="/home/guide/next" method="post">
								<div class="infor"><!--infor begin-->
									<div class="infor_x"><!--infor_x begin-->
										<h3>性&nbsp;&nbsp;&nbsp;&nbsp;别：</h3>
										<div class="select"><!--select begin-->
											<span>
												<select name="gender">
													<option value="1" <c:if test="${settingForm.gender == 1}">selected="selected"</c:if>>男</option>
													<option value="0" <c:if test="${settingForm.gender == 0}">selected="selected"</c:if>>女</option>
												</select>
											</span>
										</div><!--select end-->
										<div class="ts"></div>
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
													<option value="0">请选择</option>
													<c:forEach begin="0" end="63" var="i">
														<c:set var="year" value="${2012-i}"/>
														<option value="${year}" <c:if test="${year==settingForm.birthYear}"> selected="selected" </c:if>>${year}年</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthMonth" name="birthMonth">
													<option value="0">请选择</option>
													<c:forEach begin="1" end="12" var="month">
														<option value="${month}" <c:if test="${month==settingForm.birthMonth}"> selected="selected" </c:if>>${month}月</option>
													</c:forEach>
											</select>
											</span> <span><select id="birthDay" name="birthDay">
													<option value="0">请选择</option>
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
							<div class="save_btn"><a href="javascript:void(0);">确认&gt;&gt;</a><div id="general-error" class="error" style="display: none;"></div></div>
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
