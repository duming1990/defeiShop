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
    <html-el:hidden property="wl_order_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <c:if test="${(orderInfo.order_type eq 10) or (orderInfo.order_type eq 7) or (orderInfo.order_type eq 100)}">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>快递公司名称：</td>
         <td>
          <html-el:hidden property="wl_comp_id" styleId="wl_comp_id"/>
          <input type="text" name="waybill_name" id="waybill_name" maxlength="50" readonly="readonly" onclick="getWlCompInfoList();"/>
         </td>
       </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>运单号：</td>
	      <td><html-el:text property="waybill_no" styleId="waybill_no" maxlength="50" style="width:100px" styleClass="webinput" /></td>
	   </tr>
	   </c:if>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item">发货备注：</td>
	      <td><html-el:text property="fahuo_remark" styleId="fahuo_remark" maxlength="100" style="width:220px" styleClass="webinput" /></td>
	   </tr>
       <tr>
        <td style="text-align:center" colspan="2">
          <html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	<c:if test="${(orderInfo.order_type eq 10) or (orderInfo.order_type eq 7)}">
	$("#wl_comp_id").attr("datatype", "Require").attr("msg", "请选择快递公司！");
	$("#waybill_name").attr("datatype", "Require").attr("msg", "请选择快递公司！");
	$("#waybill_no").attr("datatype", "Require").attr("msg", "请填写运单号！");
	</c:if>
	
	var f0 = $(".ajaxForm").get(0);
	f0.onsubmit = function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=updateState&id=${af.map.order_id}&state=20",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
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

function getWlCompInfoList(){
	var url = "${ctx}/CsAjax.do?method=getWlCompInfoList";
	$.dialog({
		title:  "选择快递公司",
		width:  400,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:999,
		content:"url:"+ encodeURI(url)
	});
}


function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
//]]></script>
</body>
</html>
