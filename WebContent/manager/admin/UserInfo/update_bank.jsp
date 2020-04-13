<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
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
  <html-el:form action="/admin/UserInfo.do" styleClass="formSave">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">银行信息</th>
      </tr>
      <tr>
        <td class="title_item">开户银行：</td>
        <td width="85%"><html-el:text property="bank_name" styleClass="webinput" styleId="bank_name" maxlength="50" style="width:300px;" /></td>
      </tr>
      <tr>
        <td class="title_item">开户账号：</td>
        <td><html-el:text property="bank_account" styleClass="webinput" styleId="bank_account" maxlength="50" style="width:300px;" /></td>
      </tr>
      <tr>
        <td class="title_item">开户名：</td>
        <td width="85%"><html-el:text property="bank_account_name" styleClass="webinput" styleId="bank_account_name" maxlength="50" style="width:300px;" /></td>
      </tr>
      
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
	
		$("#bank_name").attr("datatype", "Require").attr("msg", "开户银行必须填写,");
		$("#bank_account").attr("datatype", "Require").attr("msg", "开户账号必须填写");
		$("#bank_account_name").attr("datatype", "Require").attr("msg", "开户名必须填写");
		

		$("#btn_submit").click(function() {
			if (Validator.Validate(this.form, 3) ) {
				$.jBox.tip("数据提交中...", 'loading');
				$("#btn_submit").attr("disabled", "true");
				window.setTimeout(function () { 
					$.ajax({
						type: "POST",
						url: "UserInfo.do?method=updateBank",
						data: $('.formSave').serialize(),
						dataType: "json",
						error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
						success: function(data) {
							$("#btn_submit").removeAttr("disabled");
							if(data.ret == "0"){
								$.jBox.tip(data.msg, "info");
							} else {
								$.jBox.tip(data.msg, "success");
							}
						}
					});	
		    	}, 1000);
			}
		});
		


//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
