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
  <c:set var="base_name" value="内容" />
  <c:if test="${af.map.type eq 18000}"><c:set var="base_name" value="供应商名称" /></c:if>
  <c:if test="${af.map.type eq 1123}"><c:set var="base_name" value="产品类型" /></c:if>
  <html-el:form action="/customer/BaseData.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
       <tr>
        <th colspan="2">${base_name}</th>
      </tr>
      <tr>
      <td width="15%" class="title_item"><span style="color: #F00;">*</span>${base_name}：</td>
        <td width="85%"><html-el:text property="type_name" maxlength="50" styleClass="webinput" styleId="type_name" style="width:200px" />
          &nbsp;<span id="type_name_tip" style="display:none;"></span></td>
      </tr>
      <tr>
        <td class="title_item">备注：</td>
        <td><html-el:text property="remark" maxlength="200"  styleClass="webinput" styleId="remark" style="width:200px" />
        </td>
      </tr>
      <c:if test="${af.map.is_lock eq 0}">
      <tr>
        <td class="title_item">是否锁定：</td>
        <td><html-el:select property="is_lock" styleId="is_lock">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>（锁定后，数据将无法删除） </td>
      </tr>
      </c:if>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value"  maxlength="4" size="4" styleClass="webinput" styleId="order_value"  />值越大，显示越靠前，范围：0-9999 </td>
      </tr>
      <tr>
        <td class="title_item">提示：</td>
        <td> <span style="color: #E80909;">请勿随意修改基础数据，如需修改请和管理员确认！</span> </td>
      </tr>
      <c:if test="${af.map.is_del eq 1}">
        <tr>
          <td class="title_item">是否删除：</td>
          <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
        </tr>
      </c:if>
       <tr>
        <td colspan="2" style="text-align:center">
        <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
$("#type_name").attr("datatype","Require").attr("msg","${base_name}必须填写");
$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
// 提交
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
