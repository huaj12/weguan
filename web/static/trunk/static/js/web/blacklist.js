$(document).ready(function() {
	$("div.pub_box_m > span > a").bind("click", function() {
		var targetUid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-name");
		var content = $("#dialog-confirm").html().replace("{0}", "确定要取消屏蔽"+nickname+"么?");
		showConfirm(this, "cancelShieldUid", content, function(){
			cancelShieldUid(targetUid, function(){
				$(obj).parent().parent().remove();
			});
		});
		return false;
	});
});