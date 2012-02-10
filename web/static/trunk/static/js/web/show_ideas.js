$(document).ready(function(){
	$("span#city-options > a").click(function(){
		var orderType = $("div.category").attr("order-type");
		var cityId = $(this).attr("value");
		window.location.href = "/showIdeas/" + orderType + "_" + cityId + "/1";
	});
	
	$("div.fb_area > div.fb_btn > a").click(function(){
		var ideaId = $(this).attr("idea-id");
		var send = $(this).parent().hide();
		var sending = send.prev().show();
		postIdea(ideaId, send, sending, function(){
			sending.attr("class", "send_done").children("a").text("已发布");
		});
	});
});