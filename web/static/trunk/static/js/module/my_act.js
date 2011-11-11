function actHover(li, isOver){
	if(isOver){
		$(li).addClass("hover");
	}else {
		$(li).removeClass("hover");
	}
}

function removeAct(a){
	var actId = $(a).attr("actid");
	var actName = $(a).attr("actname");
	if(actName == null || actName == '' || isNaN(actId)){
		return false;
	}
	var answer = confirm("确定不再想去 " + actName + " 么？");
	if (answer){
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
	}
}

function addCategoryAct(a){
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
				$(a).text("已添加");
				$(a).addClass("unclick");
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
		context: $(".box"),
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

function switchCategory(a, categoryId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageCategoryAct",
		type: "get",
		cache : false,
		data: {"categoryId": categoryId},
		dataType: "html",
		context: $(".i_w_g"),
		success: function(responseHTML){
			changeCategoryTab($(a).parent());
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
	});
}

function changeCategoryTab(span){
	$(".ca > span").each(function(){
		$(this).removeClass("hover");
	});
	$(span).addClass("hover");
}

function pageCategoryAct(categoryId, pageId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageCategoryAct",
		type: "get",
		cache : false,
		data: {"categoryId": categoryId, "page": pageId},
		dataType: "html",
		context: $(".i_w_g"),
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

$(document).ready(function(){

});