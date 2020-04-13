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
          <li>
            <input style="width: 65%" name="mobile" id="mobile" type="text" autocomplete="off" placeholder="请输入手机号，可用手机号登陆" maxlength="38" class="buy_input" />
            <input style="width: 30%; margin: .12rem .2rem;color: #fff;" type="button" class="btnside zhaosubmit" onClick="sendSms()" id="sendMobileCode" value="获取验证码">
          </li>
          <li>
            <input id="ver_code" type="number" name="ver_code" class="codebox-input zhaoinput" placeholder="请输入短信验证码" maxlength="6">
          </li>
          <div id="init_user">
          <li>
            <input name="new_password" id="new_password" type="password" autocomplete="off" placeholder="请输入登陆密码" maxlength="20" class="buy_input">
          </li>
          <li>
            <input name="repeat" id="repeat" type="password" autocomplete="off" placeholder="重复登陆密码" maxlength="20" class="buy_input">
          </li>
        </div>
          <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保    存</a> </div>
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
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
		$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
		$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
		$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
		$("#verificationCode").val("");
		updateVerCode();
		
		$("#new_password").attr("dataType", "Require").attr("msg", "新密码不能为空");
		$("#repeat").attr("datatype", "Repeat").attr("to", "new_password").attr("msg", "两次输入的密码不一致");
		//$("#user_name").attr("datatype","User_name").attr("msg","请填写正确的用户名格式：4-16字符的字母或数字或下划线组合！");
	
	
	var f0 = $(".ajaxForm").get(0);
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

//验证手机号
function validMobile(mobile){
	console.info(1);
	if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
		//Common.loading();
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
		if (mobile.match(reg)) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/Register.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: false, 
		        error: function (request, settings) {mui.toast(" 数据加载请求失败！ "); }, 
		        success: function (result) {
		        	//Common.hide();
					if (result == 0) {
						mui.toast("参数丢失！");
						state = "-1";
						return false;
					} else if (result == 1) {
						state = "0";
					} else if (result == 2) {
						mui.toast("该手机号码已被占用！");
						state = "-2";
						return false;
					}
		        }
			});
		} else {
			//Common.hide();
			mui.toast("手机格式不正确！");
			state = "-3";
			return false;
		}
	}
}


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

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

//]]></script>
</body>
</html>
