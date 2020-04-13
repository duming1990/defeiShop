<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
.tab-lst {display: table;width: 100%;border-bottom: 1px solid #fff;}
.tab-lst li {display: table-cell;width: 25%;text-align: center;}
.tab-lst li a {border-bottom: 1px solid #d1ccc8;}
.tab-lst li a.on {border-bottom: 1px solid #cc0000;color:#cc0000;}
.tab-lst li a {display: block;padding: 10px 0;color: #b8b4b2;font-size: .875rem;text-shadow: 1px 1px 1px rgba(255,255,255,.2);}
.detail img {max-width: 100% !important;height: auto!important;}
</style>
</head>
<body id="body" style="background-color:#FFF;">
<div class="content" id="div_content">
  <div class="sift-tab" style="height: 42px;">
    <div id="fixed" class="sift-tab" style="height: 42px;">
      <ul class="tab-lst">
        <li id="pdli1"><a href="javascript:void(0)" class="on">商品介绍</a></li>
      </ul>
    </div>
  </div>
  <div class="detail" id="pdcontent1"><div style="padding-top:40px;padding-left:20px;padding-right:20px;">${pdContentXxxx} </div></div>
</div>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	
	 $("li", ".tab-lst").click(function(){
		 var $this = $(this);
	   	 var id = $this.attr("id").replace("pdli", "");
	   	 $this.siblings().find("a").removeClass("on");
	   	 $this.find("a").addClass("on");
	   	 var contentvisible = $(".detail:visible", "#div_content");
		 	 contentvisible.fadeOut("fast", function() {
		 		$("#pdcontent" + id, "#div_content").fadeIn("fast",function(){});
			 });
	 });
	 
	 
	 <c:if test="${isWeixin}">
	 wx.config({
		 debug: false,
	      appId: '${appid}',
	      timestamp: ${timestamp},
	      nonceStr: '${nonceStr}',
	      signature: '${signature}',
	      jsApiList: [
	        'previewImage'
	      ]
	  });
	 
	 var imageUrls = [];
	 $("#pdcontent1").find("img").each(function(){
		 var url = "${ctxReal}" + $(this).attr("src");
		 imageUrls.push(url);
	 });
	 
	 $("#pdcontent1").find("img").each(function(){
		 $(this).click(function(){
			 var url = "${ctxReal}" + $(this).attr("src");
			 wx.previewImage({
					current : url,
					urls : imageUrls
				});
		 });
	 });
	</c:if> 
	 
});
//]]></script>
</body>
</html>