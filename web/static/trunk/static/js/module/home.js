$(document).ready(function(){
	var url;
	if($("div.judge").length > 0){
		url = "/app/ajax/showJudge";
	} else {
		url = "/app/ajax/showFeed";
	}
	//load feed
	jQuery.ajax({
		url: url,
		type: "get",
		cache : false,
		dataType: "html",
		success: function(result){
			showFeedHtml(result);
		},
		statusCode: {
		    401: function() {
		      window.location.href="/app/login";
		    }
		}
	});
	
	var advise = $(".homeAdvise");
	if(advise.length > 0){
		$(".homeAdvise > p").bind("click", function(){
			var currentAdvise = advise.hasClass("tz_secleted");
			//加勾或者取消勾
			if(currentAdvise){
				advise.removeClass("tz_secleted").addClass("tz_link");
			}else{
				advise.removeClass("tz_link").addClass("tz_secleted");
			}
		});
	}
});

function holdStar(index){
	var stars = $("div.star > span");
	if(stars.length > 0){
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
		$("div.star > em").text($(star).attr("tip")).show();
	}else{
		$("div.star > em").text("").hide();
	}
}

function showFeedHtml(result){
	var contentDiv = $("div.skin_top_bg > div.content.white");
	contentDiv.html(result);
//	$(".loading_home").hide();
//	contentDiv.show();
	
	$(".loading_home").fadeOut(500, function(){
		contentDiv.fadeIn(400);
	});
}

function showLoading(){
	var contentDiv = $("div.skin_top_bg > div.content.white");
	contentDiv.hide();
	$(".loading_home").show();
	contentDiv.html("");
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
			var advise = $(".homeAdvise");
			if(advise.length > 0){
				advise.removeClass("tz_link").addClass("tz_secleted");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}

function invite(){
	kaixinFeed();
	dealFeed("/app/ajax/showFeed", {});
}

function skipInvite(){
	dealFeed("/app/ajax/showFeed", {});
}

function respQuestion(questionId, answerId, tpIdentity){
	if(questionId >= 0 && answerId >= 0 &&
			tpIdentity && tpIdentity != ""){
		var isAdvise = true;
		var advise = $(".judgeAdvise");
		if(advise.length > 0){
			isAdvise = advise.hasClass("tz_secleted");
		}
		dealFeed("/app/ajax/respJudge", {"questionId": questionId, "tpIdentity": tpIdentity, "answerId": answerId, "advise": isAdvise});
	}
}

function respSpecific(type){
	if(type && type >= 0 && type <= 2){
		var data = eval("(" + $("div.jz_box > div.data").attr("data") + ")");
		if(data){
			data['type'] = type;
		}
		dealFeed("/app/ajax/respSpecific", data);
	}
}

function respRecommend(actId, type){
	if(actId >= 0 && type && type >= 0 && type <= 2){
		var isFeed = false;
		var advise = $(".homeAdvise");
		if(advise.length > 0){
			isFeed = advise.hasClass("tz_secleted");
		}
		if(type == 1){
			try{
				if(recommendFeed instanceof Function){
					recommendFeed(actId);
				}
			}catch (e) {
			}
		}
		dealFeed("/app/ajax/respRecommend", {"actId": actId, "type": type, "isFeed": isFeed});
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

function selectAdvise(adviseObj){
	var currentAdvise = $(adviseObj).hasClass("tz_secleted");
	jQuery.ajax({
		url : "/app/setupAdvise",
		type : "post",
		cache : false,
		dataType : "json",
		data : { "isAdvise" : !currentAdvise },
		success : function(result) {
			//加勾或者取消勾
			if(currentAdvise){
				$(adviseObj).removeClass("tz_secleted").addClass("tz_link");
			}else{
				$(adviseObj).removeClass("tz_link").addClass("tz_secleted");
			}
		}
	});
}