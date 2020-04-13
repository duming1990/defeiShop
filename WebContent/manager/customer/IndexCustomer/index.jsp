<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
</head>
<body class="has-order-nav" id="growth" style="position: static;">
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	if($.cookie("parId_cookie") != null){
		openItem($.cookie("parId_cookie"),true);
	}else{
		$('#header-item').addClass('header-item__current');			
		$('#workspace').attr('src','${ctx}/manager/customer/IndexCustomer.do?method=welcome');
	}
	
});
function refreshPage(){
	window.location.reload();
}

// 提示绑定手机号
    var tianxiehaoma="${tianxiehaoma}";
    var title = "请绑定手机号";
	var url = "${ctx}/manager/customer/MySecurityCenter.do?method=setMobile&isBind=true";
	
	if(tianxiehaoma==0){
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

function openItem(args,isCookie){
	var parId,src;
	if(isCookie){
		var spl = args.split(",");
		parId = spl[0];
		src = spl[3];
	}
	$("#son_" + parId).addClass("current");
	$("#workspace").attr("src",src);

}

function loadIframe(url,parId){
	if (null != parId && 0 != parId) {
		$("#son_" + parId).siblings().removeClass("current");
		$("#son_" + parId).parent().parent().siblings().find("li").removeClass("current");
		$("#son_" + parId).addClass("current");
	} else {
		$(".side-nav__list dd").each(function(){
			$(this).find("li").removeClass("current");
		});
	}
	var parId_cookie = parId + "," + url;
	$.cookie("parId_cookie", parId_cookie, { path: '/' });
	if (url.indexOf("?")){
		url = url + "&t=" + new Date().getTime();
	} else {
		url = url + "?t=" + new Date().getTime();
	}
	$("#workspace").attr("src", url);
}

function iFrameHeight() {   
	var ifm= document.getElementById("workspace"); 
	ifm.height = 50;
	var subWeb = document.frames ? document.frames["workspace"].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight + 50;
	}   
} 

function toUpLevel(){
	$.jBox.confirm("付费会员将缴费${upLevelNeedPayMoney}元,你确定要升级成为付费会员吗？", "${app_name}", submit2, { buttons: { '确定': true, '取消': false} });
}

var submit2 = function (v, h, f) {
	 if (v == true) {
		 var url = "${ctx}/IndexPayment.do?method=PayForUpLevel";
		 window.open(url);
	 }
	 return true;
};

//]]></script>
<div class="bdw" style="padding-bottom: 101px;">
 <jsp:include page="top.jsp" flush="true" />
 <div id="bd" class="cf">
  <jsp:include page="left.jsp" flush="true"/>
  <iframe id="workspace" name="workspace" width="1200px" style="float: right;" frameborder="0" onLoad="iFrameHeight()"></iframe>
</div>
</div>
<jsp:include page="../../../_footer.jsp" flush="true" />
</body>
</html>