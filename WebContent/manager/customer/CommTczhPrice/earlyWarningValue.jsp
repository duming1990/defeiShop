<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/CommTczhPrice.do" styleClass="saveForm">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td width="25%" class="title_item"><span style="color: #F00;">*</span>库存预警值：</td>
        <td width="85%">
          <html-el:text property="early_warning_value" maxlength="20" styleClass="webinput" styleId="early_warning_value" style="width:200px"/>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center">
        <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
     </td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[

		var api = frameElement.api, W = api.opener;                                  
		var f = document.forms[0];

		$("#early_warning_value").attr("datatype", "Number").attr("msg", "库存预警值必须填写");

		$("#btn_submit").click(function(){
			if(Validator.Validate(f, 3)){
				
				$.jBox.tip("正在操作...", 'loading');
				
				$.ajax({
					type: "POST" , 
					url: "CommTczhPrice.do", 
					data:"method=save&"+ $(".saveForm").serialize(),
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {}, 
			        success: function (data) {
						if (data.code == 1) {
							$.jBox.tip("操作成功", "success",{timeout:1000});
						} else {
							$.jBox.tip(data.msg, "error",{timeout:1000});
						}
						window.setTimeout(function(){
							W.refreshPage();	
						},1500);
			        }
				});
			}
		});
//]]>
</script>
</body>
</html>
