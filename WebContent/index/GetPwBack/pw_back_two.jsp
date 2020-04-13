<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Authorsite" content="1" />
<meta name="Robots" content="all" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
<title>找回密码 - ${app_name}</title>
</head>
<body>
<jsp:include page="./_header.jsp" flush="true" />
<div class="common-tip sysmsgw" style="display:none;"> 
<span class="tip-status tip-status--error"></span><span id="validate-msg"></span></div>
<div class="content">
<div id="bdw" class="bdw">
  <div id="bd" class="cf">
    <div class="content">
      <h3 class="headline"><span class="headline__content">找回密码</span> </h3>
      <ul class="steps-bar steps-bar--dark cf" style="margin-bottom: 10px;">
        <li class="step step--current" style="z-index:3;width:33.333333333333336%;"><span class="step__num">1.</span> <span>选择验证方式</span> <span class="arrow__background"></span> <span class="arrow__foreground"></span> </li>
        <li class="step step--pre" style="z-index:2;width:33.333333333333336%;"><span class="step__num">2.</span> <span>验证/修改</span> <span class="arrow__background"></span> <span class="arrow__foreground"></span> </li>
        <li class="step step--pre" style="z-index:1;width:33.333333333333336%;"><span class="step__num">3.</span> <span>完成</span> <span class="arrow__background"></span> <span class="arrow__foreground"></span> </li>
      </ul>
      <p class="verify-tip-title">您正在为账户<b>${af.map.phone}</b>
       <c:set var="headName" value="登录密码"/>
	   <c:if test="${af.map.changeType eq 2}">
	   <c:set var="headName" value="支付密码"/>
	   </c:if>
      重置${headName}，请选择找回方式：</p>
      <ul class="find-ways">
      <c:if test="${not empty hasPhone}">
        <li class="way ">
        <html-el:form action="/GetPwBack.do" method="post" styleClass="way__content cf">
		<html-el:hidden property="method" value="stepTwo" />
		<html-el:hidden property="user_id" value="${af.map.id}"  />
		<html-el:hidden property="type" value="1" />
		<html-el:hidden property="changeType" value="${af.map.changeType}" />
            <input class="btn immi-retrieve" type="submit" value="立即找回"  />
            <i class="icon icon--mobile"></i> <span class="title">通过短信验证</span> <span class="description">需要您绑定的手机可以接收短信</span>
          </html-el:form>
        </li>
        </c:if>
        <c:if test="${not empty hasEmail}">
        <li class="way  way--last ">
        <html-el:form action="/GetPwBack.do" method="post" styleClass="way__content cf">
		<html-el:hidden property="method" value="stepTwo" />
		<html-el:hidden property="type" value="2" />
		<html-el:hidden property="user_id" value="${af.map.id}"  />
		<html-el:hidden property="changeType" value="${af.map.changeType}" />
            <input class="btn immi-retrieve" type="submit" value="立即找回"  />
            <i class="icon icon--email"></i> <span class="title">通过绑定的邮箱</span> <span class="description">安全链接将发送到您绑定的邮箱</span>
         </html-el:form>
        </li>
        </c:if>
      </ul>
    </div>
  </div>
</div>
</div>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>