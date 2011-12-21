function pageUserAct(uid, page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageUserAct",
		type: "get",
		cache : false,
		data: {"uid": uid, "page": page},
		dataType: "html",
		context: $(".txqd"),
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