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
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body id="body">
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <div id="map_canvas"></div>
  <div class="mapinfo">
    <h2>${fn:escapeXml(entpInfo.entp_name)}</h2>
    <h3>${entpInfo.map.full_name}${entpInfo.entp_addr}</h3>
    <a class="vive" href="javascript:;" id="aVive">查看线路</a><span class="site"> </span> </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}&s=1"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	initialize_map("${af.map.latlng}");
	
	$("#aVive").click(function(){
		
		var entp_name = encodeURI("${entpInfo.entp_name}");
		var entp_addr = encodeURI("${entpInfo.entp_addr}");
		
		 M._bottomNav(
			 ["百度地图","高德地图"],
			 [function(){
				 <c:if test="${!isApp}">
				  location.href='http://api.map.baidu.com/marker?location=${entpLatLng}&title='+ entp_name +'&content='+ entp_addr +'&output=html&src=app';
				 </c:if>
				 <c:if test="${isApp}">
				  location.href='appnavibaidu://${entpLatLng},'+ entp_name +'';
				 </c:if>
			 },function(){
				 $.ajax({
						type: "POST",
						url: "${ctx}/CsAjax.do?method=baiduToGaodeNoApi",
						data: "locations=${entpLatLng}",
						dataType: "json",
						error: function(request, settings) {},
						success: function(datas) {
							var html="";
							if(datas.ret == 0){
								<c:if test="${!isApp}">
								 location.href='http://uri.amap.com/navigation?to='+ datas.latlng +','+ entp_addr +'&mode=car&policy=1&coordinate=gaode&src=mypage';
								</c:if>
							    <c:if test="${isApp}">
							      location.href='appnavigaode://'+ datas.latlng +','+ entp_name +'';
							    </c:if>
							}else{
								mui.toast(datas.msg);
							}
						}
				});
	 		 }]
		 );
    });
	
});
//维护坐标函数
function initialize_map(latlng){
	if('' == latlng) {
		mui.toast("位置未维护,请联系管理员！");
		return;
	}
	 var $mapbox=$("#map_canvas");
     $mapbox.height($mapbox.width());
   	var map = new BMap.Map("map_canvas");
   	var lat = latlng.split(",")[0];
	var lng = latlng.split(",")[1];
   	map.centerAndZoom(new BMap.Point(lat, lng), 14);
	
	var point = new BMap.Point(lat, lng);
	var markerInit = new BMap.Marker(point);
	map.addOverlay(markerInit);
	var ss=new BMap.RouteSearch();
} 
//]]></script>
</body>
</html>
