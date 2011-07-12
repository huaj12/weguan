<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="STUFF_CONTENT"><jsp:include page="StuffListPage.jsp"
	flush="true"></jsp:include></div>
<div id="STUFF_PAGER" class="pager"></div>
<script type="text/javascript">
//var stuffPager = new Pager("stuff", "${httpService}/user/stuff.html", 
//    ${stuffItems}, ${stuffMaxPageItems}, ${stuffMaxIndexPages}, ${stuffPageOffset},{
//        delimiter : ' | ',
//        indexMask : Pager.MASK_PREV|Pager.MASK_NEXT,
//        parameters: {
//            id: '${pageUser.loginName}',
//            stuffPoin: '${stuffPoin}',
//            typeName: '${typeName}'
//        }
//});
//stuffPager.showPager("STUFF_CONTENT", "STUFF_PAGER");
</script>