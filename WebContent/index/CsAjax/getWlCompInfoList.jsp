<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择物流公司</title>
<link href="${ctx}/m/styles/css/common/common_v1.css" rel="stylesheet" type="text/css" />
<style type="">
.slider {min-height: 250px; display: block; position: relative; background: #fff; overflow: hidden; }
.slider ul { list-style: none; }
.slider-content { float: left; width: 100%; display: block; overflow: auto; min-height: 250px; }
.slider-content ul { float: left; width: 100%; display: block; position: relative; }
.slider-content ul li { float: left; width: 100%; }
.slider-content ul ul li a { padding: 5px 10px; display: block; border-bottom: 1px solid #e2e2e2; text-transform: capitalize;cursor: pointer; }
.slider-content ul ul li a:hover { background: #f3faff; border-color: #d5ebf9; }
.slider-content .title { padding: 5px 0; text-indent: 10px; background: #bbb; color: #fff; width: 100%; float: left; font-weight: bold; text-transform: uppercase; }
.slider-content .selected .title { background: #666; }
.slider .slider-nav { position: absolute; right: 0; top: 0; background: #666; min-height: 250px; }
.slider .slider-nav ul { padding: 5px 0; }
.slider .slider-nav li a { line-height: 13px; text-align: center; color: #fff; font-weight: bold; display: block; text-transform: uppercase; cursor: pointer; }
.slider #debug { position: absolute; bottom: 0; left: 0; padding: 5px; background: #000; color: #fff; }
.slider .arrow { font-size: 0px; line-height: 0%; width: 0px; border-bottom: 8px solid #fff; border-left: 5px solid #333; border-right: 5px solid #333; position:absolute; top: 5px; }
.slider .down { border-bottom: none; border-top: 8px solid #fff;}
.slider .slide-up, .slider .slide-down { height: 20px; background: #333; text-align: center; cursor: pointer; float: right; width: 100%; position: relative; }
</style>
</head>
<body>
<div id="slider">
	<div class="slider-content">
		<ul>
			<c:forEach var="cur" items="${entityList}">
			<li id="${fn:escapeXml(cur.p_alpha)}"><a name="${fn:escapeXml(cur.p_alpha)}" class="title">${fn:escapeXml(cur.p_alpha)}</a>
				<ul>
					<c:forEach var="curSon" items="${cur.map.tempSonList}">
						<li><a onclick="returnInfo('${curSon.id}','${curSon.wl_comp_name}')">${curSon.wl_comp_name}</a></li>
					</c:forEach>
				</ul>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
                                          
$.fn.sliderNav = function(options) {
	var defaults = { items: ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"], debug: false, height: null, arrows: true};
	var opts = $.extend(defaults, options); var o = $.meta ? $.extend({}, opts, $$.data()) : opts; var slider = $(this); $(slider).addClass('slider');
	$('.slider-content li:first', slider).addClass('selected');
	$(slider).append('<div class="slider-nav"><ul></ul></div>');
	
	var screenHeight = $(window).height() - 40;
	var everyHeight = screenHeight/52;
	
	for(var i in o.items) $('.slider-nav ul', slider).append("<li><a alt='#"+o.items[i]+"' style=\"padding:"+ everyHeight +"px 5px;\">"+o.items[i]+"</a></li>");
	var height = $('.slider-nav', slider).height();
	if(o.height) height = o.height;
	$('.slider-content, .slider-nav', slider).css('height',height);
	if(o.debug) $(slider).append('<div id="debug">Scroll Offset: <span>0</span></div>');
	$('.slider-nav a', slider).mouseover(function(event){
		var target = $(this).attr('alt');
		var cOffset = $('.slider-content', slider).offset().top;
		var tOffset = $('.slider-content '+target, slider).offset().top;
		var height = $('.slider-nav', slider).height(); if(o.height) height = o.height;
		var pScroll = (tOffset - cOffset) - height/8;
		$('.slider-content li', slider).removeClass('selected');
		$(target).addClass('selected');
		$('.slider-content', slider).stop().animate({scrollTop: '+=' + pScroll + 'px'});
		if(o.debug) $('#debug span', slider).html(tOffset);
	});
	if(o.arrows){
		$('.slider-nav',slider).css('top','20px');
		$(slider).prepend('<div class="slide-up end"><span class="arrow up"></span></div>');
		$(slider).append('<div class="slide-down"><span class="arrow down"></span></div>');
		$('.slide-down',slider).click(function(){
			$('.slider-content',slider).animate({scrollTop : "+="+height+"px"}, 500);
		});
		$('.slide-up',slider).click(function(){
			$('.slider-content',slider).animate({scrollTop : "-="+height+"px"}, 500);
		});
	}
};                                          
                                          
                                          
$(document).ready(function(){
	$('#slider').sliderNav();
	$('#transformers').sliderNav({items:['autobots','decepticons'], debug: true, height: '300', arrows: false});
});

function returnInfo(val0,val1){
	var api = frameElement.api, W = api.opener;
	W.document.getElementById("wl_comp_id").value = val0;
	W.document.getElementById("waybill_name").value = val1;
	api.close();
}
//]]></script>
</body>
</html>
