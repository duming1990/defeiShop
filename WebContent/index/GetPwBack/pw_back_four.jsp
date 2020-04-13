<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
<title>找回密码 - ${app_name}</title>
<style type="">
.content {margin: 50px auto;max-width: 980px;}
.form-field {margin: 0 auto 14px;width: 340px;}
</style>
</head>
<body>
<jsp:include page="./_header.jsp" flush="true" />
 <c:set var="headName" value="登录密码"/>
 <c:if test="${af.map.changeType eq 2}">
 <c:set var="headName" value="支付密码"/>
 </c:if>
<div class="content">
  <h3 class="headline"><span class="headline__content">找回${headName}</span></h3>
  <ul class="steps-bar steps-bar--dark cf">
    <li class="step step--first"> <span class="step__num">1.</span> <span>确认账号</span> <span class="arrow__background"></span><span class="arrow__foreground"></span> </li>
    <li class="step step--post"> <span class="step__num">2.</span> <span>选择验证方式</span> <span class="arrow__background"></span><span class="arrow__foreground"></span> </li>
    <li class="step step--post step--current"> <span class="step__num">3.</span> <span>验证/修改</span> <span class="arrow__background"></span><span class="arrow__foreground"></span> </li>
    <li class="step step--last step--post"> <span class="step__num">4.</span> <span>完成</span> </li>
  </ul>
  <div class="form__wrapper">
    <html-el:form action="/GetPwBack.do" method="post">
      <html-el:hidden property="method" value="stepFour" />
      <html-el:hidden property="id" />
      <html-el:hidden property="changeType" />
      <div class="retrieve__title">
        <h3 class="title">您的验证已经成功通过，请立即修改您的${headName}</h3>
      </div>
      <div class="form-field">
        <label>新的${headName}</label>
        <input class="f-text account" name="password" id="password" type="password"  maxlength="20"/>
      </div>
      <div class="form-field">
        <label>确认${headName}</label>
        <input class="f-text account" name="_password" id="_password" type="password"  maxlength="20"/>
      </div>
      <div class="form-field">
        <input type="button" id="btn_submit" class="btn" value="下一步" />
      </div>
    </html-el:form>
  </div>
</div>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	$("#password").attr("datatype","User_name").attr("Require","true").attr("msg","请填写密码,且必须在4-16个字符之间！");
	$("#_password").attr("datatype","Repeat").attr("to","password").attr("msg","密码不一致！");
	
	// 提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
			f.submit();
		}
	});
});

//]]></script>
</body>
</html>