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
  <html-el:form action="/admin/CommentInfo" enctype="multipart/form-data">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">评论基本信息</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">评论标题：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.comm_title)}</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">评论内容：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.comm_content)}</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">评论回复：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.comm_reply)}</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">评论时间：</td>
        <td colspan="2" width="88%"><fmt:formatDate value="${af.map.comm_time}" pattern="yyyy-MM-dd"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">评论人：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.comm_uname)}</td>
      </tr>
      <c:if test="${not empty af.map.audit_user_id}">
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">评论状态：</td>
          <td colspan="2" width="88%"><c:if test="${af.map.comm_state eq -1}"> <span style="color:#F00;">
              <c:out value="审核不通过"/>
              </span> </c:if>
            <c:if test="${af.map.comm_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${af.map.comm_state eq 1}"> <span style="color:#060;">
              <c:out value="审核通过"/>
              </span> </c:if></td>
        </tr>
      </c:if>
      <tr>
        <td colspan="3" align="center"><html-el:button property="" value="返 回" styleClass="bgButton" onclick="history.back();"/></td>
      </tr>
    </table>
  </html-el:form>
</div>

<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
