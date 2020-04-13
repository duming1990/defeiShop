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
          <td width="12%" nowrap="nowrap" class="title_item">活动名称：</td>
          <td width="88%"><c:out value="${af.map.map.title}" /></td>
        </tr>
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">申请商家：</td>
          <td width="88%"><c:out value="${af.map.entp_name}" /></td>
        </tr>
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">商家用户：</td>
          <td width="88%"><c:out value="${af.map.user_name}" /></td>
        </tr>
        
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">商品：</td>
          <td width="88%">
          	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          	<c:forEach items="${list}" var="cur" varStatus="vs">
          		<tr>
		          <td width="3%" nowrap="nowrap" style="text-align: center;" class="title_item">${vs.count }</td>
		          <td ><c:out value="${cur.comm_name}" /></td>
		        </tr>
		        </c:forEach>
          	</table>
          </td>
        </tr>
      <tr>
        <td colspan="2" align="center"><input type="submit" value="审 核" class="bgButton" />
          &nbsp;&nbsp;
          <input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
      </tr>
    </table>
</div>
</body>
</html>