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
});