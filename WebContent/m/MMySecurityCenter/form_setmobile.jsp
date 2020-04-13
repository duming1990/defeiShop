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
          <li id="verificationCodeli">
            <div class="box pop-vali">
              <input style="width:40%;" type="number" name="verificationCode" id="verificationCode" class="box-flex vali-input" placeholder="请输入图片验证码">
              <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" /> <a class="switch" onClick="updateVerCode();"><span>换一张</span></a> </div>
            <div class="tips" style="display:none;">请输入图片验证码</div>
          </li>
        <div id="oldMobileVal">
<!--           <li> <span style="width: 30%" class="grey-name">原手机号：</span> -->
<%--             <input style="width: 35%" name="mobileOld" id="mobileOld" value="${user.mobile}" readonly="true" type="text" autocomplete="off" maxlength="38" class="buy_input"> --%>
<!--                       <input type="button" class="btnside zhaosubmit" id="sendMobileCode2" onClick="getMessage($('#mobileOld').val());" value="获取验证码" /> -->
<!--             <input style="width: 35%; margin: .12rem .2rem;color: #fff;" type="button" class="btnside zhaosubmit" id="sendMobileCode2" onClick="sendSms2()" value="获取验证码"> -->
<!--           </li> -->
<!--           <li> -->
<!--             <input style="width:70%;" id="ver_code_old" type="number" name="ver_code_old" class="box-flex vali-input" placeholder="请输入短信验证码" maxlength="6"> -->
<!--           </li> -->
<!--           <li> -->
<!--             <input style="width:70%;" id="password_pay" type="password" name="password_pay" class="box-flex vali-input" placeholder="请输入支付密码" maxlength="6"> -->
<!--           </li> -->
          
          <div class="box submit-btn"> <a class="com-btn" id="btn_submit_next">下一步</a> </div>
        </div>
        <div style="display:none;" id="newMobileVal">
          <li>
            <input style="width: 65%" name="mobile" id="mobile" type="text" autocomplete="off" placeholder="请输入新手机号" maxlength="38" class="buy_input">
            <input style="width: 30%; margin: .12rem .2rem;color: #fff;" type="button" class="btnside zhaosubmit" onClick="sendSms()" id="sendMobileCode" value="获取验证码">
          </li>
          <li>
            <input id="ver_code" type="number" name="ver_code" class="codebox-input zhaoinput" placeholder="请输入短信验证码" maxlength="6">
          </li>
          <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保    存</a> </div>
        </div>
      </ul>
    </div>
  </form>
</div>
<div class="pop-warp sms-captcha" style="display: none;" id="showSelectCode">
  <div class="pop-innr"> <i class="close" onClick="$(this).parents('.sms-captcha').hide();"></i>
    <div class="title">请输入图片验证码</div>
    <div class="box pop-vali">
      <input type="number" name="verificationCode" id="veri_code" class="box-flex vali-input" placeholder="请输入图片验证码">
      <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" /> <a class="switch" onClick="updateVerCode();"><span>换一张</span></a> </div>
    <div class="tips" style="display:none;">请输入图片验证码</div>
    <div class="box pop-bottom"> <a href="javascript:sendMessage();" class="box-flex next-step">下一步</a> </div>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<c:url var="urlmy" value="/m/MMyHome.do" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#ver_code_old").attr("dataType", "Require").attr("msg", "请填写手机动态码");
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
	$("#password_pay").attr("dataType", "Require").attr("msg", "请填写支付密码！");
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	<c:if test="${not empty af.map.isBind}">
		$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
		$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
		$("#verificationCodeliShow").html($("#verificationCodeli").html()).show();
		$("#oldMobileVal").remove();
		$("#newMobileVal").show();
		$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
		$("#verificationCode").val("");
		updateVerCode();
		
		$("#new_password").attr("dataType", "Require").attr("msg", "新密码不能为空");
		$("#repeat").attr("datatype", "Repeat").attr("to", "new_password").attr("msg", "两次输入的密码不一致");
		$("#user_name").attr("datatype","User_name").attr("msg","请填写正确的用户名格式：4-16字符的字母或数字或下划线组合！");
	</c:if>
	
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit_next").click(function(){
		
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					url: "MMySecurityCenter.do?method=valOldMobile",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if(data.ret == "1"){
							mui.toast(data.msg);
							$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
							$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
							//$("#verificationCodeliShow").html($("#verificationCodeli").html()).hide();
							$("#oldMobileVal").remove();
							$("#newMobileVal").show();
							$("#verificationCode").val("");
							updateVerCode();
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
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyMobile",
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

function returnTo(){
	location.href="${urlmy}";
}
var state = 0;        

function sendSms(){
	var mobile = $("#mobile").val();
	var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	if ("" == mobile) {
		mui.toast("请先输入手机号！");
		$("#mobile").focus();
		return false;
	}
	if (!mobile.match(reg)) {
		mui.toast("手机号码格式不正确！");
		$("#mobile").focus();
		return false;
	}
	if("" == $("#verificationCode").val()){
		mui.toast("请先填写验证码！");
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		Common.loading();
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/Register.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val()  + "&type=3&bdType=1&isValMobile=true" + "&t=" + new Date(),
			dataType: "json" , 
	        async: false, 
	        error: function (request, settings) {mui.toast(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	Common.hide();
	        	state= "0";
	        	if (result.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					mui.toast(result.msg);
				}
	        } 
		});
	}
}

function clock() {
	i--;
	$("#sendMobileCode").val(i + "秒重新获取"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").val("获取短信验证码"); 
		$("#sendMobileCode").attr("onclick","sendSms();");
		$("#mobile").removeAttr("readonly");
	}
}


function sendSms2(){
	var mobile = $("#mobileOld").val();
	if("" == $("#verificationCode").val()){
		mui.toast("请先填写验证码！");
		$("#veri_code").focus();
		return false;
	}
	Common.loading();
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/Register.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val()  + "&type=3&bdType=1" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {mui.toast(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	Common.hide();
	        	state= "0";
	        	if (result.ret == 1) {
	        		$("#sendMobileCode2").removeAttr("onclick");
					clock2();
				}else{
					mui.toast(result.msg);
				}
	        } 
		});
	}
}

function clock2() {
	i--;
	$("#sendMobileCode2").val(i + "秒重新获取"); 
	if(i > 0) {
		setTimeout("clock2();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").val("获取短信验证码");
		$("#sendMobileCode2").attr("onclick","sendSms2();");
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

//]]></script>
</body>
</html>
