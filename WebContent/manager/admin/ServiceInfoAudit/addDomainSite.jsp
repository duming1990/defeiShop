<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置二级域名-${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
     <div>
      <div class="nav_xinping">
       <html-el:form action="/admin/ServiceInfoAudit.do" styleClass="formSavePayInfo">
       <html-el:hidden property="id"/>
       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
        <tr><th colspan="2" style="color:#555;">二级域名</th></tr>
       <tr>
       <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>添加二级域名：</td>
       <td width="88%">
		  <html-el:text property="domain_site" maxlength="120" styleId="domain_site" style="width:200px" value="${serviceCenterInfo.domain_site}"/>
		  &nbsp;&nbsp;&nbsp;<div>示例：如何设置 <span style="color:red">芜湖县域馆 wh.9tiaofu.com</span>的域名？请在上方输入框内输入 <span style="color:red">wh</span>  即可！</div>
       </td>
       </tr>
       <tr>
        <td colspan="3" style="text-align:center">
         <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
        <html-el:button property="" value="取消二级域名" styleClass="bgButton" styleId="clean_submit" />
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
 
 $("#domain_site").attr("dataType", "Require").attr("msg", "请输入二级域名");
	$("#btn_submit").click(function(){
	if(Validator.Validate(f,1)){
		$.jBox.tip("数据提交中...", 'loading');
		$("#btn_submit").attr("disabled", "true");
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "ServiceInfoAudit.do?method=saveDomainSite",
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
	$("#clean_submit").click(function(){
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "ServiceInfoAudit.do?method=delDomainSite",
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
	});
});
//]]></script>

</body>
</html>