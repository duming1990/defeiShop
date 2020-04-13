<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="15%" align="right"><span class="red">*</span>单位名称：</td>
    <td><html-el:text property="vat_companyname" styleId="vat_companyname" maxlength="80" /></td>
    <td width="15%" nowrap="nowrap" align="right"><span class="red">*</span>纳税人识别号：</td>
    <td><html-el:text property="vat_code" styleId="vat_code" maxlength="80" /></td>
  </tr>
  <tr>
    <td nowrap="nowrap" align="right"><span class="red">*</span>注册地址：</td>
    <td><html-el:text property="vat_address" styleId="vat_address" maxlength="80" /></td>
    <td nowrap="nowrap" align="right"><span class="red">*</span>注册电话：</td>
    <td><html-el:text property="vat_phone" styleId="vat_phone" maxlength="80" /></td>
  </tr>
  <tr>
    <td nowrap="nowrap" align="right"><span class="red">*</span>开户银行：</td>
    <td><html-el:text property="vat_bankname" styleId="vat_bankname" maxlength="80" /></td>
    <td nowrap="nowrap" align="right"><span class="red">*</span>银行帐户：</td>
    <td><html-el:text property="vat_bankaccount" styleId="vat_bankaccount" maxlength="80" /></td>
  </tr>
  <tr>
    <td colspan="4"><span style="color:#E26E23;padding-left: 60px;">注意：有效增值税发票开票资质仅为一个。</span></td>
  </tr>
</table>
