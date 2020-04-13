<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="${ctx}/commons/styles/green/base.css" rel="stylesheet" type="text/css" />
<title>${entp_name}地理位置-${app_name}</title>
<style type="text/css">
<!--
.markerTitle {
padding: 4px;position: relative;left: 18px;color:#E61F11;border:#D50C0C solid 2px;background-color:#fff;
-moz-border-radius-topleft: 4px; -webkit-border-top-left-radius: 4px; -khtml-border-top-left-radius: 4px; border-top-left-radius: 4px; 
-moz-border-radius-topright: 4px; -webkit-border-top-right-radius: 4px; -khtml-border-top-right-radius: 4px; border-top-right-radius: 4px; 
-moz-border-radius-bottomleft: 4px; -webkit-border-bottom-left-radius: 4px; -khtml-border-bottom-left-radius: 4px; border-bottom-left-radius: 4px; 
-moz-border-radius-bottomright: 4px; -webkit-border-bottom-right-radius: 4px; -khtml-border-bottom-right-radius: 4px; border-bottom-right-radius: 4px;
}
-->
</style>

</head>
<body>
<div id="map_canvas" style="width:${af.map.div_map_width}px; height : ${af.map.div_map_height}px;"></div>
<div style=" position: absolute;right: 95px; top: 4px;font-weight: bold;background-color:#fff;border: 1px solid #ccc;padding: 2px"><a style="color: #000" href="javascript:void(0);" onclick="window.location.reload(true);">重新加载</a></div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 
<script type="text/javascript" src="${ctx}/scripts/google.map.plugin.js"></script> 
<script type="text/javascript">//<![CDATA[
document.oncontextmenu=function(){
	return false;
};
var map;
google.maps.event.addDomListener(window, 'load', initialize);
//初始化地图
function initialize() {
	var myLatlng = new google.maps.LatLng(40.842310000000005, 111.74884700000007);
	var initLatlng = "${entp_latlng}";
	if ("" != initLatlng) {
		var latlng = initLatlng.split(",");
		myLatlng = new google.maps.LatLng(latlng[0], latlng[1]);
	}
	var myOptions = {
		zoom : 12,
		center : myLatlng,
		navigationControl : true,
		scaleControl : true,
		streetViewControl : true,
		scrollwheel: false,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	//map.setCenter(myLatlng);
	var marker = new google.maps.Marker( {
		map : map,
		title : "${entp_name}",
		position : myLatlng,
		draggable : false,
		icon : '${ctx}/styles/images/gpoint.png'
	});
	
	var markerLabel = new Label({ map: map });
 	markerLabel.bindTo('position',marker, 'position');				
 	var divHtml = "<span class='markerTitle'>" + marker.getTitle() + "</span>";				
 	markerLabel.set("text", divHtml);
}
//]]>
</script>
</body>
</html>