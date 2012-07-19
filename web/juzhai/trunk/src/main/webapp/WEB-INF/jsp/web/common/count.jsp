<div style="display: none;">
	
	<script type="text/javascript">
		var host=document.domain;
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		if(host=="test.51juzhai.com"){
			<c:choose>
				<c:when test="${not empty isQplus&&isQplus}">
					document.write(unescape("%3Cscript src='" + _bdhmProtocol + "s4.cnzz.com/stat.php?id=3887258&web_id=3887258' type='text/javascript'%3E%3C/script%3E"));
				</c:when>
				<c:otherwise>
					document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F8eb3ef477c849f4cc74c953bebe4d0e2' type='text/javascript'%3E%3C/script%3E"));			
				</c:otherwise>
			</c:choose>
		}else{
			<c:choose>
				<c:when test="${not empty isQplus&&isQplus}">
					document.write(unescape("%3Cscript src='" + _bdhmProtocol + "s4.cnzz.com/stat.php?id=3886571&web_id=3886571' type='text/javascript'%3E%3C/script%3E"));
				</c:when>
				<c:otherwise>
					document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F0626a9e13c77bc0eeb042f151c2e0aa5' type='text/javascript'%3E%3C/script%3E"));
				</c:otherwise>
			</c:choose>
		}
	</script>
</div>