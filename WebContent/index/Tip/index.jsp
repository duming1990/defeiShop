<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触屏版-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../../m/_public_in_head.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/search/css/search_html5.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/search/css/chapter.css" />
</head>
<body id="body">
<header>
  <jsp:include page="../../m/_header.jsp" flush="true" />
</header>
<div class="content">
  <div class="gap-page"> <i class="rw"></i>
    <p> ${tip_msg} </p>
    <c:url var="url" value="${tip_url}" />
    <a class="j_submit" onClick="goUrl('${url}')">点击跳转</a> </div>
</div>
<script type="text/javascript">//<![CDATA[
//]]></script>
</body>
</html> 