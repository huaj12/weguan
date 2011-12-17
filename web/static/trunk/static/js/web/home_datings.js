$(document).ready(function() {
	$("div.item > div.close > a").bind("click", function() {
		var datingId = $(this).attr("datingid");
		var obj = $(this);
		var content = $("#dialog-remove-dating").html();
		showConfirm(this, "removeDating", content, function() {
			removeDating(datingId, function() {
				$(obj).parent().parent().remove();
			});
		});
	});
	//修改约会
	$("div.date_waiting > a").bind("click", function(){
		var uid = $(this).attr("uid");
		var datingId = $(this).attr("datingid");
		openDating(this, uid, datingId);
	});
});

function submitDating(uid) {
	date(function(result) {
		$("#actInfo" + uid).attr("href", "/act/" + result.actId).text(result.actName);
		var consumeText=null;
		switch (result.consumeType) {
		case 1:
			consumeText = "我请客";
			break;
		case 2:
			consumeText = "AA制";
			break;
		case 3:
			consumeText = "求请客";
			break;
		case 4:
			consumeText = "不用花钱";
			break;
		}
		if(consumeText!=null){
			$("#consumeType" + uid).text(consumeText);
		}
	});
}