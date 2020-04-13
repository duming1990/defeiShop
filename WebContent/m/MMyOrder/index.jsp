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
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content my-center">
	<div class="myls-listbox">
<!-- 		<div class="name">我的订单</div> -->
		<c:url var="url" value="/m/MMyOrder.do?method=list&order_type=10&mod_id=1100500100" />
		<a class="name sel" onclick="goUrl('${url}')">商品订单</a>
		<c:url var="url" value="/m/MMyOrder.do?method=list&order_type=20&mod_id=1100500100" />
		<a class="name sel" onclick="goUrl('${url}')">扫码支付订单</a>
		<c:url var="url" value="/m/MMyOrder.do?method=list&order_type=40&mod_id=1100500100" />
		<a class="name sel" onclick="goUrl('${url}')">线下派送订单</a>
		<c:url var="url" value="/m/MMyOrder.do?method=list&order_type=70&mod_id=1100500100" />
		<a class="name sel" onclick="goUrl('${url}')">内部商城订单</a>
		<c:url var="url" value="/m/MMyTuiHuo.do?method=list&mod_id=1100500100" />
		<a class="name sel" onclick="goUrl('${url}')">我的售后申请</a>
		
		<c:url var="url" value="/m/MMyOrder.do?method=list&order_type=50&mod_id=1100500100" />
		<a class="name sel" onclick="goUrl('${url}')">购买运营中心订单</a>
		
	</div>
	<div class="myls-listbox">
	     <c:url var="url" value="/m/MMyComment.do?mod_id=1100500200" />
		<a class="name sel" onclick="goUrl('${url}')">我的评价</a>
	</div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />			
</body>
</html>