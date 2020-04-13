<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>增加对公账户 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
     <div>
      <div class="nav_xinping">
       <html-el:form action="/admin/ServiceInfoAudit.do" styleClass="formSavePayInfo">
       <html-el:hidden property="id"/>
       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
        <tr><th colspan="2" style="color:#555;">账户信息</th></tr>
       <tr>
       <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>对公账户名称：</td>
       <td width="88%">
		  <html-el:text property="brought_account" maxlength="120" styleId="brought_account" style="width:200px"/>
       </td>
       </tr>
       <tr>
       <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>对公账号：</td>
       <td width="88%">
          <html-el:text property="brought_account_no" maxlength="120" styleId="brought_account_no" style="width:200px"/>
      	</td>
       </tr>
        <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开户行名称：</td>
        <td width="88%">
         <html-el:text property="bank_name" maxlength="120" styleId="bank_name" style="width:200px"/>
        </td>
       </tr>
       <tr>
        <td colspan="3" style="text-align:center">
         <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
        </td>
       </tr>
      </table>
     </html-el:form>
    </div>
  </div>
<div class="clear"></div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];  
$(document).ready(function(){
 var api = frameElement.api, W = api.opener;
 
 $("#brought_account").attr("dataType", "Require").attr("msg", "请填写对公账户");
 $("#brought_account_no").attr("dataType", "Require").attr("msg", "请填写对公账号");
 $("#bank_name").attr("dataType", "Require").attr("msg", "请填写开户行名称");
	$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		
		$.jBox.tip("数据提交中...", 'loading');
		$("#btn_submit").attr("disabled", "true");
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "ServiceInfoAudit.do?method=saveBrought",
				data: $('.formSavePayInfo').serialize(),
				dataType: "json",
				error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
				success: function(data) {
					$("#btn_submit").removeAttr("disabled");
					if(data.ret == "0"){
						$.jBox.tip(data.msg, "info");
					} else {
						$.jBox.tip(data.msg, "success");
						W.refreshPage();
					}
				}
			});	
    	}, 1000);
	 }
	});
});
//]]></script>

</body>
</html>