$(document).ready(function() {
	$("div.search_jg > a").click(function(){
		var queryString = $("input[name='queryString']").val();
		var sex = $("input[name='sex']").val();
		if(queryString==""){
			$("input[name='queryString']")[0].focus();
			return false;
		}
		window.location.href ="/searchposts?queryString="+queryString+"&sex="+sex;
		return false;
	});
});