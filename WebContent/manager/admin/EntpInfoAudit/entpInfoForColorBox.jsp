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
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="4">企业基本信息</th>
    </tr>
    <tr>
      <td width="15%" class="title_item">企业名称：</td>
      <td width="85%" colspan="3">${fn:escapeXml(af.map.entp_name)}</td>
    </tr>
    <tr>
      <th colspan="4">联系方式</th>
    </tr>
    <tr>
      <td class="title_item">所在地区：</td>
      <td><c:if test="${not empty af.map.map.full_name}">${fn:escapeXml(af.map.map.full_name)}</c:if></td>
      <td class="title_item">详细地址：</td>
      <td>${fn:escapeXml(af.map.entp_addr)}</td>
    </tr>
    <tr>
      <td class="title_item">邮编：</td>
      <td>${fn:escapeXml(af.map.entp_postcode)}</td>
      <td class="title_item">企业联系人：</td>
      <td>${fn:escapeXml(af.map.entp_linkman)}</td>
    </tr>
    <tr>
      <td class="title_item">联系电话：</td>
      <td>${fn:escapeXml(af.map.entp_tel)}</td>
      <td class="title_item">QQ：</td>
      <td>${fn:escapeXml(af.map.qq)}</td>
    </tr>
    <tr>
      <td class="title_item">传真：</td>
      <td>${fn:escapeXml(af.map.entp_fax)}</td>
      <td class="title_item">企业网址：</td>
      <td>${fn:escapeXml(af.map.entp_www)}</td>
    </tr>
    <tr>
      <th colspan="4">企业用户信息</th>
    </tr>
     <tr>
      <td width="15%" class="title_item">登录名：</td>
      <td width="85%" colspan="3">
      <c:forEach var="cur" items="${userInfoList}" varStatus="vs">
      ${fn:escapeXml(cur.user_name)}
      <c:if test="${vs.count < list_size}">,</c:if>
	</c:forEach>
      </td>
    </tr>
    <tr>
      <td class="title_item">真实姓名：</td>
      <td colspan="3">
       <c:forEach var="cur" items="${userInfoList}" varStatus="vs">
      ${fn:escapeXml(cur.real_name)}
        <c:if test="${vs.count < list_size}">,</c:if>
     </c:forEach>
      </td>
    </tr>
     <tr>
      <td class="title_item">手机：</td>
      <td colspan="3">
      <c:forEach var="cur" items="${userInfoList}" varStatus="vs">
      ${fn:escapeXml(cur.mobile)}
      <c:if test="${vs.count < list_size}">,</c:if>
      </c:forEach>
      </td>
    </tr>
    <tr>
      <td class="title_item">电子信箱：</td>
      <td colspan="3">
      <c:forEach var="cur" items="${userInfoList}" varStatus="vs">
      ${fn:escapeXml(cur.email)}
      <c:if test="${vs.count < list_size}">,</c:if>
      </c:forEach>
      </td>
    </tr>
  </table>
</div>
 
<script type="text/javascript">//<![CDATA[
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
