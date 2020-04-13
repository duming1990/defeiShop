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

      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">标题：</td>
        <td width="88%"><c:out value="${af.map.title}" /></td>
      </tr>
      <c:if test="${af.map.link_type eq 9013}">
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">副标题：</td>
        <td width="88%"><c:out value="${af.map.pre_varchar}" /></td>
      </tr>
      </c:if>
    <c:if test="${not empty (af.map.image_path)}">
   <c:if test="${af.map.link_type eq 1020 or af.map.link_type eq 70 or (af.map.link_type ge 121 and af.map.link_type le 124)}">
       <td nowrap="nowrap" class="title_item">轮播图片：</td>
        <td><img src="${ctx}/${af.map.image_path}@s400x400" title="${fn:escapeXml(af.map.image_desc)}" /></td>
        </c:if>
      <c:if test="${af.map.link_type ne 1020 and af.map.link_type ne 70 and (af.map.link_type lt 121 or af.map.link_type gt 124)}">
        <td nowrap="nowrap" class="title_item">图片：</td>
        <td><img src="${ctx}/${af.map.image_path}@s400x400" title="${fn:escapeXml(af.map.image_desc)}" /></td>
        </c:if>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">直接链接地址/商品名称：</td>
      <td><c:out value="${af.map.link_url}" /></td>
    </tr>
    <c:if test="${(af.map.link_type ge 411 and af.map.link_type le 414) or af.map.link_type eq 5020 or af.map.link_type eq 6060}">
    <tr>
      <td nowrap="nowrap" class="title_item">产品价格：</td>
      <td><fmt:formatNumber value="${af.map.pd_price}" pattern="0.00元"/></td>
    </tr>
	    <tr>
	      <td nowrap="nowrap" class="title_item">产品折扣：</td>
	      <td><fmt:formatNumber value="${af.map.pd_discount}" pattern="0.00折"/></td>
	    </tr>
    </c:if>
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