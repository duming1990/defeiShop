<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<c:forEach items="${pdInfoForHotSeacherList}" var="cur" varStatus="vs">
  <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
  <li>
    <c:if test="${vs.count le 9}"> <span><img src="${ctx}/styles/indexv2/images/xuhao00${vs.count}.png" /></span> </c:if>
    <c:if test="${vs.count gt 9}"> <span><img src="${ctx}/styles/indexv2/images/xuhao0${vs.count}.png" /></span> </c:if>
    <a  title="${fn:escapeXml(cur.comm_name)}"  href="${url}" target="_blank"> ${fn:escapeXml(fnx:abbreviate(cur.comm_name, 2 * 10, "..."))}</a></li>
  <c:if test="${vs.last eq true}">
    <c:set var="i" value="${vs.count}" />
  </c:if>
</c:forEach>
<c:forEach begin="${i}" end="10">
  <li>&nbsp;</li>
</c:forEach>
