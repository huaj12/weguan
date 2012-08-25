$(document).ready(function() {
	$("div.save_right>div.close_save>a").bind("click", function() {
		jQuery.ajax({
			url: "/rescueboy/close",
			type: "post",
			dataType: "json",
			success: function(result){
				$("div.open_save").show();
				$("div.save_ms").show();
				$("div.close_save").hide();
			},
			statusCode: {
			    401: function() {
			    	window.location.href = "/login?turnTo=" + window.location.href;
			    }
			}
		});
		return false;
	});
	$("div.save_right>div.open_save>a").bind("click", function() {
		$("div.open_save").hide();
		$("div.open_save_ing").show();
		$("div.save_ms").hide();
		jQuery.ajax({
			url: "/rescueboy/open",
			type: "post",
			dataType: "json",
			success: function(result){
				$("div.open_save_ing").hide();
				$("div.close_save").show();
			},
			statusCode: {
			    401: function() {
			    	window.location.href = "/login?turnTo=" + window.location.href;
			    }
			}
		});
		return false;
	});
	
});

