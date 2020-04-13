<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/commons/swfupload/style/default.css" type="text/css" />
<style type="text/css">
.file_hidden_class {
	height:24px;
	border-top:1px #a8a8a8 solid;
	line-height:24px;
	padding-left:6px;
	border-left:1px #a8a8a8 solid;
	border-right:1px #e6e6e6 solid;
	border-bottom:1px #e6e6e6 solid;
}
</style>
</head>
<body  style="height:2000px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/LabelManager.do"  enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>标签名称：</td>
        <td colspan="2" width="88%">
          <html-el:text property="class_name" styleId="class_name" maxlength="6" style="width:90px" styleClass="webinput"/>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>排序值：</td>
        <td colspan="2" width="88%"><html-el:text property="order_value" styleId="order_value" maxlength="10" style="width:40px" styleClass="webinput"  value="0"/></td>
      </tr>
      <tr>
        <td colspan="3" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#class_name").attr("datatype", "Require").attr("msg", "请填写标签名称！");
	$("#order_value").attr("datatype", "Number").attr("msg", "请填写排序值！");
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
           $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
           $("#btn_reset").attr("disabled", "true");
           $("#btn_back").attr("disabled", "true");
		f.submit();
		}
	});
	});


//]]></script>
</body>
</html>
