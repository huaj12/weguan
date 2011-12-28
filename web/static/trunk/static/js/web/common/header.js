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
	$("div.unlogin > a").bind("click", showLogin);
	
	var messageTimerId = null;
	$("div.my_message").hover(function(){
		if(messageTimerId){
			clearTimeout(messageTimerId);
		}
		messageTimerId = setTimeout(function(){
			$("div.my_message > p").addClass("hover");
			$("div.my_message > div").show();
		}, 300);
	}, function(){
		if(messageTimerId){
			clearTimeout(messageTimerId);
		}
		messageTimerId = setTimeout(function(){
			$("div.my_message > p").removeClass("hover");
			$("div.my_message > div").hide();
		}, 300);
	});
	var accTimerId = null;
	$("div.acc").hover(function(){
		if(accTimerId){
			clearTimeout(accTimerId);
		}
		accTimerId = setTimeout(function(){
			$("div.acc > p").addClass("hover");
			$("div.acc > div").show();
		}, 300);
	}, function(){
		if(accTimerId){
			clearTimeout(accTimerId);
		}
		accTimerId = setTimeout(function(){
			$("div.acc > p").removeClass("hover");
			$("div.acc > div").hide();
		}, 300);
	});
	
	if($("div.my_message").is(":visible")){
		setInterval(queryNotice, 10000);
	}
});

function queryNotice(){
	jQuery.ajax({
		url : "/notice/nums",
		type : "get",
		cache : false,
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				for(var key in result.result){
					$("div.my_message_show > span.#notice" + key + " > em").text(result.result[key]);
				}
			} else {
				alert(result.errorInfo);
			}
		}
	});
}

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
