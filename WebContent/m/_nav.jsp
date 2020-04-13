<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
</head>
<body >

<header class="index app_hide" >
    <div class="c-hd">
      <section class="back"> <a href="javascript:history.go(-1);"><i></i></a> </section>
      <section class="hd-title">${af.map.nav_title}</section>
      <section class="side"> 
      </section>
    </div>
</header>
<div class="content my-center">
	<c:forEach var="cur" items="${sonSysModuleList}" varStatus="vs">
		<div class="myls-listbox">
			<a href="" class="name sel" onclick="void(0);">${cur.mod_name}</a>
		</div>
	</c:forEach>
</div>
			
</body>
</html>