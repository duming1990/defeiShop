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
      <th colspan="2">站内通知</th>
    </tr>
    <tr>
      <td width="15%" class="title_item">接收人：</td>
      <td><c:if test="${af.map.is_all eq 1}" var="isall">全部用户</c:if>
        <c:if test="${af.map.is_all eq 0}">
          <c:forEach var="cur" items="${smrList}"> [${cur.map.user_name}] </c:forEach>
        </c:if></td>
    </tr>
    <tr>
      <td width="15%" class="title_item">信息主题：</td>
      <td>${af.map.title}</td>
    </tr>
    <tr>
      <td class="title_item">信息内容：</td>
      <td><c:if test="${not af.map.message}">${af.map.message}<br/></c:if>
	     <%-- <c:if test="${not af.map.message1}">${af.map.message1}<br/></c:if>
	     <c:if test="${not af.map.message2}">${af.map.message2}<br/></c:if>
	     <c:if test="${not af.map.message3}">${af.map.message3}<br/></c:if>
	     <c:if test="${not af.map.message4}">${af.map.message4}</c:if></td> --%>
    </tr>
    <tr>
      <td class="title_item">发送时间：</td>
      <td><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
    <tr>
      <td class="title_item">是否删除：</td>
      <td><c:choose>
          <c:when test="${af.map.is_del eq 0}"> 未删除 </c:when>
          <c:when test="${af.map.is_del eq 1}"> 已删除 </c:when>
        </c:choose></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="button" class="bgButton" value="返回" onclick="history.back();" /></td>
    </tr>
  </table>
</div>

</body>
</html>
