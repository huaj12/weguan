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
					url : "<%=path%>/uploadFile.action",
					type : "POST",
					dataType : "script",
					success : function(msg) {
						if (msg.indexOf("#") > 0) {
							var data = msg.split("#");
							$("#tipDiv").html(data[0]);
							$("#showImage").html(
									"<img src='<%=path%>/showImage.action?imageUrl="
											+ data[1] + "'/>");
						} else {
							$("#tipDiv").html(msg);
						}
					}
				};
				$("#form2").ajaxSubmit(options);
				return false;
			});
}