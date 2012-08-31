$(document).ready(function(){
	var closeIdeaDetailTipsCookieName = 'C_IDEA_DETAIL_TIPS';
	//判断cookie是否显示tips
	var closeIdeaDetailTips= $.cookie(closeIdeaDetailTipsCookieName);
	if(null == closeIdeaDetailTips){
		$("div.tips_want").show();
	}
	$("div.tips_want > a.close").click(function(){
		$(this).parent().remove();
		$.cookie(closeIdeaDetailTipsCookieName, true, {expires: 0, path: '/', domain: '51juzhai.com', secure: false});
		return false;
	});
	
	$("#city-select").each(function(){
		var citySelect = new CitySelectInput(this, function(cityId){
			var gender = $("input[name='genderType']").val();
			var ideaId = $("div.wgo_title").attr("idea-id");
			window.location.href = "/idea/" + ideaId + "/user/" + cityId + "_" + gender + "/1";
			return false;
		});
		citySelect.bindBlur();
		citySelect.bindClick();
    });
	
	$("#gender-select").find("span > a").bind("click", function(){
		var cityId = $("input[name='cityId']").val();
		var gender = $(this).attr("value");
		var ideaId = $("div.wgo_title").attr("idea-id");
		window.location.href = "/idea/" + ideaId + "/user/" + cityId + "_" + gender + "/1";
		return false;
	});
	
	$("div.date > a").click(function(){
		var sendBtn = this;
		$(sendBtn).unbind("click").attr("class", "sending").text("发送中");
		var targetUid = $(this).attr("target-uid");
		var ideaId = $(this).attr("idea-id");
		sendDate(targetUid, null, ideaId, this, function(){
			$(sendBtn).attr("class", "date_done").text("已约");
		});
		return false;
	});
	$("a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
		return false;
	});
	
	
	var script = document.createElement("script");
	  script.src = "http://api.map.baidu.com/api?v=1.2&callback=initialize";
	  document.body.appendChild(script);
	
});

function initialize(){
	var ideaId=$("#ideaId").val();
	$.ajax({
		url : "/idea/ajax/map",
		type : "get",
		data : {ideaId:ideaId},
		dataType : "html",
		success : function(result) {
			if(result.indexOf("container")!=-1){
				$("#map-view").show();
				$("#map-view").html(result);
				var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM}; 
				createMap("container",opts);
				$("#map-view").find("div.view_map > a").bind("click", function() {
					var ideaId=$(this).attr("idea-id");
					$.ajax({
						url : "/idea/bigmap",
						type : "get",
						data : {ideaId:ideaId},
						dataType : "html",
						success : function(result) {
							if(result.indexOf("map_tcc")!=-1){
								var dialog = openDialog(null, "bigMap", result);
								var opts = {type: BMAP_NAVIGATION_CONTROL_LARGE}; 
								createMap("big-map-container",opts);
							}
						},
						statusCode : {
							401 : function() {
								window.location.href = "/login?turnTo=" + window.location.href;
							}
						}
					});
					return false;
				});
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
	return false;
}

function createMap(id,opts){
	var lat=$("#"+id).attr("lat");
	var lng=$("#"+id).attr("lng");
	var ctiyName=$("#"+id).attr("city-name");
	var placeName=$("#"+id).attr("place-name");
	var townName=$("#"+id).attr("town-name");
	var map = new BMap.Map(id);
	map.enableScrollWheelZoom();
	var point =new BMap.Point(lng,lat);
 	map.centerAndZoom(point,15);  
	var marker = new BMap.Marker(point);        // 创建标注
	marker.setTitle(ctiyName+townName+placeName);
	map.addControl(new BMap.NavigationControl(opts));
	map.addControl(new BMap.OverviewMapControl());  
	map.addOverlay(marker);
}
