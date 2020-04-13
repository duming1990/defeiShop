<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />

</head>
<c:set var="type_name" value="商家"/>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/TuiHuoAudit.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_audit" styleId="is_audit" value="true"/>
    <jsp:include page="_view.jsp" flush="true" />
    
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>

<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$(".cb").colorbox({rel:'cb'});
	
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_note").attr("dataType", "Require").attr("msg", "请填写审核意见！");
	var f = document.forms[0];

// 	$("#btn_submit").click(function(){
// 		if(Validator.Validate(f, 3)){
// 	            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
// 	            $("#btn_reset").attr("disabled", "true");
// 	            $("#btn_back").attr("disabled", "true");
// 				f.submit();
// 		}
// 		return false;
// 	});
	
	$("#btn_submit").click(function(){
		$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true")
	
		if(Validator.Validate(f, 1)){
				 $.ajax({
						type: "POST",
						url: "${ctx}/manager/admin/TuiHuoAudit.do?method=saveAudit",
						data: $(f).serialize(),
						dataType: "json",
						error: function(){alert("数据加载请求失败！");},
						success: function(data){
							if(data.code == 1){
								$.jBox.prompt(data.msg, '系统提示', 'info', { closed: function () {
									location.href="${ctx}/manager/admin/TuiHuoAudit.do?mod_id=${af.map.mod_id}";
								} });
								
							}else{
								
								$.jBox.prompt(data.msg, '系统提示', 'info', { closed: function () { location.reload(); } });
							}
						}
				   });
				 
		}
		return false;
	});
});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
