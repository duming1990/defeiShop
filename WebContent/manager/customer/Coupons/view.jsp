<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
    <tr>
       <td width="14%" nowrap="nowrap" class="title_item">优惠券名称：</td>
       <td colspan="2">${fn:escapeXml(af.map.yhq_name)}</td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">商品名称：</td>
       <td colspan="2">${comm_name}</td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">优惠券已使用数量：</td>
       <td colspan="2">${fn:escapeXml(af.map.yhq_number_now)}</td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">优惠券使用规则：满多少可以用：</td>
       <td colspan="2">${fn:escapeXml(af.map.yhq_sytj)}</td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">优惠券金额：</td>
       <td colspan="2">${fn:escapeXml(af.map.yhq_money)}&nbsp;<span>元</span></td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">优惠券使用时间：</td>
       <td  colspan="2" height="24"><fmt:formatDate value="${af.map.yhq_start_date}" pattern="yyyy-MM-dd" var="_up_date" />
       <c:out value="${_up_date}"/></td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">优惠卷结束时间：</td>
       <td  colspan="2" height="24"><fmt:formatDate value="${af.map.yhq_end_date}" pattern="yyyy-MM-dd" var="_down_date" />
       <c:out value="${_down_date}"/></td>
    </tr>
    <tr>
       <td nowrap="nowrap" class="title_item">是否限制领取数量：</td>
       <td colspan="2"><c:if test="${af.map.is_limited eq 0}">
       <c:out value="不限制"/></c:if>
       <c:if test="${af.map.is_limited eq 1}">
       <c:out value="限制"/> </c:if></td>
    </tr>
    <c:if test="${af.map.is_limited eq 1}">
    <tr>
       <td nowrap="nowrap" class="title_item">限制领取数量：</td>
       <td colspan="2">${fn:escapeXml(af.map.limited_number)}</td>
    </tr>
    </c:if>
  </table>
  <div class="clear"></div>
</div>
<!-- main end -->
</body>
</html>
