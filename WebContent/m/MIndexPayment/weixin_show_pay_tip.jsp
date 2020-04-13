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
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
html,body{background: #fff;}
img {
    border: 0;
    display: block;
    max-width: 100%;
}
</style>
</head>
<body id="body" class="orderList">
	<div class="mf mf_browser">
		<div class="txt_brow">请在菜单中选择在浏览器中打开，以完成支付</div>
		<img src="${ctx}/m/styles/img/open_browser.jpg"/>
	</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">//<![CDATA[
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		// 通过下面这个API显示右上角按钮
			 WeixinJSBridge.call('showOptionMenu');
	});
});
$(document).ready(function(){
	if(M.isIOS()){
		$("#tip_img").attr("src","${ctx}/m/styles/img/open_browser_ios.jpg");
	}
});
//]]></script>
</body>
</html>