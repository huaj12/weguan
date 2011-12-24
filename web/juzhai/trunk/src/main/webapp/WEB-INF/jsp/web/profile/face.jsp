<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒宅网-上传头像</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="warp"><!--warp begin--> 
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
			<div class="content"><!--content begin-->
				<div class="t"></div>
				<div class="m"><!--m begin-->
					<div class="set"><!--set begin-->
						<c:set var="page" value="face" scope="request" />
						<jsp:include page="/WEB-INF/jsp/web/profile/common/left.jsp" />
						<div class="set_right"><!--set_right begin-->
							<div class="title">
								<h2>设置我的个人形象</h2>
							</div>
							<div class="my_face"><!--my_face begin-->
								<div class="upload_area"><!--upload_area begin-->
									<div class="btn">
										<form id="uploadImgForm" method="post" enctype="multipart/form-data">
											<!-- <a href="#" class="upload_btn"><em>上传</em><q><input type="file" contenteditable="false" name="profileLogo" /></q></a> -->
											<input type="file" name="profileLogo" onchange="javascript:uploadImage();"/>
											<!-- <input class="btn_file_molding" size=6 type="file" />  
											<a href="#">上传头像</a> -->
										</form>
									</div>
									<div class="uploading" style="display: none;">上传中...</div>
									<div class="error" id="logo_tip" ></div>
									<div class="uploading_ok" style="display: none">
										已成功上传 my_photo.jpg<a href="#">重新上传</a>
									</div>
								</div><!--upload_area end-->
								<div class="edit_face_area">
									<img id="target" src="" style="width: 450px"/>
									<form id="logoCutForm" action="/profile/logo/cut" method="post">
										<input id="filePath" name="filePath" type="hidden"/>
										<input id="scaledW" name="scaledW" value="450" type="hidden"/>
										<input id="face_x" name="x" type="hidden"/>
										<input id="face_y" name="y" type="hidden"/>
										<input id="face_h" name="h" type="hidden"/>
										<input id="face_w" name="w" type="hidden"/>
									</form>
								</div>
								<div class="preview_face"><!--preview_face begin-->
									<p>预览效果</p>
									<span><img src="${jzr:userLogo(profile.uid,profile.logoPic,180)}" id="preview_180" width="180" height="180" /></span>
								</div><!--preview_face end-->
								<div class="btn_area">
									<a href="javascript;void(0);" class="save">保存</a>
									<!-- <a href="#" onclick="releaseLogo();" class="cancel">取消</a> -->
								</div>
							</div><!--my_face end-->
						</div><!--set_right end-->
					</div><!--set end-->
				</div><!--m end-->
				<div class="b"></div>
			</div><!--content end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div><!--warp end-->
	<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
	<jsp:include page="/WEB-INF/jsp/web/common/script/jcrop.jsp" />
	<script type="text/javascript" src="${jzr:static('/js/web/face.js')}"></script>
	<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
</body>
</html>
