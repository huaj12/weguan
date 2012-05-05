<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="p_right"><!--p_right begin-->
	<div class="project_box"><!--project_box begin-->
		<h2>召集好友同去</h2>
		<div class="share"><!--share begin-->
			<!-- JiaThis Button BEGIN -->
			<div id="ckepop">
				<span class="jiathis_txt">分享到：</span>
				<a class="jiathis_button_tsina"></a>
				<a class="jiathis_button_renren"></a>
				<a class="jiathis_button_qzone"></a>
				<a class="jiathis_button_kaixin001"></a>
				<a class="jiathis_button_tqq"></a>
				<a class="jiathis_button_douban"></a>
			</div>
			<!-- JiaThis Button END -->
		</div><!--share end-->
	</div><!--project_box end-->
	<c:if test="${not empty actLinkList}">
		<div class="project_box"><!--project_box begin-->
			<h2>相关链接</h2>
			<div class="link"><!--link begin-->
				<c:forEach var="actLink" items="${actLinkList}">
					<p><a href="${actLink.link}">${actLink.name}》</a>来自${actLink.source}</p>
				</c:forEach>
			</div><!--link end-->
		</div><!--project_box end-->
	</c:if>
	<c:if test="${not empty actAdList}">
		<div class="project_box" style="background:none;"><!--project_box begin-->
			<h2>相关优惠</h2>
			<div class="tuangou"><!--tuangou begin-->
				<c:forEach var="actAd" items="${actAdList}">
					<div class="item_tg"><!--item_tg begin-->
						<div class="tg_pic"><a href="${actAd.link}"><img data-original="${actAd.picUrl}" src="${jzr:static('/images/web/1px.gif')}" width="210" height="153" /></a></div>
						<a href="${actAd.link}">${jzu:truncate(actAd.name,70,'...')}</a>
						<h3><p>团购价：<b>${actAd.price}元</b></p><em>${actAd.district}</em></h3>
					</div><!--item_tg end-->
				</c:forEach>
			</div><!--tuangou end-->
		</div><!--project_box end-->
	</c:if>
</div><!--p_right end-->
<script type="text/javascript">
	var jiathis_config = {
		url : "http://www.51juzhai.com/act/" + "${act.id}",
		title : "【我要拒宅】",
		summary : "最近想去 " + "${act.name}" + "，有人响应么？",
		pic : "${jzr:actLogo(act.id,act.logo,180)}"
	};
</script>