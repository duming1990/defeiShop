<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<title>${naviString}</title>
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div class="all">
      <c:url var="url" value="/manager/customer/TiXianDianZiBi.do?method=list&par_id=1100400000&mod_id=1100400100" />
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='${url}'"><i class="fa fa-history"></i>查看提现历史 </button>
  </div>
  <html-el:form action="/customer/TiXianDianZiBi.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">提现信息</th>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">余额：</td>
        <td colspan="3"><fmt:formatNumber pattern="#0.########" value="${af.map.bi_dianzi-af.map.bi_fuxiao}" var="bi_dianzi" />
        <html-el:hidden property="bi_dianzi" value="${af.map.bi_dianzi}" styleId="bi_dianzi" />
        <html-el:hidden property="bi_dianzi_real" value="${dianzi_2_rmb}" styleId="bi_dianzi_real" />
          ${bi_dianzi}元
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">可提现余额：</td>
        <td colspan="3">
        	<fmt:formatNumber pattern="#0.########" value="${dianzi_2_rmb}" var="bi" />
          	<html-el:hidden property="dianzi_2_rmb" value="${dianzi_2_rmb}" styleId="dianzi_2_rmb" />
          	${bi}元  
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>提现金额：</td>
        <td colspan="3"><html-el:text property="cash_count" maxlength="10" styleId="cash_count" style="width:100px"/>
          &nbsp;元<span id="tip" class="label label-danger" style="margin-left: 5px;"></span>
          <c:if test="${pre_number ne 0}">
          <span class="label label-info" style="margin-left:5px;">余额提现手续费${pre_number}‰</span>
          </c:if>
          </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">申请说明：</td>
        <td colspan="3"><html-el:text property="add_memo" styleId="add_memo" style="width:90%" maxlength="100"/></td>
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
<c:set var="tip_msg" value=""/>
<c:set var="tip_url" value="MySecurityCenter.do?par_id=1100620000&mod_id=1100620100"/>
<c:if test="${not empty af.map.renzheng_is_empty}">
<c:set var="tip_msg" value="请先前往进行实名认证"/>
</c:if>
<c:if test="${not empty af.map.bank_account_is_empty}">
<c:set var="tip_msg" value="请先前往安全中心维护银行账号"/>
</c:if>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js?2018-04-12"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){

	$("#cash_count").attr("datatype","MoneyVerify").attr("msg","请正确填写提现金额！例如88.88");
	$("#add_memo").attr("datatype","LimitB").attr("max","200").attr("msg","申请说明不能超过200个字！");
	setTimeout(function(){
		$("#cash_count").val("");
		$("#password_pay").val("");
	},100);	
	<c:if test="${not empty tip_msg}">
		
	setTimeout(function(){
		$("#btn_submit").val("${tip_msg}").attr("disabled", "true").addClass("disabled");	
	},100);
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href="${tip_url}";
	    }
	    return true;
	};
	myConfirm("${tip_msg}",submit);
	</c:if>
	
});     

$("#cash_count").change(function(){
	var tv = parseFloat($(this).val());
	
	var bi_dianzi = parseFloat($("#bi_dianzi_real").val());
	if (tv > (bi_dianzi)){
		$("#tip").html("超过提现金额限制，请重新选择提现金额");
		$("#btn_submit").attr("disabled","disabled").addClass("disabled");
	}else {
		$("#tip").empty();
		$("#btn_submit").removeAttr("disabled").removeClass("disabled");
	}
});


// 提交
$("#btn_submit").click(function(){
	
	if(Validator.Validate(f, 3)){
		 var cash_count = $("#cash_count").val();
		 if(cash_count <= 0) {
			 $.jBox.info("提现金额不能小于等于0", '系统提示');
			 return false;
		 }
		 if(cash_count<${cash_Min}) {
			 $.jBox.info("请正确填写提现金额，最低提现金额是"+${cash_Min}+"元！", '系统提示');
			 return false;
		 }
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true} });
}
                                                                                
//]]></script>
</body>
</html>
