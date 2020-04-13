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
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="2">产品基本信息</th>
    </tr>
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">产品名称：</td>
      <td width="88%"><span style="font-weight:bold;">
        <c:out value="${af.map.cls_name}" />
        </span></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">父类产品名称：</td>
      <td><c:out value="${af.map.par_name}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">根类产品名称：</td>
      <td><c:out value="${af.map.root_name}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">是否删除：</td>
      <td><c:if test="${af.map.is_del eq 1}">
          <c:out value="是" />
        </c:if>
        <c:if test="${af.map.is_del eq 0}">
          <c:out value="否" />
        </c:if></td>
    </tr>
    <c:if test="${af.map.is_del eq 1}">
      <tr>
        <td nowrap="nowrap" class="title_item">删除人：</td>
        <td><c:out value="${af.map.map.del_name}" /></td>
      </tr>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">是否锁定：</td>
      <td><c:if test="${af.map.is_lock eq 1}">
          <c:out value="是" />
        </c:if>
        <c:if test="${af.map.is_lock eq 0}">
          <c:out value="否" />
        </c:if></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">排序值：</td>
      <td><c:out value="${af.map.order_value}" /></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
