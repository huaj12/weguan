<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅直播</title>
		<link href="${jz:static('/css/jz_v2.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
			<c:set var="page" value="rank" scope="request" />
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="pub_style"><!--pub_style begin-->
								<div class="title">
									<h2>
										<span class="hover"><p class="l"></p><p class="r"></p><a href="javascript:void(0);">拒宅直播</a></span>
										<span><p class="l"></p><p class="r"></p><a href="#">本周热门</a></span>
									</h2>
									<a href="/app/showCategoryActs" class="more">发现更多&gt;&gt;</a>
								</div>
								<div class="box"><!--box begin-->
									<div class="f_w_g"><!--好友想去的 begin-->
										<jsp:include page="live_list.jsp" />
									</div><!--好友想去的 end-->
								</div><!--box ends-->
							</div><!--pub_style end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/app/artDialog/artDialog.jsp" />
		<script type="text/javascript" src="${jz:static('/js/module/live.js')}"></script>
		<script type="text/javascript">
		</script>
		<script type="text/javascript" src="${jz:static('/js/base/kaixin_plugin.js')}"></script>
		<jsp:include page="/WEB-INF/jsp/common/app/foot.jsp" />
	</body>
</html>