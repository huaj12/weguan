<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>推荐拒宅项目-拒宅网-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!-- content begin -->
					<div class="t"></div>
					<div class="m"><!--" begin-->
						<div class="tj_project"><!--tj_project begin-->
							<div class="title">
								<h2>推荐拒宅项目</h2>
								<a href="/showActs">返回首页</a>
							</div>
							<div class="tj_main"><!--tj_main begin-->
								<div class="xhang"><!--x_hang begin-->
									<h3>项&nbsp;&nbsp;目&nbsp;&nbsp;名：</h3>
									<div class="input">
										<p class="l"></p>
										<span class="w190"><input name="name" id="name" type="text" value="${name}" /> </span>
										<p class="r"></p>
									</div>
									<div class="error" id="name_tip"></div>
								</div><!--x_hang end-->
								<div class="xhang"><!--x_hang begin-->
									<h3>详细信息：</h3>
									<div class="text_arae">
										<textarea id="detail" name="detail" style="width: 700px; height: 200px; visibility: hidden;" cols="" rows=""></textarea>
									</div>
									<div class="error" id="detail_tip"></div>
								</div><!--x_hang end-->
								<div class="xhang"><!--x_hang begin-->
									<h3>项目封面：</h3>
									<div class="upload"><!--upload begin-->
										<!-- <a href="#" class="btn">上传</a> -->
										<div class="sc_btn"><!--sc_btn begin-->
											<form id="uploadImgForm" method="post" enctype="multipart/form-data">
												<input class="sc_btn_input" size=6 type="file" onchange="uploadImage();" name="rawActLogo" />  
												<a href="#" class="btn">上传</a>
											</form>
										</div><!--sc_btn end-->
										<div class="loading" style="display: none;">上传中...</div>
										<div class="error"  style="display: none;"></div>
										<div class="load_done" style="display: none;"><!--load_done begin-->
											<p><img id="logo" src="${jzr:static('/images/web/1px.gif')}" width="180" height="180" loading-src="${jzr:static('/images/web/1px.gif')}"/></p>
											<a href="javascript:void(0);" class="reload">重新上传</a>
										</div><!--load_done end-->
									</div><!--upload end-->
								</div><!--x_hang end-->
								<div class="xhang"><!--x_hang begin-->
									<h3>分&nbsp;&nbsp;&nbsp;&nbsp;类：</h3>
									<div class="select"><!--select begin-->
										<span><select name="categoryId" id="category_ids">
												<c:forEach var="cats" items="${categoryList}">
													<option value="${cats.id}">${cats.name }</option>
												</c:forEach>
										</select></span>
									</div><!--select end-->
								</div><!--x_hang end-->
								<div class="xhang"><!--x_hang begin-->
									<h3>地&nbsp;点(选填)：</h3>
									<div class="select"><!--select begin-->
										<span><select name="province" id="province" onchange="selectProvince(this)">
											<c:forEach var="pro" items="${provinces}" varStatus="status">
												<c:if test="${status.index==0}">
													<c:set var="s" value="${pro.id}"></c:set>
												</c:if>
												<option value="${pro.id}">${pro.name}</option>
											</c:forEach>
										</select></span>
										<span id="citys"><select id="city" name="city" onchange="selectCity(this)">
											<c:forEach var="city" items="${citys}"  varStatus="status">
												<c:if test="${status.index==0}">
													<c:set var="c" value="${city.id}"></c:set>
												</c:if>
												<c:if test="${s==city.provinceId}">
													<option value="${city.id}">${city.name}</option>
												</c:if>
											</c:forEach>
										</select></span>
										<span id="towns"><select name="town" id="town">
											<c:forEach var="town" items="${towns}">
												<c:if test="${c==town.cityId}">
													<option value="${town.id}">${town.name}</option>
												</c:if>
											</c:forEach>
										</select></span>
									</div><!--select end-->
									<div class="input">
										<p class="l"></p>
										<span class="w290"><input name="address" id="address"
											type="text" value="详细地址"
											onfocus="if(this.value=='详细地址')this.value=''"
											onblur="if(this.value=='')this.value='详细地址'" /></span>
										<p class="r"></p>
									</div>
									<div class="error" id="address_tip"></div>
								</div><!--x_hang end-->
								<div class="xhang"><!--x_hang begin-->
									<h3>时&nbsp;间(选填)：</h3>
									<div class="input"><!--select begin-->
										<p class="l"></p>
										<span class="w75"><input name="startTime"
											readonly="readonly" id="startTime" onclick="WdatePicker()"
											type="text" />
										</span>
										<p class="r"></p>
									</div><!--select end-->
									<b>&nbsp;到</b>
									<div class="input"><!--select begin-->
										<p class="l"></p>
										<span class="w75"><input name="endTime" id="endTime"
											readonly="readonly" onclick="WdatePicker()" type="text" />
										</span>
										<p class="r"></p>
									</div><!--select end-->
								</div><!--x_hang end-->
								<div class="send_btn"><!--x_hang begin-->
									<a href="javascript:void(0);" onclick="addRawAct();">发送</a>
								</div><!--x_hang end-->
							</div><!--tj_main end-->
						</div><!--tj_project end-->
					</div><!--" end-->
					<div class="b"></div>
				</div><!--content end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/raw_act.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
			<c:if test="${success}">
				<script type="text/javascript">
					$(document).ready(function(){
						var content = $("#dialog-success").html().replace("{0}", "谢谢推荐，通过审核后立即发布!");
						showSuccess(null, content);
					});
				</script>
			</c:if>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
			<div id="dialog-rawAct-success" style="display: none;">
				<div class="tj_done_show_box"><!--tj_done_show_box begin-->
					<h2>谢谢推荐，通过审核后立即发布!</h2>
					<a href="#" class="done">知道了</a>
				</div><!--tj_done_show_box end-->
			</div>
		</div><!--warp end-->
	</body>
</html>
