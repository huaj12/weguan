<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${jz:static('/js/module/msg.js')}"></script>
<jsp:include page="/WEB-INF/jsp/common/app/artDialog/artDialog.jsp" />
<div id="aboutDiv" style="display: none">
		<div class="cantact_user"><!--cantact_user begin-->
			<div class="text_area">
				<textarea id="about_content" name="" cols="" rows=""></textarea>
			</div>
			<div class="clear"></div>
			<div class="bt">
				<a href="javascript:;" onclick="sendAbout();">发送</a>
			</div>
			<div class="ck">
				<input id="about_fid" value="" type="hidden" />
				<input id="about_name" value="" type="hidden" />
				 <input
					id="about_actId" value="" type="hidden" />
			</div>
		</div><!--cantact_user end-->
	</div>	