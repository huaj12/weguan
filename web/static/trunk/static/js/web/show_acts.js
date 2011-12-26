$(document).ready(function() {
	$("div.dh_infor > div.btn_area > a").bind("click", function() {
		var actId = $(this).attr("actid");
		addAct(actId, function() {
			$("#want" + actId).hide();
			$("#dwant" + actId).show();
		});
	});

	$("div.dh_infor > div.delete_add > a").bind("click", function() {
		var actId = $(this).attr("actid");
		var actName = $(this).attr("actname");
		var content = $("#dialog-remove-act").html().replace("{0}", actName);
		showConfirm(this, "removeAct", content, function() {
			removeAct(actId, function() {
				$("#dwant" + actId).hide();
				$("#want" + actId).show();
			});
		});
	});
	
	$("div.title > div.select > span > select").bind("change", function(){
		var categoryId = $(this).attr("categoryid");
		var orderType = $(this).children('option:selected').val();
		window.location.href="/showActs/" + orderType + "/" + categoryId + "/1";
	});
});