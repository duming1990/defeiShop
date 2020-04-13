<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- main start -->
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
    <div class="clear"></div>
      <%@ include file="/commons/pages/messages.jsp" %>
      <html-el:form action="/customer/MyShippingAddress?method=save">
        <html-el:hidden property="id" styleId="id" />
        <html-el:hidden property="mod_id" />
        <html-el:hidden property="par_id" />
        <html-el:hidden property="queryString" styleId="queryString" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
          <tr>
            <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>收货人姓名：</td>
            <td><html-el:text property="rel_name" styleId="rel_name" maxlength="20" styleClass="webinput" /></td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>省份：</td>
            <td><select name="rel_province" id="province" style="width:120px;" >
                <option value="">请选择...</option>
              </select>
              <select name="rel_city" id="city" style="width:120px;">
                <option value="">请选择...</option>
              </select>
              <select name="rel_region" id="country" style="width:120px;">
                <option value="">请选择...</option>
              </select></td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>详细地址：</td>
            <td><span id="area_span">${af.map.full_name}</span>
              <html-el:text property="rel_addr" styleClass="webinput" maxlength="200" styleId="rel_addr" style="width:50%" /></td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>手机号码：</td>
            <td><html-el:text property="rel_phone" styleId="rel_phone" maxlength="11" styleClass="webinput" value="${userInfo.mobile}" /></td>
          </tr>
          <tr>
            <td class="title_item">发票：</td>
            <td>如需发票，请联系客服！</td>
          </tr>
          <tr>
            <td class="title_item">&nbsp;</td>
            <td style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
          </tr>
        </table>
      </html-el:form>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];                                            
$(document).ready(function(){

	$("#province").citySelect({
        data:getAreaDic(),
        province:"${af.map.rel_province}",
        city:"${af.map.rel_city}",
        country:"${af.map.rel_region}",
        province_required : true,
        city_required : true,
        country_required : true,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
 });
	
	$("#rel_province").change();
	$("#rel_province").change(function(){showAreaName();});
	$("#rel_city").change(function(){showAreaName();});
	$("#rel_region").change(function(){showAreaName();});
	
	$("#rel_name").attr("dataType", "Require").attr("msg", "真实姓名必须填写！");
	$("#rel_addr").attr("dataType", "Require").attr("msg", "详细地址必须填写！");
	$("#rel_phone").attr("datatype","Mobile").attr("Require","true").attr("msg","请输入正确的手机号！");
	$("#rel_email").attr("datatype","Email").attr("require","false").attr("msg","电子信箱填写有误！");

	$("#vat_companyname").attr("msg", "请填写单位名称");
	$("#vat_code").attr("msg", "请填写纳税人识别号");
	$("#vat_address").attr("msg", "请填写注册地址");
	$("#vat_phone").attr("msg", "请填写注册电话");
	$("#vat_bankname").attr("msg", "请填写开户银行");
	$("#vat_bankaccount").attr("msg", "请填写银行帐户");
	$("#invoices_payable").attr("msg", "请填写发票抬头");

	$("#invoice_type").change(function(){
		var this_value =  $(this).val();
		if (this_value == 1) {
			$("#invoice_info_tr").show();
			$("#vat_companyname").attr("dataType", "Require");
			$("#vat_code").attr("dataType", "Require");
			$("#vat_address").attr("dataType", "Require");
			$("#vat_phone").attr("dataType", "Require");
			$("#vat_bankname").attr("dataType", "Require");
			$("#vat_bankaccount").attr("dataType", "Require");
			
			$("#invoice_info_tr0").hide();
			$("#invoices_payable").removeAttr("dataType");
		} else if (this_value == 0) {
			$("#invoice_info_tr").hide();
			$("#vat_companyname").removeAttr("dataType");
			$("#vat_code").removeAttr("dataType");
			$("#vat_address").removeAttr("dataType");
			$("#vat_phone").removeAttr("dataType");
			$("#vat_bankname").removeAttr("dataType");
			$("#vat_bankaccount").removeAttr("dataType");
			
			$("#invoice_info_tr0").show();
			$("#invoices_payable").attr("dataType", "Require");
		} else {
			$("#invoice_info_tr").hide();
			$("#invoice_info_tr0").hide();
			$("#vat_companyname").removeAttr("dataType");
			$("#vat_code").removeAttr("dataType");
			$("#vat_address").removeAttr("dataType");
			$("#vat_phone").removeAttr("dataType");
			$("#vat_bankname").removeAttr("dataType");
			$("#vat_bankaccount").removeAttr("dataType");
		}
	});

	if ($("#invoice_type").val() == "1") {
		$("#invoice_info_tr").show();
		$("#vat_companyname").attr("dataType", "Require");
		$("#vat_code").attr("dataType", "Require");
		$("#vat_address").attr("dataType", "Require");
		$("#vat_phone").attr("dataType", "Require");
		$("#vat_bankname").attr("dataType", "Require");
		$("#vat_bankaccount").attr("dataType", "Require");
	}
	if ($("#invoice_type").val() == "0") {
		$("#invoice_info_tr0").show();
		$("#invoices_payable").attr("dataType", "Require");
	}
	
	var f = document.forms[0]; 

	//提交
	$("#btn_submit").click(function(){
		
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
	});
});

function showAreaName(){
	var rel_province = $("#rel_province option:selected").val() =="" ? "" :$("#rel_province option:selected").text();
	var rel_city = $("#rel_city option:selected").val() =="" ? "" :$("#rel_city option:selected").text();
	var rel_region = $("#rel_region option:selected").val() =="" ? "" :$("#rel_region option:selected").text();
	$("#area_span").empty().html(rel_province+rel_city+rel_region);
}

//]]></script>
</body>
</html>