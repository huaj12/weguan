<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网_找伴出去玩</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,约会,交友" />
		<meta  name="description"   content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
						<div class="content_box w660 z900"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<div class="tips" style="display: none;"><!--tips begin-->
									<p>没想好去哪玩？试试这个</p>
									<a href="javascript:void(0);"></a>
								</div><!--tips end-->
								<div class="send_box"><!--send_box begin-->
									<jsp:include page="send_post.jsp" />
								</div>
							</div>
							<div class="t"></div>
						</div><!--content end-->
						<div class="content_box w660 800"><!--content begin-->
							<div class="t"></div>
							<div class="m">
								<jsp:include page="post_list.jsp" />
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<jsp:include page="home_logo.jsp" />
						<jsp:include page="index_right.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
			<script type="text/javascript" src="${jzr:static('/js/web/home.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
