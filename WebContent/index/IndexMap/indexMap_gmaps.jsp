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
.markerTitle1{
  display:block;
  text-align:center;
  color:#fff;

  opacity:0.8;
  background:#4477aa;
  border:solid 3px #336699;
  border-radius:4px;
  box-shadow:2px 2px 10px #333;
  text-shadow:1px 1px 1px #666;
  padding:0 4px;
}
-->
</style>

</head>
<body>
<div id="map_canvas" style="width:${af.map.div_map_width}px; height : ${af.map.div_map_height}px;"></div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 
<script type="text/javascript" src="${ctx}/scripts/google/gmaps.js"></script> 
<script type="text/javascript">//<![CDATA[
document.oncontextmenu=function(){
	return false;
};

$(document).ready(function(){
	var myLatlng = new google.maps.LatLng(40.842310000000005, 111.74884700000007);
	var initLatlng = "${entp_latlng}";
	if ("" != initLatlng) {
		var latlng = initLatlng.split(",");
		myLatlng = new google.maps.LatLng(latlng[0], latlng[1]);
	}
	map = new GMaps({
	    div: '#map_canvas',
	    zoom : 12,
		scrollwheel: false,
	    lat: myLatlng.lat(),
	    lng: myLatlng.lng()
	});
	
	var marker = map.addMarker({
	  lat: myLatlng.lat(),
	  lng: myLatlng.lng(),
	  title : "${entp_name}",
	  icon : '${ctx}/styles/images/gpoint.png'
	});
	
	map.addControl({
	  position: 'top_right',
	  text: '重新加载',
	  style: {
	    margin: '5px',
	    padding: '1px 6px',
	    border: 'solid 1px #717B87',
	    background: '#fff'
	  },
	  events: {
	    click: function(){
	      window.location.reload(true);
	    }
	  }
	});
	var markerLabel = new Label({ map: map.map });
    markerLabel.bindTo('position',marker, 'position');				
 	var divHtml = "<span class='markerTitle'>" + marker.getTitle() + "</span>";				
 	markerLabel.set("text", divHtml);
});
 
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