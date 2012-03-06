$(document).ready(function(){
	var windowCnt = $("#window-box > ul").attr("window-count");
	var windowBox = $("#window-box");
	$("div.arrow_left > a").bind("click", function(){
		if(!windowBox.is(':animated')){
			var cLeft = windowBox.position().left;
			if(5 > cLeft){
				windowBox.animate({
					left: '+=150'
				}, 500);
			}
		}
		return false;
	});
	
	$("div.arrow_right > a").bind("click", function(){
		if(!windowBox.is(':animated')){
			var cLeft = windowBox.position().left;
			if(((windowCnt-5) * -150 + 5) < cLeft){
				windowBox.animate({
					left: '-=150'
				}, 500);
			}
		}
		return false;
	});
	
	$("a.go-login").click(function(){
		showLogin(window.location.href);
		return false;
	});
});