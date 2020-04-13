<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="2">基本信息</th>
    </tr>
    <c:if test="${af.map.title_is_strong eq 1}" var="is_strong">
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">活动名称：</td>
        <td width="88%"><span style="color:${af.map.title_color}; font-weight:bold;">
          <c:out value="${af.map.title}" />
          </span></td>
      </tr>
    </c:if>
    
      <tr>
        <td nowrap="nowrap" class="title_item">开始时间：</td>
        <td><fmt:formatDate value="${af.map.invalid_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">结束时间：</td>
        <td><fmt:formatDate value="${af.map.invalid_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">排序值：</td>
      <td><c:out value="${af.map.order_value}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">信息状态：</td>
      <td><c:choose>
          <c:when test="${af.map.info_state eq 0}"> 默认 （待审核）</c:when>
          <c:when test="${af.map.info_state eq 3}"> <span style="color:#060;">已审核（已发布）</span> </c:when>
          <c:when test="${af.map.info_state eq -3}"> <span style="color:#f00;">已审核（未发布）</span> </c:when>
        </c:choose></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
    </tr>
  </table>
</div>

<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>