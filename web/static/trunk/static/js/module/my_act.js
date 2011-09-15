function myActMouseOver(isOver){
	if(isOver){
		$(this).addClass("hover");
	}else {
		$(this).removeClass("hover");
	}
}

function removeMyAct(){
	var actId = $(this).attr("actId");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/removeAct",
		type: "post",
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				//移除内容
				$(this).parent().remove();
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}