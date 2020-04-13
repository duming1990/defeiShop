<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<c:forEach items="${base88LinkList}" var="cur" varStatus="vs"> 
<c:set var="aClass" value="" />
<c:if test="${vs.count eq 1}">
<c:set var="aClass" value="hot-link--first" />
</c:if>
<a class="hot-link log-acm-viewed ${aClass}" href="${cur.link_url}">${fn:escapeXml(cur.title)}</a>
</c:forEach>