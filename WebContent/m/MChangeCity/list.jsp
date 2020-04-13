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
<style type="text/css">
a.anchor { visibility: hidden; }
.city-side-nav { position: fixed; height: inherit; }
.city-side-nav img { -webkit-touch-callout: none; -moz-user-select: none; -webkit-user-select: none; }
.footer { margin-right: 20px; }
header.index { position: fixed; width: 100%; z-index: 10004; left: 0px; top: 0px; }
.citybox { margin-top: 0.88rem; }
.city-side-nav { top: 0.88rem; }
</style>
</head>
<body >
<div id="wrap">
  <jsp:include page="../_header.jsp" flush="true" />
  <div class="conter citybox">
    <div class="city-side-nav" style="width: 20px;"> <img src="${ctx}/m/styles/img/common/city_side.png" alt="" draggable="false" width="20px"> </div>
    <div class="city-view"> <a name="0" class="anchor"> </a>
      <div class="city-title">定位到的城市</div>
      <ul class="city-ul">
        <li class="location">
        <a id="local_p_index">定位中...</a></li>
      </ul>
      <div class="city-title">进入</div>
      <ul class="city-ul">
      	<c:url var="url" value="/m/MChangeCity.do?method=selectChangeCity&p_index=${quanguo_p_index}" />
        <li><a onclick="selectCity('${url}','${quanguo_p_index}','${quanguo_p_name}')">${quanguo_p_name}</a></li>
      </ul>
      <a name="1" class="anchor"> </a>
      <c:forEach var="cur" items="${entityList}">
      <a name="${fn:escapeXml(cur.p_alpha)}" class="anchor"> </a>
      <div class="city-title">${fn:escapeXml(cur.p_alpha)}</div>
      <ul class="city-list">
        <c:forEach var="curSon" items="${cur.map.tempSonList}">
	        <c:url var="url" value="/m/MChangeCity.do?method=selectChangeCity&p_index=${curSon.p_index}" />
	        <li><a onclick="selectCity('${url}','${curSon.p_index}','${curSon.p_name}')"><font>${fn:replace(curSon.p_name, '市', '')}</font></a></li>
        </c:forEach>
      </ul>
      </c:forEach>
    </div>
  </div>
  <div id="city_overlay" style="width: 100%;height: 100%;visibility: hidden;z-index: 99999;position: fixed;top: 0;left: 0;"></div>
  <jsp:include page="../_footer.jsp" flush="true" />
</div>
<script type="text/javascript" src="${ctx}/m/js/touch.min.js"></script>
<script type="text/javascript" src="${ctx}/m/js/city.min.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${map_baidu_key}"></script>

<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var p_index = localStorage.getItem('p_index_service_info');
	var p_name = localStorage.getItem('p_name_service_info');
	
	if(null != p_index && p_index != '' && p_index != "undefined") {
	 if(null != p_name && p_name != '' && p_name != "undefined") {
		 $("#local_p_index").text(p_name);
		 var url = app_path + "/m/MChangeCity.do?method=selectChangeCity&p_index=" + p_index;
			$("#local_p_index").click(function(){
				selectCity(url,p_index,p_name);
			});
	 }
	}
});

function selectCity(url,p_index,p_name){
	localStorage.setItem('p_index_service_info', p_index);
	localStorage.setItem('p_name_service_info',p_name);
	if(url){
		Common.loading();
		window.setTimeout(function () {
			Common.hide();
			location.href = url;
		}, 1000);
	}
}

//]]></script>
</body>
</html> 