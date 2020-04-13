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
        <li> <span style="width: 40%" class="grey-name">支付密码：</span>
          <input style="width: 60%" name="new_password_pay" id="new_password_pay" type="password" autocomplete="off" maxlength="6" class="buy_input" />
        </li>
        <li> <span style="width: 40%" class="grey-name">重复支付密码：</span>
          <input style="width: 60%" name="repeat" id="repeat" type="password" autocomplete="off" maxlength="6" class="buy_input" />
        </li>
        <c:if test="${not empty user.password_pay}">
        <li>
        <div class="box pop-vali">
      <input style="width:40%;" type="number" name="verificationCode" id="verificationCode" class="box-flex vali-input" placeholder="请输入图片验证码">
      <img src="${ctx}/images/VerificationCode.jpg" alt="验证码" id="veri_img" /> <a class="switch" onClick="updateVerCode();"><span>换一张</span></a> </div>
      <div class="tips" style="display:none;">请输入图片验证码</div>
     </li>
     <li> 
       <span style="width: 25%" class="grey-name">手机号：</span>
       <input style="width: 30%" name="mobile" id="mobile" value="${user.mobile}" type="text" autocomplete="off" maxlength="38" class="buy_input">
        <input style="width: 40%; margin: .12rem .2rem;" type="button" class="btnside zhaosubmit" id="sendMobileCode" onclick="sendSms()" value="获取验证码">
     </li>
     <li> 
        <input style="width:70%;" id="ver_code" type="number" name="ver_code" class="codebox-input zhaoinput" placeholder="请输入短信验证码" maxlength="6">
     </li>
     </c:if>
     </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保存</a> </div>
  </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<c:set var="tip_msg" value=""/>
<c:url var="tip_url" value=""/>
<c:if test="${empty user.mobile}">
<c:set var="tip_msg" value="请先前往安全中心绑定手机号"/>
<c:url var="tip_url" value="/m/MMySecurityCenter.do?method=setMobile"/>
</c:if>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script> 
<c:url var="urlhome" value="/m/MMyHome.do" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	<c:if test="${not empty user.password_pay}">
	$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
	$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请正确验证码！");
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	</c:if>
	
	<c:if test="${not empty tip_msg}">
	  Common.confirm("${tip_msg}",["确定","取消"],function(){
		location.href="${tip_url}";
	  },function(){
	  });	
	</c:if>
	
	
	$("#new_password_pay").attr("dataType", "Zip").attr("require", "true").attr("msg", "支付密码不能为空,且为6位的数字组合");
	$("#repeat").attr("datatype", "Repeat").attr("to", "new_password_pay").attr("msg", "两次输入的密码不一致");
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyPasswordPay",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								goUrl('${urlhome}',0);
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


function sendSms(){
	var mobile = $("#mobile").val();
	if ("" == mobile) {
		mui.toast("请先输入手机号！");
		$("#mobile").focus();
		return false;
	}
	if("" == $("#verificationCode").val()){
		mui.toast("请先验证码！");
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/Register.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val()   + "&type=3&bdType=3" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {mui.toast(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	state= "0";
	        	if (result.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					mui.toast("发送短信失败，请联系管理员！");
				}
	        } 
		});
	}
}

function clock() {
	i--;
	$("#sendMobileCode").val(i + "秒后获取验证码");
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").val("获取短信验证码");
		$("#sendMobileCode").attr("onclick","sendSms();");
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}


//]]></script>
</body>
</html>
