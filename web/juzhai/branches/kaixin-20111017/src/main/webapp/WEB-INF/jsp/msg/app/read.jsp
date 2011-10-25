<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅器</title>
		<link href="${jz:static('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main">
			<!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="message_box"><!--message_box begin-->
								<div class="box_body" id="readContent"><!--box_body begin-->
									<jsp:include page="/WEB-INF/jsp/msg/app/ajax/readContent.jsp" />
								</div><!--box_body end-->
							</div><!--message_box end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
			<!-- artDialog div -->
			<div id="boardDiv" style="display: none">
				<div class="cantact_user">
					<!--cantact_user begin-->
					<div class="text_area">
						<textarea id="board_content" name="" cols="" rows=""></textarea>
					</div>
					<div class="clear"></div>
					<div class="bt">
						<a href="javascript:;" onclick="sendBoard();" >发送</a>
					</div>
					<div class="ck">
						<input id="board_fid" value="" type="hidden"/>
						<input id="board_name" value="" type="hidden"/>
					</div>
				</div>
				<!--cantact_user end-->
			</div>
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/artDialog/artDialog.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/msg.js')}"></script>
		<script type="text/javascript" src="${jz:static('/js/base/kaixin_plugin.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>