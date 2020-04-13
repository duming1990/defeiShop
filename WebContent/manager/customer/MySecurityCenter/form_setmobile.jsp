<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <html-el:form action="/customer/MySecurityCenter.do" styleClass="ajaxForm">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
     <tr id="verificationCodeli">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>验证码：</td>
        <td><html-el:text property="verificationCode" styleId="verificationCode" maxlength="4" style="width:100px" styleClass="webinput" />
          &nbsp;<img height="22" width="72" class="signup-captcha-img" id="veri_img" style="vertical-align: bottom;"/> <a tabindex="-1" class="captcha-refresh inline-link" href="javascript:updateVerCode();">看不清楚？换一张</a> </td>
      </tr>
    <tbody id="oldMobileVal">
<!--         <tr> -->
<!--           <td width="14%" nowrap="nowrap" class="title_item">原手机号：</td> -->
<%--           <td><html-el:text property="mobileOld" styleId="mobileOld" readonly="true" style="width:100px" styleClass="webinput" value="${user.mobile}"/> --%>
<!--             &nbsp; -->
<!--             <button class="bgButtonFontAwesome" type="button" onclick="sendSms2()" id="sendMobileCode2"><i class="fa fa-send"></i>发送验证码</button></td> -->
<!--         </tr> -->
<!--         <tr> -->
<!--           <td nowrap="nowrap" class="title_item"><span style="color:#F00;">*</span>手机动态码：</td> -->
<%--           <td><html-el:text property="ver_code_old" styleId="ver_code_old" maxlength="6" style="width:100px" styleClass="webinput" /></td> --%>
<!--         </tr> -->
			<tr>
	          <td nowrap="nowrap" class="title_item"><span style="color:#F00;">*</span>支付密码：</td>
	          <td><html-el:password property="password_pay" styleId="password_pay" maxlength="6" style="width:100px" styleClass="webinput" /></td>
	        </tr>
        <tr>
          <td style="text-align:center" colspan="2"><button class="bgButtonFontAwesome" type="button" id="btn_submit_next"><i class="fa fa-arrow-right"></i>下一步</button></td>
        </tr>
      </tbody>
      <tbody style="display:none;" id="newMobileVal">
      <tr id="verificationCodeliShow"></tr>
        <tr>
          <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>新手机号：</td>
          <td><html-el:text property="mobile" styleId="mobile" maxlength="11" style="width:100px" styleClass="webinput"/>
            &nbsp;
            <button class="bgButtonFontAwesome" type="button" onclick="sendSms()" id="sendMobileCode"><i class="fa fa-send"></i>发送验证码</button></td>
        </tr>
        <tr>
          <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>手机动态码：</td>
          <td><html-el:text property="ver_code" styleId="ver_code" maxlength="6" style="width:100px" styleClass="webinput" /></td>
        </tr>
        <tr>
          <td style="text-align:center" colspan="2"><button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button></td>
        </tr>
      </tbody>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#ver_code_old").attr("dataType", "Require").attr("msg", "请填写手机动态码");
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
	$("#password_pay").attr("dataType", "Require").attr("msg", "请填写支付密码！");
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	<c:if test="${not empty af.map.isBind}">
		$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
		$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
		//$("#verificationCodeliShow").html($("#verificationCodeli").html()).show();
		$("#oldMobileVal").remove();
		$("#newMobileVal").show();
		$("#verificationCode").attr("dataType", "Require").attr("msg", "请填写验证码！");
		$("#verificationCode").val("");
		updateVerCode();
	</c:if>
	
	
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit_next").click(function(){
		
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=valOldMobile",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							$.jBox.tip(data.msg, "success");
							$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
							$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
							$("#oldMobileVal").remove();
							$("#newMobileVal").show();
							$("#verificationCode").val("");
							updateVerCode();
						} else {
							$.jBox.tip(data.msg, "info");
						}
					}
				});	
				
			}, 1000);
			return true;
		}
		return false;
	});
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 3)) {
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyMobile",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							$.jBox.tip(data.msg, "success");
							window.setTimeout(function () {
								returnTo();
							}, 1000);
						} else {
							$.jBox.tip(data.msg, "info");
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
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
	
}
var state = 0;        

//验证手机号
function validMobile(mobile){
	if ("" != mobile && $("#mobile").attr("readonly") != "readonly") {
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
		if (mobile.match(reg)) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ "); }, 
		        success: function (result) {
					if (result == 0) {
						$.jBox.alert("参数丢失！", '提示');
						state = "-1";
						return false;
					} else if (result == 1) {
						state = "0";
					} else if (result == 2) {
						$.jBox.alert("该手机号码已被占用！", '提示');
						state = "-2";
						return false;
					}
		        }
			});
		} else {
			$.jBox.alert("手机格式不正确！", '提示', '提示');
			state = "-3";
			return false;
		}
	}
}


function sendSms(){
	var mobile = $("#mobile").val();
	var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	if ("" == mobile) {
		$.jBox.alert("请先输入手机号！", '提示');
		$("#mobile").focus();
		return false;
	}
	if (!mobile.match(reg)) {
		$.jBox.alert("手机号码格式不正确！", '提示');
		$("#mobile").focus();
		return false;
	}
	if("" == $("#verificationCode").val()){
		$.jBox.alert("请先填写验证码！", '提示');
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val()  + "&type=3&bdType=1&isValMobile=true" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	state = "0"; 
	        	if (result.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					$.jBox.alert(result.msg, '提示');
				}
	        } 
		});
	}
}

function clock() {
	i--;
	$("#sendMobileCode").html("<i class='fa fa-send'></i>" + i + "秒后获取验证码</button>"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").html("获取短信验证码");
		$("#sendMobileCode").attr("onclick","sendSms();");
		$("#mobile").removeAttr("readonly");
	}
}


function sendSms2(){
	var mobile = $("#mobileOld").val();
	if("" == $("#verificationCode").val()){
		$.jBox.alert("请先填写验证码！", '提示');
		$("#veri_code").focus();
		return false;
	}
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val()  + "&type=3&bdType=1" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	state = "0"; 
	        	if (result.ret == 1) {
	        		$("#sendMobileCode2").removeAttr("onclick");
					clock2();
				}else{
					$.jBox.alert("发送短信失败，请联系管理员！", '提示');
				}
	        } 
		});
	}
}

function clock2() {
	i--;
	$("#sendMobileCode2").html("<i class='fa fa-send'></i>" + i + "秒后获取验证码</button>"); 
	if(i > 0) {
		setTimeout("clock2();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode2").html("获取短信验证码");
		$("#sendMobileCode2").attr("onclick","sendSms2();");
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

//]]></script>
</body>
</html>
