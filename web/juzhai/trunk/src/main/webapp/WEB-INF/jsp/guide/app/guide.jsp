<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新手引导</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="basic_yd sec_sport"><!--basic_yd begin-->
								<h3 class="fl"></h3>
								<div class="sec fr"><!--sec begin-->
									<h2>你感兴趣的娱乐</h2>
									<!-- foreach -->
									<p class="hover"><span class="fl"></span><a href="#" class="key_words">看变形金刚3</a><span class="fr"></span><em></em></p>
									<p><span class="fl"></span><a href="#" class="key_words">打篮球</a><span class="fr"></span><em></em></p>
									<!-- foreach -->
									<div class="add_insterest">
										<strong><input name="" type="text" /></strong>
										<a href="#" class="add">添加</a>
										<div class="error">请输入完整信息</div>
									</div>
								</div><!--sec end-->
							</div><!--basic_yd end-->
						</div><!--mid end-->
						<div class="bot"></div>
						<div class="round_num"><!--round_num begin-->
							<span <c:if test="${step==1}">class="hover"</c:if>></span>
							<span <c:if test="${step==2}">class="hover"</c:if>></span>
							<span <c:if test="${step==3}">class="hover"</c:if>></span>
							<span <c:if test="${step==4}">class="hover"</c:if>></span>
						</div><!--round_num end-->
						<div class="next_btn"><a href="/app/guide/next"">下一步</a></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
	</body>
</html>
