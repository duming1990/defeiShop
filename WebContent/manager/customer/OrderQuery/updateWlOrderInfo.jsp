<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/red/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="nav_xinping">
<html-el:form action="/admin/OrderQuery.do" styleClass="formSaveWlOrderInfo">
	<html-el:hidden property="id" styleId="id" />
	<html-el:hidden property="order_id" styleId="order_id" />
	<html-el:hidden property="mod_id"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>快递公司名称：</td>
         <td>
         <html-el:select property="wl_comp_id" styleId="wl_comp_id">
	         <html-el:option value="">请选择</html-el:option>
	         <c:forEach items="${wlCompInfoList}" var="cur">
                <html-el:option value="${cur.id}">${cur.wl_comp_name}</html-el:option>
             </c:forEach>
             </html-el:select>
         </td>
       </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>运单号：</td>
	      <td><html-el:text property="waybill_no" styleId="waybill_no" maxlength="50" style="width:100px" styleClass="webinput" /></td>
	   </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item">发货备注：</td>
	      <td><html-el:text property="fahuo_remark" styleId="fahuo_remark" maxlength="100" style="width:220px" styleClass="webinput" /></td>
	   </tr>
       <tr>
        <td style="text-align:center" colspan="2">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];  
$(document).ready(function(){
	var api = frameElement.api, W = api.opener;
	$("#wl_comp_id").attr("datatype", "Require").attr("msg", "请选择快递公司！");
	$("#waybill_no").attr("datatype", "Require").attr("msg", "请填写运单号！");
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
			
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "OrderQuery.do?method=saveWlOrderInfo",
					data: $('.formSaveWlOrderInfo').serialize(),
					dataType: "json",
					error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
					success: function(data) {
						$("#btn_submit").removeAttr("disabled");
						if(data.ret == "0"){
							$.jBox.tip(data.msg, "info");
						} else {
							$.jBox.tip(data.msg, "success");
							W.windowReload();
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
