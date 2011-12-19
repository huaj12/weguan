var jcrop_api;
    jQuery(function($){
     var  boundx, boundy;
      $('#target').Jcrop({
        onChange: updatePreview,
        onSelect: updatePreview,
        aspectRatio: 1
      },function(){
        // Use the API to get the real image size
        var bounds = this.getBounds();
        boundx = bounds[0];
        boundy = bounds[1];
        jcrop_api = this;
        jcrop_api.animateTo([0,0,90,90]);
        jcrop_api.setOptions(this.checked? {
			minSize: [ 90, 90 ],
			maxSize: [ 180, 180 ]
		}: {
			minSize: [ 90, 90 ],
			maxSize: [ 180, 180 ]
		});
    	
      });

      function updatePreview(c)
      {
        if (parseInt(c.w) > 0)
        {
        
          var rx = 180/c.w;
          var ry = 180/c.h;
         $('#preview_180').css({
             width: Math.round(rx * boundx) + 'px',
             height: Math.round(ry * boundy) + 'px',
             marginLeft: '-' + Math.round(rx * c.x) + 'px',
             marginTop: '-' + Math.round(ry * c.y) + 'px'
           });
          rx = 120/c.w;
          ry = 120/c.h;
         $('#preview_120').css({
             width: Math.round(rx * boundx) + 'px',
             height: Math.round(ry * boundy) + 'px',
             marginLeft: '-' + Math.round(rx * c.x) + 'px',
             marginTop: '-' + Math.round(ry * c.y) + 'px'
          });
         rx = 50/c.w;
         ry = 50/c.h;
        $('#preview_50').css({
            width: Math.round(rx * boundx) + 'px',
            height: Math.round(ry * boundy) + 'px',
            marginLeft: '-' + Math.round(rx * c.x) + 'px',
            marginTop: '-' + Math.round(ry * c.y) + 'px'
         });
        $('#face_x').val(c.x);
        $('#face_y').val(c.y);
        $('#face_w').val(c.w);
        $('#face_h').val(c.h);
        }
      };

    });
    
    function cut_face(){
    	if(w==0||w==""||h==0||h==""){
    		$("#face_error").html('请选择您要保存的照片');
    		return ;
    	}
    	 var x=$('#face_x').val();
    	 var y=$('#face_y').val();
    	 var w=$('#face_w').val();
    	 var h=$('#face_h').val();
    	var logo=$("#target").attr("src");
    	$.post('/profile/upload/logo/cut', {
    		logo : logo,
    		x : x,
    		y : y,
    		width : w,
    		height : h,
    		random : Math.random()
    	}, function(result) {
    		if(result.success){
    			location.reload();
    		}else{
    			if(result.errorInfo==""){
    				$("#logo_tip").html('保存失败');
    			}else{
    				$("#logo_tip").html(result.errorInfo);
    			}
    		}
    	});
    }
function releaseLogo(){
	jcrop_api.release();
	var logo=$("#target").attr("src");
	$("#preview_50").attr("src",logo);
	$("#preview_120").attr("src",logo);
	$("#preview_180").attr("src",logo);
}

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
							jcrop_api.setImage(data.url);
							$("#preview_50").attr("src",data.url);
							$("#preview_120").attr("src",data.url);
							$("#preview_180").attr("src",data.url);
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
    