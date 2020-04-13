<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进货管理 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <form id="listForm" name="listForm" method="post" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%">序号</th>
        <th nowrap="nowrap">商品规格</th>
        <th width="10%">进货价</th>
        <th width="8%">商品库存</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
      	  <td align="center">${cur.tczh_name}</td>
      	  <td align="center"><fmt:formatNumber pattern="￥#,##0.00" value="${cur.cost_price}" /></td>
      	  <td align="center">${cur.inventory}</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".qtip").quicktip();
});
//]]></script>
</body>
</html>