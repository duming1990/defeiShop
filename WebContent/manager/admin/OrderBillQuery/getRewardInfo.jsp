<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div style="width:99%" class="divContent">
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="OrderBillQuery.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="25%">类型</th>
	    <th>操作前金额</th>
	    <th>本次金额</th>
	    <th>操作后金额</th>
	    <th>操作时间</th>
	  </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
      <td align="center">
      	<c:if test="${not empty cur.bi_get_type}">
        <c:forEach items="${biGetTypes}" var="keys">
          <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
        </c:forEach>
        </c:if>
        <c:if test="${empty cur.bi_get_type}">入池金额</c:if>
      </td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_before}" />
        ${bi}</td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no}" />${bi}</td>
      <td align="center"><fmt:formatNumber var="bi" pattern="0.########" value="${cur.bi_no_after}" />
        ${bi}</td>
      <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
      </c:forEach>
    </table>
  </form>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){
	
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />

</body>
</html>
