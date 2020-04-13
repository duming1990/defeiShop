<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触屏版-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <!--article-->
  <form action="/m/MMySecurityCenter" enctype="multipart/form-data" method="post" class="ajaxForm">
    <div class="set-site">
      <ul>
        <li id="old_userno"> <span style="width: 40%" class="grey-name">原会员卡：</span>
          <input style="width: 60%" readonly="true" value="${user.user_no}" type="text" autocomplete="off" maxlength="38" class="buy_input">
        </li>
        <li> <span style="width: 40%" class="grey-name">用户密码：</span>
          <input style="width: 60%" name="password" id="password" type="password" autocomplete="off" maxlength="38" class="buy_input">
        </li>
        <li> <span style="width: 40%" class="grey-name">会员卡：</span>
          <input style="width: 60%" name="card_no" id="card_no" type="text" autocomplete="off" maxlength="38" class="buy_input">
        </li>
        <li> <span style="width: 40%" class="grey-name">激活码：</span>
          <input style="width: 60%" name="card_pwd" id="card_pwd" type="text" autocomplete="off" maxlength="38" class="buy_input">
        </li>
        <li>
      <div class="box pop-vali">
      <input style="width:40%;" type="number" name="verificationCode" id="verificationCode" class="box-flex vali-input" placeholder="请输入图片验证码">
      <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" /> <a class="switch" onClick="updateVerCode();"><span>换一张</span></a> </div>
    <div class="tips" style="display:none;">请输入图片验证码</div>
      </li>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保存</a> </div>
  </form>
</div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	<c:if test="${empty user.user_no}">
		$("#old_userno").hide();
	</c:if>
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	$("#password").attr("datatype","Require").attr("msg","请填写用户密码");
	$("#card_no").attr("datatype","Require").attr("msg","请填写新会员卡");
	$("#card_pwd").attr("datatype","Require").attr("msg","请填写激活码");
	$("#captcha").attr("datatype","Require").attr("msg","请填写验证码");
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyUserNo",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								returnTo();
							}, 1000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
			}, 1000);
			return true;
		}
		return false;
	});
});

function returnTo(ret,msg){
	var api = frameElement.api, W = api.opener;
	W.refreshPage(ret,msg);
	api.close();
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}
//]]></script>
</body>
</html>
