function myActHover(p, isOver){
	if(isOver){
		$(p).addClass("hover");
	}else {
		$(p).removeClass("hover");
	}
}

function hotActHover(p, isOver){
	if($(p).hasClass("none")){
		return;
	}
	if(isOver){
		$(p).addClass("hover");
	}else {
		$(p).removeClass("hover");
	}
}

function removeAct(a){
	var actId = $(a).attr("actid");
	if(isNaN(actId)){
		return false;
	}
//	var answer = confirm("确认要删除?");
//	if (answer){
		//ajax
		jQuery.ajax({
			url: "/app/ajax/removeAct",
			type: "post",
			cache : false,
			data: {"actId": actId},
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					//移除内容
					$(a).parent().remove();
					var numberText = $("#myActCount").text();
					var count = parseInt(numberText.substring(1, numberText.length-1));
					$("#myActCount").text("(" + (count-1) + ")");
				}else{
					alert("system error.");
				}
			},
			statusCode: {
			    401: function() {
			    	window.location.href="/login";
			    }
			}
		});
//	}
}

function addRecommendAct(a){
	var actId = $(a).attr("actid");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		cache : false,
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$(a).removeAttr("onclick");
				$(a).attr("title", "已添加");
				$(a).parent().attr("class", "none")
					.removeAttr("onmouseover")
					.removeAttr("onmouseout");
				pageMyAct(1);
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
	});
}


function pageMyAct(page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageMyAct",
		type: "get",
		cache : false,
		data: {"page": page},
		dataType: "html",
		context: $(".interesting"),
		success: function(responseHTML){
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
	});
}

function showHotActs(a, actCategoryId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/showHotActs",
		type: "get",
		cache : false,
		data: {"actCategoryId": actCategoryId},
		dataType: "html",
		context: $(".rec_words"),
		success: function(responseHTML){
			changeRecTabClass($(a).parent());
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
	});
}

function changeRecTabClass(p){
	$("div.rec_tab > p").each(function(){
		var pClass = $(this).attr("class");
		var idx = pClass.indexOf("act_");
		if(idx >= 0){
			$(this).attr("class", pClass.substring(4));
		}
	});
	var srcClass = $(p).attr("class");
	$(".rec_words").attr("class", "rec_words " + srcClass);
	$(p).attr("class", "act_" + srcClass);
}

$(document).ready(function(){
	$("div.rec_tab > p > a").bind("click", function(){
		var actCategoryId = $(this).attr("actcategoryid");
		var pClass = $(this).parent().attr("class");
		if(actCategoryId && actCategoryId > 0 && pClass.indexOf("act_") < 0){
			showHotActs(this, actCategoryId);
		}
	});
});