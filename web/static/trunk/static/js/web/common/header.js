function showLogin(){
	$.dialog({
		content:$("#_loginDiv")[0],
		top:"50%",
		fixed: true,
		lock: true,
		title:"加入拒宅",
		id: 'login_div_box',
		padding: 0
	});
}

$(document).ready(function(){
	$("div.area > p").bind("click", function(){
		$("div.area_list").fadeIn(500);
		registerClosChannel();
	});
	$("div.area_list > a").bind("click", function(){
		var cityId = $(this).attr("cityid");
		switchChannel(cityId);
	});
	$("div.area_list").hover(function(){
		$("body").unbind("mousedown");
	}, registerClosChannel);
});

function registerClosChannel(){
	$("body").bind("mousedown",function(){
		$("div.area_list").fadeOut(200);
	});
}

function switchChannel(cityId){
	jQuery.ajax({
		url : "/switchChannel",
		type : "get",
		cache : false,
		data : {"cityId" : cityId},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				window.location.reload();
			} else {
				alert(result.errorInfo);
			}
		}
	});
}
