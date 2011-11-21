<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty !context.tpName}">
<c:set value="/js/core/app/${context.tpName}/${context.tpName}.js" var="s"></c:set>
<c:set value="/js/core/app/${context.tpName}/${context.tpName}_plugin.js" var="plugin"></c:set>
<script type="text/javascript" src="${jz:static(s)}"></script>
<script type="text/javascript" src="${jz:static(plugin)}"></script>
</c:if>
