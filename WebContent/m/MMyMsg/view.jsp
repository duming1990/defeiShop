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
      <h2><i class="t5"></i>站内通知</h2>
    </section>
    <section class="info-details"> <span class="item-label">发送人：</span> <span class="item-content">${af.map.user_name}</span> </section>
    <section class="info-details"> <span class="item-label">消息主题：</span> <span class="item-content">${af.map.msg_title}</span> </section>
    <section class="info-details"> <span class="item-label">消息内容：</span> <span class="item-content">${af.map.msg_content}</span> </section>
    <section class="info-details"> <span class="item-label">发送时间：</span> <span class="item-content">
      <fmt:formatDate value="${af.map.send_time}" pattern="yyyy-MM-dd HH:mm:ss" />
      </span> </section>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>
