<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro_box_right"><!--pro_box_right begin-->
	<c:choose>
		<c:when test="${step==1}"><p>添加你的兴趣，开启专属于你的拒宅服务（共4步）</p></c:when>
		<c:when test="${step==2}"><p>当好友有同样的拒宅兴趣时，我们会及时通知您</p></c:when>
		<c:when test="${step==3}"><p>添加的兴趣越多，你收到好友拒宅邀请的几率越高</p></c:when>
		<c:when test="${step==4}"><p>当好友邀请你时，我们会通过你订阅的email通知你</p></c:when>
	</c:choose>
</div><!--pro_box_right end-->