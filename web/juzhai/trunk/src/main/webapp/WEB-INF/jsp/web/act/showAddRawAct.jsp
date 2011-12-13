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
									<span class="w190"><input name="" type="text" /> </span>
									<p class="r"></p>
								</div>
								<div class="error">请不要超过10个字！</div>
							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->

								<h3>详细信息：</h3>
								<div class="text_arae">
									<textarea name="detail"   style="width:700px;height:200px;visibility:hidden;" cols="" rows=""></textarea>
								</div>
							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->
								<h3>项目封面：</h3>
								<div class="upload">
									<!--upload begin-->
									<a href="#" class="btn">上传</a>
									<div class="loading">上传中...</div>
									<div class="error">上传失败。我们只支持2M以内图片哦！</div>
									<div class="load_done">
										<!--load_done begin-->
										<p>
											<img src="images/face_girl.png" />
										</p>
										<a href="#" class="reload">重新上传</a>
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

									<span> <select name="language" id="language">

											<option value="" selected="selected">休闲娱乐</option>

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

									<span> <select name="language" id="language">

											<option value="" selected="selected">上海市</option>

									</select> </span> <span> <select name="language" id="language">

											<option value="" selected="selected">浦东新区</option>

									</select> </span>

								</div>
								<!--select end-->

								<div class="input">
									<p class="l"></p>
									<span class="w290"><input name="" type="text"
										value="详细地址" /> </span>
									<p class="r"></p>
								</div>
							</div>
							<!--x_hang end-->
							<div class="xhang">
								<!--x_hang begin-->

								<h3>时&nbsp;&nbsp;&nbsp;&nbsp;间：</h3>

								<div class="select">
									<!--select begin-->

									<span> <select name="language" id="language">

											<option value="" selected="selected">2001年</option>

									</select> </span> <span> <select name="language" id="language">

											<option value="" selected="selected">12月</option>

									</select> </span> <span> <select name="language" id="language">

											<option value="" selected="selected">25日</option>

									</select> </span>

								</div>
								<!--select end-->

								<b>到</b>

								<div class="select">
									<!--select begin-->

									<span> <select name="language" id="language">

											<option value="" selected="selected">2011年</option>

									</select> </span> <span> <select name="language" id="language">

											<option value="" selected="selected">12月</option>

									</select> </span> <span> <select name="language" id="language">

											<option value="" selected="selected">25日</option>

									</select> </span>

								</div>
								<!--select end-->

							</div>
							<!--x_hang end-->



							<div class="send_btn">
								<!--x_hang begin-->

								<a href="#">发送</a>

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
			<div class="tj_done_show_box">
				<!--tj_done_show_box begin-->

				<h2>谢谢推荐，通过审核后立即发布!</h2>

				<a href="#" class="done">知道了</a>
			</div>
			<!--tj_done_show_box end-->

			<!-- content end -->
		</div>
		<!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/web/common/script/kindEditor.jsp" />
		<script>
			
		</script>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div>
	<!--warp end-->
</body>
</html>
