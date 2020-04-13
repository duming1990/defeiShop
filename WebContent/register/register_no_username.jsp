<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员注册 - ${app_name}</title>
<jsp:include page="../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
</head>
<body class="pg-unitive-signup theme--www">
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <div class="J-unitive-signup-form">
    <ul class="signup__header cf">
      <c:set var="bind_name" value="" />
      <c:if test="${not empty af.map.appid_qq}">
      <c:set var="bind_name" value=" - 绑定QQ" />
      </c:if>
      <c:if test="${not empty af.map.appid_weibo}">
      <c:set var="bind_name" value=" - 绑定微博" />
      </c:if>
      <li class="J-trigger trigger current"> <a href="javascript:void(0)"><i class="icon icon--phone"></i>用户注册${bind_name}</a> </li>
      <li class="J-trigger trigger" style="float:right;">
      <div class="rtab fr" id="rtab">
		<span>我已经是会员，现在就 
		 <c:url var="url" value="/IndexLogin.do" />
		 <a href="${url}">登录</a></span>
	  </div>
      </li>
    </ul>
    <div class="sheet" style="display: block;">
      <html-el:form action="/Register.do" enctype="multipart/form-data" styleClass="register_form">
      <input type="hidden" name="method" value="registerNoUserName" />
      <html-el:hidden property="appid_qq" />
      <html-el:hidden property="appid_weibo" />
	  <%--<html-el:hidden property="appid_weixin" />--%>
      <html-el:hidden property="user_logo" />
        <c:if test="${not empty bind_name}">
        <div class="form-field form-field--uname">
          <label>昵称</label>
          <html-el:text property="real_name" styleId="real_name" styleClass="f-text J-uname" maxlength="40"/>
        </div>
        </c:if>
        <div class="form-field form-field--mobile">
          <label><span style="color:red;">* </span>手机号</label>
          <input type="text" name="mobile" id="mobile" class="f-text J-mobile" onblur="validMobile($(this).val());" maxlength="11"/>
        </div>
        <div class="form-field form-field--mobile">
          <label><span style="color:red;">* </span>验证码</label>
          <input type="text" id="veri_code" class="f-text" name="verificationCode" placeholder="验证码" autocomplete="off" style="width:65px;"maxlength="4" />
          <img height="34" width="72" id="veri_img" /> 
          <a tabindex="-1" class="inline-link" href="javascript:updateVerCode();">看不清楚？换一张</a>
        </div>
        <div class="form-field form-field--sms">
          <label><span style="color:red;">* </span>短信验证码</label>
          <input type="text" name="verifycode"  id="verifycode" class="f-text J-sms" style="width:65px;" maxlength="6"/>
          <span class="mcodepre">
            <a class="mobilecodebtn" href="javascript:void(0);" id="sendMobileCode" onclick="getMessage($('#mobile').val());">获取短信验证码</a>
          </span>
        </div>
        <div class="form-field form-field--pwd2">
          <label><span style="color:red;">* </span>创建密码</label>
          <input type="password" name="password" id="password" class="f-text J-pwd2" maxlength="20" />
        </div>
<!--         <div class="form-field form-field--mobile"> -->
<!--           <label><span style="color:red;">* </span>邀请人</label> -->
<%--           <c:set var="readonly" value=""/> --%>
<%--           <c:if test="${not empty af.map.readolny_ymid}"> --%>
<%--            <c:set var="readonly" value="readonly='true'"/> --%>
<%--           </c:if> --%>
<%--           <input type="text" name="ymid" value="${af.map.ymid}" ${readonly}" id="ymid" class="f-text J-mobile" placeholder="邀请人的用户名/认证手机号"/> --%>
<!--           <span class="inline-tip" id="has_ymid_tip" style="display:none;"> -->
<!--           <i class="tip-status tip-status--opinfo"></i>邀请人不存在</span> -->
<!--         </div> -->
        <div class="form-field">
          <input  type="button" name="commit" id="btn_submit" class="btn" value="同意以下协议并注册" />
          <a href="" target="_blank"></a> </div>
      </html-el:form>
    </div>
  </div>
  <div class="term"> 
  <input name="agreement" class="ui-radio" id="agreement" type="radio" value="1" onClick="removeDisabled()"/>
                        <label for="agreement" class="ui-radio-label" style="font-size:12px">同意</label>
  <a class="f1" href="javascript:void(0);" onclick="getUserXyInfo();">《${app_name_min}网用户协议》</a> </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery.pstrength.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery.timers.js"></script>
<script type="text/javascript">//<![CDATA[
var f_r = $(".register_form").get(0);                             
$(document).ready(function(){
	
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
	
	$("#mobile").attr("datatype","Mobile").attr("Require","true").attr("msg","请输入正确的手机号！");
	$("#veri_code").attr("dataType", "Require").attr("msg", "验证码不能为空！");
	$("#verifycode").attr("dataType", "Require").attr("msg", "短信动态码不能为空！");
	
	$("#password").attr("datatype","LimitB").attr("min","4").attr("max","20").attr("msg","请填写密码,且必须在4-20个字符之间！");
	$("#password").pstrength();
	//$("#user_name").attr("datatype","User_name").attr("msg","请填写正确的用户名格式：4-16字符的字母或数字或下划线组合！");
	//$("#_password").attr("datatype","Repeat").attr("to","password").attr("msg","密码不一致！");
// 	$("#ymid").attr("dataType", "Require").attr("msg", "邀请人不能为空！");
	
	// 提交
	$("#btn_submit").click(function(){
		var agreement = $("#agreement").attr("checked");
		if(agreement != 'checked'){
			$.jBox.alert('请阅读并同意《${app_name_min}网用户协议》', '提示');
			return false;
		}
		if(Validator.Validate(f_r, 3)  && check_password_regx()){
			
// 			var ym_id_value = $("#ymid").val();
// 			$.ajax({
// 				type: "POST",
// 				url: "Register.do",
// 				data: "method=validateYmId&ymid=" + ym_id_value,
// 				dataType: "json",
// 				error: function(request, settings) {flag = false;},
// 				success: function(oper) {
// 					if (oper == 1) {
// 						$.jBox.alert('未查询到此推荐人，请确认填写是否正确', '提示');
// 						return false;
// 					}else{
						$("#btn_submit").attr("disabled", "true");
						$("#btn_reset").attr("disabled", "true");
						$("#btn_back").attr("disabled", "true");
						f_r.submit();
// 					}
// 				}
// 			});
		}
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
				url: "Register.do" , 
				data:"method=validateMobile&mobile=" + mobile + "&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");$("#btn_submit").addClass("btn-disabled"); }, 
		        success: function (result) {
					if (result == 0) {
						$.jBox.alert('参数丢失！', '提示');
						state = "-1";
						$("#btn_submit").attr("disabled", "true");
						$("#btn_submit").addClass("btn-disabled");
						return false;
					} else if (result == 1) {
						state = "0";
						$("#btn_submit").removeAttr("disabled");
						$("#btn_submit").removeClass("btn-disabled");
					} else if (result == 2) {
						$.jBox.alert('该手机号码已被注册！', '提示');
						state = "-2";
						$("#btn_submit").attr("disabled", "true");
						$("#btn_submit").addClass("btn-disabled");
						return false;
					}
		        }
			});
		} else {
			$.jBox.alert('手机格式不正确！', '提示');
			state = "-3";
			$("#btn_submit").attr("disabled", "true");
			$("#btn_submit").addClass("btn-disabled");
			return false;
		}
	}
}

function getMessage(mobile){
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
	if("" == $("#veri_code").val()){
		$.jBox.alert('请先验证码！', '提示');
		$("#veri_code").focus();
		return false;
	}
	
	if (state == 0) {
		state = "1"; 
		i = 120; 
		$.post("${ctx}/Register.do?method=sendMobileMessage",{"mobile":mobile,"veri_code":$("#veri_code").val(),"isValMobile":"true"},function(datas){
				
			    state = "0"; 
			    if (datas.ret == 1) {
	        		$("#sendMobileCode").removeAttr("onclick");
					clock();
					$("#btn_submit").removeAttr("disabled");
					$("#btn_submit").removeClass("btn-disabled");
				}else{
					$.jBox.alert(datas.msg, '提示');
					$("#btn_submit").attr("disabled", "true");
					$("#btn_submit").addClass("btn-disabled");
				}
			})
	}
}


function clock() {
	i--;
	$("#sendMobileCode").html(i + "秒后获取验证码"); 
	if(i > 0) {
		setTimeout("clock();", 1000);
	} else {
		state = "0"; 
		$("#sendMobileCode").html("获取短信验证码");
		$("#sendMobileCode").attr("onclick","getMessage($('#mobile').val());");
		$("#mobile").removeAttr("readonly");
	}
}

function updateVerCode(){
	$("#veri_img").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime());
}

function getUserXyInfo(){
	var url = "${ctx}/Register.do?method=getUserXyInfo";
	$.dialog({
		title:  "用户注册协议",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}


function check_password_regx(){
	 var b = check_pwd1("password");
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
function removeDisabled(){
	if($("#agreement").attr("checked") == 'checked'){
		$("#btn_submit").removeAttr("disabled")
	}
}
//]]></script>
</body>
</html>
