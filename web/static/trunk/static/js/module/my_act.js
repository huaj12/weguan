function myActMouseOver(p, isOver){
	if(isOver){
		$(p).addClass("hover");
	}else {
		$(p).removeClass("hover");
	}
}

function hotActMouseOver(p, isOver){
	if($(p).hasClass("none")){
		return;
	}
	if(isOver){
		$(p).addClass("act_hot");
	}else {
		$(p).removeClass("act_hot");
	}
}

function removeAct(a){
	var actId = $(a).attr("actId");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/removeAct",
		type: "post",
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				//移除内容
				$(this).parent().remove();
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function addAct(a){
	var actId = $(a).attr("actId");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$(a).removeAttr("onclick");
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
		      alert("未登录");
		    }
		}
	});
}


function pageMyAct(page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageMyAct",
		type: "get",
		data: {"page": page},
		dataType: "html",
		context: $(".interesting"),
		success: function(responseHTML){
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function showHotActs(a, actCategoryId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/showHotActs",
		type: "get",
		data: {"actCategoryId": actCategoryId},
		dataType: "html",
		context: $(".rec_words"),
		success: function(responseHTML){
			changeRecTabClass($(a).parent());
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function changeRecTabClass(p){
	$("dev.rec_tab > p").each(function(){
		var pClass = $(this).attr("class");
		var idx = pClass.indexof("act_");
		if(idx > 0){
			$(this).attr("class", pClass.substring(4));
		}
	});
	$(p).attr("class", "act_" + $(p).attr("class"));
}

$(document).ready(function(){
	$("p.act_hot > a").bind("click", function(){
		var actCategoryId = $(this).attr("actCategoryId");
		var pClass = $(this).parent.attr("class");
		if(actCategoryId && actCategoryId > 0 && pClass.indexof("act_") < 0){
			showHotActs(actCategoryId);
		}
	});
});