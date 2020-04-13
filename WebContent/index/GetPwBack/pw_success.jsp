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
 <c:if test="${changeType eq 2}">
 <c:set var="headName" value="支付密码"/>
 </c:if>
<div class="content">
  <h3 class="headline"><span class="headline__content">找回${headName}</span></h3>
  <ul class="steps-bar steps-bar--dark cf">
    <li class="step step--first"> <span class="step__num">1.</span> <span>确认账号</span> <span class="arrow__background"></span><span class="arrow__foreground"></span> </li>
    <li class="step step--post"> <span class="step__num">2.</span> <span>选择验证方式</span> <span class="arrow__background"></span><span class="arrow__foreground"></span> </li>
    <li class="step step--post"> <span class="step__num">3.</span> <span>验证/修改</span> <span class="arrow__background"></span><span class="arrow__foreground"></span> </li>
    <li class="step step--last step--current"> <span class="step__num">4.</span> <span>完成</span> </li>
  </ul>
  <div class="form__wrapper">
  <h3 class="retrieve__title">恭喜您已成功修改了${headName}</h3>
  <div class="retrieve-result">
    <h3 class="retrieve__title">
        <span class="tip__title">您的${headName}已经重新设置，请妥善保管</span>
    </h3>
    <div class="form-field retrieve-result__content">
    	<c:url var="url" value="/IndexLogin.do" />
        <a class="btn" href="${url}">立即登录</a>
    </div>
    </div>
</div>
</div>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$(document).ready(function(){
	
});

//]]></script>
</body>
</html>