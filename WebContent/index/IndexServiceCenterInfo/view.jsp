<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务网点 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/newscontent.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/pages.css"  />
<style>
a{ cursor:pointer;}
.citys-list{padding-bottom:20px;padding-top:0px;position:relative;z-index:2;}
.citys-list DL{border:#fff 1px solid;padding:10px 0;background:#fff;}
.citys-list DL.hover{background:#edf1f5;border:1px solid #e4e6ea;}
.citys-list DT{padding-left:25px;width:130px;font-family:Arial;float:left;color:#444;font-size:12px;}
.citys-list DD{border-left:#b2b2b2 1px dashed;margin:0px 0px 0px 140px;padding-left:15px;}
.citys-list DD a{padding-bottom:1px;margin:0px 15px 5px 0px;padding-left:5px;padding-right:5px;display:inline-block;color:#666;font-size:12px;text-decoration:none;padding-top:1px;}
.citys-list .bold{font-weight:bold;color:#c05600;}
.citys-list DD .itemsTag{position:absolute;background:#fbf8cf;border:1px solid #ddd;padding:10px;width:800px;z-index:8;display:none;font-size: 12px;}
.citys-list DD .itemsTag span,.citys-list DD .itemsTag b,.citys-list DD .itemsTag i{float:left;margin-right:10px;height:30px;line-height:30px;white-space:nowrap;font-style:normal;}
.citys-list DD .itemsTag span{margin:0;font-weight:bold;}
.citys-list DD .itemsTag b{color:#c05600;}
.citys-list DD .itemsTag u{text-decoration:none;position:absolute;background:#fbf8cf;border:1px solid #ddd;border-bottom:0;padding:5px 5px 2px;z-index:9;white-space:nowrap;}
.citys-list DD .itemsTag s{text-decoration:none;float:right;color:#D62323;cursor:pointer;}
</style>
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<div id="map-canvas" style="overflow: hidden; -webkit-transform: translateZ(0px); background-color: rgb(229, 227, 223);margin-left:auto;margin-right:auto;bottom:0px;height:600px;width:980px;">
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	getMapInfo();
});
function getMapInfo(){
	var map = new BMap.Map("map-canvas");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(101.833479,37.516756), 5);// 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.NavigationControl());// 添加平移缩放控件
	map.addControl(new BMap.ScaleControl());// 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl());//添加缩略地图控件
	map.enableScrollWheelZoom();//启用滚轮放大缩小
	
	<c:forEach items="${entityList}" var="cur">
	var point = new BMap.Point(${cur.position_latlng});
	var markerInit = new BMap.Marker(point);
	markerInit.disableDragging();//enableDragging和disableDragging方法可用来开启和关闭标注的拖拽功能
	map.addOverlay(markerInit);
	markerInit.addEventListener("click", function(e){    
		 var opts = {    
				 width : 250,     // 信息窗口宽度    
				 height: 100,     // 信息窗口高度    
				 title : "服务网点信息"  // 信息窗口标题   
				}    
		var infoWindow = new BMap.InfoWindow("网点名称："+'${cur.servicecenter_name}'+"<p/>网点地址："+'${cur.servicecenter_addr}'+"<p/>联系方式："+'${cur.servicecenter_linkman_tel}', opts);  // 创建信息窗口对象    
		map.openInfoWindow(infoWindow, e.point);      // 打开信息窗口
	});
	</c:forEach>
}
//]]></script>
</body>
</html>
