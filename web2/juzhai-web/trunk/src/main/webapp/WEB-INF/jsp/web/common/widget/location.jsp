<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<span>
	<select id="province-select" name="province" select-data="${param.provinceId}">
		<option value="0">省</option>
	</select>
</span>
<span>
	<select id="city-select" name="city" select-data="${param.cityId}">
		<option value="0">市</option>
	</select>
</span>
<span>
	<select id="town-select" name="town" id="town" select-data="${param.townId}" style="display: none;">
		<option value="-1">区</option>
	</select>
</span>
<!-- <script type="text/javascript">
	$(document).ready(function(){
		var provinceSelect = $("select#province-select");
		var citySelect = $("select#city-select");
		var townSelect = $("select#town-select");
		
		$.get('/base/initProvince', {
			random : Math.random()
		}, function(result) {
			initSelect(provinceSelect, result.result);
		});
		
		provinceSelect.bind("change", function(){
			citySelect.val("0");
			citySelect.trigger("change");
			citySelect.children("option[value!='0']").remove(); 
			if($(this).val() > 0){
				$.get('/base/initCity', {
					provinceId : $(this).val(),
					random : Math.random()
				}, function(result) {
					initSelect(citySelect, result.result);
				});
			}
		});
		
		citySelect.bind("change", function(){
			townSelect.val("-1");
			townSelect.children("option[value!='-1']").remove(); 
			if($(this).val() > 0){
				$.get('/base/initTown', {
					cityId : $(this).val(),
					random : Math.random()
				}, function(result) {
					if(!result.success){
						townSelect.hide();
					}else{
						townSelect.show();
						initSelect(townSelect, result.result);
					}
				});
			}else{
				townSelect.hide();
			}
		});
		
	});
	
	function initSelect(jselect, listResult){
		if(listResult!=null){
			for(key in listResult){
				jselect.append("<option value=\"" + key + "\">" + listResult[key] + "</option>");
			}
		}
		var selectData = jselect.attr("select-data");
		if(selectData!=null && selectData!=""){
			jselect.val(selectData);
			jselect.removeAttr("select-data");
			jselect.trigger("change");
		}
	}
</script> -->