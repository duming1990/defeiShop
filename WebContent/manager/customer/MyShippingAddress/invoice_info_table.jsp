<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable" align="left">
  <tr>
    <td width="15%" class="title_item"><span class="red">*</span>单位名称：</td>
    <td><html-el:text property="vat_companyname" styleId="vat_companyname" styleClass="webinput" maxlength="50"/></td>
    <td width="15%" nowrap="nowrap" class="title_item"><span class="red">*</span>纳税人识别号：</td>
    <td><html-el:text property="vat_code" styleId="vat_code" styleClass="webinput" maxlength="50"/></td>
  </tr>
  <tr>
    <td nowrap="nowrap" class="title_item"><span class="red">*</span>注册地址：</td>
    <td><html-el:text property="vat_address" styleId="vat_address" styleClass="webinput" maxlength="50"/></td>
    <td nowrap="nowrap" class="title_item"><span class="red">*</span>注册电话：</td>
    <td><html-el:text property="vat_phone" styleId="vat_phone"  styleClass="webinput" maxlength="13"/></td>
  </tr>
  <tr>
    <td nowrap="nowrap" class="title_item"><span class="red">*</span>开户银行：</td>
    <td><html-el:text property="vat_bankname" styleId="vat_bankname" styleClass="webinput" maxlength="50"/></td>
    <td nowrap="nowrap" class="title_item"><span class="red">*</span>银行帐户：</td>
    <td><html-el:text property="vat_bankaccount" styleId="vat_bankaccount" styleClass="webinput" maxlength="20"/></td>
  </tr>
</table>
