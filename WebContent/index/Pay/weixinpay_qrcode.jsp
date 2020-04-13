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
.qrcode {text-align: center;}
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
<fmt:formatNumber var="ff" value="${order_fee}" pattern="0.##" />
<div class="payTitle">
  <h1>${ff} 元</h1>
  <h2 id="h_title" style="color: #e84c3d;">请确认金额后，长按下方二维码支付</h2>
</div>
  <div class="qrcode">
  	<img src="${ctx}/QrCodeForWeixin.jpg?code_url=${code_url}" width="250"/>
  </div>
<c:url var="urlnext" value="/m/MMyOrder.do?par_id=1100500000"/>
<script src="${ctx}/scripts/jquery.timers.js" type="text/javascript" ></script>
<script type="text/javascript">//<![CDATA[

$(document).ready(function(){
	$(document).everyTime(5000, "waitIsPay", function(i) {// 5秒，检测一次
		$.post("${ctx}/CsAjax.do?method=waitIsPay",{out_trade_no:"${out_trade_no}",order_type:"${order_type}"},function(data){
			if (data.ret == "0") {
				mui.toast(data.msg);
				$(document).stopTime("waitIsPay");
			} else if (data.ret == "1") {
				mui.toast(data.msg, 'success');
				window.setTimeout(function () { 
					window.location.href="${urlnext}";
				}, 1500);
			}
		});
		if(i>100)$(document).stopTime("waitIsPay");//TODO 待会改
	});
    
	
});                                         
                                          
//]]></script>
</body>
</html>
