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
          <html-el:form action="/admin/OrderQuery.do" styleClass="formSaveFhInfo">
            <html-el:hidden property="order_id" styleId="order_id" />
            <html-el:hidden property="method" styleId="method" value="saveFh"/>
            <html-el:hidden property="mod_id"/>
            <html-el:hidden property="queryString" styleId="queryString" value="${af.map.queryString}"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
            <c:if test="${orderInfo.order_type ne 10}">
	            <tr><th colspan="2" style="color:#555;">物流信息</th></tr>
		        <tr>
		        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>快递公司名称：</td>
		        <td width="88%">
		         <html-el:select property="wl_comp_id" styleId="wl_comp_id">
		         <html-el:option value="">请选择</html-el:option>
		         <c:forEach items="${wlCompInfoList}" var="cur">
	                <html-el:option value="${cur.id}">${cur.wl_comp_name}</html-el:option>
	             </c:forEach>
	             </html-el:select>
		        </td>
		        </tr>
		        
		        <tr>
		        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>运单号：</td>
		        <td width="88%">
		          <html-el:text property="waybill_no" styleId="waybill_no" maxlength="50" style="width:100px" styleClass="webinput" />
		       	</td>
		        </tr>
	        </c:if>
	        <tr>
	        <td width="12%" nowrap="nowrap" class="title_item" ><span style="color: #F00;">*</span>发货备注：</td>
	        <td width="88%">
	          <html-el:text property="fahuo_remark" styleId="fahuo_remark" maxlength="50" style="width:100px" styleClass="webinput" />
	       	</td>
	        </tr>
	        <tr>
	       <td colspan="3" style="text-align:center">
	       <html-el:button property="" value="保存并且发货" styleClass="bgButton" styleId="btn_submit" />
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
$(document).ready(function(){
	<c:if test="${orderInfo.order_type ne 10}">
	$("#wl_comp_id").attr("datatype", "Require").attr("msg", "请选择快递公司！");
	$("#waybill_no").attr("datatype", "Require").attr("msg", "请填写运单号！");
	</c:if>
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
			
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "OrderQuery.do?method=saveFh",
					data: $('.formSaveFhInfo').serialize(),
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
	
	
	
	var api = frameElement.api, W = api.opener;
	$("#btn_back").click(function(){
		api.close();
	});
});
//]]></script>

</body>
</html>