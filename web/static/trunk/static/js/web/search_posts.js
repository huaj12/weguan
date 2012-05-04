$(document).ready(function() {
	$("div.search_jg > a").click(function(){
		searchPost();
	});
	
	$("#sex-select").find("span > a").bind("click", function(){
		searchPost();
	});
	registerBindKeyup(searchPost);
});
function searchPost(){
	var queryString = $("input[name='queryString']").val();
	queryString=trimStr(queryString);
	var sex = $("input[name='sex']").val();
	if(queryString==""){
		$("input[name='queryString']")[0].focus();
		return false;
	}
	window.location.href ="/searchposts?queryString="+queryString+"&sex="+sex;
	return false;
}