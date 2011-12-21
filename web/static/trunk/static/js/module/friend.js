function pageFriend(page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageFriend",
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