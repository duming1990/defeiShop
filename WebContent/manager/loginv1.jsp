<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${app_name}后台管理系统登陆</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_name}后台管理系统" />
<meta name="Keywords" content="${app_name}后台管理系统" />
<!-- 针对360浏览器的内核调用,强制调用极速模式 -->
<meta name="renderer" content="webkit" />
<link href="${ctx}/styles/loginv1/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body> 
<div class="bg-dot"></div>
<div class="login-layout">
  <div class="top">
    <h2>${app_name}运维中心</h2>
<!--     <h6> <a>商城</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a>资讯</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a>微商城</a></h6> -->
  </div>
  <div class="box">
    <html-el:form action="/login" styleClass="form_login">
      <html-el:hidden property="method" value="login" />
      <div class="lock-holder">
        <div class="form-group pull-left input-username">
          <label>账号</label>
          <input name="login_name" id="user_name" autocomplete="off" type="text" class="input-text" value="" required="" />
        </div>
        <div class="form-group pull-right input-password-box">
          <label>密码</label>
          <input name="password" id="password" class="input-text" autocomplete="off" type="password" required="" />
        </div>
      </div>
      <div class="avatar"><img src="${ctx}/styles/imagesPublic/user_header.png" /></div>
      <div class="submit"> <span>
        <div class="code">
          <div class="arrow"></div>
          <div class="code-img"><img src="" name="codeimage" id="codeimage" border="0" /></div>
          <a href="JavaScript:void(0);" id="hide" class="close" title="关闭"><i></i></a> <a href="JavaScript:void(0);" class="change" title="看不清,点击更换验证码"><i></i></a> </div>
        <input name="verificationCode" type="text" required="" class="input-code" id="captcha" placeholder="输入验证" pattern="[A-z0-9]{4}" title="验证码为4个字符" autocomplete="off" value="" />
        </span> <span>
        <input name="" class="input-button btn-submit" type="button" value="登录" />
        </span> </div>
      <div class="submit2"></div>
    </html-el:form>
  </div>
</div>
<div class="bottom">
  <h5>Powered by ${app_domain}</h5>
  <h6>© 2018 ${app_name}</h6>
</div>
<script src="${ctx}/commons/scripts/jquery.js" type="text/javascript"></script>
<script src="${ctx}/styles/loginv1/js/common.js" type="text/javascript"></script>
<script src="${ctx}/styles/loginv1/js/jquery.tscookie.js" type="text/javascript"></script>
<script src="${ctx}/styles/loginv1/js/jquery.supersized.min.js"></script>
<script src="${ctx}/styles/loginv1/js/jquery.progressBar.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-validate/jquery.validation.min.pc.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];              
$(document).ready(function(){
	
    $("#hide").click(function(){
        $(".code").fadeOut("slow");
    });
    $("#captcha").focus(function(){
        $(".code").fadeIn("fast");
        $("#codeimage").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).attr({"width":90,"height":26}).show();
    }).blur(function(){
	});
    
    $(".change").click(function(){
    	$("#codeimage").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).attr({"width":90,"height":26}).show();
    });
    
    $.supersized({

	    // 功能
	    slide_interval     : 4000,    
	    transition         : 1,    
	    transition_speed   : 1000,    
	    performance        : 1,    

	    // 大小和位置
	    min_width          : 0,    
	    min_height         : 0,    
	    vertical_center    : 1,    
	    horizontal_center  : 1,    
	    fit_always         : 0,    
	    fit_portrait       : 1,    
	    fit_landscape      : 0,    

	    // 组件
	    slide_links        : 'blank',    
	    slides             : [    
	                             {image : '${ctx}/styles/loginv1/images/1.jpg'},
	                             {image : '${ctx}/styles/loginv1/images/2.jpg'},
	                             {image : '${ctx}/styles/loginv1/images/3.jpg'},
								 {image : '${ctx}/styles/loginv1/images/4.jpg'},
								 {image : '${ctx}/styles/loginv1/images/5.jpg'}
	                   ]

	});
    
	//跳出框架在主窗口登录
	if(top.location!=this.location)	top.location=this.location;
	$('#user_name').focus();
	//动画登录
	$('.btn-submit').click(function(e){
		var user_name = $("#user_name").val();
		if(null == user_name || user_name == ''){
			alert("用户名不可为空")
			return false;
		}
		var user_name = $("#password").val();
		if(null == user_name || user_name == ''){
			alert("密码不可为空")
			return false;
		}
		
		<c:if test="${isEnabledCode}">
		var user_name = $("#captcha").val();
		if(null == user_name || user_name == ''){
			alert("验证码不可为空")
			return false;
		}
		</c:if>
		
		$.jBox.tip("数据提交中...", 'loading');
		window.setTimeout(function () { 
			$.ajax({
				type: "POST",
				url: "login.do?method=login",
				data: $('.form_login').serialize(),
				error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
				success: function(data) {
					if(data.code == "0"){
						$.jBox.tip(data.msg, "info");
					} else {
						$.jBox.tip(data.msg, "success");
						$('.input-username,dot-left').addClass('animated fadeOutRight')
				        $('.input-password-box,dot-right').addClass('animated fadeOutLeft')
				        $('.btn-submit').addClass('animated fadeOutUp')
	                    $('.avatar').addClass('avatar-top');
	                    $('.submit').hide();
	                    $('.submit2').html('<div class="progress"><div class="progress-bar progress-bar-success" aria-valuetransitiongoal="100"></div></div>');
	                    $('.progress .progress-bar').progressbar({
	                      done : function() {
	                    	  location.href="${ctx}" + data.datas.dataUrl;
	                      }
	                    }); 
					}
				}
			});	
    	}, 500);
});

	// 回车提交表单
	$('.form_login').keydown(function(event){
	    if (event.keyCode == 13) {
	        $('.btn-submit').click();
	    }
	});
    
});

//]]></script>
</body>
</html>
