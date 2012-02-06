<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<div class="send_box"><!--send_box begin-->
	<div class="face"><a href="javascript:void(0);"><img src="${jzr:userLogo(profile.uid,profile.logoPic,120)}" width="120" height="120" /></a></div>
	<form name=sendPost id="uploadPicForm" method="post" enctype="multipart/form-data">
	<div class="send_area"><!--send_area begin-->
		<div class="select_menu" name="purposeType"><!--select_menu begin-->
			<p><a href="javascript:void(0);">请选择</a></p>
			<div></div>
			<div class="select_box"><!--select_box begin-->
				<span>
					<a href="javascript:void(0);" value="0">我想去</a>
					<a href="javascript:void(0);" value="1" class="selected">我想找伴儿</a>
					<a href="javascript:void(0);" value="2">我想找一个男生</a>
					<a href="javascript:void(0);" value="3">我想找一个女生</a>
				</span>
				<em></em>
			</div><!--select_box end-->
		</div><!--select_menu end-->
		<div class="send_box_error" style="display: none;"></div>
		<div class="random_select"><a href="#">没想好？随机一个吧</a></div>
		<div class="textarea"><textarea name="content" cols="" rows=""></textarea></div>
		<div class="jh"><!--jh begin-->
			<div id="send-post-date" class="menu_item"><!--menu_item begin-->
				<input type="hidden" name="dateString" value="" />
				<p><a href="javascript:void(0);" class="time">时间</a></p>
			</div><!--menu_item end-->
			<div id="send-post-address" class="menu_item"><!--menu_item begin-->
				<input type="hidden" name="place" value="" />
				<p><a href="javascript:void(0);" class="adress" title="">地点</a></p>
				<div class="show_area w230"><!--show_area begin-->
					<div class="area_title"><h5>填写在哪里拒宅</h5><a href="javascript:void(0);"></a></div>
					<div class="input"><em class="l"></em><span class="w140"><input type="text" init-msg="详细地址"/></span><em class="r"></em></div>
					<div class="ok_btn"><a href="javascript:void(0);">确认</a></div>
					<div class="error" style="display: none;">不要超过20个字哦</div>
				</div><!--show_area end-->
			</div><!--menu_item end-->
			<div id="send-post-pic" class="menu_item"><!--menu_item begin-->
				<input type="hidden" name="pic" value="" />
				<p><a href="javascript:void(0);" class="photo">图片</a></p>
				<div class="show_area w280"><!--show_area begin-->
					<div class="upload-input">
						<div class="upload"><!--upload begin-->
							<input class="btn_file_molding" size=6 type="file" name="postPic" />
							<a href="javsacript:void(0);">上传头像</a> 
						</div><!--upload end-->
						<div class="load_error" style="display: none;"></div>
						<!-- <div class="close"><a href="#"></a> </div> -->
						<div class="ts">仅支持JPG、GIF、PNG图片文件，文件小于5M</div>
					</div>
					<div class="upload_ok" style="display: none;"><!--upload_ok begin-->
						<em><a href="javascript:void(0);">重新上传</a></em><!-- <span><a href="#"></a></span> -->
						<div class="img"><img src="${jzr:static('/images/web/1px.gif')}" init-pic="${jzr:static('/images/web/1px.gif')}" width="250" /></div>
					</div><!--upload_ok end-->
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
		<input type="hidden" name="sendWeibo" value="false"/>
		<div class="tb"><span></span><p>同步到:</p><em></em></div>
	</div><!--send_area end-->
	</form>
</div><!--send_box end-->