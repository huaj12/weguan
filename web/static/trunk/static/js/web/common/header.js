var title =document.title;
$(document).ready(function(){
//	var n = 0; //top值
//	var obj = $("div.fix_top"); //position:fixed对象
//	$(window).bind("scroll", function(){
//		obj.css("top", function(){
//			return (document.body.scrollTop||document.documentElement.scrollTop)+n+'px';
//		});
//	});
//	$(window).bind("resize", function(){
//		obj.css("top", function(){
//			return (document.body.scrollTop||document.documentElement.scrollTop)+n+'px';
//		});
//	});
	
//	$("div.area > p").bind("click", function(){
//		var obj = $("div.area_list");
//		if(obj.is(":visible")){
//			$(this).removeClass("hover");
//			$("div.area_list").hide();
//		} else {
//			$(this).addClass("hover");
//			$("div.area_list").show();
//		}
//		return false;
//	});
//	$("div.area_list > a").bind("click", function(){
//		var cityId = $(this).attr("cityid");
//		switchChannel(cityId);
//		return false;
//	});
//	$("div.area").hover(function(){
//		$("body").unbind("mousedown");
//	}, registerClosChannel);
	
	//登录
	$("a.login-btn").bind("click", function(){
		var uri = $(this).attr("go-uri");
		var incode = $(this).attr("incode");
		var url = uri + "?turnTo=" + window.location.href;
		if(incode != null && incode != ""){
			url = url + "&incode=" + incode;
		}
		window.location.href = url;
		return false;
	});
	
	var messageTimerId = null;
	$("div.my_message > div#messageSelect").hover(function(){
		if(messageTimerId){
			clearTimeout(messageTimerId);
		}
		messageTimerId = setTimeout(function(){
			$("div.my_message > div#messageSelect > p").addClass("hover");
			$("div.my_message > div#messageSelect > div").first().show();
		}, 300);
		return false;
	}, function(){
		if(messageTimerId){
			clearTimeout(messageTimerId);
		}
		messageTimerId = setTimeout(function(){
			$("div.my_message > div#messageSelect > p").removeClass("hover");
			$("div.my_message > div#messageSelect > div").first().hide();
		}, 300);
		return false;
	});
	
	//账号
	var accTimerId = null;
	$("div.acc").hover(function(){
		if(accTimerId){
			clearTimeout(accTimerId);
		}
		accTimerId = setTimeout(function(){
			$("div.acc > p").addClass("hover");
			$("div.acc > div").show();
		}, 300);
		return false;
	}, function(){
		if(accTimerId){
			clearTimeout(accTimerId);
		}
		accTimerId = setTimeout(function(){
			$("div.acc > p").removeClass("hover");
			$("div.acc > div").hide();
		}, 300);
		return false;
	});
	
	if($("div.my_message").is(":visible")){
		queryNotice();
		setInterval(queryNotice, 10000);
	}
	
	$("a.feed-back").click(function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
});

function queryNotice(){
	jQuery.ajax({
		url : "/notice/nums",
		type : "get",
		cache : false,
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				var totalCnt = 0;
				for(var key in result.result){
					if(result.result[key] > 0){
						totalCnt += result.result[key];
						$("div.my_message > div.my_message_show > span#notice-auto-float-" + key).show();
					}else{
						$("div.my_message > div.my_message_show > span#notice-auto-float-" + key).hide();
					}
					$("div.my_message_show > span#notice-float-" + key + " > em").text(result.result[key] > 0 ? result.result[key] : "");
					$("div.my_message_show > span#notice-auto-float-" + key + " > em").text(result.result[key] > 0 ? result.result[key] : "");
				}
				var newTitle = title;
				if(totalCnt > 0){
					newTitle = "(" + totalCnt + ") " + title;
				}
				document.title=newTitle;
			}
		}
	});
}

//function registerClosChannel(){
//	$("body").bind("mousedown",function(){
//		$("div.area > p").removeClass("hover");
//		$("div.area_list").hide();
//	});
//}

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