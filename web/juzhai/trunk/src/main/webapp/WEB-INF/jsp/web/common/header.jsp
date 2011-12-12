<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="top">
				<!--top begin-->
				<h1></h1>
				<div class="area">
					<!--area begin-->
					<p>上海</p>
					<div class="area_list">
						<!--about_list begin-->
						<a href="#">上海</a> <a href="#">北京</a> <a href="#">深圳</a> <a
							href="#">上海</a> <a href="#">北京</a>
					</div>
					<!--about_list end-->
				</div>
				<!--area end-->
				<div class="menu">
					<!--menu begin-->
					<a href="#" title="" class="selceted">找伴儿</a> <a href="#" title="">出去玩</a>
				</div>
				<!--menu end-->
				<div class="about">
					<!--about begin-->
					<p>关于</p>
					<div class="about_list">
						<!--about_list begin-->
						<a href="#">关于我们</a> <a href="#">安全拒宅</a> <a href="#">系统通知</a> <a
							href="#">微博拒宅器</a> <a href="#">人人拒宅器</a> <a href="#">开心拒宅器</a> <a
							href="#">安卓拒宅器</a>
					</div>
					<!--about_list end-->
				</div>
				<!--about end-->
				<div class="user_area">
					<!--user_area begin-->
					<div class="unlogin" style="display: none;">
						<a href="#">加入拒宅</a>
					</div>
					<div class="login">
						<!--login begin-->
						<div class="user_box">
							<!--user_box begin-->
							<p>
								<img src="${jz:static('/images/face.png')}" />
							</p>
							<a href="#" class="user boy">其实不想走</a> <a href="#" class="set">设置</a>
							<c:choose>
							<c:when test="${context.uid==0}">
							<a href="#" onclick="showLogin();" class="esc">登陆</a>
							</c:when>
							<c:otherwise>
							<a href="#"  class="esc">退出${context.uid}</a>
							</c:otherwise>
							</c:choose>
							
						</div>
						<!--user_box end-->
						<div class="user_message">
							<!--user_message begin-->
							<p>
								有<a href="#">12</a>人对你感兴趣<br /> 有<a href="#">17</a>个人想约你<br />
								有<a href="#">1</a>个关于你的系统通知<br /> 有<a href="#">2</a>人接受了你的邀请<br />
							</p>
						</div>
						<!--user_message end-->
					</div>
					<!--login end-->
				</div>
				<!--user_area end-->
				<div class="search">
					<!--search begin-->
					<div class="s_l"></div>
					<div class="s_m">
						<!--s_m begin-->
						<input name="" type="text" value="输入拒宅项目,如:打台球" />
					</div>
					<!--s_m end-->
					<div class="s_r">
						<a href="#"></a>
					</div>
					<div class="xl_menu">
						<!--xl_menu begin-->
						<a href="#">打篮球</a> <a href="#">打台球</a> <a href="#">打电动</a> <a
							href="#">打篮球</a>
					</div>
					<!--xl_menu end-->
				</div>
				<!--search end-->
			</div>
			<!--top end-->
			<!--login_show_box end-->
				<div id="_loginDiv" style="display: none"   class="login_show_box_1" >
					<h2></h2>
					<div class="link">
						<a href="/web/login/6"><img src="${jz:static('/images/web/weibo_btn.gif')}" /> </a>
					</div>
				</div>
			<!--login_show_box end-->