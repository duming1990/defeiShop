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
.order-tip{
    padding: 0.9rem 4%;
    text-align: center;
    background-color: #fff;
}
.order-tip p{
    padding: .3rem 0px;
    color: green;
}
</style>
</head>
<body id="body" class="orderList">
<jsp:include page="../_header.jsp" flush="true" />
<c:set var="pay_state_tip" value="您已支付完成，感谢您在${app_name}消费。"/>
<c:if test="${pay_state eq -1}">
	<c:set var="pay_state_tip" value="支付失败，余额不足！"/>
</c:if>
<c:if test="${pay_state eq -2}">
	<c:set var="pay_state_tip" value="支付失败，订单状态已改变！"/>
</c:if>
<c:if test="${pay_state eq -3}">
	<c:set var="pay_state_tip" value="支付失败，红包余额不足！"/>
</c:if>
<div class="order-tip">
  <h3>${pay_state_tip}</h3>
  <p>订单号：${trade_index}</p>
</div>
<c:url var="url" value="/m/MMyOrder.do?order_type=${order_type}&mod_id=1100500100"/>
  <div class="deal-btn" id="buybox">
  <a onclick="goUrl('${url}')"><label>查看订单</label></a></div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(function(){
	
});
//]]></script>
</body>
</html>