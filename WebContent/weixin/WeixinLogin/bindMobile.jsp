<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${app_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_name}后触屏版登陆" />
<meta name="Keywords" content="${app_name}后触屏版登陆" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<jsp:include page="../../m/_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/styles/css/pop-ver.css?v=20170116" />
</head>
<body>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<jsp:include page="../../m/_header.jsp" flush="true" />
<!--content-->
<div class="content bg-white" id="divContent">
  <div class="entry" style="display:none;"> </div>
  <!--article-->
  <div class="login-box">
    <c:set var="url" value="/WeixinLogin.do" />
    <html-el:form action="${url}" method="post" styleClass="ajaxForm">
      <html-el:hidden property="user_id" styleId="user_id"/>
      <input type="hidden" name="return_url" value="${fn:escapeXml(af.map.return_url)}">
      <ul class="codebox">
          <li class="box name">
	          <input id="mobile" type="tel" name="mobile" class="box-flex codebox-input zhaoinput width75" maxlength="11" placeholder="请输入您的手机号" />
	          <input type="button" class="btnside zhaosubmit" id="sendMobileCode" onClick="getMessage($('#mobile').val());" value="获取验证码" />
	      </li>
          <li>
            <input id="ver_code" type="number" name="ver_code" class="codebox-input zhaoinput" placeholder="请输入短信验证码" maxlength="6" />
          </li>
      </ul>
      <div class="deal-btn" style="padding-top:.2rem;">
        <input type="button" id="btn_submit" class="j_submit" name="doregister" value="手机绑定" />
      </div>
    </html-el:form>
  </div>
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
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "WeixinLogin?method=bindMobile",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								location.href= "${af.map.return_url}";
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

	 var state = 0;                                         
	//验证手机号
	function validMobile(mobile){
		if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
			var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
			if (mobile.match(reg)) {
				$.ajax({
					type: "POST" , 
					url: "${ctx}/CsAjax.do" , 
					data:"method=validateMobileForWeiXin&mobile=" + mobile + "&t=" + new Date(),
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");$("#btn_submit").attr("disabled", "true").addClass("j_submit_disabled"); }, 
			        success: function (data) {
						if (data.ret == 0) {
							mui.toast('参数丢失！');
							state = "-1";
							return false;
						} else if (data.ret == 1) {
							state = "0";
							$("#showSelectCode").show();
						} else if (result == 2) {
							mui.toast("该手机号码已被占用！");
							state = "-2";
							return false;
						}
			        }
				});
			} else {
				mui.toast('手机格式不正确！');
				state = "-3";
				return false;
			}
		}
	}

	function getMessage(mobile){
		if ("" == mobile) {
			mui.toast('请先输入手机号！');
			$("#mobile").focus();
			return false;
		}
		validMobile(mobile);
	}
	
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
		i = 120; 
		$("#mobile").attr("readonly", "true");
		$.post("${ctx}/CsAjax.do?method=sendMobileMessage",{"mobile":mobile,"veri_code":$("#veri_code").val()},function(datas){
			if (datas.ret == 1) {
				$("#showSelectCode").hide();
        		$("#sendMobileCode").removeAttr("onclick");
				clock();
			}else{
				mui.toast(datas.msg);
			}
		});
	}

	function clock() {
		i--;
		$("#sendMobileCode").val(i + "秒后获取验证码"); 
		if(i > 0) {
			setTimeout("clock();", 1000);
		} else {
			state = "0"; 
			$("#sendMobileCode").val("获取短信验证码");
			$("#sendMobileCode").attr("onclick","getMessage($('#mobile').val());");
			$("#mobile").removeAttr("readonly");
		}
	}
	
//setLocation();	
//]]></script>
</body>
</html>
