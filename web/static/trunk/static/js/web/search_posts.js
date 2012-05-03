$(document).ready(function() {
	$("div.search_jg > a").click(function(){
		var queryString = $("input[name='queryString']").val();
		var sex = $("input[name='sex']").val();
		window.location.href ="/searchposts?queryString="+queryString+"&sex="+sex;
		return false;
	});
});