<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/MerchantCheckTwo.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
       
    <tr>
      <th colspan="4">结算审核</th>
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
<%--     ：${fn:escapeXml(cur.cash_rate)}元 --%>
      <td class="title_item">添加人姓名：</td>
      <td colspan="3">${fn:escapeXml(af.map.add_user_name)}</td>
    </tr>
    <tr>
      <td class="title_item">添加时间：</td>
      <fmt:formatDate  value="${af.map.add_date}" pattern="yyyy-MM-dd" var="add_date" />
      <td colspan="3">${add_date}</td>
    </tr>
    <tr>
      <td width="15%" class="title_item">手续费：</td>
      <td colspan="3"><html-el:text property="cash_rate" maxlength="20" styleId="cash_rate" style="width:200px" value="${af.map.cash_rate}"/><span>元</span></td>
    </tr>
     <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
            <td colspan="3">
               <html-el:select property="is_check" styleId="is_check" style="width:120px;">
	             <html-el:option value="0">待结算</html-el:option>
	             <html-el:option value="-1">结算失败</html-el:option>
	             <html-el:option value="1">结算成功</html-el:option>
	          </html-el:select>
            </td>
       </tr>
       
       <tr>
         <td class="title_item"><span style="color: #F00;">*</span>审核意见：</td>
         <td colspan="3">
         <html-el:text property="confirm_desc" maxlength="20" styleId="confirm_desc" style="width:200px" />
         </td>
       </tr>
      <tr>
        <td colspan="4" style="text-align:center">
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	$("#is_check").attr("datatype","Require").attr("msg","请选择审核状态");
	$("#confirm_desc").attr("datatype","Require").attr("msg","备注必须填写");
	$("#cash_rate").attr("datatype","Require").attr("msg","手续费必须填写");
});     
// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
