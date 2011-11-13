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
		    	window.location.href="/login";
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
		    	window.location.href="/login";
		    }
		}
	});
}