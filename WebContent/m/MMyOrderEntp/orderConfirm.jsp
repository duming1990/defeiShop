<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body>
<div class="content">
  <html-el:form action="/MyOrderEntp.do" enctype="multipart/form-data" styleClass="ajaxForm0">
    <div class="set-site">
      <ul class="formUl">
         <li><span class="grey-name">订单密码：</span>
        <input type="number" name="order_password" placeholder="请输入订单八位密码" id="order_password" maxlength="8" />
        </li>
      </ul>
    </div>
    <div class="box submit-btn">
    <input type="button" class="com-btn" id="btn_submit" value="保存" /></div>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	
	$("#order_password").attr("datatype","Require").attr("msg","请输入订单八位密码！");
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit").click(function(){
		if(Validator.Validate(f0, 1)){
			 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			 $.ajax({
					type: "POST",
					url: "?method=updateState&id=${af.map.order_id}&state=40",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							window.setTimeout(function () {
								returnTo();
							}, 500);
						}else {
							mui.toast(data.msg);
							$("#btn_submit").attr("value", "保 存").removeAttr("disabled");
						}
					}
				});	
			return true;
		}
	});
});

function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
//]]></script>
</body>
</html>
