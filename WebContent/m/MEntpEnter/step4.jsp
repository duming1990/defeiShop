<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta content="telephone=no" name="format-detection"/>
<meta name="wap-font-scale" content="no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${header_title}</title>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<style type="text/css">
.news-title{font-size:.4rem;line-height:.5rem;font-weight: bold;}
</style>
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="w">
<div class="section-detailbox">
 <section class="title">
 <h1 class="news-title" style="text-align: center;">${fn:escapeXml(mod_name)}</h1>
  <div class="subinfo">
    <div class="fr">
  </div>
  <div style="font-size: 0.6rem;text-align: center;color: #ec5051;">
  <c:if test="${af.map.audit_state eq 0}">正在审核中……</c:if>
  <c:if test="${af.map.audit_state eq 2}">审核通过</c:if>
  <c:if test="${af.map.audit_state eq -2}">审核不通过</c:if>
  </div>
  <c:if test="${not empty msg}"><div style="text-align: center;color: darkgoldenrod;font-size: 0.3rem;">${msg.msg_content}</div></c:if>
  <c:if test="${empty msg}"><div style="text-align: center;color: darkgoldenrod;font-size: 0.3rem;">感谢您在仁义联盟商城申请商家入驻！</div></c:if>
  <div>店铺名称：${af.map.entp_name}</div>
  <div>店铺简介：${af.map.entp_desc}</div>
  </section>
</div>
<div class="box submit-btn"> <a class="com-btn" id="btn_submit0" onclick="goUrl();">重新申请</a> </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript"> 

function goUrl(){
	location.href="MEntpEnter.do?";
}
</script> 
</body>
</html>

