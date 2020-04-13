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
<div class="tipmsg">
  <c:forEach var="cur" items="${helpModulelist}">
    <c:url var="url" value="/m/MHelpDocument.do?method=view&h_mod_id=${cur.h_mod_id}" />
   	<a href="${url}"><p style="background: white;"><b>${cur.mod_name} </b></p></a>
  </c:forEach>
</div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
//]]></script>
</body>
</html>
