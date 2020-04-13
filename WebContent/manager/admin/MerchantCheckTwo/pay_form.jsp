<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收货地址信息 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />

<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/red/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/public.js"></script>

<!-- main start -->
<div>
  <div>
        <div class="nav_xinping">
          <html-el:form action="/admin/MerchantCheckTwo.do" styleClass="formSaveFhInfo">
            <html-el:hidden property="id" styleId="id" />
            <html-el:hidden property="method" styleId="method" value="pay"/>
            <html-el:hidden property="mod_id"/>
            <html-el:hidden property="queryString" styleId="queryString" value="${af.map.queryString}"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" > <span style="color: #F00;">*</span>付款备注：</td>
	        <td width="88%">
	          <html-el:text property="pay_remarks" styleId="pay_remarks" maxlength="500" style="width:80%;" styleClass="webinput" />
	       	</td>
	        </tr>
	        <tr>
	       <td colspan="3" style="text-align:center">
	       <html-el:button property="" value="确认付款" styleClass="bgButton" styleId="btn_submit" />
           &nbsp;
           <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back"/>
          </td>
          </tr>
            </table>
          </html-el:form>
        </div>
    </div>
  </div>
  <div class="clear"></div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];  
	$("#pay_remarks").attr("datatype", "Require").attr("msg", "请添加付款备注！");
$(document).ready(function(){
	var api = frameElement.api, W = api.opener;
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
		    $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	        $("#btn_reset").attr("disabled", "true");
	        $("#btn_back").attr("disabled", "true");
			$.ajax({
				type: "POST",
				url: "${ctx}/manager/admin/MerchantCheckTwo.do?method=pay",
				data: $(f).serialize(),
				dataType: "json",
				error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
				success: function(data) {
					if(data.ret == "1"){
						$.jBox.tip(data.msg, "success");
						 setTimeout(function(){ 
							parent.location.reload();
							api.close();
						 }, 2500);
					} else {
						$.jBox.tip(data.msg, "info");
						$("#btn_submit").removeAttr("disabled");
					}
				}
			});	
		}
	});
	$("#btn_back").click(function(){
		api.close();
	});
});
//]]></script>

</body>
</html>