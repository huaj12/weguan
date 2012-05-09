function pageLive(page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageLive",
		type: "get",
		cache : false,
		data: {"page": page},
		dataType: "html",
		context: $(".f_w_g"),
		success: function(responseHTML){
			$(this).html(responseHTML);
			setHeight();
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}

function wantTo(a){
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
			$(a).removeAttr("onclick");
			$(a).text("已添加");
			$(a).addClass("ytj");
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
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
		    	window.location.href="/app/login";
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
			setHeight();
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
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
			setHeight();
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}