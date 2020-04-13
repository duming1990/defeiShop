<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:1500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="PoorManager.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="10%">户主姓名</th>
        <th width="8%">用户编码</th>
        <th width="5%">民族</th>
        <th width="5%">性别</th>
        <th width="5%">家庭人口</th>
        <th width="15%">家庭住址</th>
        <th width="8%">电话</th>
        <th width="10%">扶贫金额</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${fn:escapeXml(cur.real_name)}</td>
          <c:if test="${not empty cur.user_name}">
          	<td align="center">${fn:escapeXml(cur.user_name)}</td>
          </c:if>
          <c:if test="${empty cur.user_name}">
          	<td align="center">---</td>
          </c:if>
          <td align="center">${fn:escapeXml(cur.nation)}</td>
          <td align="center">
          	<c:if test="${cur.sex eq 1}">女</c:if>
          	<c:if test="${cur.sex eq 0}">男</c:if>
		  </td>
		  <td align="center">${fn:escapeXml(cur.family_num)}</td>
          <td align="center">${fn:escapeXml(cur.addr)}</td>
          <td align="center">${fn:escapeXml(cur.mobile)}</td>
          <td align="center"><fmt:formatNumber pattern="#,##0.00" value="${cur.map.aid_money}"/></td>
        </tr>
      </c:forEach>
      <tr>
      	<td align="center" colspan="8"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">
</script>
</body>
</html>
