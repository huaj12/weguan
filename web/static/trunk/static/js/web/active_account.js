$(document).ready(function(){
	$("a#resend").click(function(){
		var btn = this;
		jQuery.ajax({
			url: "/passport/sendactive",
			type: "post",
			dataType: "json",
			success: function(result){
				var content = $("#dialog-success").html().replace("{0}", "重发成功！");
				showSuccess(btn, content);
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login?turnTo=" + window.location.href;
				}
			}
		});
	});
});