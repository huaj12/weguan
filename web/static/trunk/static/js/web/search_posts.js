$(document).ready(function() {
	$("#sex-select").find("span > a").bind("click", function(){
		$("#search-post-form").submit();
		return false;
	});
	$("div.search_jg > a").click(function(){
		$("#search-post-form").submit();
		return false;
	});
	
});
