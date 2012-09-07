<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${jz:static('/css/jquery.autocomplete.css')}" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${jz:static('/js/jquery/jquery.autocomplete.js')}"></script>
<script type="text/javascript" src="${jz:static('/js/core/core.js')}"></script>
<script>
var AddActInput =  Class.extend({
	init: function(jInputObj){
		inputObj = jInputObj;
	},
	bindAutocomplete:function(){
		//注册autoComplete
		inputObj.autocomplete("/searchAutoMatch", {
			dataType: "json",
			parse: function(datas) {
				var parsed = [];
				for (var i=0; i < datas.length; i++) {
					var data = datas[i];
					if (data) {
						parsed[parsed.length] = {
							data: data,
							value: data.name,
							result: data.name
						};
					}
				}
				return parsed;
			},
			formatItem: function(item) {
				return item.name;
			}
		});
	}
});
</script>
