<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty context.tpName}">
	<c:set value="/js/core/app/${context.tpName}/${context.tpName}.js"
		var="s"></c:set>
	<c:set
		value="/js/core/app/${context.tpName}/${context.tpName}_plugin.js"
		var="plugin"></c:set>
	<script type="text/javascript" src="${jz:static(s)}"></script>
	<script type="text/javascript" src="${jz:static(plugin)}"></script>
</c:if>
<c:if test="${context.tpName=='renren'}">
	<script>
		Renren.init({
			appId : '${jz:appId(context.tpId)}'
		});
	</script>
	<script type="text/javascript"
		src="http://static.connect.renren.com/js/v1.0/FeatureLoader.jsp"></script>
	<script>
		$(document).ready(
				function() {
					XN_RequireFeatures([ "Connect", "CanvasUtil" ], function() {
						//这里要自己填两个参数：api_key和跨域文件xd_receiver.html的路径 
						XN.Main.init("${jz:appKey(context.tpId)}",
								"${jz:static('/renren/xd_receiver.html')}");
						//这里要填需要自定义的高度如“800px”，注意应该有'px'单位 
						setHeight();
					});
				});
	</script>
</c:if>
<c:if test="${context.tpName=='weibo'}">
	<script
		src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js"
		language="JavaScript"></script>
</c:if>