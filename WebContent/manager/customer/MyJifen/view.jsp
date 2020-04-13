<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr>
      <th colspan="4">返现卡信息</th>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">返现卡号：</td>
      <td >${cardInfo.card_no}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">持卡人：</td>
      <td >${userInfo.user_name}</td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.getElementById("form_bind");
$(document).ready(function(){
	
});
//]]></script>
</body>
</html>
