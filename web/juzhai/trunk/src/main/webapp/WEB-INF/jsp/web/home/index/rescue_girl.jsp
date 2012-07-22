<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
			<c:if test="${not empty profiles&&fn:length(profiles)>2}">
				<div  class="jjzv_show_box" style="display: none">
					<div class="title"><h2>解救宅女</h2><a href="javascript:void(0);"></a></div>
						<div class="con">
								<p>今天有<font>${fn:length(profiles)}</font>位合适您的宅女正在等待被解救。</p>
								<ul>
									<c:forEach var="profile" items="${profiles}" varStatus="index">
										<li>
											<c:if test="${index.index<5}">
												<a href="javascript:void(0);"><img src="${jzr:userLogo(profile.uid,profile.logoPic,80)}" width="50" height="50"  /></a>
											</c:if>
										</li>
										<input type="hidden" value="${profile.uid}" name="uids"/>
									</c:forEach>
								</ul>
								<div class="btn"><a href="javascript:void(0);" class="jjtm"></a><a href="javascript:void(0);" class="ckws"></a></div>
						</div>
						<div class='suss_done' style="display: none">
							<p></p>
							<span>您的解救信号已发出<br />等待宅女们的回复吧</span>
						</div>
						<div class="jjzv_loading" style="display: none">
							<p></p><span>解救中...</span>
						</div>
						
				</div>
			</c:if>



