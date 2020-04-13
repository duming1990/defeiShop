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
      <h2><i class="t5"></i>我的货款</h2>
    </section>
    <section class="info-details"> <span class="item-label">货款总额：</span> 
      <span class="item-content"><span class="price">¥
      <fmt:formatNumber pattern="#0.########" value="${af.map.bi_huokuan}" /> 元
      </span></span> </section>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	 var topBtnUrl = "${ctx}/m/MMyBiHuoKuan.do?method=list&mod_id=${af.map.mod_id}";
	 setTopBtnUrl(topBtnUrl);
	});
//]]></script>
</body>
</html>
