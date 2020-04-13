<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Authorsite" content="1" />
<meta name="Robots" content="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
<jsp:include page="../../_public_header.jsp" flush="true" />
<title>找回密码 - ${app_name}</title>
<style type="">
.form-field {margin: 0 auto 14px;width: 340px;}
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body>
<jsp:include page="./_header.jsp" flush="true" />
<div class="content">
<div id="bdw" class="bdw">
  <div id="bd" class="cf">
    <div class="content">
      <h3 class="headline"><span class="headline__content">找回密码</span> </h3>
      <ul class="steps-bar steps-bar--dark cf" style="margin-bottom: 10px;">
        <li class="step step--pre" style="z-index:3;width:33.333333333333336%;"><span class="step__num">1.</span> <span>选择验证方式</span> <span class="arrow__background"></span> <span class="arrow__foreground"></span> </li>
        <li class="step step--current" style="z-index:2;width:33.333333333333336%;"><span class="step__num">2.</span> <span>验证/修改</span> <span class="arrow__background"></span> <span class="arrow__foreground"></span> </li>
        <li class="step step--pre" style="z-index:1;width:33.333333333333336%;"><span class="step__num">3.</span> <span>完成</span> <span class="arrow__background"></span> <span class="arrow__foreground"></span> </li>
      </ul>
      <c:if test="${type eq 1}">
      <p class="verify-tip-title">您正在通过“绑定的手机”方式进行验证/修改</p></c:if>
      <c:if test="${type eq 2}">
      <p class="verify-tip-title">您正在通过“绑定的邮箱”方式进行验证/修改</p></c:if>
      <div class="common-tip sysmsgw" style="display:none;margin: 10px auto 10px;"> <span class="tip-status tip-status--error"></span><span id="validate-msg"></span></div>
      <html-el:form action="/GetPwBack.do" method="post" styleClass="verify-cont verify--info">
	  <html-el:hidden property="method" value="stepThree" />
	  <html-el:hidden property="id" />
	  <html-el:hidden property="type" styleId="type"/>
	  <html-el:hidden property="changeType" styleId="changeType" value="${changeType}"/>
        <div class="verify-help-title cf"> <span class="tip-status tip-status--large tip-status--large--info"></span>
        <c:if test="${af.map.type eq 1}">
          <h3 class="title title-singleline">为了您的账户安全，请先验证手机</h3></c:if>
        <c:if test="${af.map.type eq 2}">
          <h3 class="title title-singleline">为了您的账户安全，请先验证邮箱</h3></c:if>
        </div>
        <div class="mobile-verify-info">
        <c:if test="${af.map.type eq 1}">
          <div class="form-field form-field--smssender">
            <label>您绑定的手机号</label>
            <div style="padding-top: 6px;">
              <p> <span class="text color-highlight">${af.map.mobile}</span> </p>
            </div>
          </div>
          </c:if>
          <c:if test="${af.map.type eq 2}">
          <div class="form-field form-field--smssender">
            <label>您绑定的邮箱号</label>
            <div style="padding-top: 6px;">
              <p> <span class="text color-highlight">${af.map.email}</span> </p>
            </div>
          </div>
          </c:if>
          <div class="form-field form-field--smssender" style="overflow:hidden;">
            <label>验证码</label>
	         <input class="f-text verify-code" name="verificationCode" type="text" maxlength="4" id="veri_code" style="width:100px;"/>
	         <img height="36px" width="72px" class="signup-captcha-img"   id="veri_img" />
	         <a tabindex="-1" class="captcha-refresh J-captcha-refresh" href="javascript:updateVerCode();">看不清楚？换一张</a>
          </div>
          <c:if test="${af.map.type eq 1}">
          <div class="form-field" style="overflow:hidden;">
            <label>短信动态码</label>
            <input type="text" name="verifycode"  id="verifycode" class="f-text" maxlength="6" style="width:100px;float:left;margin-right:2px;"/>
             <div class="smssender-cont">
                 <span class="mcodepre">
                   <a class="mobilecodebtn" href="javascript:void(0);" id="sendMobileCode" onclick="getMessage();">获取短信验证码</a>
                 </span>
              </div>
          </div>
          <div class="common-bubble resend-tip">
	        <h4 class="resend-tip__head">没有收到验证码？</h4>
	        <ul class="resend-tip__list">
	            <li class="resend-message-tip__head">网络通讯异常可能会造成短信丢失，请重新发送短信</li>
                <li>请核实手机是否已欠费停机，或者屏蔽了系统短信</li>
                <c:url var="url" value="/GetPwBack.do?method=chooseAgain&user_id=${af.map.id}" />
                <li>如果手机${af.map.mobile}已丢失或停用，请选择<a href="${url}">选择其它验证方式</a></li>
	        </ul>
		    </div>
          </c:if>
          <c:if test="${af.map.type eq 2}">
          <div class="form-field" style="overflow:hidden;">
            <label>邮箱动态码</label>
            <input type="text" name="verifycode"  id="verifycode" class="f-text" maxlength="6" style="width:100px;float:left;margin-right:2px;"/>
             <div class="smssender-cont">
                 <span class="mcodepre">
                   <a class="mobilecodebtn" href="javascript:void(0);" id="sendEmail" onclick="sendEmailSms();">获取邮箱验证码</a>
                 </span>
              </div>
          </div>
          <div class="common-bubble resend-tip">
	        <h4 class="resend-tip__head">没有收到邮件？</h4>
	        <ul class="resend-tip__list">
	            <li>请先检查是否在垃圾邮件中。如果还未收到，请重新发送邮件<br /></li>
	            <c:url var="url" value="/GetPwBack.do?method=chooseAgain&user_id=${af.map.id}" />
	            <li>还是没收到？请选择<a href="${url}">其他找回方式</a></li>
	        </ul>
		    </div>
          </c:if>
        </div>
        <div class="form-field">
          <input type="button" id="btn_submit" class="btn next-step" value="下一步" /></div>
      </html-el:form>
    </div>
  </div>
</div>
</div>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	// 提交
	$("#btn_submit").click(function(){
		
		var verifycode = $("#verifycode").val();
		var veri_code = $("#veri_code").val();
		if(null == verifycode || '' == verifycode){
			$("#validate-msg").text("请输入短信动态码");
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

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

var state = 0;
function sendEmailSms(){
	var email = "${no_en_email}";
	if ("" == email && '' != email) {
		alert("邮箱地址有误！");
		return false;
	}
	
	if("" == $("#veri_code").val()){
		alert("请先输入验证码！");
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/Register.do" , 
			data:"method=sendEmail&email=" + email + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	if (result.state == 1) {
	        		$("#sendEmail").removeAttr("onclick");
					clock();
				}else{
					alert("发送短信失败，请联系管理员！");
				}
	        } 
		});
	}
}

function getMessage(){
	var mobile = "${no_en_mobile}";
	if ("" == mobile) {
		alert("手机号有误！");
		return false;
	}
	if("" == $("#veri_code").val()){
		alert("请先输入验证码！");
		$("#veri_code").focus();
		return false;
	}
		i = 120; 
		$.post("${ctx}/Register.do?method=sendMobileMessage",{"mobile":mobile,"veri_code":$("#veri_code").val(),"type":"2"},function(datas){
				if (datas.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					alert(datas.msg);
				}
		})
}
function clock() {
	i--;
	var type = $("#type").val();
	if(type == 1){
	$("#sendMobileCode").html(i + "秒后获取验证码");}
	if(type == 2){
	$("#sendEmail").html(i + "秒后获取验证码");}
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		
		if(type == 1){
		    $("#sendMobileCode").html("获取短信验证码");
			$("#sendMobileCode").attr("onclick","getMessage();");
		}else{
			$("#sendEmail").html("获取邮箱验证码");
			$("#sendEmail").attr("onclick","sendEmailSms();");
		}
	}
}


//]]></script>
</body>
</html>