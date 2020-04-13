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
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <div class="section-detailbox">
    <section class="title">
      <h2><i class="t5"></i>我的增值券</h2>
    </section>
    <fmt:formatNumber var="fxb"  pattern="#0.########" value="${af.map.leiji_money_entp/rmb_to_fanxianbi_rate}" />
    <fmt:formatNumber var="fxb_to_rmb"  pattern="#0.########" value="${af.map.leiji_money_entp}" />
    <section class="info-details"> <span class="item-label">增值券总额：</span> 
      <span class="item-content"><span class=""> ${fxb}增值券 = ${fxb_to_rmb}元</span></span> </section>
    <section class="info-details"> <span class="item-label">说明：</span> 
      <span class="item-content tip-danger">100增值券=${rmb_to_fanxianbi_rate*100}元</span> </section>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	 var topBtnUrl = "${ctx}/m/MMyFxMoney.do?method=list&mod_id=${af.map.mod_id}";
	 setTopBtnUrl(topBtnUrl);
	});
//]]></script>
</body>
</html>
