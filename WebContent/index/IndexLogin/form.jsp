<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登陆 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
</head>
<body class="pg-unitive-login theme--www">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="site-body-wrapper" style="margin:30px 0px;background:${baseFiles.file_desc};">
  <div class="site-body cf">
    <div class="promotion-banner"> 
    <c:set var="imgUrl" value="${ctx}/styles/index/images/loginImg.jpg"/>
    <c:if test="${not empty baseFiles}">
    <c:set var="imgUrl" value="${ctx}/${baseFiles.save_path}"/>
    </c:if>
    <img src="${imgUrl}"  width="500" height="384" alt="${app_name}" /> </div>
    <div class="login-section">
     <html-el:form action="/IndexLogin.do" styleClass="form form--stack">
      <html-el:hidden property="method" value="login" />
      <html-el:hidden property="returnUrl" />
        <div class="validate-info" style="display:none;">
        <i class="tip-status tip-status--opinfo"></i><span id="validate-msg"></span>
        </div>
        <span class="login-type">账号登录</span>
        <div class="form-field form-field--icon"><i class="icon icon-user"></i>
          <input type="text" id="user_name" class="f-text" name="user_name" placeholder="手机号/用户名" value="${login_name}" />
        </div>
        <div class="form-field form-field--icon"><i class="icon icon-password"></i>
          <input type="password" id="password" class="f-text" name="password" placeholder="密码" value="${password}" />
        </div>
        <div class="form-field form-field--captcha" id="tr_verificationCode" style="overflow:hidden;">
          <input type="text" id="veri_code" class="f-text" name="verificationCode" maxlength="4" placeholder="验证码" autocomplete="off" style="float:left;margin-right:5px;"/>
          <img class="signup-captcha-img" id="veri_img" width="105" height="34" src="${ctx}/images/loading_login.gif"/> 
          <a href="javascript:updateVerCode();" style="font-size: 12px;">看不清楚？<br/>换一张</a>
           </div>
        <div class="form-field form-field--auto-login cf"> 
        <c:url var="urlBack" value="/GetPwBack.do" />
        <a href="${urlBack}" target="_blank" class="forget-password">忘记密码？</a>
          <c:set var="is_rem" value="" />
          <c:if test="${not empty is_remember}">
          <c:set var="is_rem" value="checked='checked'" />
          </c:if>
          <input type="checkbox" value="1" name="is_remember" id="is_remember" ${is_rem} />
          <label class="normal" for="is_remember">7天内自动登录</label>
        </div>
        <div class="form-field form-field--ops">
          <input type="submit" class="btn" name="commit" id="commit" value="登录" />
        </div>
        <p class="signup-guide">还没有账号？
         <c:url var="url" value="/Register.do" />
        <a href="${url}" target="_blank">免费注册</a></p>
      </html-el:form>
<!--       <div class="oauth-wrapper"> -->
<!--         <h3 class="title-wrapper"><span class="title">用合作网站账号登录</span></h3> -->
<!--         <div class="oauth cf">  -->
<%--         <c:url var="qq" value="/IndexLogin.do?method=qq" /> --%>
<%--         <c:url var="weibo" value="/IndexLogin.do?method=weibo" /> --%>
<%--         <a class="oauth__link oauth__link--qq" href="${qq}" target="_blank"></a>  --%>
<%--         <a class="oauth__link oauth__link--weibo" href="${weibo}" target="_blank"></a>  --%>
<!--         </div> -->
<!--       </div> -->
    </div>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	//if ("${isEnabledCode}" == "0") {
	//	$("#tr_verificationCode").hide();
	//}
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).show();
	//$("#veri_code").focus(function(){
		//$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).show();
	//}).blur(function(){
		//$("#veri_img").hide().attr("src", "${ctx}/images/loading_login.gif");
	//});
	
});

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}


var f = $(".form--stack").get(0);
f.onsubmit = function () {
	var user_name = $("#user_name").val();
	var password = $("#password").val();
	if(null == user_name || '' == user_name){
		$("#validate-msg").text("请输入用户名");
		$(".validate-info").show();
		$("#user_name").focus();
		return false;
	}else if(null == password || '' == password){
		$("#validate-msg").text("请输入密码");
		$(".validate-info").show();
		$("#password").focus();
		return false;
	}
	//if ("${isEnabledCode}" == 1) {
		var veri_code = $("#veri_code").val();
		if(null == veri_code || '' == veri_code){
			$("#validate-msg").text("请输入验证码");
			$(".validate-info").show();
			$("#veri_code").focus();
			return false;
		}
	//}
	return true;
}
//]]></script>
</body>
</html>
