$(document).ready(function(){
	$("#city-select").each(function(){
		var citySelect = new CitySelectInput(this, function(cityId){
			var orderType = $("div.category").attr("order-type");
			window.location.href = "/showideas/" + orderType + "_" + cityId + "/1";
			return false;
		});
		citySelect.bindBlur();
		citySelect.bindClick();
    });
	
	$("div.zj_friend > a").bind("click", function() {
		var id = $(this).attr("idea-id");
		$("#show_icons_"+id).toggle();
		return false;
	});
	$("body").bind("mousedown",function(event){
		$("div.zj_friend > div.show_icons").each(function(){
			if($(event.target).closest($(this)).length <= 0){
				$(this).hide();
			}
		});
	});
});