$(document).ready(function() {
	//添加项目
	$("a.wantgo").bind("click", function() {
		var actId = $(this).attr("actid");
		var obj = this;
		addAct(this, actId, function(){
			$(obj).hide();
			$("div.cancel_add").show();
		});
		return false;
	});
	//添加约
	$("div.cancel_add > a.delete").bind("click", function(){
		var actId = $(this).attr("actid");
		var actName = $(this).attr("actname");
		var obj = $(this);
		var content = $("#dialog-remove-act").html().replace("{0}", actName);
		showConfirm(this, "removeAct", content, function(){
			removeAct(actId, function(){
				$(obj).parent().hide();
				$("a.wantgo").show();
			});
		});
		return false;
	});
});