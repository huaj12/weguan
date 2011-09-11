<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>拒宅器</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jz:url('/js/jquery-1.6.3.min.js')}"></script>
		<!-- [if IE 6] -->
		<!-- <script src="iepng.js" type="text/javascript"></script>
		<script type="text/javascript">
		   EvPNG.fix('div, ul, img, li, input, p, span, h3, h2, h1, h4, a');  //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
		</script> -->
		<!-- [endif] -->
	</head>
	<body>
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content white"><!--content begin-->
						<jsp:include page="feed_fragment.jsp" />
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_bottom.jsp" />
		</div><!--main end-->
	</body>
</html>
