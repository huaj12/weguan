var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="detail"]', {
		resizeType : 1,
		uploadJson : '/act/kindEditor/upload',
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
				'bold', 'italic', 'underline',

				'removeformat', '|', 'justifyleft', 'justifycenter',
				'justifyright', 'insertorderedlist', 'insertunorderedlist',
				'|', 'emoticons', 'image', 'link' ]
	});
});

function uploadImage() {
	$(document).ready(
			function() {
				var options = {
					url : "/act/ajax/temp/addActImage",
					type : "POST",
					dataType : "json",
					success : function(data) {
						if (data.error==0) {
							$("#logo_tip").html("上传成功");
							$("#logo").attr("src",data.url);
						} else {
							$("#logo_tip").html(data.message);
						}
					},
					error:function(data){
						$("#logo_tip").html("上传失败");
					}
				};
				$("#uploadImgForm").ajaxSubmit(options);
				return false;
			});
}

function selectCity(obj) {
	$.get('/act/selectCity', {
		proId : obj.value,
		random : Math.random()
	}, function(result) {
		$("#citys").html(result);
	});
}
function addRawAct(){
	var name=$("#name").val();
	var detail=editor.html();
	var logo=$("#logo").attr("src");
	var category_ids=$("#category_ids").val();
	var address=$("#address").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var province=$("#province").val();
	var city=$("#city").val();
	if(address=="详细地址"){
		address="";
	}
	if(!checkValLength(name, 2, 20)){
		$("#name")[0].focus();
		$("#name_tip").html("项目名控制在1－10个中文内！").stop(true, true).show()
		.fadeOut(4000);
		return ;
	}
	var detail_length = getByteLen(detail);
	if(detail_length>4000){
		editor.focus();
		$("#detail_tip").html("详细信息不能超过2000个中文当前"+(detail_length/2)+"字").stop(true, true).show()
		.fadeOut(4000);
		return ;
	}
//	if(logo==null||logo==""){
//		$("#logo")[0].focus();
//		$("#logo_tip").html("项目图片不能为空").stop(true, true).show()
//		.fadeOut(4000);
//		return ;
//	}
	if(!checkValLength(address, 0, 60)){
		$("#address")[0].focus();
		$("#address_tip").html("详细地址必须少于30个字！").stop(true, true).show()
		.fadeOut(4000);
	}
	$.post('/act/web/ajax/addAct', {
		name : name,
		detail:detail,
		logo:logo,
		categoryIds:category_ids,
		address:address,
		startTime:startTime,
		endTime:endTime,
		city:city,
		province:province,
		random : Math.random()
	}, function(result) {
		if(result.success){
			$.dialog({
				content:$("#tj_show_box")[0],
				top:"50%",
				fixed: true,
				lock: true,
				title:"",
				id: 'tj_div',
				padding: 0
			});
		}else{
			$.dialog({
				content:result.errorInfo,
				top:"50%",
				fixed: true,
				lock: true,
				title:"",
				id: 'tj_div',
				padding: 0
			});
		}
	});
	
	
	
	
}
	