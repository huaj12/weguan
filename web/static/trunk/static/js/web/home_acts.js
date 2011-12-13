$(document).ready(function() {
	$("div.item > div.close > a").each(function() {
		$(this).bind("click", function() {
			var actId = $(this).attr("actid");
			var actName = $(this).attr("actname");
			var obj = $(this);
			$.dialog({
				follow : this,
				icon : 'question',
				fixed : true,
				drag : false,
				resize : false,
				esc : true,
				id : 'question',
				content : '确定不再想去 ' + actName + ' 么？',
				// content : $("#questionBox").html(),
				cancelVal : '取消',
				cancel : true,
				ok : function() {
					jQuery.ajax({
						url : "/act/removeAct",
						type : "post",
						cache : false,
						data : {
							"actId" : actId
						},
						dataType : "json",
						context : $(obj),
						success : function(result) {
							if (result && result.success) {
								$(this).parent().parent().remove();
							} else {
								alert("system error.");
							}
						},
						statusCode : {
							401 : function() {
								window.location.href = "/login";
							}
						}
					});
					return true;
				}
			});
		});
	});
});