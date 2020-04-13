<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>地图信息 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/business-info.css"  />
</head>
<body class="pg-deal pg-deal-default pg-deal-detail" id="deal-default" style="position: static;">
<div id="map-canvas" class="mapbody" style="overflow: hidden; -webkit-transform: translateZ(0px); background-color: rgb(229, 227, 223);bottom:0px;height:${af.map.height}px;width:${af.map.width}px;">
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	getMapInfo();
});
function getMapInfo(){
	var map = new BMap.Map("map-canvas");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(${af.map.latlng}), 12);// 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.NavigationControl());// 添加平移缩放控件
	map.addControl(new BMap.ScaleControl());// 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl());//添加缩略地图控件
	map.enableScrollWheelZoom();//启用滚轮放大缩小
	
	var point = new BMap.Point(${af.map.latlng});
	var markerInit = new BMap.Marker(point);
	markerInit.enableDragging();
	map.addOverlay(markerInit);
	
	
	map.addEventListener("click",function(event){
		var url = "${ctx}/IndexViewMap.do?method=viewDetails&latlng=${af.map.latlng}&width=750&height=500&entp_id=${af.map.entp_id}";
		$.dialog({
			title:  "查看详情",
			width:  770,
			height: 520,
	        lock:true ,
	        zIndex: 9999,
			content:"url:"+url
		});
	});
	
}
//]]></script>
</body>
</html>