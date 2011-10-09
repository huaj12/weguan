$(document).ready(function(){
	//load feed
	jQuery.ajax({
		url: "/app/ajax/showFeed",
		type: "get",
		cache : false,
		dataType: "html",
		success: function(result){
			showFeedHtml(result);
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
});

function holdStar(index){
	var stars = $("div.star > span");
	if(stars){
		stars.each(function(i, element){
			if(i < index){
				$(element).removeClass("link").addClass("hover");
			}else{
				$(element).removeClass("hover").addClass("link");
			}
		});
	}
	var star = stars[index-1];
	if(star){
		$("div.star > em").text($(star).attr("title")).show();
	}else{
		$("div.star > em").text("").hide();
	}
}

function showFeedHtml(result){
	$(".loading").hide();
	$("div.skin_top_bg > div.content.white").html(result);
}

function showLoading(){
	$("div.skin_top_bg > div.content.white").html("");
	$(".loading").show();
}

function dealFeed(url, data){
	//清除tips
	$("div.pro_box").hide();
	showLoading();
	//load feed
	jQuery.ajax({
		url: url,
		type: "post",
		cache : false,
		data: data,
		dataType: "html",
		success: function(result){
			showFeedHtml(result);
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function answer(questionId, answerId, tpIdentity, times){
	if(questionId >= 0 && answerId >= 0 &&
			tpIdentity && tpIdentity != "" &&
			times && times > 0){
		dealFeed("/app/ajax/answer", {"questionId": questionId, "tpIdentity": tpIdentity, "answerId": answerId, "times": times});
	}
}

function response(type){
	if(type && type >= 0 && type <= 2){
		var data = eval("(" + $("div.jz_box > div.data").attr("data") + ")");
		if(data){
			data['type'] = type;
		}
		dealFeed("/app/ajax/respFeed", data);
	}
}

function showTip(obj, show, arg){
	if(show){
		var tip = $(obj).attr("tip");
		if(arg && arg != ""){
			tip = tip.replace("{0}", arg);
		}
		if(tip&&tip!=""){
			$("div.pro_box > p").text(tip);
			$("div.pro_box").show();
		}
	}else{
		$("div.pro_box").hide();
	}
}