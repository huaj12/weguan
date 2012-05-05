<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
<c:when test="${context.tpName=='renren'}">
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fe8528cc48cc1d18f4f4b95fc7f647ca6' type='text/javascript'%3E%3C/script%3E"));
</script>
</c:when>
<c:when test="${context.tpName=='kaixin001'}">
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F5a97c74a4e06e69b4a28327e000ef281' type='text/javascript'%3E%3C/script%3E"));
</script>
</c:when>
<c:when test="${context.tpName=='weibo'}">
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fb73bc1c2384f88cd0fe9ee5fd13da54d' type='text/javascript'%3E%3C/script%3E"));
</script>
</c:when>
</c:choose>


