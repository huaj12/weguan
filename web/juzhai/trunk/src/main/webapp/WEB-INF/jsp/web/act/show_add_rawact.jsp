<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒宅网</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="warp">
		<!--warp begin-->
		<div class="main">
			<!--main begin-->
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
			<!-- content begin -->
			<div class="content">
				<!--content begin-->
				<div class="t"></div>
				<div class="m">
					<!--" begin-->
					<div class="tj_project">
						<!--tj_project begin-->
						<div class="title">
							<h2>推荐拒宅项目</h2>
							<a href="#">返回首页</a>
						</div>
						<div class="tj_main">
							<!--tj_main begin-->
							<div class="xhang">
								<!--x_hang begin-->
								<h3>项&nbsp;&nbsp;目&nbsp;&nbsp;名：</h3>
								<div class="input">
									<p class="l"></p>
									<span class="w190"><input name="" id="name" type="text" /> </span>
									<p class="r"></p>
								</div>
								<div class="error" id="name_tip" ></div>
							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->

								<h3>详细信息：</h3>
								<div class="text_arae">
									<textarea id="detail" name="detail"
										style="width: 700px; height: 200px; visibility: hidden;"
										cols="" rows=""></textarea>
								</div>
								<div class="error" id="detail_tip" ></div>
							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->
								<h3>项目封面：</h3>
								<div class="upload">
									<!--upload begin-->
									<form id="uploadImgForm" method="post"
										enctype="multipart/form-data">
										<input type="file" onchange="uploadImage();" name="fileupload" />
									</form>
									<div class="loading"></div>
									<div class="error" id="logo_tip"></div>
									<div class="load_done">
										<!--load_done begin-->
										<p>
											<img id="logo" src="" />
										</p>
										<!-- >a href="#" class="reload">重新上传</a -->
									</div>
									<!--load_done end-->

								</div>
								<!--upload end-->

							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->

								<h3>分&nbsp;&nbsp;&nbsp;&nbsp;类：</h3>

								<div class="select">
									<!--select begin-->

									<span> <select name="category_ids" id="category_ids">
											<c:forEach var="cats" items="${categoryList}">
												<option value="${cats.id}">${cats.name }</option>
											</c:forEach>
									</select> </span>

								</div>
								<!--select end-->

							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->

								<h3>地&nbsp;&nbsp;&nbsp;&nbsp;点：</h3>



								<div class="select">
									<!--select begin-->

									<span> <select name="province" id="province"
										onchange="selectCity(this)">
											<c:forEach var="pro" items="${provinces}" varStatus="status">
												<c:if test="${status.index==0}">
													<c:set var="s" value="${pro.id}"></c:set>
												</c:if>
												<option value="${pro.id}">${pro.name}</option>
											</c:forEach>
									</select>
									</span> <span id="citys"><select id="city" name="city">
											<c:forEach var="city" items="${citys}">
												<c:if test="${s==city.provinceId}">
													<c:set var="c" value="${city.id}"></c:set>
													<option value="${city.id}">${city.name}</option>
												</c:if>
											</c:forEach>
									</select> </span>
										<span id="towns" ><select name="town" id="town">
													<c:forEach var="town" items="${towns}">
														<c:if test="${c==town.cityId}">
															<option
																<c:if test="${profile.town==town.id}">selected="selected"</c:if>
																value="${town.id}">${town.name}</option>
														</c:if>
													</c:forEach>
											</select> </span>	
								</div>
								<!--select end-->

								<div class="input">
									<p class="l"></p>
									<span class="w290"><input name="address" id="address" type="text"
										value="详细地址" onfocus="if(this.value=='详细地址')this.value=''" onblur="if(this.value=='')this.value='详细地址'" /> </span>
									<p class="r"></p>
								</div>
								<div class="error" id="address_tip" ></div>
							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->

								<h3>时&nbsp;&nbsp;&nbsp;&nbsp;间：</h3>

								<div class="input">
									<!--select begin-->

									<span class="w190"><input name="" readonly="readonly" id="startTime"
										onClick="WdatePicker()" type="text" /> </span>
								</div>
								<!--select end-->
								<b>到</b>
								<div class="input">
									<!--select begin-->
									<span class="w190"><input id="endTime" readonly="readonly" onClick="WdatePicker()"
										name="" type="text" /> </span>
								</div>
								<!--select end-->

							</div>
							<!--x_hang end-->



							<div class="send_btn">
								<!--x_hang begin-->

								<a href="javascript:;" onclick="addRawAct();">发送</a>

							</div>
							<!--x_hang end-->
						</div>
						<!--tj_main end-->
					</div>
					<!--tj_project end-->
				</div>
				<!--" end-->

				<div class="b"></div>

			</div>
			<!--content end-->
			<div class="tj_done_show_box" id="tj_show_box" style="display: none">
				<!--tj_done_show_box begin-->

				<h2 id="tj_tip"></h2>

				<a href="#" onclick="closeAllDiv();" class="done">知道了</a>
			</div>
			<!--tj_done_show_box end-->

			<!-- content end -->
		</div>
		<!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
		<script type="text/javascript" src="${jzr:static('/js/core/validation.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/core/core.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/web/raw_act.js')}"></script>
		<script type="text/javascript"
			src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
		<script type="text/javascript"
			src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
</body>
</html>
