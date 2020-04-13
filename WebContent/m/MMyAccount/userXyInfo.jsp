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
<jsp:include page="../_public_in_head.jsp" flush="true" />
<style type="text/css">
.cancel-btn {
    height: 100%;
    background: #d2d2d2;
    text-align: center;
    color: #fff;
    cursor: pointer;
    overflow: hidden;
    border: none;
    border-radius: .06rem;
    padding: .18rem .2rem;
    font-size: .28rem;
    line-height: 100%;
}
</style>
</head>
<body >
<jsp:include page="../_header.jsp" flush="true" />
<div class="content my-center">
	<h1 style="text-align: center;font-size: 25px;">${newsInfo.title }</h1>
	<div style="padding:10px;" class="text">${newsInfo.map.content}</div>
</div>
<c:url var="urlOut" value="/m/MIndexLogin.do?method=logout"/>
<c:url var="urlCancelUser" value="/m/MMyAccount.do?method=cancelUser"/>
<jsp:include page="../_footer.jsp" flush="true" />			
<script type="text/javascript">//<![CDATA[
//]]></script>
</body>
</html>