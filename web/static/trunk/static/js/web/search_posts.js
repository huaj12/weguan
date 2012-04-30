$(document).ready(function() {
	$("div.search_jg > a").click(function(){
		var queryString = $("input[name='queryString']").val();
		var sex = $("input[name='sex']").val();
		window.location.href = "/seachposts/"+encodeURI(queryString)+"_"+sex+"/1";
		return false;
	});
	$("div.tags > a").click(function(){
		window.location.href="/findposts?queryString="+$(this).text();
		return false;
	});
});