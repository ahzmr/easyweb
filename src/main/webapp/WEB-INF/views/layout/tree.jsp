<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/includes/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<title><sitemesh:title/></title>
	<%@include file="/WEB-INF/includes/common.jsp"%>

	<link rel="stylesheet" href="${libUrl}/zTree_v3/css/zTreeStyle/zTreeStyle.css" />
	<script src="${libUrl}/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
	<link rel="stylesheet" href="${libUrl}/jquery-treetable/css/jquery.treetable.css" />
	<link rel="stylesheet" href="${libUrl}/jquery-treetable/css/jquery.treetable.theme.css" />
	<script src="${libUrl}/jquery-treetable/js/jquery.treetable.js"></script>

	<sitemesh:head/>
</head>
<body>
	<sitemesh:body/>
</body>
</html>