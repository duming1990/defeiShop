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
<jsp:include page="../_nav.jsp" flush="true"/>
<div class="security-box">
  <div class="security-box__status">
    <h4 class="security-box__status__head">您的安全等级</h4>
    <div class="security-box__status__percentbg">
      <div class="security-box__status__percent security-box__status__percent-${percent}"></div>
    </div>
    <c:set var="s_tip" value="安全级别低"/>
    <c:if test="${percent eq 60}">
    <c:set var="s_tip" value="安全级别中"/>
    </c:if>
    <c:if test="${percent gt 60}">
    <c:set var="s_tip" value="恭喜您，账号安全等级高"/>
    </c:if>
    <p class="security-box__status__tip">${s_tip}</p>
  </div>
  <div id="J-security-list" class="security-lists" data-needsmsup="false">
    <ul>
      <c:set var="set_name" value="未设置"/>
      <c:set var="btn_name" value="立即设置"/>
      <c:set var="set_class" value="warn"/>
      <c:if test="${not empty user.password}">
        <c:set var="set_name" value="已设置"/>
        <c:set var="btn_name" value="修改"/>
        <c:set var="set_class" value="success"/>
      </c:if>
      <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>登录密码</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">设置登陆密码，降低盗号风险</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0)" data-opt="${set_class}" id="J-set-password">${btn_name}</a></span> </li>
      <c:set var="set_name" value="未验证"/>
      <c:set var="btn_name" value="立即绑定"/>
      <c:set var="set_class" value="warn"/>
      <c:if test="${not empty user.mobile}">
        <c:set var="set_name" value="已验证"/>
        <c:set var="btn_name" value="换绑"/>
        <c:set var="set_class" value="success"/> 
      </c:if>
<%--       <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>手机号</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">绑定手机，可直接使用手机号登陆</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0)" data-opt="${set_class}" id="J-mobile-bind">${btn_name}</a></span> </li> --%>
<%--       <c:set var="set_name" value="未设置"/> --%>
<%--       <c:set var="btn_name" value="立即验证"/> --%>
<%--       <c:set var="set_class" value="warn"/> --%>
<%--       <c:if test="${not empty user.email}"> --%>
<%--         <c:set var="set_name" value="已设置"/> --%>
<%--         <c:set var="btn_name" value="换绑"/> --%>
<%--         <c:set var="set_class" value="success"/> --%>
<%--       </c:if> --%>
<%--       <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>邮箱</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">您绑定的邮箱，可以使用邮箱登录</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0);" data-opt="${set_class}" id="J-email-verifie">${btn_name}</a></span> </li> --%>
<%--       <c:set var="set_name" value="未设置"/> --%>
<%--       <c:set var="btn_name" value="立即设置"/> --%>
<%--       <c:set var="set_class" value="warn"/> --%>
<%--       <c:if test="${not empty user.password_pay}"> --%>
<%--         <c:set var="set_name" value="已设置"/> --%>
<%--         <c:set var="btn_name" value="修改"/> --%>
<%--         <c:set var="set_class" value="success"/> --%>
<%--       </c:if> --%>
<%--       <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>支付密码</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">保护账号安全，在提现时使用</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0);" data-opt="${set_class}" id="J-password-pay">${btn_name}</a></span> </li> --%>
<%--       <c:set var="set_name" value="未设置"/> --%>
<%--       <c:set var="btn_name" value="立即设置"/> --%>
<%--       <c:set var="set_class" value="warn"/> --%>
<%--       <c:if test="${user.is_set_security eq 1}"> --%>
<%--         <c:set var="set_name" value="已设置"/> --%>
<%--         <c:set var="btn_name" value="修改"/> --%>
<%--         <c:set var="set_class" value="success"/> --%>
<%--       </c:if>  --%>
<%--       <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>安全问题</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">保护账户安全，验证您身份的工具之一</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0);" data-opt="${set_class}" id="J-securequestion">${btn_name}</a></span> </li> --%>
      
<%--       <c:set var="set_name" value="未设置"/> --%>
<%--       <c:set var="btn_name" value="立即设置"/> --%>
<%--       <c:set var="set_class" value="warn"/> --%>
<%--       <c:if test="${(not empty user.bank_account) and (not empty user.bank_branch_name)}"> --%>
<%--         <c:set var="set_name" value="已设置"/> --%>
<%--         <c:set var="btn_name" value="查看"/> --%>
<%--         <c:set var="set_class" value="success"/> --%>
<%--       </c:if> --%>
<%--       <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>银行账号</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">绑定银行卡号</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0)" data-opt="${set_class}" id="J-bank-bind">${btn_name}</a></span> </li> --%>
      
<%--       <c:set var="set_name" value="未认证"/> --%>
<%--       <c:set var="btn_name" value="立即认证"/> --%>
<%--       <c:set var="set_class" value="warn"/> --%>
<%--       <c:if test="${user.is_renzheng eq 1}"> --%>
<%--         <c:set var="set_name" value="已认证"/> --%>
<%--         <c:set var="btn_name" value="修改"/> --%>
<%--         <c:set var="set_class" value="success"/> --%>
<%--       </c:if> --%>
<%--       <c:if test="${(not empty baseAuditRecord) and (baseAuditRecord.audit_state ne 1)}"> --%>
<%--        <c:set var="set_name" value="待审核"/> --%>
<%--         <c:set var="btn_name" value="修改"/> --%>
<%--         <c:set var="set_class" value="warn"/> --%>
<%--       </c:if> --%>
<%--       <li class="security-lists__item"> <span class="security-lists__item__col security-lists__item__col--label"><i class="security-lists__item__status security-lists__item__status--${set_class}"></i>实名认证</span> <span class="security-lists__item__col security-lists__item__col--info security-lists__item__col--info--${set_class}">${set_name}</span> <span class="security-lists__item__col security-lists__item__col--desc">实名认证</span> <span class="security-lists__item__col security-lists__item__col--action"> <a href="javascript:void(0)" data-opt="${set_class}" id="J-shimingrenzheng">${btn_name}</a></span> </li> --%>
    </ul>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#J-set-password").click(function(){
		var opt = $(this).attr("data-opt");
		setPassword(opt);
	});
	$("#J-mobile-bind").click(function(){
		var opt = $(this).attr("data-opt");
		setMobile(opt);
	});
	$("#J-email-verifie").click(function(){
		var opt = $(this).attr("data-opt");
		setEmail(opt);
	});
	$("#J-password-pay").click(function(){
		var opt = $(this).attr("data-opt");
		setPasswordPay(opt);
	});
	$("#J-securequestion").click(function(){
		var opt = $(this).attr("data-opt");
		setSecurequestion(opt);
	});
	$("#J-user-no-verifie").click(function(){
		var opt = $(this).attr("data-opt");
		setUserNo(opt);
	});
	$("#J-bank-bind").click(function(){
		var opt = $(this).attr("data-opt");
		setbank(opt);
	});
	$("#J-shimingrenzheng").click(function(){
		var opt = $(this).attr("data-opt");
		setRenzheng(opt);
	});
});

function setPassword(opt) { 
	var title = "设置密码";
	if(opt == "success"){
		title = "修改密码";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setPassword&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

function setMobile(opt) {
	var title = "绑定手机号";
	if(opt == "success"){
		title = "更换绑定手机号";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setMobile&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function setEmail(opt) {
	var title = "绑定邮箱";
	if(opt == "success"){
		title = "更换绑定邮箱";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setEmail&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function setPasswordPay(opt) { 
	var title = "设置支付密码";
	if(opt == "success"){
		title = "更换支付密码";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setPasswordPay&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 320,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function setSecurequestion(opt) { 
	var title = "设置安全问题";
	if(opt == "success"){
		title = "更换安全问题";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setSecurequestion&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 330,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function setUserNo(opt) {
	var title = "绑定会员卡";
	if(opt == "success"){
		title = "更换绑定会员卡";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setUserNo&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 300,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function setbank(opt) {
	var title = "绑定银行卡";
	if(opt == "success"){
		title = "更换绑定银行卡号";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setBank&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 420,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function setRenzheng(opt) {
	var title = "实名认证";
	if(opt == "success"){
		title = "实名认证修改";
	}
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setRenzheng&opt=" + opt ;
	$.dialog({
		title:  title,
		width:  500,
		height: 380,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

function refreshPage(){
	window.location.reload();
}
//]]></script>

</body>
</html>
