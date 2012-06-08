<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
						<div class="content_box w285"><!--content begin-->
							<div class="t"></div>
							<div class="m">
							<div class="right_title"><h2>ta的拒宅</h2><a href="/home/${postProfile.uid}">更多</a></div>
								<div class="ta_jz"><!--ta_jz begin-->
									<ul>
										<c:forEach items="${userPostList}" var="postView">
												<li>
													<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.purposeType}"/></c:import>:</font><a href="/post/${postView.id}"><c:out value="${postView.content}"></c:out></a></p>
												</li>
										</c:forEach>
									</ul>
								</div><!--ta_jz end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->