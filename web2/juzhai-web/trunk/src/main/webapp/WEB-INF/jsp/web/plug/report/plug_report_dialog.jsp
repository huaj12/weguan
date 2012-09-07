<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="jzr"
	uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet"
	type="text/css" />
												<div class="jb_show_box">
													<!--jb_show_box begin-->
													<form id="report_form">
														<h2 class="girl">
															确定要举报<a href="javascript:void(0)" class="user">${profile.nickname}</a>
															<c:if test="${contentType!=3}">
															的
															<c:import
																url="/WEB-INF/jsp/web/common/fragment/report_content_type.jsp">
																<c:param name="reportContentType" value="${contentType}" />
															</c:import>
															<a href="javascript:void(0)" class="mess">"${jz:truncate(content,52,'...')}"</a>
															</c:if>
															么？
														</h2>
														<div class="choose">
															<!--choose begin-->
															<p>请选择举报类型:</p>
															<span><b><input name="reportType"
																	checked="checked" type="radio" value="0" /> </b><em>其他</em></span>
																<span><b><input name="reportType"
																		type="radio" value="1" /> </b><em>色情</em> </span> <span><b><input
																		name="reportType" type="radio" value="2" /> </b><em>人身攻击</em>
															</span> <span><b><input name="reportType"
																		type="radio" value="3" /> </b><em>垃圾广告</em> </span>
														</div>
														<!--choose end-->
														<div class="bcsm">
															<!--bcsm begin-->

															<p>补充说明(可不填):</p>
															<div class="error" id="report_description_tip"></div>

															<span><textarea name="description" cols="" rows=""></textarea>
															</span> <em>匿名举报,您的信息不会被透漏</em> <a href="javascirpt:void(0);"
																onclick="saveReport();return false;">确定举报</a>
														</div>
														<!--bcsm end-->
														<input type="hidden" value="${uid}" name="reportUid" /> <input
															type="hidden" value="${contentId}" name="contentId" />
														<input type="hidden" value="${contentType}" name="contentType"/>	
													</form>
												</div>
												<!--jb_show_box end-->
