<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登陆 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_v2.css?20161018"  />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
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
      <div class="login_content">
        <div class="login_header" id="login_header">
<!--           <div class="login_type ">扫码登录</div> -->
          <div class="login_type login_type_active">账号登录</div>
        </div>
        <div class="login_body">
          <div class="login_qrcode hide"  id="login_content_0">
            <div id="qrcodeContent">
              <div class="login_qrcode_content" id="qrcode">
                <input type="hidden" id="code" name="code" value="${code}"/>
                <img alt="Scan me!" style="display: block;" id="qrcodeImg" src="${ctx}/QrCodeForWeixin.jpg?code_url=${code_url}" width="230"/></div>
              <div class="login_qrcode_text"> 请使用APP扫描二维码登录 <span id="refreashQrCodeBtn" style="color:#ec5051;cursor:pointer;" onclick="updateQrCode();"><i class="fa fa-refresh"></i>刷新</span> </div>
            </div>
            <div class="login_qrcode_refresh" id="login_qrcode_refresh">您的二维码已失效，<br />
              请点击下方刷新按钮</div>
            <div class="qrcode-msg login_qrcode_app_confirm" id="login_qrcode_app_confirm">
              <div class="msg-ok">
                <div class="msg-icon"> <i class="fa fa-check-circle icon-ok"></i> <i class="fa fa-mobile-phone icon-phone"></i> </div>
                <h6>扫描成功！</h6>
                <p>请在手机上确认登录</p>
                <div class="link"><a onclick="updateQrCode();" class="light-link J_QRCodeRefresh">返回二维码登录</a></div>
              </div>
            </div>
          </div>
          <div class="login_phone " id="login_content_1">
            <html-el:form action="/IndexLogin.do" styleClass="content1Form">
              <html-el:hidden property="method" value="login" />
              <html-el:hidden property="returnUrl" value="${af.map.returnUrl}"/>
              <div class="login_input_content">
                <input class="login_input" type="text" id="user_name" name="user_name" placeholder="手机号/用户名" value="${login_name}" />
              </div>
              <div class="login_input_content">
                <input class="login_input" type="password" id="password" name="password" placeholder="密码" value="${password}" />
              </div>
              <div class="login_input_content" id="tr_verificationCode">
                <input id="veri_code" name="verificationCode" class="login_input login_input_code" maxlength="4" autocomplete="off" type="text" placeholder="验证码" />
                <div class="login_input_code_btn_content" style="padding-left:10px;"> <img class="img_code" id="veri_img" src=""/> <i class="fa fa-refresh img_code_reflash" onclick="updateVerCode();"></i>
                  <c:url var="urlBack" value="/GetPwBack.do" />
                  <a href="${urlBack}" target="_blank" class="foget_pwd">忘记密码?</a> </div>
              </div>
              <div class="login_input_content">
                <input type="submit" class="login_button" name="commit" id="commit" value="登录" />
              </div>
            </html-el:form>
            <h3 class="title-wrapper" style="text-align:right;padding-bottom:8px;">
              <span class="title">还没有账号？
               <c:url var="url" value="/Register.do" />
               <a href="${url}" target="_blank">免费注册</a></span></h3>
<!--             <div class="oauth-wrapper"> -->
<!--               <h3 class="title-wrapper"><span class="title">用合作网站账号登录</span></h3> -->
<!--               <div class="oauth cf"> -->
<%--                 <c:url var="qq" value="/IndexLogin.do?method=qq" /> --%>
<%--                 <c:url var="weibo" value="/IndexLogin.do?method=weibo" /> --%>
<%--                 <a class="oauth__link oauth__link--qq" href="${qq}" target="_blank"></a> <a class="oauth__link oauth__link--weibo" href="${weibo}" target="_blank"></a> </div> --%>
<!--             </div> -->
            <div class="toast toast_error" id="validate-msg">密码不能为空</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<c:url var="go_url" value="/manager/customer/index.do" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.timers.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).show();
	
	$("#login_header div").each(function(index){
		$(this).click(function(){
			if(index == 0 && !$(this).hasClass("login_type_active")){
				updateQrCode();
			}
			if(index == 1){
				$(document).stopTime("waitIsAppConfirmLogin");
			}
			$(this).addClass("login_type_active").siblings().removeClass("login_type_active");
			$("#login_content_" + index).removeClass("hide").siblings().addClass("hide");
			
		});
	});
	
// 	waitIsAppConfirmLogin();
	
});

var is_success = true;
function waitIsAppConfirmLogin(){
	$(document).everyTime(2000, "waitIsAppConfirmLogin", function(i) {// 5秒，检测一次
		var code = $("#code").val();
	
		if(null == code || "" == code){
			$(document).stopTime("waitIsAppConfirmLogin");
			return false;
		}
		$.post("${ctx}/CsAjax.do?method=waitIsAppConfirmLogin",{code:code},function(data){
			console.info(data.code + "-" + i);
			if (data.code == 1) {
				$(document).stopTime("waitIsAppConfirmLogin");
				$.jBox.tip(data.msg, 'success');
				window.setTimeout(function () { 
					window.location.href="${go_url}";
				}, 1500);
			} else if (data.code == 2 && is_success) {
				$("#login_qrcode_app_confirm").show();
				$("#login_qrcode_refresh").hide();
				$("#qrcodeContent").hide();
			}
		});
		if(i>30){
			is_success= false;
			$(document).stopTime("waitIsAppConfirmLogin");
			$("#login_qrcode_refresh").show();
			$("#login_qrcode_app_confirm").hide();
			$("#qrcodeContent").show();
		}
	});
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

function updateQrCode(){
	$.post("${ctx}/CsAjax.do?method=updateQrCode",{},function(data){
		$("#qrcodeImg").attr("src", "${ctx}/QrCodeForWeixin.jpg?code_url="+ data.code_url);
		$("#code").val(data.code);
		$("#login_qrcode_refresh").hide();
		$("#login_qrcode_app_confirm").hide();
		$("#qrcodeContent").show();
		waitIsAppConfirmLogin();
	});
}


var f = $(".content1Form").get(0);
f.onsubmit = function () {
	var user_name = $("#user_name").val();
	var password = $("#password").val();
	if(null == user_name || '' == user_name){
		$("#validate-msg").text("请输入用户名");
		showTip($("#validate-msg"));
		$("#user_name").focus();
		return false;
	}else if(null == password || '' == password){
		$("#validate-msg").text("请输入密码").show();
		showTip($("#validate-msg"));
		$("#password").focus();
		return false;
	}
	var veri_code = $("#veri_code").val();
	if(null == veri_code || '' == veri_code){
		$("#validate-msg").text("请输入验证码").show();
		showTip($("#validate-msg"));
		$("#veri_code").focus();
		return false;
	}
	return true;
}

function showTip(obj){
	  obj.animate({opacity:1},500,'linear',function(){
        toastTimer = setTimeout(function(){
      	  obj.animate({opacity:0},500,'linear',function(){
        });
      },3000);
	});
}

//]]></script>
</body>
</html>
