<%@page import="redis.clients.jedis.Jedis"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.InetSocketAddress"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String host = "127.0.0.1";
	int port = 6379;
	Jedis jedis = new Jedis(host, port);
	out.println("<pre>");
	out.print(jedis.info());
	out.println("</pre>");
	jedis.disconnect();
%>