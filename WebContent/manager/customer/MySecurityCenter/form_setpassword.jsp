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
<!--        <tr> -->
<!--          <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>原密码：</td> -->
<!--          <td> -->
<%--          <html-el:password property="old_password" styleId="old_password" maxlength="16" style="width:200px" styleClass="webinput" /> --%>
<%--          &nbsp;<a class="" href="${ctx}/getpwback.shtml" target="_blank">忘记密码?</a> --%>
<!--          </td> -->
<!--        </tr> -->
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>新密码：</td>
         <td><html-el:password property="new_password" styleId="new_password" maxlength="16" style="width:200px" styleClass="webinput" /></td>
       </tr>
       <tr>
         <td  nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>重复新密码：</td>
         <td><html-el:password property="repeat" styleId="repeat" maxlength="16" style="width:200px" styleClass="webinput" /></td>
       </tr>
       <tr>
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>验证码：</td>
         <td><html-el:text property="verificationCode" styleId="verificationCode" maxlength="4" style="width:100px" styleClass="webinput" />
          &nbsp;<img height="22" width="72" class="signup-captcha-img" id="veri_img" style="vertical-align: bottom;"/> 
          <a tabindex="-1" class="captcha-refresh inline-link" href="javascript:updateVerCode();">看不清楚？换一张</a>
         </td>
       </tr>
       <tr>
        <td style="text-align:center" colspan="2">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	//$("#old_password").attr("dataType", "Require").attr("msg", "原密码不能为空");
	$("#new_password").attr("dataType", "Require").attr("msg", "新密码不能为空");
	$("#repeat").attr("datatype", "Repeat").attr("to", "new_password").attr("msg", "两次输入的密码不一致");
	
	$("#mobile").attr("dataType", "Mobile").attr("msg", "请正确填写手机号码");
	$("#ver_code").attr("dataType", "Require").attr("msg", "请填写手机动态码");
	$("#verificationCode").attr("dataType", "Require").attr("msg", "请正确验证码！");
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 3)  && check_password_regx()) {
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifyPassword",
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


function check_password_regx(){
	 var b = check_pwd1("new_password");
   switch (b) {
   case 0:
       return true;
   case 1:
       alert("密码不能为空");
       break;
   case 2:
       alert("密码应为6-20位字符");
       break;
   case 3:
       alert("密码应为6-20位字符");
       break;
   case 4:
       alert("密码中不允许有空格");
       break;
   case 5:
       alert("密码不能全为数字");
       break;
   case 6:
       alert("密码不能全为字母，请包含至少1个数字或符号 ");
       break;
   case 7:
       alert("密码不能全为符号");
       break;
   case 8:
       alert("密码不能全为相同字符或数字");
       break;
   default:
       alert("6-20个大小写英文字母、符号或数字的组合")
   }
   return false;
}


function check_pwd1(f) {
  var d = $("#" + f).val();
  if (d == "") {
      return 1
  }
  if (d.length > 20) {
      return 2
  }
  if (d.length < 6) {
      return 3
  }
  var g = /\s+/;
  if (g.test(d)) {
      return 4
  }
  var a = /^[0-9]+$/;
  if (a.test(d)) {
      return 5
  }
  var b = /^[a-zA-Z]+$/;
  if (b.test(d)) {
      return 6
  }
  var e = /^[^0-9A-Za-z]+$/;
  if (e.test(d)) {
      return 7
  }
  if (isSameWord(d)) {
      return 8
  }
  var c = "^[\\da-zA-Z\\.\\,\\`\\~\\!\\@\\#\\$\\%\\\\^\\&\\*\\(\\)\\-\\_\\=\\+\\[\\{\\]\\}\\\\|\\;\\:\\'\\'\\\"\\\"\\<\\>\\/\\?]+$";
  var h = new RegExp(c);
  if (!h.test(d)) {
      return 10
  }
  return 0
}

function isSameWord(b) {
   var d;
   if (b != null && b != "") {
       d = b.charCodeAt(0);
       d = "\\" + d.toString(8);
       var c = "[" + d + "]{" + (b.length) + "}";
       var a = new RegExp(c);
       return a.test(b)
   }
   return true
}

function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}

var state = 0;        


function sendSms(){
	var mobile = $("#mobile").val();
	if ("" == mobile) {
		alert("请先输入手机号！");
		$("#mobile").focus();
		return false;
	}
	if("" == $("#verificationCode").val()){
		alert("请先验证码！");
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.ajax({
			type: "POST" , 
			url: "${ctx}/CsAjax.do" , 
			data:"method=sendMobileMessage&mobile=" + mobile + "&veri_code=" + $("#verificationCode").val()   + "&type=3&bdType=3" + "&t=" + new Date(),
			dataType: "json" , 
	        async: true, 
	        error: function (request, settings) {alert(" 数据加载请求失败！ "); },
	        success: function (result) {
	        	if (result.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
				}else{
					alert("发送短信失败，请联系管理员！");
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
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

//]]></script>
</body>
</html>
