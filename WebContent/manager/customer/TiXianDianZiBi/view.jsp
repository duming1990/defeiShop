<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<title>${naviString}</title>
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr>
      <th colspan="4">提现信息</th>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">提现人：</td>
      <td colspan="3">${af.map.map.apply_user_name}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">提现金额：</td>
      <td colspan="3"><fmt:formatNumber pattern="#0.00" value="${af.map.cash_count}" /></td>
    </tr>
       <tr>
      <td width="14%" nowrap="nowrap" class="title_item">手续费：</td>
      <td colspan="3"><fmt:formatNumber pattern="#0.00" value="${af.map.cash_rate}" /></td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">实际提现金额：</td>
      <td colspan="3"><fmt:formatNumber pattern="#0.00" value="${af.map.cash_pay}" /></td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">申请时间：</td>
      <td colspan="3"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">申请说明：</td>
      <td colspan="3">${fn:escapeXml(af.map.add_memo)}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">审核人：</td>
      <td colspan="3">${fn:escapeXml(af.map.map.audit_user_name)}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">审核时间：</td>
      <td colspan="3"><fmt:formatDate value="${af.map.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">审核说明：</td>
      <td colspan="3">${fn:escapeXml(af.map.audit_memo)}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">审核状态：</td>
      <td colspan="3"><c:choose>
          <c:when test="${af.map.info_state eq 0}"><span class="label label-default">未审核</span></c:when>
          <c:when test="${af.map.info_state eq 1}"><span class="label label-info">已审核(待付款)</span></c:when>
          <c:when test="${af.map.info_state eq -1}"><span class="label label-danger">审核不通过(已退款)</span></c:when>
          <c:when test="${af.map.info_state eq 2}"><span class="label label-success">已付款</span></c:when>
          <c:when test="${af.map.info_state eq -2}"><span class="label label-danger">已退款</span></c:when>
          <c:otherwise>未定义</c:otherwise>
        </c:choose></td>
    </tr>
    <c:if test="${af.map.info_state eq -2}">
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">退款时间：</td>
      <td colspan="3"><fmt:formatDate value="${af.map.tuikuan_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">退款说明：</td>
      <td colspan="3">${fn:escapeXml(af.map.tuikuan_memo)}</td>
    </tr>
    </c:if>
    <tr>
      <td colspan="4" style="text-align:center"><input type="button" class="bgButton" value=" 返回 " onclick="history.back();" /></td>
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
