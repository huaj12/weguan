<%@page trimDirectiveWhitespaces="true" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.InetSocketAddress"%>
<%@page import="java.util.Map"%>
<%@page import="net.rubyeye.xmemcached.utils.AddrUtil"%>
<%@page import="net.rubyeye.xmemcached.MemcachedClientBuilder"%>
<%@page import="net.rubyeye.xmemcached.XMemcachedClientBuilder"%>
<%@page import="net.rubyeye.xmemcached.MemcachedClient"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String host = "127.0.0.1";
	int port = 11211;
	String[] keys = new String[] { "version", "pointer_size", "uptime",
			"bytes_read", "bytes_written", "cmd_get", "cmd_set",
			"get_hits", "get_misses", "curr_connections",
			"total_connections", "curr_items", "total_items",
			"evictions", "bytes", "limit_maxbytes", "rusage_user",
			"rusage_system" };

	InetSocketAddress address = new InetSocketAddress(host, port);
	List<InetSocketAddress> addressList = new ArrayList<InetSocketAddress>(
			1);
	addressList.add(address);
	MemcachedClientBuilder builder = new XMemcachedClientBuilder(
			addressList);
	MemcachedClient memcachedClient = builder.build();
	Map<InetSocketAddress, Map<String, String>> map = memcachedClient
			.getStats();
	Map<String, String> statsMap = map.get(address);
	System.out.println("size:" + statsMap.size());
	try {
		//close memcached client
		memcachedClient.shutdown();
	} catch (IOException e) {
		e.printStackTrace();
	}
	out.println("<pre>");
	for (String key : keys) {
		out.println(key + ":" + statsMap.get(key));
	}
	out.println("</pre>");
%>