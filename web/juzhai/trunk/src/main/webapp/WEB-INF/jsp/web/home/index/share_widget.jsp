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
			<!-- Baidu Button BEGIN -->
			    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'大家周末是不是又要一个人宅在家了？不如来拒宅网跟我们一起脱宅吧!','wbuid':'2294103501','url':'http://www.51juzhai.com/','pic':'${jzr:static('/images/share_pic.jpg')}'}">
			        <a class="bds_tsina"></a>
			        <a class="bds_tqq"></a>
			        <a class="bds_douban"></a>
			    </div>
			    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'大家周末是不是又要一个人宅在家了？不如来拒宅网跟我们一起脱宅吧!'}">
			         <a class="bds_renren"></a>
			    </div>
			    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'加入拒宅找伴儿出去玩!','url':'http://www.51juzhai.com/','comment':'大家周末是不是又要一个人宅在家了？不如来拒宅网跟我们一起脱宅吧!','pic':'${jzr:static('/images/share_pic.jpg')}'}">
			       	<a class="bds_qzone"></a>
			       	<a class="bds_kaixin001"></a>
			    </div>
			<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=593065" ></script>
			<script type="text/javascript" id="bdshell_js"></script>
			<script type="text/javascript">
			var bds_config = {'snsKey':{'tsina':'3631414437','qzone':'100249114','douban':'00fb7fece2b96fd202f27fc6a82c4f76'}};
				document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
			</script>
			<!-- Baidu Button END -->
		</div><!--share_icons end-->
		<c:if test="${context.uid > 0}">
			<c:set var="qq" value="${jzd:qqGroup(loginUser.city)}" />
			<c:if test="${qq != null && qq != ''}">
				<div class="qqqun">${jzd:cityName(loginUser.city)}拒宅QQ群：${qq}</div>
			</c:if>
		</c:if>
	</div>
	<div class="t"></div>
</div><!--content end-->