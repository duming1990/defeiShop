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
  <html-el:form action="/admin/UserInfo.do" styleClass="ajaxForm">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">登录名：</td>
        <td width="85%">${af.map.user_name }</td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
        <td>${af.map.real_name}</td>
      </tr>
      <tr>
        <th colspan="2">银行信息</th>
      </tr>
      <tr>
        <td class="title_item">开户银行：</td>
        <td width="85%"><html-el:text property="bank_name" styleClass="webinput" styleId="bank_name" maxlength="50" style="width:200px;" /></td>
      </tr>
      <tr>
        <td class="title_item">开户账号：</td>
        <td><html-el:text property="bank_account" styleClass="webinput" styleId="bank_account" maxlength="50" style="width:200px;" /></td>
      </tr>
      <tr>
        <td class="title_item">开户名：</td>
        <td width="85%"><html-el:text property="bank_account_name" styleClass="webinput" styleId="bank_account_name" maxlength="50" style="width:200px;" /></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
var $form  = $(".ajaxForm");
		var f = document.forms[0];
		
		$("#bank_name").attr("datatype","Require").attr("msg","请选择开户银行！");
		$("#bank_account").attr("datatype","Require").attr("msg","请填写开户账号！");
		$("#bank_account_name").attr("datatype","Chinese").attr("msg","请填写开户名,且只能是中文");
		

		// 提交
		var api = frameElement.api, W = api.opener;
		var f = document.forms[0];
		$("#btn_submit").click(function() {
			
			if (Validator.Validate(f, 3)) {
				
				$.ajax({
					type: "POST" , 
					url: "${ctx}/manager/admin/PoorManager.do?method=saveBankCard" , 
					data: $form.serialize(),
					dataType: "json", 
			        async: true, 
			        error: function(request, settings) {},
			        success: function (data) {
			        	alert(data.msg);
			        	
						if (data.ret == "-1") {
							return false;
						}else{
							W.location.reload()
							api.close();
						}
			        }
				});
			}
		});



//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
