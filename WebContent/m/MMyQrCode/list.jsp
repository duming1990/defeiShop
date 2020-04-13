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
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<style>
canvas, img {
    image-rendering: optimizeQuality;
    image-rendering: -moz-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: optimize-contrast;
    -ms-interpolation-mode: nearest-neighbor;
}
</style>
</head>
<body style="height:91%">
<div class="content" id="content" style="height:100%;background:url(${ctx}/images/bg.png);background-repeat:no-repeat;background-size: 100% 100%;background-origin: content-box">
  <div class="blank" style="position:absolute;top:10px;left:39%;">
    <c:set var="user_logo" value="${ctx}/styles/imagesPublic/user_header.png" />
    <c:if test="${not empty userInfo.user_logo}">
      <c:set var="user_logo" value=" ${ctx}/${userInfo.user_logo}@s400x400" />
    </c:if>
    <c:if test="${fn:contains(userInfo.user_logo, 'http://')}">
      <c:set var="user_logo" value="${userInfo.user_logo}"/>
    </c:if>
  <img src="${user_logo}" width="76" height="76" style="border-radius: 50%;"/></div>
  <div class="pay-pw" style="">
    <div class="blank" style="text-align: center;padding:23%;">${userInfo.user_name}</div>
    <div class="mc">
      <ul class="com-ul" style="text-align: center">
        <li class="last"><img alt="我的二维码" src="${ctx}/${fileTruePath}?t=${time}" width="36%" style="margin-top:-69px;" />
        </li>
      </ul>
    </div>
  </div>
</div>
 <img id="imageQR" src="" style="width:100%;height:100%;"/>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/html2canvas.js"></script>
<script type="text/javascript">
var wxData = {
        imgUrl : 'http://${app_domain}/styles/imagesPublic/user_header.png',
        link : '${share_url}',
        desc: '快来【${app_name}】赚钱吧！点击赚钱：${share_url}',
        title : "${userInfo.user_name}的仁义联盟商城，红利发放中。。。-${app_name}"
    };

function share(user_id,is_app,user_name) {
	if (is_app) {
		window.location.href="appshareyaoqing://" + user_id + "//" + user_name;
	} else {
		mobShare.config({
		    params: {
		        url: wxData.link, // 分享链接
		        title: wxData.title, // 分享标题
		        description: wxData.desc, // 分享内容
		        pic: wxData.imgUrl, // 分享图片，使用逗号,隔开
		        reason: wxData.desc,//只应用与QZone与朋友网
		    }
		});
	}
}
</script>
<c:if test="${not is_app}">
<jsp:include page="../_share_wap.jsp" flush="true" />
</c:if>
</body>
</html>
