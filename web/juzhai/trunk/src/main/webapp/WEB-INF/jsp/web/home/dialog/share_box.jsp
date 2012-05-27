<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
					<div id="show-share-idea-box" style="display: none">	
						<div class="quick_share_box" ><!--jb_show_box begin-->
							<div class="title"><!--title begin-->
								<h2>分享拒宅好主意</h2>
								<a href="javascript:void(0);"></a>
							</div><!--title end-->
							<div class="idea_area"><!--idea_area begin-->
										<div class="img"><img width="200" height="140" src=""/></div>
										<div class="img_infor"><!--img_infor begin-->
											<h2>{title}</h2>
											<p id="tip">时间:</p><span id="data">{beginTime} - {endTime} </span>
											<div class="clear"></div>
											<p>地点:</p><span>{cityName} {townName} {place} </span>
										</div><!--img_infor end-->
								<div class="clear"></div>
								<div class="btn"><a href="javascript:void(0);">提  交</a></div>
							</div><!--idea_area end-->
						</div><!--jb_show_box end-->
					</div>

						<div id="share-idea-tip-box" style="display: none">
							<div class="quick_share_box box300" ><!--jb_show_box begin-->
								<div class="title"><!--title begin-->
									<h2>分享成功</h2>
									<a href="javascript:void(0);"></a>
								</div><!--title end-->
								<div class="share_suc"><!--share_suc begin-->
									<p>好主意提交成功:)</p>
									<div class="btn"><a href="javascript:void(0);">知道了</a></div>
								</div><!--share_suc end-->
							</div><!--jb_show_box end-->
						</div>

					<div id="share-idea-box" style="display: none">
						<div  class="quick_share_box" ><!--jb_show_box begin-->
							<div class="title"><!--title begin-->
								<p class="hd"></p>
								<h2>分享你感兴趣的活动</h2>
								<a href="javascript:void(0);"></a>
							</div><!--title end-->
							<div class="link_area"><!--link_area begin-->
								<div class="pub_x"><!--pub_x begin-->
								<div class="pub_input"><p class="l"></p><span class="w450"><input name="" type="text" value="" init-tip="" class="fous_befor" /></span><p class="r"></p></div>
								</div><!--pub_x end-->
								<div class="error" style="display:none">链接无法识别，请输入正确的URL</div>
								<div class="btn" ><a href="javascript:void(0);" >确  定</a></div>
								<div class="link">
									<a href="" ></a>
									<input type="hidden" value="http://www.douban.com/location/world/" id="link-hd" init-tip="将豆瓣同城中的活动链接黏贴于此" title="分享你感兴趣的活动" name="去豆瓣同城逛逛">
									<input type="hidden" value="http://www.dianping.com/" id="link-qc" init-tip="将大众点评中的商户链接黏贴于此" title="分享你感兴趣的好去处"  name="去大众点评逛逛">
									<input type="hidden" value="http://www.tuan800.com/" id="link-tg"  init-tip="将tuan800.com的团购链接黏贴于此" title="分享你感兴趣的团购" name="去团购逛逛">
								</div>
							</div><!--link_area end-->
						</div><!--jb_show_box end-->
					</div>













