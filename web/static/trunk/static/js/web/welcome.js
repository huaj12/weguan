var step=0;
var length=5;
var all=$("#all").val();
$(document).ready(function(){
	$("div.arrow_left > a").bind("click", function(){
		if(step>0){
			$("#user_"+(step-1)).show();
			$("#user_"+(step+length-1)).hide();
			step--;
		}
	});
	
	$("div.arrow_right > a").bind("click", function(){
		if(step<(all-length)){
			$("#user_"+step).hide();
			$("#user_"+(step+length)).show();
			step++;
		}
	});
});