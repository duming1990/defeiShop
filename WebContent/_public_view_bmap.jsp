<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<div  id="ckzb_map" style="width:880px;">
	<div id="map_canvas" style="width:880px;height:500px;"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	initialize_map("${af.map.latlng}");
});
//维护坐标函数
function initialize_map(latlng){
	if('' == latlng) {
		alert("位置未维护,请联系管理员！");
		return;
	}
   	var map = new BMap.Map("map_canvas");
   	var lat = latlng.split(",")[0];
	var lng = latlng.split(",")[1];
   	map.centerAndZoom(new BMap.Point(lat, lng), 11);
   	map.addControl(new BMap.MapTypeControl()); 
   	map.enableScrollWheelZoom(true); 
	
	var point = new BMap.Point(lat, lng);
	var markerInit = new BMap.Marker(point);
	markerInit.enableDragging();
	map.addOverlay(markerInit);
} 
//]]></script>
