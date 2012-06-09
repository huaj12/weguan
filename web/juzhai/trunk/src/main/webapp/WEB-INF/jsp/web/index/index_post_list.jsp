<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<c:if test="${not empty postView }">									
						<div class="content_box w285"><!--content begin-->
						<div class="t"></div>
							<div class="m">
							<div class="right_title"><h2>正在找伴儿的小宅</h2><a href="/home">更多</a></div>
								<div class="zbering"><!--zbering begin-->
									<ul>
										<c:forEach items="${postView }" var="view">
											<li>
											<div class="photo"><a href="/home/${view.profileCache.uid}"><img src="${jzr:userLogo(view.profileCache.uid,view.profileCache.logoPic,80)}" width="60" height="60"/></a></div>
											<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${view.post.purposeType}"/></c:import>:</font><a href="/post/${view.post.id}"><c:out value="${jzu:truncate(view.post.content,90,'...')}"></c:out></a></p>
											</li>
										</c:forEach>	
									</ul>
								</div><!--zbering end-->
							</div>
						<div class="t"></div>
						</div><!--content end-->
				</c:if>		