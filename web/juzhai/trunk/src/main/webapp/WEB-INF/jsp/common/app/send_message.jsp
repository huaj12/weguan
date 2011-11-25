<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${jz:static('/js/module/msg.js')}"></script>
<div id="aboutDiv" style="display: none">
	<div class="cantact_user">
		<!--cantact_user begin-->
		<div class="text_area">
			<textarea id="about_content" name="" cols="" rows=""></textarea>
		</div>
		<div class="clear"></div>
		<div class="bt">
			<a href="javascript:;" onclick="sendAbout();">发送</a>
		</div>
		<div class="ck">
			<c:if test="${context.tpName=='weibo'}">
				<input id="isWeibo" type="hidden" />
				<p>
					<em><input name="wb" id="type_weibo" type="checkbox" value="1" /> </em><span>微薄</span>
				</p>
				<p>
					<em><input name="wb" id="type_comment" type="checkbox" value="2" /> </em><span>评论</span>
				</p>
			</c:if>
				<input id="about_fid" value="" type="hidden" />
				<input id="about_name" value="" type="hidden" />
				<input id="about_actId" value="" type="hidden" />
			
		</div>
	</div>
	<!--cantact_user end-->

</div>
