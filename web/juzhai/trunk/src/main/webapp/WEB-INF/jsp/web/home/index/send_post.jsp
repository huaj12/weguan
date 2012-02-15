<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form name="sendPost${postForm.postId}" method="post" enctype="multipart/form-data">
	<c:if test="${postForm != null}">
		<input type="hidden" name="postId" value="${postForm.postId}" />
	</c:if>
	<div class="send_area"><!--send_area begin-->
		<div class="select_menu" name="purposeType"><!--select_menu begin-->
			<p><a href="javascript:void(0);">请选择</a></p>
			<div></div>
			<div class="select_box"><!--select_box begin-->
				<span>
					<a href="javascript:void(0);" value="0" <c:if test="${postForm.purposeType == 0}">class="selected"</c:if>>我想去</a>
					<a href="javascript:void(0);" value="1" <c:if test="${postForm == null || postForm.purposeType == 1}">class="selected"</c:if>>我想找伴儿去</a>
					<a href="javascript:void(0);" value="2" <c:if test="${postForm.purposeType == 2}">class="selected"</c:if>>我想找一个男生</a>
					<a href="javascript:void(0);" value="3" <c:if test="${postForm.purposeType == 3}">class="selected"</c:if>>我想找一个女生</a>
				</span>
				<em></em>
			</div><!--select_box end-->
		</div><!--select_menu end-->
		<div class="send_box_error" style="display: none;"></div>
		<div class="random_select"><c:if test="${postForm == null}"><a href="#">没想好？随机一个吧</a></c:if></div>
		<div class="textarea"><textarea name="content" cols="" rows="">${postForm.content}</textarea></div>
		<div class="jh"><!--jh begin-->
			<div id="send-post-date" class="menu_item <c:if test='${postForm != null && postForm.date != null}'>done</c:if>"><!--menu_item begin-->
				<input type="hidden" name="dateString" value="<fmt:formatDate value='${postForm.date}' pattern='yyyy-MM-dd'/>" />
				<p><a href="javascript:void(0);" class="time"><c:choose><c:when test="${postForm != null && postForm.date != null}"><fmt:formatDate value="${postForm.date}" pattern="MM-dd"/></c:when><c:otherwise>时间</c:otherwise></c:choose></a></p>
			</div><!--menu_item end-->
			<div id="send-post-address" class="menu_item <c:if test='${not empty postForm.place}'>done</c:if>"><!--menu_item begin-->
				<input type="hidden" name="place" value="${postForm.place}" />
				<p><a href="javascript:void(0);" class="adress" title="${postForm.place}">地点</a></p>
				<div class="show_area w230"><!--show_area begin-->
					<div class="area_title"><h5>填写在哪里拒宅</h5><a href="javascript:void(0);"></a></div>
					<div class="input"><em class="l"></em><span class="w140"><input type="text" init-msg="详细地址" value="${postForm.place}"/></span><em class="r"></em></div>
					<div class="ok_btn"><a href="javascript:void(0);">确认</a></div>
					<div class="error" style="display: none;">不要超过20个字哦</div>
				</div><!--show_area end-->
			</div><!--menu_item end-->
			<div id="send-post-pic" class="menu_item <c:if test='${not empty postForm.pic}'>done</c:if>"><!--menu_item begin-->
				<input type="hidden" name="pic" value="${postForm.pic}" />
				<p><a href="javascript:void(0);" class="photo">图片</a></p>
				<div class="show_area w280"><!--show_area begin-->
					<div class="upload_photo_area"><!--upload_photo_arae begin-->
						<div class="close1"><a href="javascript:void(0);"></a></div>
						<div class="upload" <c:if test="${not empty postForm.pic}">style="display: none;"</c:if>><!--upload begin-->
							<div class="upload_btn"><!--upload_btn begin-->
								<input class="btn_file_molding" size=6 type="file" name="postPic" />
								<a href="javascript:void(0);">上传图片</a>
							</div><!--upload_btn end-->
							<div class="load_error" style="display:none;"></div>
							<div class="clear"></div>
							<div class="ts">仅支持JPG GIF PNG BMP图片文件,文件小于2M</div>
						</div><!--upload end-->
						<div class="upload_ok" <c:if test="${empty postForm.pic}">style="display: none;"</c:if>><!--upload_ok begin-->
							<em><a href="javascript:void(0);">重新上传</a></em>
							<c:choose>
								<c:when test="${empty postForm.pic}"><c:set var="initImgUrl" value="${jzr:static('/images/web/1px.gif')}" /></c:when>
								<c:otherwise><c:set var="initImgUrl" value="${jzr:postPic(postForm.postId, postForm.ideaId, postForm.pic, 450)}" /></c:otherwise>
							</c:choose>
							<div class="img"><img src="${initImgUrl}" init-pic="${jzr:static('/images/web/1px.gif')}" width="250" /></div>
						</div><!--upload_ok end-->
					</div><!--upload_photo_arae end-->
				</div><!--show_area end-->
			</div><!--menu_item end-->
			<!-- <div class="menu_item">menu_item begin
				<p><a href="#" class="link">连接</a></p>
				<div class="show_area w230">show_area begin
					<div class="input"><em class="l"></em><span class="w140"><input name="" type="text" value="链接地址"/></span><em class="r"></em></div>
					<div class="ok_btn"><a href="#">确认</a></div>
				</div>show_area end
			</div>menu_item end -->
		</div><!--jh end-->
		<div class="btn"><a href="javascript:void(0);">发布拒宅</a></div>
		<div class="sending" style="display:none;"><a href="javascript:void(0)">发布中</a></div>
		<div class="tb">
			<input type="hidden" name="sendWeibo" value="false"/>
			<span></span><p>同步到:</p><em></em>
		</div>
	</div><!--send_area end-->
</form>
<c:if test="${postForm != null}">
	<c:choose>
		<c:when test="${isRepost == null||!isRepost}">
			<script type="text/javascript">
				function submitPost(sendForm){
					$.ajax({
						url : "/post/modifyPost",
						type : "post",
						cache : false,
						data : sendForm.serialize(),
						dataType : "json",
						success : function(result) {
							sendForm.find("div.sending").hide();
							sendForm.find("div.btn").show();
							if(result&&result.success){
								var content = $("#dialog-success").html().replace("{0}", "发布成功！");
								showSuccess(null, content);
								window.location.href =  window.location.href;
							}else{
								sendForm.find(".send_box_error").text(result.errorInfo).show();
							}
						},
						statusCode : {
							401 : function() {
								window.location.href = "/login?turnTo=" + window.location.href;
							}
						}
					});
				}
			</script>
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				function submitPost(sendForm){
					$.ajax({
						url : "/post/createPost",
						type : "post",
						cache : false,
						data : sendForm.serialize(),
						dataType : "json",
						success : function(result) {
							sendForm.find("div.sending").hide();
							sendForm.find("div.btn").show();
							if(result&&result.success){
								closeDialog("openPostSender");
								var content = $("#dialog-success").html().replace("{0}", "发布成功！");
								showSuccess(null, content);
							}else{
								sendForm.find(".send_box_error").text(result.errorInfo).show();
							}
						},
						statusCode : {
							401 : function() {
								window.location.href = "/login?turnTo=" + window.location.href;
							}
						}
					});
				}
			</script>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
	    $("div.select_menu").each(function(){
	    	var select = new SelectInput(this);
	    	select.bindBlur();
	    	select.bindClick();
	    	select.bindSelect();
	    });
	    
		var postSender = new PostSender($("form[name='sendPost${postForm.postId}']"));
		postSender.bindSubmit(submitPost);
	</script>
</c:if>