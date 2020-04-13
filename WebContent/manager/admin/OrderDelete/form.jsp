<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/admin/OrderDelete" styleClass="formClassSearch">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
 	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
          <th width="15%">请输入订单号：</th>
          <td><html-el:text property="trade_index" styleId="trade_index" maxlength="125" style="width:280px" styleClass="webinput"/>
          &nbsp;&nbsp;<html-el:button property="" value="下一步" styleClass="bgButton" styleId="btn_submit" />
          </td>
      </tr>

    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#trade_index").attr("datatype", "Require").attr("msg", "请填写订单号！");
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		$("#trade_index").val($.trim($("#trade_index").val()));
		if(Validator.Validate(f, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			f.submit();
		}
		return false;
	});
});
//]]>
</script>
</body>
</html>
