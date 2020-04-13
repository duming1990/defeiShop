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
</head>
<body >
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content my-center">
	<div class="myls-listbox">
		<a href="${ctx}/m/MMySecurityCenter.do?method=setPassword" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;登录密码</a>
		<a href="${ctx}/m/MMySecurityCenter.do?method=setMobile" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;换绑手机</a>
		<a href="${ctx}/m/MMySecurityCenter.do?method=setPasswordPay" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;支付密码</a>
		<a href="${ctx}/m/MMySecurityCenter.do?method=setSecurequestion" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;安全问题</a>
<%-- 		<a href="${ctx}/m/MMySecurityCenter.do?method=setUserNo" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;会员卡</a> --%>
		<a href="${ctx}/m/MMySecurityCenter.do?method=setBank" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;银行账号</a>
		<a href="${ctx}/m/MMySecurityCenter.do?method=setRenzheng" class="name sel" onclick="void(0);">&nbsp;&nbsp;&nbsp;&nbsp;实名认证</a>
	</div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />			
</body>
</html>