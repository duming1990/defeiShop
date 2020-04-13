<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/customer/EntpApply.do" enctype="multipart/form-data" styleClass="formSaveContent">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="saveContent" />
    <html-el:hidden property="entp_id" styleId="entp_id" value="${af.map.id}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td class="title_item">店铺详细信息：</td>
        <td colspan="3"><html-el:textarea property="entp_content" styleId="entp_content" style="width:650px;height:200px;visibility:hidden;" styleClass="webinput"></html-el:textarea>
          <div>点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
        </td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];  
$(document).ready(function(){
	var api = frameElement.api, W = api.opener;
	var editor = KindEditor.create("textarea[id='entp_content']");
	// 提交
	$("#btn_submit").click(function(){
		$("#entp_content").val(editor.html());
		if(Validator.Validate(f, 1)){
			
			$.jBox.tip("数据提交中...", 'loading');
			$("#btn_submit").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "EntpApply.do?method=saveContent",
					data: $('.formSaveContent').serialize(),
					dataType: "json",
					error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
					success: function(data) {
						$("#btn_submit").removeAttr("disabled");
						if(data.ret == "0"){
							$.jBox.tip(data.msg, "info");
						} else {
							$.jBox.tip(data.msg, "success",{timeout:1000});
							window.setTimeout(function () {
								W.windowReload();
							}, 1500);
						}
					}
				});	
	    	}, 1000);	
		}
	});
});
//]]></script>
</body>
</html>
