<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收货地址修改 - ${app_name}</title>
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
          <html-el:form action="/admin/OrderQuery.do" styleClass="formSaveOrderAddress">
            <html-el:hidden property="order_id" styleId="order_id" />
            <html-el:hidden property="mod_id"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
            <tr><th colspan="2" style="color:#555;">修改收货信息</th></tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>收货人：</td>
	        <td width="88%">
	        <html-el:text property="rel_name" styleId="rel_name" maxlength="20" style="width:100px" styleClass="webinput" />
	        </td>
	        </tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>收货人电话：</td>
	        <td width="88%">
	       		<html-el:text property="rel_phone" styleId="rel_phone" maxlength="50" style="width:100px" styleClass="webinput" />
	       	</td>
	        </tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>收货区域：</td>
	        <td width="88%">
	        <select id="province" class="address-province dropdown--small" name="rel_province">
	          </select>
	          <select id="city" class="address-city dropdown--small" name="rel_city">
	          </select>
	          <select id="country" class="address-district dropdown--small" name="rel_region">
	        </select>
	       	</td>
	        </tr>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>详细地址：</td>
	        <td width="88%">
	       	<html-el:text property="rel_addr" styleId="rel_addr" maxlength="50" style="width:150px" styleClass="webinput" />
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
	$("#province").citySelect({
        data:getAreaDic(),
        province:"${af.map.rel_province}",
        city:"${af.map.rel_city}",
        country:"${af.map.rel_region}",
        province_required : true,
        city_required : true,
        country_required : true,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
 });
	 
	 
	 $("#rel_name").attr("dataType", "Require").attr("msg", "请填写收货人姓名");
	 $("#rel_addr").attr("dataType", "Require").attr("msg", "请填写地址");
	 $("#rel_phone").attr("dataType", "Mobile").attr("msg", "手机号码格式不正确！");
	
	
	$("#btn_submit").click(function(){
	if(Validator.Validate(f, 1)){
		
		$.jBox.tip("数据提交中...", 'loading');
		$("#btn_submit").attr("disabled", "true");
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "OrderQuery.do?method=saveOrderAddress",
				data: $('.formSaveOrderAddress').serialize(),
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