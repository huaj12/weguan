<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>偶遇你的ta</title>
		<link href="${jzr:static('/css/jz_promotion.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="app" id="step1"><!--APP begin-->
			<div class="sen_area"><!--sen_area begin-->
				<div class="sen2"><!--sen1 begin-->
					<div class="sen2_img"></div>
					<div class="sen2_txt"></div>
					<div class="sen2_btn"><a href="javascript:void(0);"></a></div>
					<p>开始计算并发布结果</p>
				</div><!--sen1 end-->
			</div><!--sen_area end-->
		</div><!--APP end-->
		<div class="app" id="step2" style="display: none"><!--APP begin-->
			<div class="sen_area"><!--sen_area begin-->
				<div class="sen3"><!--sen1 begin-->
					<div class="loading_icon"></div>
					<div class="loading_text" id="loading_text1" style="display:none"><img src="${jzr:static('/images/promotion/loading_txt1.jpg')}" /></div>
					<div class="loading_text" id="loading_text2" style="display:none"><img src="${jzr:static('/images/promotion/loading_txt2.jpg')}" /></div>
					<div class="loading_text" id="loading_text3" style="display:none"><img src="${jzr:static('/images/promotion/loading_txt3.jpg')}" /></div>
					<div class="loading_text" id="loading_text4" style="display:none"><img src="${jzr:static('/images/promotion/loading_txt4.jpg')}" /></div>
					<div class="loading_text" id="loading_text5" style="display:none"><img src="${jzr:static('/images/promotion/loading_txt5.jpg')}" /></div>
				</div><!--sen1 end-->
			</div><!--sen_area end-->
		</div><!--APP end-->
		<script type="text/javascript" src="${jzr:static('/js/jquery/jquery-1.6.3.min.js')}"></script>
		<script type="text/javascript" src="${jzr:static('/js/web/promotion/promotion.js')}"></script>
		<script type="text/javascript">
			var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
			document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F2111f93794bbfa52e7c1d546eada7644' type='text/javascript'%3E%3C/script%3E"));
		</script>
	</body>
</html>
