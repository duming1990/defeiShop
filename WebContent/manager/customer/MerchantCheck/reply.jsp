<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
   <html-el:form action="/customer/MerchantCheck.do" enctype="multipart/form-data">
    <input type="hidden" name="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="queryString" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="2">结算确认</th>
      </tr>
      <tr>
	      <td width="15%" class="title_item">订单金额：</td>
	      <td colspan="3">${fn:escapeXml(af.map.sum_order_money)}<span>元</span></td>
	  </tr>
      <tr>
	      <td width="15%" class="title_item">结算金额：</td>
	      <td colspan="3">${fn:escapeXml(af.map.sum_money)}<span>元</span></td>
	  </tr>
	  <tr>
	      <td class="title_item">添加人姓名：</td>
	      <td colspan="3">${fn:escapeXml(af.map.add_user_name)}</td>
	  </tr>
	  <tr>
	      <td class="title_item">添加时间：</td>
	      <fmt:formatDate  value="${af.map.add_date}" pattern="yyyy-MM-dd" var="add_date" />
	      <td colspan="3">${add_date}</td>
	  </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">开始时间：</td>
        <td width="88%"><fmt:formatDate value="${af.map.add_check_date}" pattern ="yyyy_MM-dd HH:mm:ss"/></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">结束时间：</td>
        <td width="88%"><fmt:formatDate value="${af.map.end_check_date}" pattern ="yyyy_MM-dd HH:mm:ss"/></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">商家确认状态：</td>
        <td width="88%">
        	<html-el:select property="confirm_state" styleId="confirm_state" style="width:120px;">
	             <html-el:option value="0">待确认</html-el:option>
	             <html-el:option value="1">确认通过</html-el:option>
	             <html-el:option value="-1">确认不通过</html-el:option>
	        </html-el:select>
        </td>
      </tr>
      <tr>
        <td align="center" colspan="2"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){
	
	
});
$("#btn_submit").click(function(){
	
	if(Validator.Validate(f, 3)){
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});
//]]></script>
</body>
</html>
