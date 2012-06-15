$(document).ready(function(){
	
	$("a.kw").click(function(){
		var kwShowDiv = $("div.kw_show").show();
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(kwShowDiv[0]).length <= 0){
				kwShowDiv.hide();
			}
		});
		return false;
	});
	
	$("a.jj").click(function(){
		var jjShowDiv = $("div.jj_show").show();
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(jjShowDiv[0]).length <= 0){
				jjShowDiv.hide();
			}
		});
		return false;
	});
	
	$("a.next").click(function(){
		return next(this);
	});
	
	$("a.xy").click(function(){
		return responseClick(this);
	});
	
	$("div.user-remove-interest > a.done").bind("click", function() {
		return doRemoveInterest(this);
	});
	$("div.user-add-interest > a").bind("click", function() {
		return doAddInterest(this);
	});
});

function doRemoveInterest(btn){
	var uid = $(btn).attr("uid");
	removeInterestConfirm(uid, btn, function(){
		$("div.remove-interest-" + uid).hide();
		$("div.interest-" + uid).attr("style", "");
	});
	return false;
}

function doAddInterest(btn){
	var uid = $(btn).attr("uid");
	interest(btn, uid, function(){
		$("div.interest-" + uid).hide();
		$("div.remove-interest-" + uid).attr("style", "");
	});
	return false;
}

function next(btn){
	var rescueUid = $(btn).attr("rescue-uid");
	$(btn).unbind("click").attr("class", "loading").text("加载中");
	changeRescueUser(rescueUid);
	return false;
}

function responseClick(btn){
	var postId = $(btn).attr("post-id");
	var rescueUid = $(btn).attr("rescue-uid");
	responsePost(postId, "", function(){
		changeRescueUser(rescueUid);
	}, function(errorInfo){
		alert(errorInfo);
	},btn);
}

function changeRescueUser(rescueUid){
	$.ajax({
		url : "/changerescueuser",
		type : "post",
		cache : false,
		data : {"rescueUid" : rescueUid},
		dataType : "html",
		success : function(result) {
			$("div.jj_mid > div").fadeOut(500, function(){
				$("div.jj_mid > div").html(result).fadeIn(500, function(){
					$("div.jj_mid > div").find("a.next").click(function(){
						return next(this);
					});
					$("div.jj_mid > div").find("a.xy").click(function(){
						return responseClick(this);
					});
					$("div.jj_mid > div").find("div.user-remove-interest > a.done").bind("click", function() {
						return doRemoveInterest(this);
					});
					$("div.jj_mid > div").find("div.user-add-interest > a").bind("click", function() {
						return doAddInterest(this);
					});
				});
			});
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}