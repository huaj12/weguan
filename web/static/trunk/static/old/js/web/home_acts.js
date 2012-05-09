$(document).ready(function() {
	$("div.item > div.close > a").bind("click", function() {
		var actId = $(this).attr("actid");
		var actName = $(this).attr("actname");
		var obj = $(this);
		var content = $("#dialog-remove-act").html().replace("{0}", actName);
		showConfirm(this, "removeAct", content, function(){
			removeAct(actId, function(){
				$(obj).parent().parent().remove();
			});
		});
		return false;
	});
});