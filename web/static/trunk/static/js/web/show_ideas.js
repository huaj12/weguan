$(document).ready(function(){
	$("span#city-options > a").click(function(){
		var orderType = $("div.category").attr("order-type");
		var cityId = $(this).attr("value");
		window.location.href = "/showideas/" + orderType + "_" + cityId + "/1";
	});
});