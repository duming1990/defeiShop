<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${app_name}后台管理系统登陆</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_name}后台管理系统" />
<meta name="Keywords" content="${app_name}后台管理系统" />
<link href="${ctx}/styles/login/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="bg-dot"></div>
<div class="login-layout">
  <div class="top">
    <h2>平台管理中心</h2>
    <h6>
    <a>商城</a>
    &nbsp;&nbsp;|&nbsp;&nbsp;
    <a>资讯</a>
    &nbsp;&nbsp;|&nbsp;&nbsp;
    <a>微商城</a></h6>
  </div>
  <div class="box">
   <html-el:form action="/login" styleClass="loginForm">
        <html-el:hidden property="method" value="login" />
      <span>
      <label>帐号</label>
      <input name="login_name" id="login_name" type="text" class="input-text" required/>
      </span>
      <span>
      <label>密码</label>
      <input name="password" id="password" class="input-password" type="password" required />
      </span> 
      <span>
      <div class="code" style="display: none;">
        <div class="arrow"></div>
        <div class="code-img"><img src="" name="codeimage" id="codeimage" border="0" /></div>
        <a href="JavaScript:void(0);" id="hide" class="close" title="关闭"><i></i></a><a href="javascript:void(0);" class="change" title="看不清,点击更换验证码"><i></i></a> </div>
      <input name="verificationCode" maxlength="4" type="text" class="input-code" id="captcha"  title="验证码为4个字符"  required />
      </span> <span>
      <input class="input-button" type="submit" value="登录" />
      </span>
    </html-el:form>
  </div>
</div>
<div class="bottom">
  <h5>Powered by ${app_domain}</h5>
  <h6>© 2018 ${app_name}</h6>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-validate/jquery.validation.min.pc.js"></script> 
<script type="text/javascript">//<![CDATA[
var f = document.forms[0]; 
$(document).ready(function(){
	var random_bg=Math.floor(Math.random()*4+1);
    var bg='url(${ctx}/styles/imagesPublic/admin/bg_'+random_bg+'.jpg)';
    $("body").css("background-image",bg);
    $("#hide").click(function(){
        $(".code").fadeOut("slow");
    });
    $("#captcha").focus(function(){
        $(".code").fadeIn("fast");
        $("#codeimage").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).attr({"width":90,"height":26}).show();
    }).blur(function(){
    	$(".code").fadeOut("slow");
		$("#codeimage").hide().attr("src", "${ctx}/images/loading_login.gif").removeAttr("width").removeAttr("height");;
	});
    
    $(".change").click(function(){
    	$("#codeimage").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).attr({"width":90,"height":26}).show();
    });
});
//]]></script>
</body>
</html>
