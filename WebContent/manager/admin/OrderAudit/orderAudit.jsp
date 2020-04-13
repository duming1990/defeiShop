<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单审核 - ${app_name}</title>
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
<div>
  <div>
        <div class="nav_xinping">
          <html-el:form action="/admin/OrderAudit.do" styleClass="formSaveFhInfo">
            <html-el:hidden property="id" styleId="id" />
            <html-el:hidden property="mod_id"/>
            <html-el:hidden property="queryString" styleId="queryString" value="${af.map.queryString}"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
            
      <tr id="tr_main_pic">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00" id="span_main_pic">*</span>交易凭证：</td>
        <td colspan="2">
          <c:set var="img" value="${ctx}/${af.map.main_pic}" />
          
          <img src="${img}" height="80" id="main_pic_img" />
          <html-el:hidden property="main_pic" styleId="main_pic" />
          <div class="files-warp" id="main_pic_warp">
            <div class="btn-files"> <span>上传主图</span>
              <input id="main_pic_file" type="file" name="main_pic_file" />
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
          </div>
         <span class="label label-danger">说明：每日最大派送金额${everyDayMaxSend},如果大于该金额，则需要上传交易凭证,并且后台进行审核！</span> 
        </td>
      </tr>
		     <tr>
		        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
		        <td colspan="2" width="88%"><html-el:select property="audit_state" styleId="audit_state">
		            <html-el:option value="">请选择...</html-el:option>
		            <html-el:option value="1">审核通过</html-el:option>
		            <html-el:option value="-1">审核不通过</html-el:option>
		          </html-el:select></td>
		     </tr>
	         <tr>
		      	<td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核说明：</td>
		      	<td colspan="2">
		      		<html-el:textarea property="audit_desc" styleClass="webinput" styleId="audit_desc"  style="width:250px; height:80px;" ></html-el:textarea>
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];  
$(document).ready(function(){
	var btn_name = "上传凭证";
	upload("main_pic", "image", btn_name, "${ctx}");
	
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_desc").attr("datatype","Limit").attr("min","1").attr("max","125").attr("msg","审核说明在125个汉字之内");
	$("#main_pic").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
			
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "OrderAudit.do?method=orderAuditSave",
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