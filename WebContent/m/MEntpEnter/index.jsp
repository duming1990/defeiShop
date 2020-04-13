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
<link href="${ctx}/m/styles/css/my/my-v1.css?20160406" rel="stylesheet" type="text/css" />
</head>
<body >
<header class="index app_hide" >
    <div class="c-hd">
      <section class="back"> <a href="javascript:history.go(-1);"><i></i></a> </section>
      <section class="hd-title">个人中心</section>
      <section class="side"> 
      </section>
    </div>
</header>
<div class="content my-center">
	<div class="myls-listbox">
		<c:url var="url" value="/m/MMyAccount.do?method=list&mod_id=1100600200" />
		<a onclick="goUrl('${url}')" class="name sel">我的账号</a>
	</div>
	<div class="myls-listbox">
	<c:url var="url" value="/m/MMyFav.do?mod_id=1100600400" />
		<a onclick="goUrl('${url}')" class="name sel">我的收藏</a>
	</div>
	<div class="myls-listbox">
	<c:url var="url" value="/m/MMyShippingAddress.do?mod_id=1100600500" />
		<a onclick="goUrl('${url}')" class="name sel">收货地址</a>
	</div>
	<div class="myls-listbox">
	<c:url var="url" value="/m/MMySecurityCenter.do?mod_id=1100620100" />
		<a onclick="goUrl('${url}')" class="name sel">安全中心</a>
	</div>
	<div class="myls-listbox">
	<c:url var="url" value="/m/MMyMsg.do?mod_id=1100630100" />
		<a onclick="goUrl('${url}')" class="name sel">消息中心</a>
	</div>
	<c:if test="${empty af.map.appid_weixin}">
	<div class="myls-listbox">
	<c:url var="url" value="/weixin/WeixinLogin.do?method=bindWeixin&user_id=${userInfo.id}" />
		<a onclick="goUrl('${url}')" class="name sel">绑定微信</a>
	</div>
	</c:if>
	<c:if test="${af.map.is_entp eq 1}">
	<div class="myls-listbox">
	 <c:url var="url" value="/m/MEntpInfo.do?method=index&entp_id=${af.map.own_entp_id}" />
	 <a onclick="goUrl('${url}')" class="name sel">我的店铺</a>
	</div>
	</c:if>
<!-- 	<div class="myls-listbox"> -->
<%-- 	<c:url var="url" value="/m/MCardOrderHy.do?mod_id=1100300097" /> --%>
<%-- 		<a onclick="goUrl('${url}')" class="name sel">会员卡购买</a> --%>
<!-- 	</div> -->
	
	<div class="box submit-btn">
		<a class="com-btn" id="logOut">退出登录</a>
	</div>
</div>
<c:url var="urlOut" value="/m/MIndexLogin.do?method=logout"/>
<jsp:include page="../_footer.jsp" flush="true" />			
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	$("#logOut").click(function(){
		 Common.confirm("你确定要退出登录吗？",["确定","取消"],function(){
			 goUrl('${urlOut}');
		},function(){
		});
	});
});
//]]></script>
</body>
</html>