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
<style type="text/css">
.cancel-btn {
    height: 100%;
    background: #d2d2d2;
    text-align: center;
    color: #fff;
    cursor: pointer;
    overflow: hidden;
    border: none;
    border-radius: .06rem;
    padding: .18rem .2rem;
    font-size: .28rem;
    line-height: 100%;
}
</style>
</head>
<body >
<jsp:include page="../_header.jsp" flush="true" />
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
	<div class="myls-listbox">
		<a class="name sel" id="clearLocalStorage">清空缓存</a>
	</div>
	<div class="myls-listbox">
	<c:url var="url" value="/m/MMyAccount.do?method=getUserXyInfo" />
		<a onclick="goUrl('${url}')" class="name sel">服务条款</a>
	</div>
	<div class="box submit-btn">
		<a class="com-btn" id="logOut">退出登录</a>
	</div>
	<div class="box submit-btn">
		<a class="cancel-btn" id="cancelUser">账号注销</a>
	</div>
</div>
<c:url var="urlOut" value="/m/MIndexLogin.do?method=logout"/>
<c:url var="urlCancelUser" value="/m/MMyAccount.do?method=cancelUser"/>
<jsp:include page="../_footer.jsp" flush="true" />			
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	$("#logOut").click(function(){
		 Common.confirm("您确定要退出登录吗？",["确定","取消"],function(){
			 goUrl('${urlOut}');
		},function(){
		});
	});
	
	$("#cancelUser").click(function(){
		 Common.confirm("账号注销将删除您在本平台的所有数据，请谨慎选择，您确定要注销账号吗？",["确定","取消"],function(){
			 goUrl('${urlCancelUser}');
		},function(){
		});
	});
	$("#clearLocalStorage").click(function(){
		 Common.confirm("您确定要清空缓存吗？",["确定","取消"],function(){
			 localStorage.clear();
			 goUrl('/m/index.shtml');
		},function(){
		});
	});
});
//]]></script>
</body>
</html>