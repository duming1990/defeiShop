<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<html-el:form action="/customer/MyOrderEntp.do" styleClass="ajaxForm">
    <html-el:hidden property="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">卡号：</td>
         <td>${af.map.card_no}</td>
       </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item">密码：</td>
	      <td>${pwd}</td>
	   </tr>
	   <tr>
	      <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>联系电话：</td>
	      <td><html-el:text property="received_mobile" styleId="received_mobile" maxlength="11" style="width:100px" styleClass="webinput" /></td>
	   </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item">备注：</td>
	      <td><html-el:text property="remark" styleId="remark" maxlength="60" style="width:220px" styleClass="webinput" /></td>
	   </tr>
       <tr>
        <td style="text-align:center" colspan="2">
          <html-el:submit property="" value="发送短信" styleClass="bgButton" styleId="btn_submit" />
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#received_mobile").attr("datatype", "Mobile").attr("msg", "请填写联系电话！");
	
	var f0 = $(".ajaxForm").get(0);
	f0.onsubmit = function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=msgActive",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.code == "1"){
							$.jBox.tip(data.msg, "success",{timeout:1000});
							window.setTimeout(function () {
								returnTo();
							}, 1500);
						} else {
							$.jBox.tip(data.msg, "info",{timeout:1000});
							$("#btn_submit").attr("value", "保 存").removeAttr("disabled");
						}
					}
				});	
			}, 1000);
			return false;
		}
		return false;
		
		
	}
});
function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
//]]></script>
</body>
</html>
