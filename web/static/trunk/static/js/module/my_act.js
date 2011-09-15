function myActMouseOver(isOver){
	if(isOver){
		$(this).addClass("hover");
	}else {
		$(this).removeClass("hover");
	}
}

function hotActMouseOver(isOver){
	if(isOver){
		$(this).addClass("act_hot");
	}else {
		$(this).removeClass("act_hot");
	}
}

function removeAct(){
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

function addAct(){
	var actId = $(this).attr("actId");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				pageMyAct(1);
			}else{
				
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}


function pageMyAct(page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageMyAct",
		type: "get",
		data: {"page": page},
		dataType: "html",
		context: $(".interesting"),
		success: function(responseHTML){
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function showHotActs(actCategoryId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/showHotActs",
		type: "get",
		data: {"actCategoryId": actCategoryId},
		dataType: "html",
		context: $(".rec_words"),
		success: function(responseHTML){
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}