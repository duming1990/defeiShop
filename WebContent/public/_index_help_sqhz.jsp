<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<c:url var="url" value="javascript:void(0);" />
<c:forEach items="${helpModule10050000List}" var="cur" varStatus="vs">
  <c:if test="${cur.mod_url eq 'HelpInfo.do?method=single'}">
    <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=${cur.h_mod_id}" />
     <c:if test="${not empty server_min_domain}">
      <c:url var="url" value="http://www.${server_min_domain}/help-h_mod_id-${cur.h_mod_id}.shtml" />
    </c:if>
  </c:if>
  <c:if test="${cur.mod_url eq 'HelpInfo.do?method=list'}">
    <c:url var="url" value="/IndexHelpInfo.do?method=list&h_mod_id=${cur.h_mod_id}" />
     <c:if test="${not empty server_min_domain}">
      <c:url var="url" value="http://www.${server_min_domain}/IndexHelpInfo.do?method=list&h_mod_id=${cur.h_mod_id}" />
    </c:if>
  </c:if>
  <p><a title="${fn:escapeXml(cur.mod_name)}"  href="${url}" target="_blank"> ${fn:escapeXml(cur.mod_name)}</a> </p>
</c:forEach>
