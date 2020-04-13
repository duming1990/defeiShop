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
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr>
      <th colspan="4">我的余额</th>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">余额：</td>
      <td><fmt:formatNumber pattern="#0.##" value="${af.map.bi_dianzi}" />元
      </td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">待返余额：</td>
      <td><fmt:formatNumber pattern="#0.##" value="${af.map.bi_dianzi_lock}" />元
      </td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">余额说明：</td>
      <td><span class="label label-info">${baseData904.pre_number2}余额=${baseData904.pre_number}元</span></td>
    </tr>
    <tr>
      <td colspan="2" style="text-align:center">
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='MyBiDianZi.do?method=list&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-search"></i>查看明细</button>
     </td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.getElementById("form_bind");
$(document).ready(function(){
	
});
//]]></script>
</body>
</html>
