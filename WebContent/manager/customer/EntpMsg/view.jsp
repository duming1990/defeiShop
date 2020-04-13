<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <table width="100%" border="0" cellpadding="1" cellspacing="1" class="backTable">
    <tr>
      <th colspan="2">商家基本信息</th>
    </tr>
    <tr>
      <td  width="15%"  class="title_item">申请区域：</td>
      <td>${fn:escapeXml(af.map.p_name)}</td>
    </tr>
    <tr>
      <td class="title_item">申请标题：</td>
      <td>${fn:escapeXml(af.map.msg_title)}</td>
    </tr>
    <tr>
      <td class="title_item">申请内容：</td>
      <td>${fn:escapeXml(af.map.msg_content)}</td>
    </tr>
    <tr>
      <td class="title_item">申请时间：</td>
      <td><fmt:formatDate value="${af.map.send_time}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
    <tr>
      <td class="title_item">回复状态：</td>
      <td><c:choose>
          <c:when test="${af.map.info_state eq 0}"><span class="label label-danger">未回复</span></c:when>
          <c:when test="${af.map.info_state eq 1}"><span class="label label-success">已回复</span></c:when>
        </c:choose></td>
    </tr>
    <tr>
      <td class="title_item">回复时间：</td>
      <td><fmt:formatDate value="${af.map.reply_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
      <td class="title_item">回复人：</td>
      <td>${fn:escapeXml(af.map.reply_user_name)}</td>
    </tr>
    <tr>
      <td class="title_item">回复内容：</td>
      <td>${fn:escapeXml(af.map.reply_content)}</td>
    </tr>
    <tr>
      <td colspan="2" style="text-align:center"><input type="button" class="bgButton" value=" 返回 " onclick="history.back();" /></td>
    </tr>
  </table>
</div>
</body>
</html>
