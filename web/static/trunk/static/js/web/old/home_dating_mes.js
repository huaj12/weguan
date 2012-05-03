$(document).ready(function() {
	//修改约会
	$("div.date_btn > a").bind("click", function(){
		var datingId = $(this).attr("datingid");
		openDatingResp(datingId);
		return false;
	});
});

function submitRespDating(datingId){
	respDating(function(result){
		$("#respDating"+datingId).hide();
		$("#hasRespDating"+datingId).show();
	});
}