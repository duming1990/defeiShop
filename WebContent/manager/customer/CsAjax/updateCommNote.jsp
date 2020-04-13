<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<title>商品上架</title>
</head>
<body>
<div align="center">
  <html-el:form action="/customer/CsAjax.do" styleClass="ajaxForm">
    <html-el:hidden property="comm_id" styleId="comm_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>重要通知：</td>
        <td colspan="2"><html-el:textarea property="comm_notes" styleId="comm_notes" style="width:400px"/> 
        <div style="color:red;"><html-el:checkbox property="show_notes" styleId="show_notes" value="1"/><label for="show_notes"> 是否在商品页面显示此通知</label></div>
        </td>
      </tr>
      <tr>
          <td style="text-align:center" colspan="2">
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button></td>
        </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#comm_notes").attr("dataType", "Require").attr("msg", "请填写重要通知");
	var f0 = $(".ajaxForm").get(0);
	
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=saveCommNote",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							$.jBox.tip(data.msg, "success");
							 window.setTimeout(function () {
								returnTo();
							}, 1000);
						} else {
							$.jBox.tip(data.msg, "info");
						}
					}
				});	
			}, 1000);
			return true;
		}
		return false;
	});
});

function returnTo(){
	var api = frameElement.api, W = api.opener;
	api.close();
}


//]]></script>
</body>
</html>
