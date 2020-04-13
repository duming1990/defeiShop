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
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/erweima/index.css?v=20180314"  />
<style type="text/css">
.c-hd section.back a {
    line-height: 100%;
    padding: .22rem .2rem;
    height: .44rem;
    width: .4rem;
    margin: .2rem .3rem 0;
}
</style>
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
    <div class="user">
    <c:set var="user_logo" value="${ctx}/styles/imagesPublic/user_header.png" />
    <c:if test="${not empty userInfo.user_logo}">
    <c:set var="user_logo" value="${ctx}/${userInfo.user_logo}" />
  </c:if>
  <c:if test="${fn:contains(userInfo.user_logo, 'http://')}">
    <c:set var="user_logo" value="${userInfo.user_logo}" />
  </c:if>
        <img class="img" src="${user_logo}"/>
        <div class="msgs">
            <p class="msg">${userInfo.user_name}的专属二维码</p>
            <p class="msg">邀请您加入${app_name}</p>
        </div>
    </div>
    <div class="code">
        <img class="img" alt="我的二维码" src="${ctx}/${fileTruePath}?t=${time}" />
        <p class="msg1">长按图片识别二维码</p>
        <p class="msg2">您的加入<br/>故乡离得更近，变得更美</p>
    </div>
    <div class="remark">
        <p class="msg">安徽${app_name}电子商务有限公司</p>
        <p class="msg">更多详情微信搜索“${app_name}”公众号</p>
    </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	  var inData = { 
			  appId: '${appid}',
		      timestamp: '${timestamp}',
		      nonceStr: '${nonceStr}',
		      signature: '${signature}'
	  };

	  var user_logo = "${userInfo.user_logo}";
	  if(user_logo.indexOf("http://") == -1){
		  user_logo = Common.api + '${user_logo}';
  	  }
	  var shareData = { 
			title : '${userInfo.user_name}的专属二维码',
			desc : '${ShareQrCodeDesc}',
			link : '${share_url}',
			imgUrl :user_logo,
      };
	 Common.weixinConfig(inData,shareData);
});
</script>
</body>
</html>
