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
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body id="body">
<div id="wrap">
 <jsp:include page="../_header.jsp" flush="true" />
  <!--content-->
  <div class="content bg-white">
    <div class="user-cutover">
      <ul class="box nav-ors">
        <li class="selected"><a>账号登录</a></li>
      </ul>
    </div>
    <div class="entry" style="display:none;"></div>
    <div class="login-box">
    <html-el:form action="/MIndexLogin.do" method="post" styleClass="form_login">
      <html-el:hidden property="method" value="login" />
      	<html-el:hidden property="returnUrl" />
        <ul class="codebox">
          <li class="name">
            <input type="text" name="user_name" id="user_name" class="codebox-input" placeholder="用户名/手机号" maxlength="16" />
          </li>
          <li class="box pwd">
            <input type="password" name="password_hide" id="password_hide" class="box-flex codebox-input input-1-password" placeholder="登录密码" autocomplete="off" maxlength="32" />
            <input type="text" name="password_show" id="password_show" class="box-flex codebox-input input-1-text" placeholder="登录密码" autocomplete="off" maxlength="32" style="display:none;" />
            <input type="button" class="pwd-mode toggle-pwd" id="changePassShowType" />
          </li>
        </ul>
        <div class="deal-btn">
          <input type="button" class="j_submit" name="loginsub" id="loginSubmit" value="登录" />
        </div>
        
        <div class="login-find">
         <c:url var="url" value="/m/MGetPwBack.do"/>
        <a  href="${url}">忘记密码</a></div>
      </html-el:form>
    </div>
  </div>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	 var topBtnUrl = "${ctx}/m/MRegister.do";
	 setTopBtnUrl(topBtnUrl);
	
	 var f = document.forms[0];
	
	 $("#loginSubmit").click(function(){
	    var user_name = $("#user_name").val();
		var password = $("#password_hide").val();
		if(null == user_name || '' == user_name){
			mui.toast("请输入用户名");
			return false;
		}else if(null == password || '' == password){
			mui.toast("请输入密码");
			return false;
		}
		Common.loading();
		
		<c:if test="${not isApp}">
		  f.submit();
		</c:if>
		
		<c:if test="${isApp}">
		$.ajax({
			dataType:"json",
			url: "${ctx}/service/WebService.do?method=loginForWeb&user_name="+user_name+"&password="+password,
			success: function(data){
				Common.hide();
				if(data.code == 1){
					window.location.href="appautologin://"+ data.user_id;
					return false;
				} else {
					mui.toast(data.msg);
				}
			}
		});
		</c:if>
	 });
	 
	 $("#password_show").change(function(){
		var thisVal = $(this).val();
		$("#password_hide").val(thisVal);
	 });
	 $("#password_hide").change(function(){
		var thisVal = $(this).val();
		$("#password_show").val(thisVal);
	 });
	 
	 $("#changePassShowType").click(function(){
		if($(this).hasClass("pwd-mode-open")){
			$(this).removeClass("pwd-mode-open");
			$("#password_hide").show();
			$("#password_show").hide();
		}else{
			$(this).addClass("pwd-mode-open");
			$("#password_show").show();
			$("#password_hide").hide();
		}
	 });
});

//]]></script>
</body>
</html>
