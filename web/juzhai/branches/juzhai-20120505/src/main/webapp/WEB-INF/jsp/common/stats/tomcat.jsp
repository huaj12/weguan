<%@page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	long total = Runtime.getRuntime().totalMemory();
	long max = Runtime.getRuntime().maxMemory();
	long free = Runtime.getRuntime().freeMemory();
	out.println("<pre>");
	out.println("total_memory:" + total);
	out.println("max_memory:" + max);
	out.println("free_memory:" + free);
	out.println("</pre>");
%>