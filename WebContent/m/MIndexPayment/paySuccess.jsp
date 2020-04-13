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
<div class="order-tip">
  <h3>您已支付完成，感谢您在${app_name}消费。</h3>
</div>
<c:url var="url" value="/m/MChongZhiXiaoFeiBi.do?method=list&mod_id=1100400400"/>
<c:if test="${not empty isPayChongZhiEntpFxMoney}">
 <c:url var="url" value="/m/MChongZhiEntpFxMoney.do?method=list&mod_id=1300510130"/>
</c:if> 
<c:if test="${not empty isPayCityManger}">
 <c:url var="url" value="/m/MMyHome.do"/>
</c:if> 
  <div class="deal-btn" id="buybox">
  <a onclick="goUrl('${url}')"><label>查看详情</label></a></div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(function(){
	
});
//]]></script>
</body>
</html>