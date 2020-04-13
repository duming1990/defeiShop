<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta charset="utf-8">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="pragma" content="no-cache">
<link href="${ctx}/commons/bootstrap/default/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/app/styles/css/main.css" rel="stylesheet">
<script src="${ctx}/commons/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="${ctx}/commons/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/mobiledialog/mobiledialog.js"></script> 
<style type="text/css">
ul li{
  float: left;
}
</style>
</head>
<body>
<c:url var="iosUrl" value="http://a.app.qq.com/o/simple.jsp?pkgname=io.ninePorts.app" />
<c:url var="androidUrl" value="http://a.app.qq.com/o/simple.jsp?pkgname=io.ninePorts.app" />
<c:url var="appurl" value="http://a.app.qq.com/o/simple.jsp?pkgname=io.ninePorts.app" />
<script>
  if(M.isAndroid()){
   location.href="${appurl}";
  }else if(M.isIOS()){
   location.href="${appurl}";
  }
</script>
  <section id="web">
    <div class="header">
      <div class="header-content-1">
        <img src="${ctx}/app/styles/images/kdjz_logo.png">
        <span class="title">${app_name}</span>
      </div>

      <div class="header-content-2" style="padding-top:139px">
        <div class="left">
          <p class="title-1">简单、安全、便捷</p>
          <p class="title-2">您生活的好助手</p>
        </div>
        <div class="right">
          <img src="${ctx}/app/styles/images/kdjz_screen_01.png">
        </div>
      </div>
    </div>
    <div style="clear: both;"></div>
    <div class="middle4"></div>
    <a id="r_download"></a>
    <div class="middle5">
      <div class="content">
        <p>手机下载APP</p>
      </div>
      <div class="content" style="padding-top:70px;">
        <ul>
          <li><img src="${ctx}/styles/imagesPublic/qr_code.png" width="320"></li>
          <li>
          <c:url var="url" value="${iosUrl}" />
          <a href="${url}"><img src="${ctx}/app/styles/images/kdjz_iphone.png" width="320"></a></li>
          <c:url var="url" value="${androidUrl}" />
          <li><a href="${url}"><img src="${ctx}/app/styles/images/kdjz_android.png" width="320"></a></li>
        </ul>
      </div>
      <div style="clear: both;"></div>
      <div class="content" style="padding-top:50px;">
        <h6>©2015 ${app_name}</h6>
        <h6>${app_beian_no}</h6>
      </div>
    </div>
  </section>

  <!-- wap -->
  <section id="wap">
    <div class="wap-top-margin-height"></div>
    <div>
      <img src="${ctx}/app/styles/images/kdjz_wap_header.png" style="width:100%">
    </div>
    
    <div class="wap-margin-height"></div>
    <div class="container">
      <div class="row">
        <div style="display:inline;margin-left:10%">
          <c:url var="url" value="${iosUrl}" />
          <a href="${url}">
            <img src="${ctx}/app/styles/images/kdjz_wap_ios.png" style="width:38.2%">
          </a>
        </div>
        <div style="display:inline">
          <img src="${ctx}/app/styles/images/kdjz_tap.png" style="width:2.3%">
        </div>
        <div style="display:inline">
          <c:url var="url" value="${androidUrl}" />
          <a href="${url}">
            <img src="${ctx}/app/styles/images/kdjz_wap_android.png" style="width:38.2%">
          </a>
        </div>
      </div>
    </div>
    <div>
      <img src="${ctx}/app/styles/images/kdjz_third_hr.png" style="width:100%">
    </div>
    <div>
      <img src="${ctx}/app/styles/images/kdjz_wap_screen.png" style="width:100%">
    </div>
    <div style="height:8px;"></div>
  </section>
  <script type="text/javascript">
  function download(){
	  alert("请点击微信右上角按钮，然后在弹出的菜单中，点击在浏览器中打开，即可安装");
  }
  </script>
</body>
</html>
