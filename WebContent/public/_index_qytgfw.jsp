<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<c:forEach items="${entpPromoteApplyList}" var="cur" varStatus="vs">
  <c:url var="url" value="${cur.entp_uri}" />
  <li><a href="${url}" title="${fn:escapeXml(cur.title)}" target="_blank">
  	<c:set var="imgurl" value="${ctx}/images/no_image.jpg"/>
  	<c:if test="${not empty cur.image_path}">
    	<c:set var="imgurl" value="${ctx}/${fn:substringBefore(cur.image_path, '.')}_240.${fn:substringAfter(cur.image_path, '.')}"/>
  	</c:if>
  	<img alt="${fn:escapeXml(cur.title)}" src="${imgurl}" height="61" width="170"/></a>
 </li>
  <c:if test="${vs.last eq true}">
    <c:set var="i" value="${vs.count}" />
  </c:if>
</c:forEach>
<c:forEach begin="${i}" end="3">
  <li style="border: 0;">&nbsp;</li>
</c:forEach>


