<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro_box_right"><!--pro_box_right begin-->
	<c:choose>
		<c:when test="${step==1}"><p>添加你的拒宅兴趣，才会收到好友的邀请哦</p></c:when>
		<c:when test="${step==2}"><p>当好友有同样的拒宅兴趣时，我们会及时通知您</p></c:when>
		<c:when test="${step==3}"><p>每添加一个拒宅兴趣可获得5个拒宅积分哦</p></c:when>
		<c:when test="${step==4}"><p>我们只会在下班前给您发邮件哦</p></c:when>
	</c:choose>
</div><!--pro_box_right end-->