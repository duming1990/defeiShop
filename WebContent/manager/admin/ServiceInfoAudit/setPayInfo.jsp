<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改支付信息 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div>
      <div class="nav_xinping">
        <html-el:form action="/admin/ServiceInfoAudit.do" styleClass="formSavePayInfo">
          <html-el:hidden property="id"/>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
          <tr><th colspan="2" style="color:#555;">支付信息</th></tr>
       <tr>
       <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>支付方式：</td>
       <td width="88%">
        <html-el:select property="pay_type" styleId="pay_type">
           <html-el:option value="">请选择...</html-el:option>
           <c:forEach var="cur" items="${payTypeList}">
           <html-el:option value="${cur.index}">${cur.name}</html-el:option>
           </c:forEach>
           </html-el:select>
       </td>
       </tr>
       <tr>
       <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>支付时间：</td>
       <td width="88%">
      		<html-el:text property="pay_date" styleId="pay_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="cursor:pointer;"  />
      	</td>
       </tr>
        <tr id="up_date_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>生效开始时间：</td>
        <td width="88%">
          <fmt:formatDate value="${af.map.effect_date}" pattern="yyyy-MM-dd" var="_effect_date" />
          <html-el:text property="effect_date" styleId="effect_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="cursor:pointer;" value="${_effect_date}" /></td>
      </tr>
      <tr id="down_date_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>生效结束时间：</td>
        <td width="88%">
          <fmt:formatDate value="${af.map.effect_end_date}" pattern="yyyy-MM-dd" var="_effect_end_date" />
          <html-el:text property="effect_end_date" styleId="effect_end_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="cursor:pointer;" value="${_effect_end_date}" /></td>
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
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];  
$(document).ready(function(){
 var api = frameElement.api, W = api.opener;
	 
 $("#pay_type").attr("dataType", "Require").attr("msg", "请选择支付方式");
 $("#pay_date").attr("dataType", "Require").attr("msg", "请选择支付时间");
 $("#effect_date").attr("dataType", "Require").attr("msg", "请选择生效开始时间");
 $("#effect_end_date").attr("dataType", "Require").attr("msg", "请选择生效结束时间");
	
	$("#btn_submit").click(function(){
	if(Validator.Validate(f, 1)){
		
		$.jBox.tip("数据提交中...", 'loading');
		$("#btn_submit").attr("disabled", "true");
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "ServiceInfoAudit.do?method=savePayInfo",
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