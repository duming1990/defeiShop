<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>${app_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Description" content="${app_name}后触屏版找回密码" />
<meta name="Keywords" content="${app_name}后触屏版找回密码" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/styles/css/pop-ver.css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content bg-white">
    <div class="login-box">
    	<html-el:form action="/MGetPwBack.do" method="post" styleClass="form_getPwBack">
    	<html-el:hidden property="id" styleId="id" value="${af.map.id}" />
    	<html-el:hidden property="method" value="stepTwo" />
    	<input type="hidden"  id="mobile" value="${af.map.mobile}"/>
            <ul class="codebox">
                <li class="box name">
                    <input id="mobile_veri_code" type="text" name="mobile_veri_code" class="box-flex codebox-input zhaoinput" placeholder="请输入短信验证码" />
                    <input type="button" class="btnside zsms" id="sendMobileCode" />
                </li>
                <li class="pwd">
                    <input type="password" name="password" id="password" class="codebox-input zhaoinput" placeholder="请设置新密码" autocomplete="off" maxlength="32"/>
                </li>
                <li class="pwd">
                    <input type="password" id="_password" name="_password" class="codebox-input zhaoinput" autocomplete="off" placeholder="请确认新密码"/>
                </li>            </ul>
            <div class="deal-btn">
               <input type="button" class="j_submit" id="btn_submit" name="sub" value="确认" />
            </div>
        </html-el:form>
    </div>
    <div class="password-serve"><span>找回密码如遇到问题，请拨打${app_name_min}客服<br>客服热线:${app_tel}</span></div>
    
    <div class="pop-warp sms-captcha" style="display: none;" id="showSelectCode">
    <div class="pop-innr"> <i class="close" onclick="$(this).parents('.sms-captcha').hide();"></i>
      <div class="title">请输入图片验证码</div>
      <div class="box pop-vali">
        <input type="text" name="verificationCode" id="veri_code" class="box-flex vali-input" placeholder="请输入图片验证码" />
        </div>
      <div class="box pop-vali">
        <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" /> <a class="switch" onclick="updateVerCode();"><span>换一张</span></a> </div>
      <div class="tips" style="display:none;">请输入图片验证码</div>
      <div class="box pop-bottom"> <a href="javascript:sendMessage();" class="box-flex next-step">下一步</a> </div>
    </div>
  </div>
</div>
<script type="text/javascript">//<![CDATA[

$(document).ready(function(){
	
	var f = document.forms[0];
	// 提交
	$("#btn_submit").click(function(){
		
		var mobile_veri_code = $("#mobile_veri_code").val();
		var password = $("#password").val();
		var _password = $("#_password").val();
		if(null == mobile_veri_code || '' == mobile_veri_code){
			mui.toast("请输入短信验证码");
			return false;
		}else if(null == password || '' == password){
			mui.toast("请输入新密码");
			return false;
		}else if(null == _password || '' == _password){
			mui.toast("请确认新密码");
			return false;
		}else if(password.length < 6){
			mui.toast("密码长度不能小于6位");
			return false;
		}else if(password != _password){
			mui.toast("两次输入密码不一致，请确认");
			return false;
		}
		
		f.submit();
	});
	clock();
});

var i =60; 

function sendMessage(){
	if("" == $("#veri_code").val()){
		mui.toast('请先验证码！');
		$("#veri_code").focus();
		return false;
	}
	var mobile = $("#mobile").val();
	if ("" == mobile) {
		mui.toast('请先输入手机号！');
		$("#mobile").focus();
		return false;
	}
	$.post("${ctx}/Register.do?method=sendMobileMessage",{"mobile":mobile,"veri_code":$("#veri_code").val()},function(datas){
			if (datas.ret == 1) {
				$("#showSelectCode").hide();
        		$("#sendMobileCode").removeAttr("onclick");
        		i=60;
				clock();
			}else{
				mui.toast(datas.msg);
			}
	});
}

function getMessage(){
	$("#showSelectCode").show();
}

function clock() {
	i--;
	$("#sendMobileCode").val(i + "秒"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		$("#sendMobileCode").val("获取短信验证码");
		$("#sendMobileCode").attr("onclick","getMessage();");
		$("#mobile").removeAttr("readonly");
	}
}


//]]></script>
</body>
</html>