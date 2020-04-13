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
      <h2><i class="t5"></i>提现信息</h2>
    </section>
    <section class="info-details"> <span class="item-label">提现人：</span> <span class="item-content">${af.map.map.apply_user_name}</span> </section>
    <section class="info-details"> <span class="item-label">提现金额：</span> <span class="item-content"><span class="price">¥
      <fmt:formatNumber pattern="#0.00" value="${af.map.cash_count}" />
      </span></span> </section>
    <section class="info-details"> <span class="item-label">实际提现金额：</span> <span class="item-content"><span class="price">¥
      <fmt:formatNumber pattern="#0.00" value="${af.map.cash_pay}" />
      </span></span> </section>
    <section class="info-details"> <span class="item-label">申请时间：</span> <span class="item-content">
      <fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" />
      </span> </section>
    <section class="info-details"> <span class="item-label">申请说明：</span> <span class="item-content">${fn:escapeXml(af.map.add_memo)}</span> </section>
    <c:if test="${af.map.cash_type eq 40}">
    <section class="info-details"> 
	    <span class="item-label">凭证图片：</span> 
	    <img src="${ctx}/${af.map.proof_img}"/>
    </section>
    </c:if>
   
    <section class="info-details"> <span class="item-label">审核人：</span> <span class="item-content">${fn:escapeXml(af.map.map.audit_user_name)}</span> </section>
    <section class="info-details"> <span class="item-label">审核时间：</span> <span class="item-content">
      <fmt:formatDate value="${af.map.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" />
      </span> </section>
    <section class="info-details"> <span class="item-label">审核说明：</span> <span class="item-content">${fn:escapeXml(af.map.audit_memo)}</span> </section>
    <section class="info-details"> <span class="item-label">审核状态：</span> <span class="item-content">
      <c:choose>
        <c:when test="${af.map.info_state eq 0}"><span class="label label-default">未审核</span></c:when>
        <c:when test="${af.map.info_state eq 1}"><span class="label label-info">已审核(待付款)</span></c:when>
        <c:when test="${af.map.info_state eq -1}"><span class="label label-danger">审核不通过(已退款)</span></c:when>
        <c:when test="${af.map.info_state eq 2}"><span class="label label-success">已付款</span></c:when>
        <c:when test="${af.map.info_state eq -2}"><span class="label label-danger">已退款</span></c:when>
        <c:otherwise>未定义</c:otherwise>
      </c:choose>
      </span> </section>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>
