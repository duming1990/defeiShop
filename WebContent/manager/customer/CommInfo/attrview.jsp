<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- main start -->
<div class="mainbox mine">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
    <tr>
      <td width="12%" nowrap="nowrap" class="title_item">套餐主规格名称：</td>
      <td width="88%"><c:out value="${af.map.attr_name}"></c:out></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">排序值：</td>
      <td><c:out value="${af.map.order_value}"></c:out></td>
    </tr>
    <tr id="xz_hidden_tbody" >
      <td colspan="2"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="backTable">
          <tr>
            <th width="60%">套餐子规格名称</th>
            <th width="30%">排序值</th>
          </tr>
          <c:forEach var="cur" items="${sonList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap"><c:out value="${cur.attr_name}"></c:out>
                <c:if test="${af.map.is_show_pic eq 1}"> <img height="50" src="${ctx}/${cur.pic_path}@s400x400"/> </c:if>
              </td>
              <td align="center" nowrap="nowrap"><c:out value="${cur.order_value}"></c:out></td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><input type="button" value="返 回" onclick="history.back();" id="btn_back" class="bgButton" /></td>
    </tr>
  </table>
</div>
<!-- main end -->
</body>
</html>
