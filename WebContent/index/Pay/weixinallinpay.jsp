<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="Description" content="${app_name}" />
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="../../m/_public_in_head.jsp" flush="true" />
<style>
.fee { text-align: center;font-size: 4em;color: #333; line-height: 1em;font-family: Helvetica; height: 55px;margin-bottom: 14px;}
.fee:after {content: "元";font-size: 0.6rem;position: relative;top: -3px;left: 2px;}
.payTitle {min-height: 90px;margin: 0 auto;padding: 0;position: relative;overflow: hidden;text-align: center;}
.payTitle h1 {margin-top: 33px;font-size: 2em;font-weight: 700;color: #e84c3d;margin: 23px 0 10px;line-height: 1em;}
@media (min-width: 320px) {.payTitle h1 {font-size: 1.5em;}}
@media  (min-width: 340px) {.payTitle h1 {font-size: 2em;}}
.payTitle h2 {font-size: .8em;color: #666;font-weight: normal;}
</style>
</head>
<body>
<jsp:include page="../../m/_header.jsp" flush="true" />
<div class="payTitle">
  <h1>微信支付</h1>
  <h2 id="h_title">请确认金额后，在进行支付</h2>
</div>
<div class="new-ct bind">
  <fmt:formatNumber var="ff" value="${order_fee}" pattern="0.##" />
  <div class="fee">${ff}</div>
    <div class="box submit-btn">
      <input type="button" class="com-btn" id="bs" value="确认支付" onclick="pay();return false;"/>
    </div>
</div>
<c:url var="urlnext" value="/m/MMyHome.do"/>
<c:if test="${order_type eq 90 }">
<c:url var="urlnext" value="/m/MActivity.do?method=paySuccess"/>
</c:if>

<script type="text/javascript" charset="UTF-8" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">//<![CDATA[
function pay(){
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appid}',
      	timestamp: '${timestamp}',
      	nonceStr: '${nonceStr}',
      	signature: '${signature}',
	    jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function(){
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.chooseWXPay({
		    timestamp: '${payJsRequest.timeStamp}', // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		    nonceStr: '${payJsRequest.nonceStr}', // 支付签名随机串，不长于 32 位
		    package: "${payJsRequest.package}", // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: '${payJsRequest.signType}', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		    paySign: '${payJsRequest.paySign}', // 支付签名
		    success: function(res) {  
                if (res.errMsg == "chooseWXPay:ok") {  
                	mui.toast("支付成功");
    				setTimeout(function(){location.href="${urlnext}";}, 2000);
                } else {  
                    alert(res.errMsg);  
                }  
            }
		});
	});
}

//]]></script>
</body>
</html>
