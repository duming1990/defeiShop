<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/lightGallery/css/lightgallery.css"/>
</head>
<c:set var="type_name" value="商家"/>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/UserAudit.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
    <html-el:hidden property="link_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">用户名：</td>
        <td colspan="3">${fn:escapeXml(af.map.add_user_name)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">真实姓名：</td>
        <td colspan="3">${fn:escapeXml(af.map.opt_note)}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item">身份证号码：</td>
        <td colspan="3">${fn:escapeXml(id_card)}</td>
      </tr>
      <tr>
          <td class="title_item" nowrap="nowrap">身份证正面：</td>
          <td nowrap="nowrap" colspan="3" class="lightgallery"><c:if test="${not empty img_id_card_zm}"> <a href="${ctx}/${img_id_card_zm}@compress" title="身份证正面"> <img height="200" src="${ctx}/${img_id_card_zm}@s400x400" /></a> </c:if></td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap">身份证背面：</td>
          <td nowrap="nowrap" colspan="3" class="lightgallery"><c:if test="${not empty img_id_card_fm}"> <a href="${ctx}/${img_id_card_fm}@compress" title="身份证背面" > <img height="200" src="${ctx}/${img_id_card_fm}@s400x400" /></a> </c:if></td>
        </tr>
      <tr>
      <tr>
        <td width="15%" class="title_item">添加时间：</td>
        <td colspan="3"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      </tr>
        <th colspan="4">审核信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="3"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="-1">审核不通过</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item">审核意见:</td>
        <td colspan="3"><html-el:text property="audit_note" styleId="audit_note" maxlength="125" style="width:480px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="4" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/m/scripts/lightGallery/js/lightgallery-all.min.js?20180530"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$('.lightgallery').lightGallery({download:false});
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
// 	$("#audit_desc_two").attr("dataType", "Require").attr("msg", "请填写审核意见！");
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
	            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	            $("#btn_reset").attr("disabled", "true");
	            $("#btn_back").attr("disabled", "true");
				f.submit();
		}
		return false;
	});
});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
