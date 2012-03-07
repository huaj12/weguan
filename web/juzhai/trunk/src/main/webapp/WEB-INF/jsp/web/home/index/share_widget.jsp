<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content_box w285"><!--content begin-->
	<div class="t"></div>
	<div class="m">
		<div class="right_title"><h2>邀请朋友拒宅</h2></div>
		<div class="share_icons"><!--share_icons begin-->
			<div id="jiathis_style_32x32"><!-- JiaThis Button BEGIN -->
				<a class="jiathis_button_tsina"></a>
				<a class="jiathis_button_qzone"></a>
				<a class="jiathis_button_douban"></a>
				<a class="jiathis_button_tqq"></a>
				<a class="jiathis_button_renren"></a>
				<a class="jiathis_button_kaixin001"></a>
			</div><!-- JiaThis Button END -->
			<script type="text/javascript">
				var jiathis_config = {
					url : "http://www.51juzhai.com/",
					title : " ",
					summary : "大家周末是不是又要一个人宅在家了？不如来拒宅网跟我们一起脱宅吧!",
					pic: "${jzr:static('/images/share_pic.jpg')}"
				};
			</script>
			<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
		</div><!--share_icons end-->
	</div>
	<div class="t"></div>
</div><!--content end-->