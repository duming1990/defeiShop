<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
<title>找回密码 - ${app_name}</title>
<style type="">
.content {margin: 50px auto;max-width: 980px;}
.form-field {margin: 0 auto 14px;width: 340px;}
</style>
</head>
<body>
<jsp:include page="./_header.jsp" flush="true" />
<div class="common-tip sysmsgw" style="display:none;">
<span class="tip-status tip-status--error"></span><span id="validate-msg"></span></div>
<div class="content">
 <h3 class="headline">
 <c:set var="headName" value="找回登录密码"/>
 <c:if test="${af.map.changeType eq 2}">
 <c:set var="headName" value="找回支付密码"/>
 </c:if>
 <span class="headline__content">${headName}</span></h3>
    <ul class="steps-bar steps-bar--dark cf">
<li class="step step--first step--current">
    <span class="step__num">1.</span>
    <span>确认账号</span>
    <span class="arrow__background"></span><span class="arrow__foreground"></span>
</li>
<li class="step step--post">
    <span class="step__num">2.</span>
    <span>选择验证方式</span>
    <span class="arrow__background"></span><span class="arrow__foreground"></span>
</li>
<li class="step step--post">
    <span class="step__num">3.</span>
    <span>验证/修改</span>
    <span class="arrow__background"></span><span class="arrow__foreground"></span>
</li>
<li class="step step--last step--post">
        <span class="step__num">4.</span>
        <span>完成</span>
    </li>
</ul>

<div class="form__wrapper">
<html-el:form action="/GetPwBack.do" method="post">
<html-el:hidden property="method" value="stepOne" />
<html-el:hidden property="changeType" value="${af.map.changeType}" />
 <div class="form-field">
     <label>${app_name_min}账户</label>
     <input class="f-text account" name="user_name" id="user_name" type="text" placeholder="手机号/邮箱" maxlength="20"/>
     <span class="tip" style="display:none;"><i class="icon"></i><span></span></span>
     </div>
     <div class="form-field captcha cf">
         <label>验证码</label>
         <input class="f-text verify-code" name="verificationCode" type="text" maxlength="4" id="veri_code" style="width:100px;"/>
         <img height="36px" width="72px" class="signup-captcha-img"   id="veri_img" style="display:none;"/>
     </div>
     <div class="form-field">
         <input type="button" id="btn_submit" class="btn" value="下一步" />
     </div>
</html-el:form>
</div>
</div>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	
	$("#veri_code").focus(function(){
		$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).show();
	}).blur(function(){
		$("#veri_img").hide().attr("src", "${ctx}/images/loading_login.gif");
	});
	
	// 提交
	$("#btn_submit").click(function(){
		
		var user_name = $("#user_name").val();
		var veri_code = $("#veri_code").val();
		if(null == user_name || '' == user_name){
			$("#validate-msg").text("请输入账号");
			$(".common-tip").show();
			return false;
		}else if(null == veri_code || '' == veri_code){
				$("#validate-msg").text("请输入验证码");
				$(".common-tip").show();
				return false;
		}
		f.submit();
	});
});
//]]></script>
</body>
</html>