<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>好主意-发布 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jzr:static('/js/My97DatePicker/WdatePicker.js')}"></script>
	</head>
	<body>
										<div class="warp"><!--warp begin-->
											<div class="main"><!--main begin-->
											<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
											<div class="huodong_area"><!--huodong_area begin-->
											<div class="area_t"></div>
											<div class="area_m"><!--area_m begin-->
											<div class="hd_form"><!--hd_form begin-->
											<div class="title"><h2><c:choose><c:when test="${empty idea }">分享</c:when><c:otherwise>编辑</c:otherwise></c:choose> ${jzd:categoryName(categoryId)}信息</h2><p><font>*</font>标记的是必填项</p><c:if test="${empty idea }"><a href="/idea/select/category">重新选择拒宅分类</a></c:if></div>
											<div class="hr_line"></div>
												<input name="categoryId" type="hidden" value="${categoryId}"/>
												<input name="ideaId" type="hidden" value="${idea.id}"/>
												<input name="createUid" type="hidden" value="${idea.createUid}" />
												<div class="pub_x"><!--pub_x begin-->
													<h3><font>*</font>标题：</h3>
													<div class="pub_input"><p class="l"></p><span class="w330"><input name="content" value="${idea.content}" type="text" /></span><p class="r"></p></div><em id="content-tip" style="display: none"></em>
												</div><!--pub_x end-->
												
												<div class="pub_x"><!--pub_x begin-->
													<h3><font>*</font>封面：</h3>
													<div class="load_pic_btn">
													<form id="uploadImgForm" name="imgForm" method="post" enctype="multipart/form-data">
													<input class="btn_file_molding" size="6" type="file" name="rawIdeaLogo" onchange="javascript:uploadImage();"/>  
													<a href="#">上传图片</a>
													</form>
													</div>
													<div class="loading_pic_btn" style="display: none"><a href="#">上传中...</a></div>
													<em style="display: none" id="pic-tip"></em>
													<div class="pic_img" <c:if test="${empty idea.pic }">style="display: none"</c:if>><img <c:if test='${not empty jzr:ideaPic(idea.id,idea.pic, 200) }'>src="${jzr:ideaPic(idea.id,idea.pic, 200)}" </c:if> width="180" height="180" /></div>
													<input type="hidden" name="pic" value="${idea.pic}" />
												</div><!--pub_x end-->
											
											
												<c:if test="${categoryId!=8}">
														<c:if test="${categoryId!=1&&categoryId!=5}">
															<div class="pub_x"><!--pub_x begin-->
																<h3><c:if test="${categoryId==3||categoryId==6 }"><font>*</font></c:if>时间：</h3>
																<div class="pub_input"><!--pub_input begin-->
																<p class="l"></p><span class="w88"><input name="startDay" type="text"  init-tip="开始日期" value="<fmt:formatDate value="${idea.startTime}" pattern="yyyy-MM-dd" />"  onclick="WdatePicker();return false;" value="" /></span><p class="r"></p>
																</div><!--pub_input end-->
																
																<div class="pub_sel"><!--pub_sel begin-->
																	<p class="l"></p>
																	<span class="w68">
																		<select name="startHour" >
																			<c:if test="${not empty idea.startTime }"><option value="<fmt:formatDate value="${idea.startTime}" pattern="HH" />" ><fmt:formatDate value="${idea.startTime}" pattern="HH" />点</option></c:if>
																			<c:forEach begin="0" end="23" var="hour">
																				<option value="${hour}" >
																					<c:choose>
																						<c:when test="${hour <10 }">0${hour}</c:when>
																						<c:otherwise>${hour}</c:otherwise>
																					</c:choose>
																				点</option>
																			</c:forEach>
																		</select>
																	</span>
																	<p class="r"></p>
																</div><!--pub_sel end-->
																
															<div class="pub_sel"><!--pub_sel begin-->
																<p class="l"></p>
																	<span class="w68">
																		<select name="startMinute" >
																		<c:if test="${not empty idea.startTime }"><option value="<fmt:formatDate value="${idea.startTime}" pattern="mm" />" ><fmt:formatDate value="${idea.startTime}" pattern="mm" />点</option></c:if>
																			<c:forEach begin="0" end="55" var="minute" step="5">
																				<option value="${minute}" >	
																					<c:choose>
																						<c:when test="${minute <10 }">0${minute}</c:when>
																						<c:otherwise>${minute}</c:otherwise>
																					</c:choose>
																				分</option>
																			</c:forEach>
																		</select>
																	</span>
																<p class="r"></p>
															</div><!--pub_sel end-->
															<em style="display: none" id="start-date-tip" ></em>
															</div><!--pub_x end-->
														
														<div class="pub_x"><!--pub_x begin-->
															<h3>&nbsp;&nbsp;</h3>
															<div class="pub_input"><!--pub_input begin-->
																<p class="l"></p><span class="w88"><input name="endDay" type="text" value="<fmt:formatDate value="${idea.endTime}" pattern="yyyy-MM-dd" />" init-tip="结束日期"  onclick="WdatePicker();return false;" /></span><p class="r"></p>
															</div><!--pub_input end-->
															<div class="pub_sel"><!--pub_sel begin-->
																<p class="l"></p>
																	<span class="w68">
																		<select name="endHour" >
																		<c:if test="${not empty idea.endTime }"><option value="<fmt:formatDate value="${idea.endTime}" pattern="HH" />" ><fmt:formatDate value="${idea.endTime}" pattern="HH" />点</option></c:if>
																			<c:forEach begin="0" end="23" var="hour">
																				<option value="${hour}" >
																				<c:choose>
																					<c:when test="${hour <10 }">0${hour}</c:when>
																					<c:otherwise>${hour}</c:otherwise>
																				</c:choose>
																				点</option>
																			</c:forEach>
																		</select>
																	</span>
																<p class="r"></p>
															</div><!--pub_sel end-->
															
															<div class="pub_sel"><!--pub_sel begin-->
																<p class="l"></p>
																<span class="w68">
																<select name="endMinute"  >
																<c:if test="${not empty idea.endTime }"><option value="<fmt:formatDate value="${idea.endTime}" pattern="mm" />" ><fmt:formatDate value="${idea.endTime}" pattern="mm" />点</option></c:if>
																<c:forEach begin="0" end="55" var="minute" step="5">
																	<option value="${minute}" >
																				<c:choose>
																					<c:when test="${minute <10 }">0${minute}</c:when>
																					<c:otherwise>${minute}</c:otherwise>
																				</c:choose>
																	分</option>
																</c:forEach>
																</select>
																</span>
																<p class="r"></p>
															</div><!--pub_sel end-->
															<em style="display: none" id="end-date-tip" ></em>
															<div class="clear"></div>
															<div class="clear"></div>
														</div><!--pub_x end-->
													</c:if>
												<c:if test="${categoryId!=4}">
													<div class="pub_x"><!--pub_x begin-->
													<h3><c:if test="${categoryId==3||categoryId==6 }"><font>*</font></c:if>地点：</h3>
														<div class="pub_sel"><!--pub_sel begin-->
														<p class="l"></p>
														<span>
														<select id="province-select" name="province" select-data="${province}">
															<option value="0">请选择</option>
														</select>
														</span>
														<p class="r"></p>
														</div><!--pub_sel end-->
														
														<div class="pub_sel"><!--pub_sel begin-->
														<p class="l"></p>
														<span>
														<select id="city-select" name="city" select-data="${city}">
															<option value="0">请选择</option>
														</select>
														</span>
														<p class="r"></p>
														</div><!--pub_sel end-->
														
														<div class="pub_sel" id="town_div"><!--pub_sel begin-->
															<p class="l"></p>
															<span class="w68">
															<select id="town-select" name="town" select-data="${town}" style="display: none;">
																<option value="-1">请选择</option>
															</select>
															</span>
															<p class="r"></p>
														</div><!--pub_sel end-->
														
													<div class="pub_input"><p class="l"></p><span class="w230"><input name="place" value="${idea.place }" type="text" init-tip="输入详细地址"  class="fous_befor" /></span><p class="r"></p></div><em id="place-tip" style="display: none"></em>
													</div><!--pub_x end-->
												</c:if>
											</c:if>
											<div class="pub_x"><!--pub_x begin-->
											<h3><c:if test="${categoryId==3||categoryId==6 }"><font>*</font></c:if>介绍：</h3>
											<div class="jieshao"><textarea name="detail">${idea.detail}</textarea></div>
											<em id="detail-tip" style="display: none"></em>
											</div><!--pub_x end-->
											
											<div class="pub_x"><!--pub_x begin-->
												<h3>费用：</h3>
												<div class="pub_sel"><!--pub_sel begin-->
												<p class="l"></p>
												<span>
												<select id="charge-select" >
												<option value="0"  >免费</option>
												<option value="1"  <c:if test="${not empty idea.charge&&idea.charge!=0&&idea.charge!=null }">selected="selected" </c:if>>收费</option>
												</select>
												</span>
												<p class="r"></p>
												</div><!--pub_sel end-->
												<div class="pub_input" id="charge-input" <c:if test="${empty idea.charge&&idea.charge==0||idea.charge==null }">style="display: none"</c:if>><p class="l"></p><span class="w80"><input name="charge" type="text" value="${idea.charge }" /></span><p class="r"></p><div class="yuan">元</div></div><em id="charge-tip" style="display: none"></em>
												<div class="clear"></div>
											</div><!--pub_x end-->
											
											<div class="pub_x"><!--pub_x begin-->
												<h3>链接：</h3>
												<div class="pub_input"><p class="l"></p><span class="w330"><input name="link" type="text"  class="fous_befor" init-tip="输入相关链接的url地址" value="${idea.link }" /></span><p class="r"></p></div><em style="display: none" id="link-tip"></em>
												<div class="clear"></div>
											</div><!--pub_x end-->
											<div class="pub_x"><!--pub_x begin-->
												<div class="send_btn"><a href="javascript:void(0)">好了,提交</a></div>
												<div class="pub_tss">我们会在1天内完成审核</div>
												<div class="clear"></div>
												<div class="clear"></div>
												<div class="clear"></div>
												<div class="clear"></div>
												<div class="clear"></div>
												<div class="clear"></div>
												<div class="clear"></div>
												<div class="clear"></div>
											</div><!--pub_x end-->
											</div><!--hd_form end-->
											</div><!--area_m end-->
											<div class="clear"></div>
											<div class="area_b"></div>
											</div><!--huodong_area end-->
										</div><!--main end-->
												<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
												<script type="text/javascript" src="${jzr:static('/js/jquery/jquery.form.js')}"></script>
												<script type="text/javascript" src="${jzr:static('/js/web/user_create_idea.js')}"></script>
												<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
										</div><!--warp end-->
</body>
</html>
