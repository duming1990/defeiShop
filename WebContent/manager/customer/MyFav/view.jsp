<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的收藏 - ${app_name}</title>
<meta content="${app_name}我的收藏关键字" name="keywords" />
<meta content="${app_name}我的收藏介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../public_page_in_head.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
      <div class="all">
      <button class="bgButtonFontAwesome" type="button" onclick="history.back();"><i class="fa fa-history"></i>返回列表 </button>
      </div>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
         <tr>
        <td width="12%" nowrap="nowrap" class="title_item">商品或店铺名称：</td>
        <td colspan="2" width="88%">${fn:escapeXml(af.map.title_name)}</td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">收藏类型：</td>
        <td colspan="2" width="88%">
      	  <c:if test="${af.map.sc_type eq 1}">店铺</c:if>
          <c:if test="${af.map.sc_type eq 2}">商品</c:if>
        </td>
      </tr>
  	  <tr>
        <td width="12%" nowrap="nowrap" class="title_item">添加时间：</td>
        <td colspan="2" width="88%"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd" /></td>
      </tr>
      </table>
</body>
</html>