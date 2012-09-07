<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<div class="content_box w285"><!--content begin-->
								<div class="t"></div>
									<div class="m">
										<div class="tips_want" style="display: none"><!--tips_upload begin-->
											<a href="javascript:void(0);" class="txt">点击我想去就可能有人约你同去哦</a>
											<a href="javascript:void(0);" class="close"></a>
										</div><!--tips_upload end-->
										<div class="want_go">
											<c:choose>
												<c:when test="${idea.useCount>0}">
													<div class="person_num"><font>${idea.useCount}</font>人想去</div>
												</c:when>
												<c:otherwise>
												<div class="none">还没有人想去</div>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${hasUsed}">
													<div class="btn done"><a href="javascript:void(0);">已想去</a></div>
												</c:when>
												<c:otherwise>
													<div class="btn idea-btn idea-btn-${idea.id}"><a href="javascript:void(0);" idea-id="${idea.id}">我想去</a></div>															
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								<div class="t"></div>
							</div><!--content end-->