$(document).ready(function() {
	$("#search-post-form").submit(function(){
		var queryString = $("input[name='queryString']").val();
		queryString=trimStr(queryString);
		if(queryString==""){
			$("input[name='queryString']")[0].focus();
			return false;
		}else{
			return true;
		}
	});
	$("#sex-select").find("span > a").bind("click", function(){
		$("#search-post-form").submit();
		return false;
	});
	$("div.search_jg > a").click(function(){
		$("#search-post-form").submit();
		return false;
	});
	
});
