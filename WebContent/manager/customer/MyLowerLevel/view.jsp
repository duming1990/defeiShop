<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<jsp:include page="../_nav.jsp" flush="true"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
  <tr>
    <td width="14%" nowrap="nowrap" class="title_item">上级用户名：</td>
    <td><c:out value="${userInfoSon.ymid}" /></td>
  </tr>
  <tr>
    <td nowrap="nowrap" class="title_item">用户名：</td>
    <td><c:out value="${userInfoSon.user_name}" /></td>
  </tr>
  <tr>
    <td nowrap="nowrap" class="title_item">添加时间：</td>
    <td><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd"/> </td>
  </tr>
  <tr align="center">
    <td colspan="2">
    <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
    </td>
  </tr>
</table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	
});
//]]></script>
</body>
</html>