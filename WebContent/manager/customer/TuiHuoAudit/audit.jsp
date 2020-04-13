<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情- ${app_name}</title>
<meta content="${app_name}订单管理" name="keywords" />
<meta content="${app_name}订单" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height: 1300px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  
  <jsp:include page="_view.jsp" flush="true" />
  
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("a.viewImgMain").colorbox();
	var audit_state = $("#audit_state").val();
	var return_way = $("#return_way").val();
	
	
	
// 	if(audit_state != 2){
// 		$("#_th_wl_company").hide();
// 		$("#_hh_wl_no").hide();
// 		$("#_return_addr").hide();
// 		$("#return_link_man").hide();
// 	}
	
// 	$("#audit_state").change(function(){
//         if("2" == $(this).val()){
//         	$("#_th_wl_company").show();
//     		$("#_hh_wl_no").show();
//     		$("#_return_addr").show();
//     		$("#return_link_man").show();
    		
//     		$("#hh_wl_company").attr("dataType", "Require").attr("msg", "请选择物流公司！");
// 	    	$("#return_addr").attr("dataType", "Require").attr("msg", "请填写退货地址！");
// 	    	$("#return_link_man").attr("dataType", "Require").attr("msg", "请填写退货联系人！");
// 	    	$("#hh_wl_no").attr("dataType", "Require").attr("msg", "请填写物流单号！");
        	
//         }else{
//         	$("#_th_wl_company").hide();
//     		$("#_hh_wl_no").hide();
//     		$("#_return_addr").hide();
//     		$("#return_link_man").hide();
    		
//     		$("#hh_wl_company").removeAttr("datatype");
//     		$("#hh_wl_no").removeAttr("datatype");
//     		$("#return_addr").removeAttr("datatype");
//     		$("#return_link_man").removeAttr("datatype");
//         }
// 	});
	

	
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#remark").attr("dataType", "Require").attr("msg", "请选择审核说明！");
	
	
	var f = document.forms[0];
	
	$("#btn_submit").click(function(){
		
		if(Validator.Validate(f, 3)){
	            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	            $("#btn_reset").attr("disabled", "true");
	            $("#btn_back").attr("disabled", "true");
				f.submit();
		}
		return false;
	});
});
//]]></script>
</body>
</html>
