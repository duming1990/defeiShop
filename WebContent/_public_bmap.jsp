<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<link href="${ctx}/commons/styles/green/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<style type="">
#query{
border: 1px solid #EBEBEB;
height: 20px;
}
</style>
</head>
<body onload="initialize_f('${af.map.result_id}')">
<div id="whzb_map" style="width:100%;">
	<div align="left" style="margin-top:5px;"><span style="font-weight: bold">关键字:</span>
	  <input type="text" size="60" id="query" onkeydown="javascript:if(event.keyCode==13) submitQuery({showInfo : true,result_id : '${af.map.result_id}'});" />
	   <a class="butbase" href="#" onclick="submitQuery({showInfo : true,result_id : '${af.map.result_id}'});"><span class="icon-search">查 询</span></a>
	   <a class="butbase" href="#" onclick="closeDialog()"><span class="icon-ok">确定坐标</span></a>&nbsp;&nbsp;
	</div>
	<div align="left" id="results">拖动（下面 ↓）地图中的点或者在地图上点击，维护坐标</div>
	<div id="map_canvas" style="width:100%;height:440px;"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}"></script>
<script type="text/javascript">//<![CDATA[
var map;       
var ctxpath = "${ctx}";
var markerInit;
var api = frameElement.api;
var W = api.opener;
//维护坐标函数
function initialize_f(result_id){
	var initLatlng = W.document.getElementById(result_id).value;
	//百度地图API功能
   	map = new BMap.Map("map_canvas");// 创建Map实例
	
   	if ("" != initLatlng) {
   		var latlng = initLatlng.split(",");
		map.centerAndZoom(new BMap.Point(latlng[0], latlng[1]), 11);// 初始化地图,设置中心点坐标和地图级别
	}else{
		map.centerAndZoom(new BMap.Point(${baidu_default_point}), 11);// 初始化地图,设置中心点坐标和地图级别
	}
	map.addControl(new BMap.NavigationControl());// 添加平移缩放控件
	map.addControl(new BMap.ScaleControl());// 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl());//添加缩略地图控件
	map.enableScrollWheelZoom();//启用滚轮放大缩小
	map.addControl(new BMap.MapTypeControl());//添加地图类型控件
	
	var point;
	if ("" != initLatlng) {
		var latlng = initLatlng.split(",");
		point = new BMap.Point(latlng[0], latlng[1]);
	}else{
		point = new BMap.Point(${baidu_default_point});
	}
	markerInit = new BMap.Marker(point);
	markerInit.enableDragging();
	map.addOverlay(markerInit);
	markerInit.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	
	markerInit.addEventListener("dragging",function(){
		markerInit.setPosition(this.getPosition());
		var newPoint = this.getPosition().lng + "," + this.getPosition().lat;
		W.document.getElementById(result_id).value = newPoint;
	});
	map.addEventListener("click",function(event){
		var cp = event.point;
		markerInit.setPosition(cp);
		map.setCenter(cp);
		var newPoint = cp.lng + "," + cp.lat;
		W.document.getElementById(result_id).value = newPoint;
	});
}   
function closeDialog(){
	api.close();	
}
//]]></script>
<script type="text/javascript" src="${ctx}/scripts/baidu.map.geocoder.js?v20160114"></script>
</body>
</html>