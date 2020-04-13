<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="Description" content="${app_name}" />
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="../../m/_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/login/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div data-role="page" data-title="${app_name}"  data-theme="${page_theme}">
<div align="center" data-role="content" style="word-wrap: break-word;">
<div class="content-wrapper">
      <div class="tip_public">
        <p> <i style="color: #e84c3d;" class="fa fa-smile-o fa-5x"></i> </p>
        <p>来吧,给${app_name}贡献1分钱！</p>
        <c:if test="${not empty code_url}">
        <p><img src="${ctx}/QrCodeForWeixin.jpg?code_url=${code_url}"></p>
        </c:if>
      </div>
    </div>
<%-- 	<div>${json}</div> --%>
</div>
	
<script type="text/javascript">

var x_json = ${json};

//调用微信JS api 支付
function jsApiCall() {
	var ua = navigator.userAgent.toLowerCase();
	var wechatInfo = ua.match(/microMessenger\/([\d\.]+)/i) ;
	if(!wechatInfo) {
	    tip("请在微信中支付", "error");
	    return false;
	} else if (wechatInfo[1] < "5.0") {
	    tip("请在微信5.0以上版本中操作", "error");
	    return false;
	}
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest',
		x_json,
		function(res){
			if(res.err_msg == 'get_brand_wcpay_request:ok'){
				//支付成功，可以做跳转到支付成功的提示页面
				tip("支付成功", "check");
			}else{
				//支付失败
				//alert(res.err_msg);
			}
		}
	);
}

function callpay() {
	if (typeof WeixinJSBridge == "undefined"){
	    if( document.addEventListener ){
	        document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
	    }else if (document.attachEvent){
	        document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
	        document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
	    }
	}else{
	    jsApiCall();
	}
}
</script>
</div>
</body>
</html>
