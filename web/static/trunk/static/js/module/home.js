$(document).ready(function(){
	//load feed
	jQuery.ajax({
		url: "/app/ajax/showFeed",
		type: "get",
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
		$("div.star > p.zai").text($(star).attr("title"));
	}else{
		$("div.star > p.zai").text("请选择！");
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
	showLoading();
	//load feed
	jQuery.ajax({
		url: url,
		type: "post",
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

function grade(star, tpIdentity, times){
	if(star && star > 0 && tpIdentity && tpIdentity != "" && times && times > 0){
		dealFeed("/app/ajax/grade", {"tpIdentity": tpIdentity, "star": star, "times": times});
	}
}

function response(obj, type){
	if(type && type >= 0 && type <= 2){
		var data = $(obj).parent().attr("data");
		if(data){
			data['type'] = type;
		}
		dealFeed("/app/ajax/respFeed", data);
	}
}