$(document).ready(function(){
	$("div.area > p").bind("click", function(){
		var obj = $("div.area_list");
		if(obj.is(":visible")){
			$("div.area_list").fadeOut(200);
		} else {	
			$("div.area_list").fadeIn(500);
		}
	});
	$("div.area_list > a").bind("click", function(){
		var cityId = $(this).attr("cityid");
		switchChannel(cityId);
	});
	$("div.area").hover(function(){
		$("body").unbind("mousedown");
	}, registerClosChannel);
	$("div.user_box > a.login").bind("click", showLogin);
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
				window.location.href = window.location.href;
			} else {
				alert(result.errorInfo);
			}
		}
	});
}

function showLogin(){
	$.dialog({
		content:$("#dialog-login").html(),
		top:"50%",
		fixed: true,
		lock: true,
		id: 'login_div_box'
	});
}
