<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改销量  - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/red/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- main start -->
<div>
  <div>
        <div class="nav_xinping">
          <html-el:form action="/admin/CommInfo.do" styleClass="formUpdateSaleCount">
            <html-el:hidden property="id" styleId="id" />
            <html-el:hidden property="mod_id"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
            <tr><th colspan="2" style="color:#555;">修改商品销量信息</th></tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item">真实销量：</td>
	        <td width="88%">${af.map.sale_count}</td>
	        </tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item">当前销售量：</td>
	        <td width="88%">${af.map.sale_count_update}</td>
	        </tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>增加/减少销售量：</td>
	        <td width="88%">
	        <html-el:text property="sale_count_update_add" styleId="sale_count_update_add" maxlength="6" style="width:100px" styleClass="webinput" />
	        </td>
	        </tr>
	        <tr>
	       <td colspan="3" style="text-align:center">
	        <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
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
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];  
$(document).ready(function(){
	
	var api = frameElement.api, W = api.opener;
	
	$("#sale_count_update").attr("dataType", "Integer").attr("msg", "请填写销售量");
	
	
	$("#btn_submit").click(function(){
	if(Validator.Validate(f, 1)){
		
		$.jBox.tip("数据提交中...", 'loading');
		$("#btn_submit").attr("disabled", "true");
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "CommInfo.do?method=saveSaleCount",
				data: $('.formUpdateSaleCount').serialize(),
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
	
	
	$("#btn_back").click(function(){
		api.close();
	});
});
//]]></script>

</body>
</html>